package com.example.istcityy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login extends AppCompatActivity {
    private EditText email, name, password;
    private String textmail, textname, textpassword;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReferance;
    private HashMap<String, Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.singinmail);
        name = (EditText) findViewById(R.id.singinname);
        password = (EditText) findViewById(R.id.singinpassword);
        mAuth = FirebaseAuth.getInstance();
        mReferance = FirebaseDatabase.getInstance().getReference();



        TextView textView = findViewById(R.id.textView2); // textView1, tıklanabilir TextView'in id'si olsun
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tıklama olayı tetiklendiğinde hangi sayfaya gitmek istediğinizi belirtin
                Intent intent = new Intent(login.this, Singin.class);
                startActivity(intent);
            }
        });

    }

    public void singin(View v) {
        textmail = email.getText().toString();
        textname = name.getText().toString();
        textpassword = password.getText().toString();

        if (!TextUtils.isEmpty(textmail) && !TextUtils.isEmpty(textname) && !TextUtils.isEmpty(textpassword)) {
            mAuth.createUserWithEmailAndPassword(textmail, textpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mData = new HashMap<>();
                                mUser = mAuth.getCurrentUser();
                                mData.put("userName", textname);
                                mData.put("userMail", textmail);
                                mData.put("userPassword", textpassword);
                                mData.put("userId", mUser.getUid());
                                mReferance.child("users").child(mUser.getUid())
                                        .setValue(mData)
                                        .addOnCompleteListener(login.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(login.this, "kayıt basarılı", Toast.LENGTH_LONG).show();
                                                    // Yeni sayfayı açmak için Intent oluştur
                                                    Intent intent = new Intent(login.this, Singin.class);
                                                    intent.putExtra("user_email", textmail); // Emaili Intent ile gönder
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "boş kalmamalı", Toast.LENGTH_LONG).show();
        }
    }
}
