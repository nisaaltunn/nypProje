package com.example.istcityy;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class eczane extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ListView listView;
    private List<String> hospitalNames;
    private List<String> hospitalLocations;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eczane);

        // Firebase Realtime Database referansını al
        databaseReference = FirebaseDatabase.getInstance().getReference().child("eczane");

        // ListView'i layouttan al
        listView = findViewById(R.id.eczanelist);

        // Liste için boş bir ArrayList oluştur
        hospitalNames = new ArrayList<>();
        hospitalLocations = new ArrayList<>();

        // ValueEventListener oluştur ve privatehospital altındaki alt düğümleri dinle
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Liste temizle
                hospitalNames.clear();
                hospitalLocations.clear();
                // Her bir alt düğüm için döngü
                for (DataSnapshot hospitalSnapshot : dataSnapshot.getChildren()) {
                    // Alt düğümün altındaki "name" ve "location" değerlerini al
                    String hospitalName = hospitalSnapshot.child("name").getValue(String.class);
                    String hospitalLocation = hospitalSnapshot.child("location").getValue(String.class);
                    // Listeye alınan değerleri ekle
                    hospitalNames.add(hospitalName);
                    hospitalLocations.add(hospitalLocation);
                }
                // ArrayAdapter oluştur ve ListView'e bağla
                adapter = new ArrayAdapter<>(eczane.this, android.R.layout.simple_list_item_1, hospitalNames);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Hata durumunda logla
                Log.w(TAG, "loadHospitals:onCancelled", databaseError.toException());
            }
        });

        // ListView öğesine tıklama olayını ekle
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedHospitalName = hospitalNames.get(position);
                String selectedHospitalLocation = hospitalLocations.get(position);

                // maps aktivitesine geçiş için intent oluştur
                Intent intent = new Intent(eczane.this, maps.class);
                // Seçilen hastanenin adını ve konumunu maps aktivitesine aktar
                intent.putExtra("placeName", selectedHospitalName);
                intent.putExtra("placeLocation", selectedHospitalLocation);

                // maps aktivitesini başlat
                startActivity(intent);
            }
        });

        // SearchView'i layouttan al
        SearchView searchView = findViewById(R.id.searcheczane);

        // SearchView'de metin değiştiğinde tetiklenecek olan listener'ı ekle
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Arama yapılınca tetiklenecek olan kısmı buraya yazabilirsiniz, ancak bu örnekte kullanmıyoruz
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Arama metni değiştiğinde tetiklenen kısım
                adapter.getFilter().filter(newText); // Filtreleme işlemi için adapter'a yeni metni iletilir
                return false;
            }
        });

        // Geri butonu için olay dinleyici
        ImageView backButton = findViewById(R.id.imageView14);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
