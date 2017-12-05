package com.example.bruno.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GpsTracker gps = new GpsTracker(this);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );


            }




        }loop();
    }public void loop(){

        final GpsTracker gps = new GpsTracker(this);
        final byte testByte = 0;
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                while (testByte == 0) {
                    try {
                        Thread.sleep(5000);// Waits for 1 second (1000 milliseconds)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    Context context = getApplicationContext();
                    String deviceId = Settings.Secure.getString(context.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    DatabaseReference myReflat = database.getReference(deviceId + "/location/lat");
                    DatabaseReference myReflong = database.getReference(deviceId + "/location/long");

                    myReflat.setValue(gps.getLatitude());
                    myReflong.setValue(gps.getLongitude());
                    run();
                }
            }
        };myRunnable.run();}

}
