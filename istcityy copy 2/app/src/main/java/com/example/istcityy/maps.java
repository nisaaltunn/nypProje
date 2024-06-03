package com.example.istcityy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class maps extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    private String placeLocation;
    private String placeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Konum bilgisini intent'ten al
        placeLocation = getIntent().getStringExtra("placeLocation");
        placeName = getIntent().getStringExtra("placeName");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        if (placeLocation != null && !placeLocation.isEmpty()) {
            // Konumu ayrıştırın
            String[] locationParts = placeLocation.split(",");
            double latitude = Double.parseDouble(locationParts[0]);
            double longitude = Double.parseDouble(locationParts[1]);
            LatLng location = new LatLng(latitude, longitude);

            // Haritada bir işaretçi (marker) ekleyin
            gMap.addMarker(new MarkerOptions().position(location).title(placeName));

            // Haritayı işaretlenen konuma hareket ettirin
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
        }
    }
}
