package com.here.android.example.sb_locationpermissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

    }

    private void checkPermissions(){
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission( this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale( this, permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }  else  {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions( this, new String[]{permission}, 2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app -defined int constant. The callback method gets the
                // result of the request.
            }
        } else  {
            // Permission has already been granted
        }
    }

    private void enableLocation()  {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                useLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListener);
        } catch (SecurityException e){
            Log.d( "MainActivity", e.toString());
        }
    }

    private void useLocation(Location location) {
        Log.d( "MainActivity", "You are here: " + location.toString());

        Context context = getApplicationContext();
        CharSequence text = "You moved here: " + location.toString();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
                case 2: {
                     // If request is cancelled, the result arrays are empty.
                    if (grantResults.length >  0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // permission was granted, yay! Do the
                            // contacts -related task you need to do.
                        enableLocation();
                    } else {
                            // permission denied, boo! Disable the
                           // functionality that depends on this permission.
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request.
        }
    }
}
