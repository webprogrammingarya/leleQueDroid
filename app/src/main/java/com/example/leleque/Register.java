package com.example.leleque;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Register extends AppCompatActivity {

    Button register;
    EditText inputUsername, inputPassword;
    TextView link_keHalamanLogin;
    private static final String REGISTER_ENDPOINT = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/postNewUsers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.btn_register);
        link_keHalamanLogin = findViewById(R.id.link_keHalamanLogin);

        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform registration
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    new RegisterUserTask().execute(username, password);
                } else {
                    Toast.makeText(Register.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Pindah ke halaman login
        link_keHalamanLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });
    }

    private class RegisterUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            try {
                // Modify the URL to include query parameters
                String registerUrl = REGISTER_ENDPOINT + "?username=" + username + "&password=" + password;
                URL url = new URL(registerUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create JSON request body
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", username);
                requestBody.put("password", password);

                // Write JSON to the connection output stream
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestBody.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Read the response
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.next());
                }
                scanner.close();

                return response.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                // Handle the registration response here
                // You may want to parse the response JSON and take appropriate actions
                Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
