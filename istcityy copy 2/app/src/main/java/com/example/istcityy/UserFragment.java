package com.example.istcityy;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private EditText userEmailEditText;
    private EditText userNameEditText;
    private String userEmail;
    private String userName;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SharedPreferences oluştur
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user2, container, false);

        // EditText bileşenlerine kullanıcı bilgilerini set et
        userEmailEditText = rootView.findViewById(R.id.editTextText5);
        userNameEditText = rootView.findViewById(R.id.editTextText6);

        userEmail = sharedPreferences.getString("user_email", "");
        userName = sharedPreferences.getString("user_name", "");

        userEmailEditText.setText(userEmail);
        userNameEditText.setText(userName);

        // EditText bileşenlerine TextWatcher ekleyerek değişiklikleri dinle
        userEmailEditText.addTextChangedListener(userTextWatcher);
        userNameEditText.addTextChangedListener(userTextWatcher);

        // Buton click listener
        Button updateButton = rootView.findViewById(R.id.logoutButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Kullanıcı adını bul ve EditText'e yaz
        findUsernameByEmail(userEmail);

        return rootView;
    }

    // EditText bileşenlerindeki değişiklikleri dinlemek için TextWatcher
    private TextWatcher userTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            // Herhangi bir EditText bileşeninde değişiklik yapıldığında, kullanıcı bilgilerini güncelle
            userEmail = userEmailEditText.getText().toString().trim();
            userName = userNameEditText.getText().toString().trim();
        }
    };

    private void findUsernameByEmail(String email) {
        // Veritabanı referansı oluştur
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        // E-posta adresine göre sorgu yap
        Query query = databaseRef.orderByChild("userMail").equalTo(email);

        // Sorguyu dinle
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Sorgu sonucunda veritabanından dönen veriyi işle
                if (dataSnapshot.exists()) {
                    // Eğer veri bulunduysa
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Kullanıcı adını al
                        String username = snapshot.child("userName").getValue(String.class);
                        EditText userNameEditText = getView().findViewById(R.id.editTextText4);
                        userNameEditText.setText(username);
                    }
                } else {
                    // Eğer veri bulunamadıysa
                    // İlgili kullanıcı bulunamadı mesajını gösterebilirsiniz
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Sorgu iptal edildiğinde gerçekleştirilecek işlemler
            }
        });
    }



}