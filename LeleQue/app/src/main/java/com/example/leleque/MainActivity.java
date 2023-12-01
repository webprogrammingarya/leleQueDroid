package com.example.leleque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.leleque.halaman.Grafik;
import com.example.leleque.halaman.Keuangan;
import com.example.leleque.halaman.LeleBot;
import com.example.leleque.halaman.Profil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    LinearLayout link_keHalamanArtikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the logged-in user's username from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String loggedInUsername = preferences.getString("username", "");

        // Find the TextView to display the welcome message
        TextView welcomeMessage = findViewById(R.id.welcome_message);
        // Set the welcome message with the logged-in username
        welcomeMessage.setText("Hi " + loggedInUsername);


        link_keHalamanArtikel = findViewById(R.id.link_keHalamanArtikel);

        link_keHalamanArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Artikel.class);
                startActivity(i);
            }
        });




        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Action for home item click
                    // Misalnya, navigasi ke halaman home
                     Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                     startActivity(homeIntent);
                    return true;
                } else if (id == R.id.grafik) {
                    // Action for grafik item click
                    // Misalnya, navigasi ke halaman grafik
                     Intent grafikIntent = new Intent(MainActivity.this, Grafik.class);
                     startActivity(grafikIntent);
                    return true;
                } else if (id == R.id.keuangan) {
                    // Action for keuangan item click
                    // Misalnya, navigasi ke halaman keuangan
                     Intent keuanganIntent = new Intent(MainActivity.this, Keuangan.class);
                     startActivity(keuanganIntent);
                    return true;
                } else if (id == R.id.robot) {
                    // Action for robot item click
                    // Misalnya, navigasi ke halaman robot
                     Intent robotIntent = new Intent(MainActivity.this, LeleBot.class);
                     startActivity(robotIntent);
                    return true;
                } else if (id == R.id.profil) {
                    // Action for profil item click
                    // Misalnya, navigasi ke halaman profil
                    Intent profilIntent = new Intent(MainActivity.this, Profil.class);
                    startActivity(profilIntent);
                    return true;
                }
                return false;
            }
        });

    }
}