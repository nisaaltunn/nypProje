package com.example.istcityy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
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

public class placelist extends AppCompatActivity {

    private static final String TAG = "placelist";
    private DatabaseReference databaseReference;
    private List<String> placeList;
    private List<String> placeList1;
    private List<String> placeLocation;
    private List<String> placeImageNameList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placelist);

        // ListView'i layouttan al
        listView = findViewById(R.id.visitlist);
        searchView = findViewById(R.id.searchVisit);

        // Verileri tutacak bir liste oluştur
        placeList = new ArrayList<>();
        placeList1 = new ArrayList<>();
        placeImageNameList = new ArrayList<>();
        placeLocation = new ArrayList<>();
        // ArrayAdapter oluştur ve ListView'e bağla
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placeList);
        listView.setAdapter(adapter);

        // Firebase Realtime Database referansını al
        databaseReference = FirebaseDatabase.getInstance().getReference().child("places");

        // Veritabanındaki değişiklikleri dinle
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Liste temizle
                placeList.clear();
                placeImageNameList.clear();

                // Her bir "place" alt düğümü için döngü
                for (DataSnapshot placeSnapshot : dataSnapshot.getChildren()) {
                    // Her bir "place" alt düğümündeki veriyi al
                    String placeName = placeSnapshot.child("name").getValue(String.class);
                    String placeComment = placeSnapshot.child("comment").getValue(String.class);
                    String placeImageName = placeSnapshot.child("images").getValue(String.class);
                    String placeLocation1 = placeSnapshot.child("location").getValue(String.class);
                    // Veriyi listeye ekle
                    placeList1.add(placeName + "\n" + placeComment);
                    placeList.add(placeName);
                    placeImageNameList.add(placeImageName);
                    placeLocation.add(placeLocation1);
                }
                // Adaptera değişiklikleri bildir
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Hata durumunda logla
                Log.w(TAG, "loadPlaces:onCancelled", databaseError.toException());
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlaceName = placeList1.get(position);
                String selectedPlaceImage = placeImageNameList.get(position);
                String selectedPlaceLocation = placeLocation.get(position);

                // İkinci aktiviteye geçiş için intent oluştur
                Intent intent = new Intent(placelist.this, place.class);
                // Seçilen öğenin adını, yorumunu ve resim URL'sini ikinci aktiviteye aktar
                intent.putExtra("placeData", selectedPlaceName);
                intent.putExtra("placeImageName", selectedPlaceImage);
                intent.putExtra("placeLocation",selectedPlaceLocation);


                // İkinci aktiviteyi başlat
                startActivity(intent);
            }
        });
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
