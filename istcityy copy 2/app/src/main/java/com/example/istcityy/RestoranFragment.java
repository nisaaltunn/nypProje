package com.example.istcityy;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class RestoranFragment extends Fragment {

    public RestoranFragment() {
        // Boş yapıcı metod
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_restoran, container, false);

        // ImageView'i rootView üzerinden bul
        ImageView imageView = rootView.findViewById(R.id.restoranİmages);
        Button btn =rootView.findViewById(R.id.buttonrestoran);
        Button btn2 =rootView.findViewById(R.id.button2);
        // ImageView'e tıklama olayını ekle
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // İkinci aktiviteye geçiş için intent oluştur
                Intent intent = new Intent(getActivity(), restoranList.class);

                // İkinci aktiviteyi başlat
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // İkinci aktiviteye geçiş için intent oluştur
                Intent intent = new Intent(getActivity(), restoran.class);

                // İkinci aktiviteyi başlat
                startActivity(intent);
            }
        });
        return rootView;
    }
}
