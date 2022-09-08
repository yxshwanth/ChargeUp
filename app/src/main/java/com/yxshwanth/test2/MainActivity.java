package com.yxshwanth.test2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SupportMapFragment smf;
    FusedLocationProviderClient client;
    private GoogleMap mMap;

    LatLng ISB_Road = new LatLng(17.413978, 78.327549);
    LatLng Volltic_waverock = new LatLng(17.418164, 78.346651);
    LatLng Sohini_tech_park = new LatLng(17.420000, 78.350000);
    LatLng Fairfield = new LatLng(17.423858, 78.347334);
    LatLng Heart_cup = new LatLng(17.445357, 78.355601);
    LatLng Pepper_club_cafe = new LatLng(17.46377, 78.3088);
    LatLng Sarath_city_mall = new LatLng(17.457774, 78.36396);
    LatLng Hitech_Next_Galleria = new LatLng(17.447984, 78.384328);
    LatLng Shilparamam = new LatLng(17.4550176691812, 78.3784816085177);
    LatLng Tejaswi_motors = new LatLng(17.45345, 78.38334);


    private ArrayList<LatLng> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext((getApplicationContext()))
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        locationArrayList = new ArrayList<>();
        locationArrayList.add(ISB_Road);
        locationArrayList.add(Volltic_waverock);
        locationArrayList.add(Sohini_tech_park);
        locationArrayList.add(Fairfield);
        locationArrayList.add(Heart_cup);
        locationArrayList.add(Pepper_club_cafe);
        locationArrayList.add(Sarath_city_mall);
        locationArrayList.add(Hitech_Next_Galleria);
        locationArrayList.add(Shilparamam);
        locationArrayList.add(Tejaswi_motors);

    }

    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You")
                                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_person_pin_24));

                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                        mMap = googleMap;
                        // inside on map ready method
                        // we will be displaying all our markers.
                        // for adding markers we are running for loop and
                        // inside that we are drawing marker on our map.
                        for (int i = 0; i < locationArrayList.size(); i++) {

                            // below line is use to add marker to each location of our array list.
                            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Charging Station")
                                    .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_ev_station_24)));

                            // below lin is use to zoom our camera on map.
                            // below line is use to move our camera to the specific location.
                        }
                    }
                });

            }});

        }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        ((Drawable) vectorDrawable).setBounds( 0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap= Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
