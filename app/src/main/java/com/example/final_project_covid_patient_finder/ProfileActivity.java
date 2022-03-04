package com.example.final_project_covid_patient_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class ProfileActivity extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference reference;
    String userId;
    EditText Nameedittxt;



    Button logoutbtn;
    Button golocationbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        golocationbtn=findViewById(R.id.getlocationbtn);
        this.setTitle("Profile");





        logoutbtn=findViewById(R.id.logputbtnid1_profileactivity);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });

        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

        final TextView GreetingTextView=(TextView)findViewById(R.id.greting);
        Nameedittxt=(EditText)findViewById(R.id.nameidProfileActivity);
        final EditText Nidedittxt=(EditText)findViewById(R.id.NIDidProfileActivity);
        final EditText Mobileedittxt=(EditText)findViewById(R.id.mobileidProfileActivity);
        final EditText Birthdateedittxt=(EditText)findViewById(R.id.birthdateididProfileActivity);
        final EditText CoronaStatusedittxt=(EditText)findViewById(R.id.Corona_status_idProfileActivity);
        final EditText VaccineStatusedittxt=(EditText)findViewById(R.id.vaccinated_status_idProfileActivity);
        final EditText HealthStatusedittxt=(EditText)findViewById(R.id.Health_status_idProfileActivity);
        final EditText Emailedittxt=(EditText)findViewById(R.id.email_idProfileActivity);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                User userprofile=snapshot.getValue(User.class);
                if (userprofile!=null){
                    String email=userprofile.email;
                    String Name=userprofile.Name;
                    String Nid=userprofile.Nid;
                    String Mobile=userprofile.Mobile;
                    String Birthdate=userprofile.Birthdate;
                    String CoronaStatus=userprofile.CoronaStatus;
                    String Vaccinated_Status=userprofile.Vaccinated_Status;
                    String HealthStatus=userprofile.HealthStatus;

                    GreetingTextView.setText("Welcome "+Name+"!");
                    Nameedittxt.setText(Name);
                    Nidedittxt.setText(Nid);
                    Mobileedittxt.setText(Mobile);
                    Birthdateedittxt.setText(Birthdate);
                    CoronaStatusedittxt.setText(CoronaStatus);
                    VaccineStatusedittxt.setText(Vaccinated_Status);
                    HealthStatusedittxt.setText(HealthStatus);
                    Emailedittxt.setText(email);


                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happend", Toast.LENGTH_SHORT).show();

            }
        });


        golocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,GetlatitudeLongititudeActivity.class);
               intent.putExtra("name",Nameedittxt.getText().toString());
               intent.putExtra("CoronaStatus",CoronaStatusedittxt.getText().toString());
                startActivity(intent);
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.all_user_list_menu){
            Intent intent=new Intent(ProfileActivity.this,AllRegisterdPersonActivity.class);
            startActivity(intent);
            return true;
        }

        if(item.getItemId()==R.id.vaccinatedppl_menu){
            Intent intent=new Intent(ProfileActivity.this,VaccinatedPeople.class);
            startActivity(intent);
            return true;
        }


        if(item.getItemId()==R.id.nonvaccinatedppl_menu){
            Intent intent=new Intent(ProfileActivity.this,NonVaccinatedppl.class);
            startActivity(intent);
            return true;
        }

        if(item.getItemId()==R.id.coronanegative_menu){
            Intent intent=new Intent(ProfileActivity.this,CoronaStatusNegative.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.coronaPositive_menu){
            Intent intent=new Intent(ProfileActivity.this,CoronaStatusPositive.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.health_status_good_menu){
            Intent intent=new Intent(ProfileActivity.this,HealthStatusGood.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.health_status_modarate_menu){
            Intent intent=new Intent(ProfileActivity.this,HealthStatusmodarate.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.health_status_week_menu){
            Intent intent=new Intent(ProfileActivity.this,HealthStatusWeak.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.logout_menu){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}