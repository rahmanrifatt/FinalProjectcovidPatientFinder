package com.example.final_project_covid_patient_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HealthStatusmodarate extends AppCompatActivity {
    private ListView listview;
    DatabaseReference databaseReference;
    private List<User> userList;
    private CustomAdapter customAdapter;
    String dt="Moderate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_statusmodarate);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        userList= new ArrayList<>();
        customAdapter=new CustomAdapter(HealthStatusmodarate.this,userList);
        listview=findViewById(R.id.listviewid1);

        this.setTitle("Moderate health people list");
    }
    @Override
    protected void onStart(){
        Query query=databaseReference.orderByChild("healthStatus").equalTo(dt);


        query.addValueEventListener(new ValueEventListener() {
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