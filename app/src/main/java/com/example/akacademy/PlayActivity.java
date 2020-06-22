package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.akacademy.Adapter.CourseListAdapter;
import com.example.akacademy.Model.MyCourse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private WebView videoPlay;
    private ListView courseList;
    ArrayList<MyCourse> list;
    DatabaseReference myData;
    private String courseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        String url = getIntent().getStringExtra("url");
        courseid = getIntent().getStringExtra("courseid");
        videoPlay = findViewById(R.id.web_view);

        videoPlay.getSettings().setJavaScriptEnabled(true);
        videoPlay.getSettings().setAppCacheEnabled(true);
        videoPlay.getSettings().setDomStorageEnabled(true);

        //String venkat="<iframe src="+url+" width=\"WIDTH\" height=\"HEIGHT\" frameborder=\"0\" webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>";
        //String venkat="<iframe src=\"https://player.vimeo.com/video/346520178\" width=\"640\" height=\"360\" frameborder=\"0\" allow=\"autoplay; fullscreen\" allowfullscreen></iframe>";
        //videoPlay.loadData(url,"text/html","UTF-8");
        videoPlay.loadUrl(url);

        myData=FirebaseDatabase.getInstance().getReference("course");



        //list = new ArrayList<MyCourse>();
        //getFirebaseData();
       /* list.add(new MyCourse("inner and outer sphere","https://player.vimeo.com/video/346520178"));
        list.add(new MyCourse("stereotype","https://player.vimeo.com/video/346520178"));
        list.add(new MyCourse("complexity","https://player.vimeo.com/video/346520178"));
    */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
           // CourseListAdapter adapter = new CourseListAdapter(this,list);
            //courseList.setAdapter(adapter);
            courseList=findViewById(R.id.course_list);

            getFirebaseData();


            // active list item
            courseList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            courseList.setSelector(android.R.color.holo_blue_light);


            courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //startActivity(new Intent(CorurselistActivity.this,PlayActivity.class).putExtra("url",list.get(position).getUrl()));
                    //Toast.makeText(CorurselistActivity.this, list.get(position).getName(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PlayActivity.this, "hello!!", Toast.LENGTH_SHORT).show();
                    videoPlay.loadUrl(list.get(position).getUrl());
                }
            });
        }


    }
    private void getFirebaseData() {
        //dialog.show();
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.hasChild(courseid)){
                    list = new ArrayList<MyCourse>();
                    for(DataSnapshot groupSnapshot: dataSnapshot.child(courseid).child("courseDetails").getChildren()){
                        MyCourse course = groupSnapshot.getValue(MyCourse.class);
                        // Log.i("pp",course.getName());
                        list.add(course);

                    }


                    CourseListAdapter adapter = new CourseListAdapter(PlayActivity.this, (ArrayList<MyCourse>) list);
                    courseList.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
