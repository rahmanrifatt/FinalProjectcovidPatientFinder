package com.example.final_project_covid_patient_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllRegisterdPersonActivity extends AppCompatActivity {

    private ListView listview;
    DatabaseReference databaseReference;
    private List<User> userList;
    private CustomAdapter customAdapter;

    private CustomAdapter customAdapter2;
    Button cnclsrcbtn;
    AutoCompleteTextView txtsearch;
    ArrayAdapter adapter;
    ArrayList listofuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_registerd_person);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        userList= new ArrayList<>();
        customAdapter=new CustomAdapter(AllRegisterdPersonActivity.this,userList);
        listview=findViewById(R.id.listviewid1);
        this.setTitle("All user list");



        cnclsrcbtn=findViewById(R.id.cnclbttnid);


        txtsearch = findViewById(R.id.textsearch);
        listofuser=new ArrayList<>();
        //adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_activated_1,listofuser);

        customAdapter2=new CustomAdapter(AllRegisterdPersonActivity.this,listofuser);

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(event);
        cnclsrcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });



    }

    private void refresh() {
        finish();
        startActivity(getIntent());
    }

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> nids = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                String nid = ds.child("nid").getValue(String.class);
                nids.add(nid);

            }
            ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nids);
            txtsearch.setAdapter(adapter1);
            txtsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String nid = txtsearch.getText().toString();
                    searchuser(nid);


                }
            });

        } else {
            Log.d("Patients_details", "no data found ");
        }


    }


    public void searchuser(String nid) {
        Query query=databaseReference.orderByChild("nid").equalTo(nid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for (DataSnapshot ds:snapshot.getChildren()){
                        //Helper helper=new Helper(ds.child("name").getValue(String.class),ds.child("nid").getValue(String.class),ds.child("phone").getValue(String.class),ds.child("radiobtn_corona_status").getValue(String.class),ds.child("key").getValue(String.class));
                        User user=ds.getValue(User.class);
                        //listofuser.add("Name:"+helper.getName()+"\n"+"NID:"+helper.getNid()+"\n"+"Phone:"+helper.getPhone()+"\n"+"Corona status:"+helper.getRadiobtn_corona_status()+"\n"+"Key:"+helper.getKey());

                        listofuser.add(user);
                        //helperList.add(helper);
                    }
                    //listview.setAdapter(adapter);
                    listview.setAdapter(customAdapter2);
                    //listview.setAdapter(customAdapter);






                }else {
                    Log.d("Patients_details", "no data found ");
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



    }




    @Override
    protected void onStart(){


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot1: datasnapshot.getChildren())
                {
                    User user=dataSnapshot1.getValue(User.class);
                    userList.add(user);
                }
                listview.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}