package com.example.final_project_covid_patient_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText loginEmailEdittext,loginpassedittextid;
    TextView registrationNowtextview;
    Button loginbtn;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        this.setTitle("Log in");
        mAuth=FirebaseAuth.getInstance();





        loginEmailEdittext=findViewById(R.id.loginMailid);
        loginpassedittextid=findViewById(R.id.loginpassid);
        registrationNowtextview=findViewById(R.id.textviewcregistrationid);
        loginbtn=findViewById(R.id.loginbtnid1);


        registrationNowtextview.setOnClickListener(this);
        loginbtn.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.loginbtnid1:
                userLogin();
                break;
            case R.id.textviewcregistrationid:
                Intent intent=new Intent(getApplicationContext(),Registration_Activity.class);
                startActivity(intent);

                break;



        }



    }

    private void userLogin() {


        String email= loginEmailEdittext.getText().toString().trim();
        String password= loginpassedittextid.getText().toString().trim();

        if(email.isEmpty())
        {
            loginEmailEdittext.setError("enter an email address");
            loginEmailEdittext.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            loginEmailEdittext.setError("enter a valid email address");
            loginEmailEdittext.requestFocus();
            return;
        }
        //checking pass validity
        if(password.isEmpty())
        {
            loginpassedittextid.setError("enter a password");
            loginpassedittextid.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            loginpassedittextid.setError("enter a valid password");
            loginpassedittextid.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    finish();
                    Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"logon unsuccessfull",Toast.LENGTH_SHORT).show();


                }

            }
        });



    }
}