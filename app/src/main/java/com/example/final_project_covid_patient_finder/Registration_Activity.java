package com.example.final_project_covid_patient_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Registration_Activity extends AppCompatActivity {
    EditText namereg;
    EditText nid_num_reg;
    EditText mobile_reg;
    EditText birthdate;
    EditText signupMailid;
    EditText signuppassid;
    RadioGroup radioGroup1_for_corona_status;
    RadioGroup radioGroup2_for_vaccinated_status;
    RadioGroup radioGroup3_for_health_status;
    RadioButton radioButton1_for_corona_sts;
    RadioButton radioButton2_for_vaccinated_sts;
    RadioButton radioButton3_for_health_sts;
    EditText minutes;
    TextView countdownTimerText;
    CountDownTimer countDownTimer;
    Button signupbtn;
    FirebaseAuth mAuth;
    Button go_for_log_in_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.setTitle("Registration");
        mAuth=FirebaseAuth.getInstance();
        countdownTimerText = (TextView) findViewById(R.id.countdownText);
        minutes = (EditText) findViewById(R.id.enterMinutes);
        minutes.setText("1");

        namereg=findViewById(R.id.nameidregActivity);
        go_for_log_in_btn=findViewById(R.id.go_for_login_reg_activity);
        go_for_log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent=new Intent(Registration_Activity.this,MainActivity.class);
                  startActivity(intent);

            }
        });



        nid_num_reg=findViewById(R.id.NIDidregActivity);
        mobile_reg=findViewById(R.id.mobileidregActivity);
        signupMailid=findViewById(R.id.signupMailid_regActivity);
        signuppassid=findViewById(R.id.signuppassid_regActivity);
        signupbtn=findViewById(R.id.signupbtn_reg_activity);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (countDownTimer == null) {
                    String getMinutes = minutes.getText().toString();//Get minutes from edittexf
                    //Check validation over edittext
                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                        int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds
                        startTimer(noOfMinutes);
                    }
                }

                else
                    Toast.makeText(Registration_Activity.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty

                int select_corona=radioGroup1_for_corona_status.getCheckedRadioButtonId();
                radioButton1_for_corona_sts=findViewById(select_corona);

                int select_vaccinated=radioGroup2_for_vaccinated_status.getCheckedRadioButtonId();
                radioButton2_for_vaccinated_sts=findViewById(select_vaccinated);

                int select_hlth_sts=radioGroup3_for_health_status.getCheckedRadioButtonId();
                radioButton3_for_health_sts=findViewById(select_hlth_sts);

                registerd();

              //  Intent intent=new Intent(Registration_Activity.this,MainActivity.class);
               // startActivity(intent);
            }
        });


        radioGroup1_for_corona_status=findViewById(R.id.radiogrpidcoronaSTSTus_regActivityid);
        radioGroup2_for_vaccinated_status=findViewById(R.id.radiogrpid_vacinitade_regActivityid);
        radioGroup3_for_health_status=findViewById(R.id.radiogrp_health_statusid_regActivityid);



        birthdate=findViewById(R.id.birthdateidregActivity);
        birthdate.setInputType(InputType.TYPE_NULL);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdateDialogbar(birthdate);


            }
        });

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("My notification","My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



    }

    private void startTimer(int noOfMinutes) {

        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {
                NotificationCompat.Builder builder=new NotificationCompat.Builder(Registration_Activity.this,"My notification");
                builder.setContentTitle("Notification");
                builder.setContentText("14 days over! Re-test for COVID");
                builder.setSmallIcon(R.drawable.ic_baseline_message_24);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat=NotificationManagerCompat.from(Registration_Activity.this);
                managerCompat.notify(1,builder.build());


                countdownTimerText.setText("14 Days Over!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                // startTimer.setText(getString(R.string.start_timer));//Change button text






            }
        }.start();

    }

    private void registerd() {

        String email=signupMailid.getText().toString().trim();
        String password=signuppassid.getText().toString().trim();
        String Name=namereg.getText().toString().trim();
        String Nid=nid_num_reg.getText().toString().trim();
        String Mobile=mobile_reg.getText().toString().trim();
        String Birthdate=birthdate.getText().toString().trim();
        String CoronaStatus=radioButton1_for_corona_sts.getText().toString().trim();
        String Vaccinated_Status=radioButton2_for_vaccinated_sts.getText().toString().trim();
        String HealthStatus=radioButton3_for_health_sts.getText().toString().trim();


        if (Name.isEmpty()){
            namereg.setError("Name is required!");
            namereg.requestFocus();
            return;

        }

        if (Nid.isEmpty()){
            nid_num_reg.setError("NID is required!");
            nid_num_reg.requestFocus();
            return;

        }
        if (Mobile.isEmpty()){
            mobile_reg.setError("Mobile number is required!");
            mobile_reg.requestFocus();
            return;

        }

        if (Birthdate.isEmpty()){
            birthdate.setError("Date of birth is required!");
            birthdate.requestFocus();
            return;

        }
        if (CoronaStatus.isEmpty()){
            radioButton1_for_corona_sts.setError("corona status is required!");
            radioButton1_for_corona_sts.requestFocus();
            return;

        }
        if (Vaccinated_Status.isEmpty()){
            radioButton2_for_vaccinated_sts.setError("vaccination status is required!");
            radioButton2_for_vaccinated_sts.requestFocus();
            return;

        }
        if (HealthStatus.isEmpty()){
            radioButton3_for_health_sts.setError("Health status is required!");
            radioButton3_for_health_sts.requestFocus();
            return;

        }



        if(email.isEmpty())
        {
            signupMailid.setError("enter an email address");
            signupMailid.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signupMailid.setError("enter a valid email address");
            signupMailid.requestFocus();
            return;
        }
        //checking pass validity
        if(password.isEmpty())
        {
            signuppassid.setError("enter a password");
            signuppassid.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            signuppassid.setError("enter a valid password");
            signuppassid.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user=new User(email,password,Name,Nid,Mobile,Birthdate,CoronaStatus,Vaccinated_Status,HealthStatus);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Registration_Activity.this, "user has been registerd successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(Registration_Activity.this, "Faild to registerd,try again!!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }

                    }
                });





    }

    private void showdateDialogbar(EditText birthdate) {
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
                birthdate.setText(simpleDateFormat.format(calendar.getTime()));



            }
        };
        new DatePickerDialog(Registration_Activity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();



    }
}