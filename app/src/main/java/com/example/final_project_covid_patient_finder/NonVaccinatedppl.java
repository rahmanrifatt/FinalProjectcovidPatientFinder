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

public class NonVaccinatedppl extends AppCompatActivity {
    private ListView listview;
    DatabaseReference databaseReference;
    private List<User> userList;
    private CustomAdapter customAdapter;
    String dt="NO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_vaccinatedppl);
        this.setTitle("Non-vaccinated people list");
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        userList= new ArrayList<>();
        customAdapter=new CustomAdapter(NonVaccinatedppl.this,userList);
        listview=findViewById(R.id.listviewid1);
    }

    @Override
    protected void onStart(){
        Query query=databaseReference.orderByChild("vaccinated_Status").equalTo(dt);


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