package com.example.istcityy;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class restoranList extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private List<String> placeList;
    private List<String> placeList1;
    private List<String> placeImageNameList;
    private List<String> placeLocationlist;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_list);

        listView = findViewById(R.id.restoranlist);
        searchView = findViewById(R.id.searchrestoran);

        // Verileri tutacak bir liste oluştur
        placeList = new ArrayList<>();
        placeList1 = new ArrayList<>();
        placeImageNameList = new ArrayList<>();
        placeLocationlist = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placeList);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("restoran");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                placeList.clear();
                placeList1.clear();
                placeImageNameList.clear();
                placeLocationlist.clear();

                for (DataSnapshot placeSnapshot : dataSnapshot.getChildren()) {
                    String placeName = placeSnapshot.child("name").getValue(String.class);
                    String placeComment = placeSnapshot.child("comment").getValue(String.class);
                    String placeImageName = placeSnapshot.child("images").getValue(String.class);
                    String placeLocation1 = placeSnapshot.child("location").getValue(String.class);

                    placeList1.add(placeName + "\n" + placeComment);
                    placeList.add(placeName);
                    placeImageNameList.add(placeImageName);
                    placeLocationlist.add(placeLocation1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(ContentValues.TAG, "loadPlaces:onCancelled", databaseError.toException());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlaceName = placeList1.get(position);
                String selectedPlaceImage = placeImageNameList.get(position);
                String selectedPlaceLocation = placeLocationlist.get(position);

                Intent intent = new Intent(restoranList.this, place.class);
                intent.putExtra("placeData", selectedPlaceName);
                intent.putExtra("placeImageName", selectedPlaceImage);
                intent.putExtra("placeLocation", selectedPlaceLocation);

                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        ImageView backButton = findViewById(R.id.imageView14);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}




