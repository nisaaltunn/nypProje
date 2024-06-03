package com.example.istcityy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class place extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);



        ImageView resim = findViewById(R.id.imageView16);
        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
        // ImageView'i bul
        ImageView imageView = findViewById(R.id.imageView13);

        // Intent'ten verileri al
        String placeData = getIntent().getStringExtra("placeData");
        String placeImageName = getIntent().getStringExtra("placeImageName"); // Resim dosyasının adını intent'ten al

        // Resmi setImageResource() yöntemiyle ayarla
        // R.drawable içerisine yerleştirilen kizkulesi resminin dosya adını alarak resmi imageView'a yükle
        int imageResourceId = getResources().getIdentifier(placeImageName, "drawable", getPackageName());
        imageView.setImageResource(imageResourceId);

        // Veriyi ayır
        String[] dataParts = placeData.split("\n");
        String placeName = dataParts[0]; // Yer adı
        String comment = dataParts[1];   // Yorum

        // Yer adını TextView'e yerleştir
        TextView textViewPlaceName = findViewById(R.id.textViewname);
        textViewPlaceName.setText(placeName);

        // Yorumu TextView'e yerleştir
        TextView textViewComment = findViewById(R.id.textViewcomment);
        textViewComment.setText(comment);


        Button mapButton = findViewById(R.id.harita);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Diğer sayfadan gelen konum bilgisini al
                String placeLocation = getIntent().getStringExtra("placeLocation");

                // Intent'i oluştur
                Intent intent = new Intent(place.this, maps.class);

                // Konum bilgisini intent'e ekle
                intent.putExtra("placeLocation", placeLocation);
                intent.putExtra("placeName",placeName);

                // Harita aktivitesini başlat
                startActivity(intent);
            }
        });
    }
}