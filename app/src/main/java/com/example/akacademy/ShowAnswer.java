package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.akacademy.Adapter.QuizListAdapter;
import com.example.akacademy.Model.MyTest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAnswer extends AppCompatActivity {

    private String testID;
    private List<String> response;
    private DatabaseReference myQuestion;
    private List<MyTest> list;
    private QuizListAdapter adapter;
    private ListView question_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_answer);



    }
}
