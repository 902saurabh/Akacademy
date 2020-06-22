package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.widget.Toast;

import com.example.akacademy.Model.MyPdf;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfReader extends AppCompatActivity {

    PDFView pdfView;
    DatabaseReference myData;
    private String pdfid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);
        pdfid = getIntent().getStringExtra("pdfid");
        pdfView = findViewById(R.id.pdfView);
        myData = FirebaseDatabase.getInstance().getReference("Pdfs");
        getFirebaseData();
    }

    private void getFirebaseData() {
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(pdfid)){
                   // MyPdf pdf=new MyPdf();
                    DataSnapshot groupSnapshot= (DataSnapshot) dataSnapshot.child(pdfid).child("pdfDetails").child("pdf1");
                       MyPdf  pdf = groupSnapshot.getValue(MyPdf.class);
                        Toast.makeText(PdfReader.this, pdf.getName(), Toast.LENGTH_SHORT).show();

                        new RetrievePDFStream().execute(pdf.getUrl());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class RetrievePDFStream extends AsyncTask<String,Void,InputStream>{
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;

            try{

                URL urlx=new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection) urlx.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());

                }
            }catch (IOException e){
                return null;
            }
            return inputStream;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }

}
