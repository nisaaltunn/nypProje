package com.example.istcityy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class yakinYerler extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private FusedLocationProviderClient fusedLocationClient;
    private Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yakin_yerler);

        // Intent'ten ekstra veriyi al
        Intent intent = getIntent();
        String extraData = intent.getStringExtra("extra_data");

        // Firebase veritabanı referansını al (dinamik olarak "places" yerine "extraData" kullan)
        mDatabase = FirebaseDatabase.getInstance().getReference().child(extraData);

        // Haritayı yükle
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Konum sağlayıcı istemcisini başlat
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Kullanıcıdan konum izni iste
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Mevcut konumu al
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                myLocation = location;
                // Firebase'den verileri al ve haritada göster
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot placeSnapshot : dataSnapshot.getChildren()) {
                            String name = placeSnapshot.child("name").getValue(String.class);
                            String locationString = placeSnapshot.child("location").getValue(String.class);
                            String[] latLngArray = locationString.split(", ");
                            double latitude = Double.parseDouble(latLngArray[0]);
                            double longitude = Double.parseDouble(latLngArray[1]);
                            String placeName = placeSnapshot.getKey();

                            LatLng placeLocation = new LatLng(latitude, longitude);
                            // Mesafe hesapla
                            float[] distance = new float[1];
                            Location.distanceBetween(myLocation.getLatitude(), myLocation.getLongitude(),
                                    placeLocation.latitude, placeLocation.longitude, distance);
                            // Yakın yerlerin işaretlenmesi

                            mMap.addMarker(new MarkerOptions().position(placeLocation).title(name));
                        }
                        // Kullanıcının konumunu haritada göster
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Error:", databaseError.toException());
                    }
                });
            }
        });
    }
}
