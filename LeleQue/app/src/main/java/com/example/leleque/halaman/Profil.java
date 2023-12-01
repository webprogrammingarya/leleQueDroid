package com.example.leleque.halaman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.leleque.Login;
import com.example.leleque.MainActivity;
import com.example.leleque.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profil extends AppCompatActivity {

    TextView usernameTextView, passwordTextView;
    Button link_keHalamanEdit;
    TextView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);



        usernameTextView = findViewById(R.id.usernameLog);
        passwordTextView = findViewById(R.id.passwordLog);
        link_keHalamanEdit = findViewById(R.id.keHalamanEdit);


        // Retrieve the logged-in user's username and password from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String loggedInUsername = preferences.getString("username", "");
        String loggedInPassword = preferences.getString("password", "");

        // Set the TextViews with the logged-in username and password
        usernameTextView.setText(loggedInUsername);
        passwordTextView.setText(loggedInPassword);


        // Di dalam metode onCreate() pada Profil.java
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profil);


        // Pindah ke halaman login
        link_keHalamanEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfilEdit.class);
                startActivity(i);
            }
        });

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Action for home item click
                    // Misalnya, navigasi ke halaman home
                    Intent homeIntent = new Intent(Profil.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (id == R.id.grafik) {
                    // Action for grafik item click
                    // Misalnya, navigasi ke halaman grafik
                    Intent grafikIntent = new Intent(Profil.this, Grafik.class);
                    startActivity(grafikIntent);
                    return true;
                } else if (id == R.id.keuangan) {
                    // Action for keuangan item click
                    // Misalnya, navigasi ke halaman keuangan
                    Intent keuanganIntent = new Intent(Profil.this, Keuangan.class);
                    startActivity(keuanganIntent);
                    return true;
                } else if (id == R.id.robot) {
                    // Action for robot item click
                    // Misalnya, navigasi ke halaman robot
                    Intent robotIntent = new Intent(Profil.this, LeleBot.class);
                    startActivity(robotIntent);
                    return true;
                } else if (id == R.id.profil) {
                    // Action for profil item click
                    // Misalnya, navigasi ke halaman profil
                    Intent profilIntent = new Intent(Profil.this, Profil.class);
                    startActivity(profilIntent);
                    return true;
                }
                return false;
            }
        });

    }
}