package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akacademy.Adapter.CourseListAdapter;
import com.example.akacademy.Adapter.MyItemGroupAdapter;
import com.example.akacademy.Interface.IFirebaseLoadListener;
import com.example.akacademy.Model.ItemData;
import com.example.akacademy.Model.ItemGroup;
import com.example.akacademy.Model.MyCourse;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CorurselistActivity extends AppCompatActivity implements IFirebaseLoadListener {
    IFirebaseLoadListener iFirebaseLoadListener;
    private TextView heading;
    private ListView courseList;
    ArrayList<MyCourse> list;
    private DrawerLayout drawerLayout;
    private String courseid;
    private TextView username;
    private TextView email;

    DatabaseReference myData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corurselist);

        heading=findViewById(R.id.text_view);
        courseList=findViewById(R.id.course_list);

        courseid=getIntent().getStringExtra("courseid");
        myData = FirebaseDatabase.getInstance().getReference("course");
        iFirebaseLoadListener = (IFirebaseLoadListener) this;
       /* username = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            email.setText(user.getEmail());

        }*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        //
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();


        getFirebaseData();


       /* list.add(new MyCourse("inner and outer sphere","https://player.vimeo.com/video/346520178"));
        list.add(new MyCourse("stereotype","https://player.vimeo.com/video/346520178"));
        list.add(new MyCourse("complexity","https://player.vimeo.com/video/346520178"));

        CourseListAdapter adapter = new CourseListAdapter(this,list);
        courseList.setAdapter(adapter);*/

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CorurselistActivity.this,PlayActivity.class).putExtra("url",list.get(position).getUrl()).putExtra("courseid",courseid));
                Toast.makeText(CorurselistActivity.this, list.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFirebaseData() {
        //dialog.show();
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.hasChild("id1")){
                     list = new ArrayList<MyCourse>();
                    for(DataSnapshot groupSnapshot: dataSnapshot.child("id1").child("courseDetails").getChildren()){
                        MyCourse course = groupSnapshot.getValue(MyCourse.class);
                       // Log.i("pp",course.getName());
                        list.add(course);

                    }


                    CourseListAdapter adapter = new CourseListAdapter(CorurselistActivity.this, (ArrayList<MyCourse>) list);
                    courseList.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        //MyItemGroupAdapter adapter = new MyItemGroupAdapter(this,itemGroupList);
        //my_recycler_view.setAdapter(adapter);
        //dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }
}
