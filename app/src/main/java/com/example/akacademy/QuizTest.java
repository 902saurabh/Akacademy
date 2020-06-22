package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akacademy.Adapter.CourseListAdapter;
import com.example.akacademy.Adapter.QuizListAdapter;
import com.example.akacademy.Model.MyCourse;
import com.example.akacademy.Model.MyPdf;
import com.example.akacademy.Model.MyTest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizTest extends AppCompatActivity {

    private TextView heading;
    private ListView question_list;
    private DatabaseReference myQuestion;
    private String testID;
    private ArrayList<MyTest>list;
    private int score;
    private final int totalScore=4;
    private List<String> response;

    private Button submit;
    QuizListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_test);

        heading = findViewById(R.id.text_view);
        question_list = findViewById(R.id.question_list);
        myQuestion = FirebaseDatabase.getInstance().getReference("test");
        testID = getIntent().getStringExtra("testid");
        submit = findViewById(R.id.submit_button);


        getFirebaseData();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean temp = true;
                for(int i=0;i<question_list.getCount();i++){
                    String ans = adapter.answers.get(i);
                    if(ans.equals("Not Attempted")){
                        temp=false;

                    }
                }

                if(temp){

                    response = new ArrayList<>();
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getAnswer().equals(adapter.answers.get(i))){
                            score++;

                        }
                        response.add(adapter.answers.get(i));
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizTest.this);

                    builder.setMessage("Your Score: "+Integer.toString(score)+"/"+Integer.toString(list.size()));

                    builder.setPositiveButton("Re-attempt", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(QuizTest.this,QuizTest.class).putExtra("testid",testID));
                        }
                    });

                    builder.setNegativeButton("Show-Answer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           QuizListAdapter adapter2 = new QuizListAdapter(QuizTest.this,list,false, (ArrayList<String>) response);
                            question_list.setAdapter(adapter2);
                          //  startActivity(new Intent(QuizTest.this,ShowAnswer.class).putExtra("testid",testID).putStringArrayListExtra("response", (ArrayList<String>) response));
                        }
                    });

                    builder.show();
                    score = 0;

                }else{
                    Toast.makeText(QuizTest.this, "Please attempt all the questions", Toast.LENGTH_SHORT).show();
                }



            }
        });

        /*question_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // startActivity(new Intent(QuizTest.this,PlayActivity.class).putExtra("url",list.get(position).getUrl()).putExtra("courseid",courseid));
                Toast.makeText(QuizTest.this, list.get(position).getAnswer(), Toast.LENGTH_SHORT).show();
                //onRadioButtonClicked(list.get(position).getAnswer());
                final String ans = list.get(position).getAnswer();
                radioGroup = findViewById(R.id.option);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId) {
                            case R.id.option1:
                                    if (ans.equals("a") && flag){
                                      score++;
                                    }
                                    break;
                            case R.id.option2:
                                if (ans.equals("b")){
                                    score++;
                                }
                                    break;
                            case R.id.option3:
                                if (ans.equals("c")){
                                    score++;
                                }
                                score++;
                                    break;
                            case R.id.option4:
                                if (ans.equals("d")){
                                    score++;
                                }
                                 break;

                        }
                });
            }
        });*/

    }

  /*  private void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
       switch(view.getId()) {
            case R.id.option1:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.option2:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.option3:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.option4:
                if (checked)

                    break;
        }
    }*/

    private void getFirebaseData() {

        myQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.hasChild(testID)){
                    list = new ArrayList<MyTest>();
                    for(DataSnapshot groupSnapshot: dataSnapshot.child(testID).child("questionDetails").getChildren()){

                        MyTest question = groupSnapshot.getValue(MyTest.class);
                        // Log.i("pp",course.getName());

                       // Toast.makeText(QuizTest.this, question.getImage(), Toast.LENGTH_SHORT).show();
                        list.add(question);

                    }
                    adapter = new QuizListAdapter(QuizTest.this,list,true);
                    question_list.setAdapter((ListAdapter) adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
