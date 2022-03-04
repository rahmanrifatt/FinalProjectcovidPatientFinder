package com.example.final_project_covid_patient_finder;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.final_project_covid_patient_finder.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,IBaseGpsListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LocationManager manager;
    List<Marker> mymarkerList = new ArrayList<>();
    DatabaseReference reference;
    LatLng myLocation = new LatLng(0f,0f);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        reference= FirebaseDatabase.getInstance().getReference("location");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);








    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, (LocationListener) this);



        ValueEventListener listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(Marker marker: mymarkerList){
                    marker.remove();
                }

                mymarkerList = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Double latitude = ds.child("latitude").getValue(Double.class);
                    Double longitude = ds.child("longitude").getValue(Double.class);
                    String name=ds.child("name").getValue(String.class);
                    String Corona_Sts=ds.child("coronaStatus").getValue(String.class);

                    LatLng location = new LatLng(latitude, longitude);
                    double distance = getDistanceFromLatLonInKm(myLocation,location);
                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title("marker"));

                    if (Corona_Sts.equals("Negative")) {
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                    else if (Corona_Sts.equals("Positive")){
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    }
                    marker.setTitle(String.format("Name: "+name+"  corona Status :"+Corona_Sts+ "  distance="+"%.2f",distance));
                    mymarkerList.add(marker);

                    if(distance > 0.00 && distance < 100.00 && Corona_Sts.equals("Positive")){
                        ///TODO show notification


                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                            NotificationChannel channel=new NotificationChannel("location_notification","Location notification", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager=getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }

                        NotificationCompat.Builder builder=new NotificationCompat.Builder(MapsActivity.this,"location_notification");
                        builder.setContentTitle("A Covid+ve person is near you");
                        builder.setContentText("A corona positive patient is near to you, Please keep distance.");
                        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(MapsActivity.this);
                        managerCompat.notify(1,builder.build());



                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

 */


    }

    public static double getDistanceFromLatLonInKm(LatLng latLng1,LatLng latLng2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(latLng2.latitude-latLng1.latitude);  // deg2rad below
        double dLon = deg2rad(latLng2.longitude-latLng1.longitude);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(latLng1.latitude)) * Math.cos(deg2rad(latLng2.latitude)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c * 1000; // Distance in m
        return d;
    }

    public static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }



    @Override
    public void onLocationChanged(Location location) {
        myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15f) );

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