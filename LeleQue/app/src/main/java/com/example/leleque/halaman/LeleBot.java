package com.example.leleque.halaman;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.leleque.MainActivity;
import com.example.leleque.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import im.crisp.client.ChatActivity;
import im.crisp.client.Crisp;
public class LeleBot extends AppCompatActivity {

    Button btn_open_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lele_bot);

        btn_open_chat = findViewById(R.id.btn_open_chat);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.robot);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Action for home item click
                    // Misalnya, navigasi ke halaman home
                    Intent homeIntent = new Intent(LeleBot.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (id == R.id.grafik) {
                    // Action for grafik item click
                    // Misalnya, navigasi ke halaman grafik
                    Intent grafikIntent = new Intent(LeleBot.this, Grafik.class);
                    startActivity(grafikIntent);
                    return true;
                } else if (id == R.id.keuangan) {
                    // Action for keuangan item click
                    // Misalnya, navigasi ke halaman keuangan
                    Intent keuanganIntent = new Intent(LeleBot.this, Keuangan.class);
                    startActivity(keuanganIntent);
                    return true;
                } else if (id == R.id.robot) {
                    // Action for robot item click
                    // Misalnya, navigasi ke halaman robot
                    Intent robotIntent = new Intent(LeleBot.this, LeleBot.class);
                    startActivity(robotIntent);
                    return true;
                } else if (id == R.id.profil) {
                    // Action for profil item click
                    // Misalnya, navigasi ke halaman profil
                    Intent profilIntent = new Intent(LeleBot.this, Profil.class);
                    startActivity(profilIntent);
                    return true;
                }
                return false;
            }
        });
    }

    public void adminChat(View v) {
        Crisp.configure(getApplicationContext(), "89a782df-fd34-48d9-8cec-ffa8cbfc9545");
        Intent crispIntent = new Intent(this, ChatActivity.class);
        startActivity(crispIntent);
    }
}