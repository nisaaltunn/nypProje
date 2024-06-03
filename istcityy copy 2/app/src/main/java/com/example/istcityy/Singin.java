package com.example.istcityy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Singin extends AppCompatActivity {
    private EditText email, password;
    private String textmail, textpassword;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        editor = sharedPreferences.edit();



        TextView textView = findViewById(R.id.textView6); // textView1, tıklanabilir TextView'in id'si olsun
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tıklama olayı tetiklendiğinde hangi sayfaya gitmek istediğinizi belirtin
                Intent intent = new Intent(Singin.this, login.class);
                startActivity(intent);
            }
        });

    }

    public void login(View view) {
        textmail = email.getText().toString();
        textpassword = password.getText().toString();
        if (!TextUtils.isEmpty(textmail) && !TextUtils.isEmpty(textpassword)) {
            mAuth.signInWithEmailAndPassword(textmail, textpassword)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();
                            // Kullanıcı bilgilerini SharedPreferences'e kaydet
                            editor.putString("user_email", textmail);
                            editor.putString("user_password", textpassword);
                            editor.apply();

                            // Eğer giriş yapan kullanıcı admin ise AdminActivity'e yönlendir
                            if (textmail.equals("admin@gmail.com")) {
                                Intent intent = new Intent(Singin.this, admin.class);
                                startActivity(intent);
                            } else {
                                // Admin değilse normal kullanıcı olarak ana ekrana yönlendir
                                Intent intent = new Intent(Singin.this, MainActivity2.class);
                                startActivity(intent);
                            }

                            Toast.makeText(Singin.this, "Giriş başarılı.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Singin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Boş bırakılan alanlar var.", Toast.LENGTH_LONG).show();
        }
    }
}