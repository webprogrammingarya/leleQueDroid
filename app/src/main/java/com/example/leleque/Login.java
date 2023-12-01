package com.example.leleque;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {

    Button login;
    TextView daftar;
    EditText inputUsername, inputPassword;
    private static final String LOGIN_ENDPOINT = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/getPenggunaByUsernamePassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btn_login);
        daftar = findViewById(R.id.buat_akun);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    loginUser(username, password);
                } else {
                    Toast.makeText(Login.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(final String username, final String password) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String loginURL = LOGIN_ENDPOINT + "?username=" + username + "&password=" + password;
                    URL url = new URL(loginURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    connection.disconnect();

                    handleLoginResponse(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this, "Error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void handleLoginResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            // Check if the JSON array contains at least one user object
            if (jsonArray.length() > 0) {
                JSONObject userObject = jsonArray.getJSONObject(0);

                // Extract user details
                String userId = userObject.getString("_id");
                String username = userObject.getString("username");
                String password = userObject.getString("password");
                // Extract other user details as needed (nama, nomorHP, email, etc.)

                // Check if the entered username and password match the retrieved data
                String enteredUsername = inputUsername.getText().toString();
                String enteredPassword = inputPassword.getText().toString();

                if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
                    // Login successful, perform the appropriate action
                    // Save the logged-in user's username to SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userId", userId);
                    editor.putString("username", username);
                    editor.putString("password", password);

                    editor.apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Start a new activity or perform actions after successful login
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    });
                    return; // Exit the method as login is successful
                }

            }

            // If no matching user found or login failed, show an error message
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Login.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
