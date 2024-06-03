package com.example.istcityy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;

import com.example.istcityy.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




        replaceFragment(new HomeFragment());
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.home) {
                        replaceFragment(new HomeFragment());
                        return true;
                    } else if (item.getItemId() == R.id.visit) {
                        replaceFragment(new VisitFragment());
                        return true;
                    } else if (item.getItemId() == R.id.restorant) {
                        replaceFragment(new RestoranFragment());
                        return true;
                    } else if (item.getItemId() == R.id.hospital) {
                        replaceFragment(new HospitalFragment());
                        return true;
                    }
                    else if (item.getItemId() == R.id.user) {
                        replaceFragment(new UserFragment());
                        return true;
                    }


                    else {
                        return false;
                    }

                });
            }

            private void replaceFragment(Fragment fragment) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
            }
        }


