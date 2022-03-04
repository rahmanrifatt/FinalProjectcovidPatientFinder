package com.example.final_project_covid_patient_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetlatitudeLongititudeActivity extends AppCompatActivity implements IBaseGpsListener{


    TextView tv_location;
    private static final int PERMISSION_LOCATION = 1000;

    Button  b_button,gtm;

    FirebaseUser user;
    DatabaseReference reference;
    String userId;
    EditText name;
    EditText coronastatus;
    String extraname;
    String extracoronaSts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getlatitude_longititude);
        extraname=getIntent().getStringExtra("name").toString();
        extracoronaSts=getIntent().getStringExtra("CoronaStatus").toString();
        this.setTitle("Tracker");

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("location");
        userId=user.getUid();
        coronastatus=findViewById(R.id.coronaStatusidgetlatlng);
        coronastatus.setText(extracoronaSts);

        name=findViewById(R.id.nameidgetlatlng);
        name.setText(extraname);
        gtm=findViewById(R.id.gtm);
        gtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GetlatitudeLongititudeActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });




        tv_location = findViewById(R.id.tv_location);
        b_button = findViewById(R.id.bt_location);
        b_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);


                } else {
                    showLocation();
                }

            }


        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            } else {
                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
                finish();

            }
        }

    }



    private void showLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            tv_location.setText("loading location");
            if (ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);

        }else {
            Toast.makeText(this, "enable GPS",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        }


    }



    private String hereLocation(Location location){


        return "Lat:"+location.getLatitude()+"\nLON:"+location.getLongitude();

    }


    @Override
    public void onLocationChanged(Location location) {
        tv_location.setText(hereLocation(location));

        String Name=name.getText().toString().trim();
        String CoronaStatus=coronastatus.getText().toString().trim();
       double Latitude= location.getLatitude();
        double Longitude=location.getLongitude();
        Helper helper=new Helper(Latitude,Longitude,Name,CoronaStatus);
        reference.child(userId).setValue(helper);



    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onGpsStatusChanged(int event) {

    }
}