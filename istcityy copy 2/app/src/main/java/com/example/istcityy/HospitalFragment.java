package com.example.istcityy;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class HospitalFragment extends Fragment {

    public HospitalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hospital, container, false);

        // Butonları bul
        ImageView hastane = rootView.findViewById(R.id.hospital);
        ImageView eczane = rootView.findViewById(R.id.eczane);
        ImageView privatehospital = rootView.findViewById(R.id.imageView6);


        // Button1'e tıklama olayı ekle
        hastane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent oluştur ve hedef sayfayı belirle
                Intent intent = new Intent(getActivity(), hospital.class); // İlk sayfa için FirstActivity olarak değiştirin
                // Intent'i başlat
                startActivity(intent);
            }
        });

        // Button2'ye tıklama olayı ekle
        eczane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent oluştur ve hedef sayfayı belirle
                Intent intent = new Intent(getActivity(), eczane.class); // İkinci sayfa için SecondActivity olarak değiştirin
                // Intent'i başlat
                startActivity(intent);
            }
        });
        // Button2'ye tıklama olayı ekle
        privatehospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent oluştur ve hedef sayfayı belirle
                Intent intent = new Intent(getActivity(), privatehospital.class); // İkinci sayfa için SecondActivity olarak değiştirin
                // Intent'i başlat
                startActivity(intent);
            }
        });

        return rootView;
    }
}
