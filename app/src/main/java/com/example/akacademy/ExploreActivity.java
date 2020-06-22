package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.akacademy.Adapter.MyItemGroupAdapter;
import com.example.akacademy.Interface.IFirebaseLoadListener;
import com.example.akacademy.Model.ItemData;
import com.example.akacademy.Model.ItemGroup;
import com.example.akacademy.Model.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dmax.dialog.SpotsDialog;


public class ExploreActivity extends AppCompatActivity implements IFirebaseLoadListener {



    AlertDialog dialog;
    IFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView my_recycler_view;
    DatabaseReference myData;
    DatabaseReference pdfRef;
    DatabaseReference testRef;
    private DrawerLayout drawerLayout;

    private TextView username;
    private TextView email;
    Session session;

    //DatabaseReference myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);



        //===========navigation bar=============
        Toolbar toolbar = findViewById(R.id.toolbar);
        //
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        username = headerView.findViewById(R.id.user_name);
        email = headerView.findViewById(R.id.user_email);

        session = new Session(getApplicationContext());
        username.setText(session.getName());
        email.setText(session.getEmail());
        toggle.syncState();




        my_recycler_view = (RecyclerView) findViewById(R.id.recycle_view_course);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));




        myData = FirebaseDatabase.getInstance().getReference("courses");
        pdfRef = FirebaseDatabase.getInstance().getReference("study_materials");
        testRef = FirebaseDatabase.getInstance().getReference("test_materials");
        dialog = new SpotsDialog.Builder().setContext(this).build();
        iFirebaseLoadListener = (IFirebaseLoadListener) this;
        List<ItemGroup> itemGroups = new ArrayList<>();
        getFireBaseData(myData);



    }


    private void getFireBaseData(DatabaseReference myData){
       // dialog.show();
        final List<ItemGroup> itemGroups = new ArrayList<>();
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot groupSnapShot:dataSnapshot.getChildren()){


                    ItemGroup itemGroup = new ItemGroup();
                    itemGroup.setHeaderTitle(groupSnapShot.child("headerTitle").getValue(true).toString());

                    GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>(){};


                    itemGroup.setListItem(groupSnapShot.child("listItem").getValue(genericTypeIndicator));

                    itemGroups.add(itemGroup);


                }
                // show pdf data
                pdfRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for(DataSnapshot groupSnapShot:dataSnapshot.getChildren()){


                            ItemGroup itemGroup = new ItemGroup();
                            itemGroup.setHeaderTitle(groupSnapShot.child("headerTitle").getValue(true).toString());

                            GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>(){};


                            itemGroup.setListItem(groupSnapShot.child("listItem").getValue(genericTypeIndicator));

                            itemGroups.add(itemGroup);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());

                    }
                });
                // end of pdf data

                // show test data

                testRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for(DataSnapshot groupSnapShot:dataSnapshot.getChildren()){


                            ItemGroup itemGroup = new ItemGroup();
                            itemGroup.setHeaderTitle(groupSnapShot.child("headerTitle").getValue(true).toString());

                            GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>(){};


                            itemGroup.setListItem(groupSnapShot.child("listItem").getValue(genericTypeIndicator));

                            itemGroups.add(itemGroup);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());

                    }
                });

                //end of test data

                iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });



    }


    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        MyItemGroupAdapter adapter = new MyItemGroupAdapter(this,itemGroupList);
        my_recycler_view.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }

}
