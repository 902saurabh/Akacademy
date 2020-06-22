package com.example.akacademy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.akacademy.Model.MyCourse;
import com.example.akacademy.Model.MyTest;
import com.example.akacademy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuizListAdapter extends ArrayAdapter<MyTest> {

    private final Context context;
    private final ArrayList<MyTest> values;
    public static ArrayList<String> answers;
    private RadioGroup radioGroup;
    private boolean check;


    public QuizListAdapter(Context context,ArrayList<MyTest> list,boolean check){
        super(context, R.layout.test_question_view,list);
        this.context=context;
        this.values=list;
        this.check = check;

        answers = new ArrayList<>();
        for (int i = 0; i <list.size(); i++) {
            answers.add("Not Attempted");
        }
    }

    public QuizListAdapter(Context context,ArrayList<MyTest> list,boolean check,ArrayList<String> response){
        super(context, R.layout.test_question_view,list);
        this.context=context;
        this.values=list;
        this.check = check;
         answers = response;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.test_question_view,parent,false);

        ImageView question = rowView.findViewById(R.id.test_image);
        TextView correct = rowView.findViewById(R.id.correct_answer);
        Picasso.get().load(values.get(position).getImage()).into(question);

        radioGroup = rowView.findViewById(R.id.option);
        RadioButton optionA= rowView.findViewById(R.id.option1);
        RadioButton optionB= rowView.findViewById(R.id.option2);
        RadioButton optionC= rowView.findViewById(R.id.option3);
        RadioButton optionD= rowView.findViewById(R.id.option4);

        optionA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answers.set(position,"a");

                }
            }
        });

        optionB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answers.set(position,"b");

                }
            }
        });

        optionC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answers.set(position,"c");
                    //method(position);
                }
            }
        });

        optionD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answers.set(position,"d");
                   // method(position);
                }
            }
        });
        //TextView courseName = (TextView)rowView.findViewById(R.id.course_name);
        //courseName.setText(values.get(position).getName());

       if(!check){
           correct.setText("Correct Answer: ("+values.get(position).getAnswer()+")");
           if(answers.get(position).equals("a")){
               radioGroup.check(R.id.option1);
           }
           if(answers.get(position).equals("b")){
               radioGroup.check(R.id.option2);
           }
           if(answers.get(position).equals("c")){
               radioGroup.check(R.id.option3);
           }
           if(answers.get(position).equals("d")){
               radioGroup.check(R.id.option4);
           }

          // radioGroup.setEnabled(false);

       }
        method(position);
        return rowView;
    }

    private void method(int i) {
        for (int j=0;j<=answers.size();j++)
        {
            if (i == j) {
                String str = answers.get(i);
                if(str.equals("Not Attempted")){
                    continue;
                }else{

                    if(str.equals("a")){
                        radioGroup.check(R.id.option1);
                    }
                    if(str.equals("b")){
                        radioGroup.check(R.id.option2);
                    }
                    if(str.equals("c")){
                        radioGroup.check(R.id.option3);
                    }
                    if(str.equals("d")){
                        radioGroup.check(R.id.option4);
                    }
                }

            }
        }
    }



}
