package com.example.istcityy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private DatabaseReference databaseReference;

    public HomeFragment() {
        // Boş yapıcı metod
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        // Resimleri bul
        ImageView uskudar = rootView.findViewById(R.id.uskudarRota);
        ImageView sultanahmet = rootView.findViewById(R.id.sultanahmetRota);



        uskudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), gezirotasi.class);
                startActivity(intent);
            }
        });


        sultanahmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), gezirotasi1.class);
                startActivity(intent);
            }
        });




        return rootView;
    }
}
