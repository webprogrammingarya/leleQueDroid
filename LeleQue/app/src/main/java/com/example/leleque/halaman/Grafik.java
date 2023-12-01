package com.example.leleque.halaman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.leleque.MainActivity;
import com.example.leleque.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Grafik extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.grafik);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Action for home item click
                    // Misalnya, navigasi ke halaman home
                    Intent homeIntent = new Intent(Grafik.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (id == R.id.grafik) {
                    // Action for grafik item click
                    // Misalnya, navigasi ke halaman grafik
                    Intent grafikIntent = new Intent(Grafik.this, Grafik.class);
                    startActivity(grafikIntent);
                    return true;
                } else if (id == R.id.keuangan) {
                    // Action for keuangan item click
                    // Misalnya, navigasi ke halaman keuangan
                    Intent keuanganIntent = new Intent(Grafik.this, Keuangan.class);
                    startActivity(keuanganIntent);
                    return true;
                } else if (id == R.id.robot) {
                    // Action for robot item click
                    // Misalnya, navigasi ke halaman robot
                    Intent robotIntent = new Intent(Grafik.this, LeleBot.class);
                    startActivity(robotIntent);
                    return true;
                } else if (id == R.id.profil) {
                    // Action for profil item click
                    // Misalnya, navigasi ke halaman profil
                    Intent profilIntent = new Intent(Grafik.this, Profil.class);
                    startActivity(profilIntent);
                    return true;
                }
                return false;
            }
        });
    }
}