package com.example.istcityy;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class VisitFragment extends Fragment {

    private static final String TAG = "VisitFragment";

    private DatabaseReference databaseReference;

    public VisitFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment'ın layoutunu şişir ve rootView'e at
        View rootView = inflater.inflate(R.layout.fragment_visit, container, false);


        // ListView'i layouttan al

        Button navigateButton = rootView.findViewById(R.id.buttonlist5);
        Button muzebutton = rootView.findViewById(R.id.buttonmuze);
        Button haritagoruntule =rootView.findViewById(R.id.buttonlist6);
        Button haritagoruntulemuze =rootView.findViewById(R.id.buttonmuze3);
        Button stadyum =rootView.findViewById(R.id.buttonmuze2);
        Button stadyumList =rootView.findViewById(R.id.buttonmuze4);
        Button dogaList =rootView.findViewById(R.id.buttonmuze7);
        Button dogaMap =rootView.findViewById(R.id.buttonmuze8);

        dogaMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), yakinYerler.class);
                intent.putExtra("extra_data", "doga");
                startActivity(intent);
            }
        });
        stadyum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), yakinYerler.class);
                intent.putExtra("extra_data", "stadyum");
                startActivity(intent);
            }
        });


        haritagoruntule.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), yakinYerler.class);
                 intent.putExtra("extra_data", "places");
                 startActivity(intent);
             }
         });

        haritagoruntulemuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), yakinYerler.class);
                intent.putExtra("extra_data", "muze");
                startActivity(intent);
            }
        });


        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Başka bir aktiviteye geçiş için intent oluştur
                Intent intent = new Intent(getActivity(), placelist.class);
                startActivity(intent);
            }
        });



        stadyumList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Başka bir aktiviteye geçiş için intent oluştur
                Intent intent = new Intent(getActivity(), StadyumList.class);
                startActivity(intent);
            }
        });



        dogaList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Başka bir aktiviteye geçiş için intent oluştur
                Intent intent = new Intent(getActivity(), dogaList.class);
                startActivity(intent);
            }
        });


        muzebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Başka bir aktiviteye geçiş için intent oluştur
                Intent intent = new Intent(getActivity(), muze.class);
                startActivity(intent);

            }
        });

        // rootView'ü döndür
        return rootView;
    }
}