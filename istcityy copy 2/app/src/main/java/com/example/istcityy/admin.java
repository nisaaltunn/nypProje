package com.example.istcityy;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin extends AppCompatActivity {

    private EditText imagesEditText, commentEditText, tabloEditText, altTabloEditText, nameEditText, locationEditText;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Firebase Realtime Database referansını al
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // EditText bileşenlerini tanımla
        imagesEditText = findViewById(R.id.images);
        commentEditText = findViewById(R.id.comment);
        tabloEditText = findViewById(R.id.tablo);
        altTabloEditText = findViewById(R.id.alttablo);
        nameEditText = findViewById(R.id.name);
        locationEditText = findViewById(R.id.location);


        ImageView resim = findViewById(R.id.uskudarRota);
        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(admin.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        // Ekleme düğmesine tıklama olayını ekle
        Button ekleButton = findViewById(R.id.buttonekle);
        ekleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ekleVeri();
            }
        });


        // Silme düğmesine tıklama olayını ekle
        Button silButton = findViewById(R.id.sil);
        silButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                silVeri();
            }
        });
    }


    private void silVeri() {
        // Silinecek düğümün ana tablo ve alt tablo adını al
        String tablo = tabloEditText.getText().toString(); // Ana tablo adını al
        String altTablo = altTabloEditText.getText().toString(); // Alt tablo adını al

        // Alt tablo adı varsa, düğüm adını alt tablo ile birleştir
        if (!altTablo.isEmpty()) {
            // Alt tablo adı belirtildiyse, alt tablonun tam adını silinecek düğüm adı olarak kullan
            String silinecekDugumAdi = tablo + "/" + altTablo;

            // Düğümü veritabanından sil
            databaseReference.child(silinecekDugumAdi).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Silme başarılı olduğunda bir bildirim veya geri bildirim sağlayabilirsiniz.
                        Toast.makeText(admin.this, "Alt tablo başarıyla silindi", Toast.LENGTH_SHORT).show();
                    } else {
                        // Silme işlemi başarısız olduğunda bir hata mesajı gösterebilirsiniz.
                        Toast.makeText(admin.this, "Alt tablo silinemedi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // Alt tablo belirtilmediyse, kullanıcıya bir hata mesajı göster
            Toast.makeText(admin.this, "Alt tablo belirtilmedi", Toast.LENGTH_SHORT).show();
        }
    }


    private void ekleVeri() {
        // EditText bileşenlerinden verileri al
        String images = imagesEditText.getText().toString();
        String comment = commentEditText.getText().toString();
        String tablo = tabloEditText.getText().toString(); // Ana tablo adını al
        String altTablo = altTabloEditText.getText().toString(); // Alt tablo adını al
        String name = nameEditText.getText().toString();
        String location = locationEditText.getText().toString();

        // Veritabanına yeni bir giriş eklemek için ana tablo adını belirle
        String anaTabloAdi = tablo;

        // Ana tablo adı varsa alt tablo adını ekleyerek tam düğüm adını oluştur
        if (!altTablo.isEmpty()) {
            anaTabloAdi += "/" + altTablo;
        }

        // Veritabanına yeni bir giriş ekle
        databaseReference.child(anaTabloAdi).setValue(new Veri(comment, images, location, name));





    }

}
