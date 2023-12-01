package com.example.leleque.halaman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.leleque.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfilEdit extends AppCompatActivity {

    EditText etUserId, etUsername;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_edit);

        etUserId = findViewById(R.id.userId);
        etUsername = findViewById(R.id.usernameLog);
        btnEdit = findViewById(R.id.simpanEdit);

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String loggedInUserId = preferences.getString("userId", "");
        String loggedInUsername = preferences.getString("username", "");

        etUserId.setText(loggedInUserId);
        etUsername.setText(loggedInUsername);

    }

    public void putUserProfile(View view) {
        String urlEndPoints = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/putUserProfile";

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String loggedInUserId = preferences.getString("userId", "");

        StringRequest sr = new StringRequest(
                Request.Method.PUT,
                urlEndPoints,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ProfilEdit.this, "Username berhasil diubah", Toast.LENGTH_SHORT).show();
                        // Simpan username yang telah diubah ke SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", etUsername.getText().toString()); // Simpan username baru
                        editor.apply();

                        Intent i = new Intent(getApplicationContext(), Profil.class);
                        startActivity(i);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfilEdit.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", loggedInUserId);
                params.put("username", etUsername.getText().toString()); // assuming etUsername is the EditText for username
                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(sr);
    }


}
//    public void putUserProfile(View view) {
//        String urlEndPoints = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/getAllDataPengguna";
//        StringRequest sr = new StringRequest(
//                Request.Method.GET,
//                urlEndPoints,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(ProfilEdit.this, response, Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ProfilEdit.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
//        rq.add(sr);
//    }
