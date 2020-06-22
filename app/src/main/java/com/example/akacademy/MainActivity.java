package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.akacademy.Model.MyCourse;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myData = FirebaseDatabase.getInstance().getReference("course");

        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               // for(DataSnapshot groupSnapShot: dataSnapshot.getChildren()){
                  if(dataSnapshot.hasChild("id1")){

                      for(DataSnapshot groupSnapshot: dataSnapshot.child("id1").child("courseDetails").getChildren()){
                          MyCourse course = groupSnapshot.getValue(MyCourse.class);
                          Log.i("pp",course.getName());

                      }

                      MyCourse course = dataSnapshot.child("id2").child("courseDetails").getValue(MyCourse.class);
                      Toast.makeText(MainActivity.this, course.getUrl(), Toast.LENGTH_SHORT).show();
                      Log.i("kkk",course.getName());
                  }
               // }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }
}
