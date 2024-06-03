package com.example.istcityy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =findViewById(R.id.button2);
        Button buton1 =findViewById(R.id.button4);
        TextView signInTextView = findViewById(R.id.textView1);
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yeni sayfayı açmak için Intent oluştur
                Intent intent = new Intent(MainActivity.this, Singin.class);
                startActivity(intent);

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yeni sayfayı açmak için Intent oluştur
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });

        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}