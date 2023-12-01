package com.example.leleque;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leleque.halaman.Keuangan;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class pembukuanPemasukan extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private EditText editTextJumlah2, editTextKeterangan2;
    private TextView pilihanKategori2, currentDateTextView2;
    private Button btnSimpan2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembukuan_pemasukan);

        editTextJumlah2 = findViewById(R.id.editTextJumlah2);
        editTextKeterangan2 = findViewById(R.id.editTextKeterangan2);
        pilihanKategori2 = findViewById(R.id.pilihanKategori2);
        currentDateTextView2 = findViewById(R.id.currentDateTextView2);
        btnSimpan2 = findViewById(R.id.btnSimpan2);


        TextView currentDateTextView2 = findViewById(R.id.currentDateTextView2);

        // Mendapatkan tanggal dan waktu saat ini
        Calendar calendar = Calendar.getInstance();

        // Menggunakan SimpleDateFormat untuk mengatur format tampilan tanggal
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        String currentDate = dateFormat.format(calendar.getTime());

        // Menetapkan tanggal saat ini ke TextView
        currentDateTextView2.setText(currentDate);


        btnSimpan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get data from the input fields

                String kategori = pilihanKategori2.getText().toString();
                String jumlah = editTextJumlah2.getText().toString();
                String keterangan = editTextKeterangan2.getText().toString();
                String tanggal = currentDateTextView2.getText().toString();

                new pembukuanPemasukan.CatatPemasukanTask().execute(kategori, jumlah, keterangan, tanggal);
            }
        });
    }

    private static final String PEMBUKUAN_ENDPOINT = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/postPembukuan";

    private class CatatPemasukanTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String kategori = params[0];
            String jumlah = params[1];
            String keterangan = params[2];
            String tanggal = params[3];

            try {
                // Modify the URL to include query parameters
                String pembukuanUrl = PEMBUKUAN_ENDPOINT + "?kategori=" + kategori + "&jumlah=" + jumlah + "&keterangan=" + keterangan + "&tanggal=" + tanggal;
                URL url = new URL(pembukuanUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create JSON request body
                JSONObject requestBody = new JSONObject();

                requestBody.put("kategori", kategori);
                requestBody.put("jumlah", jumlah);
                requestBody.put("keterangan", keterangan);
                requestBody.put("tanggal", tanggal);



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
                Toast.makeText(pembukuanPemasukan.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(pembukuanPemasukan.this, Keuangan.class);
                startActivity(intent);
            } else {
                Toast.makeText(pembukuanPemasukan.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showPopup2(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu2);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView pilihanKategori2 = findViewById(R.id.pilihanKategori2);

        switch (item.getItemId()) {
            case R.id.item13:
                pilihanKategori2.setText("Penjualan Benih Lele");
                return true;
            case R.id.item14:
                pilihanKategori2.setText("Hasil Penjualan Ikan");
                return true;
            case R.id.item15:
                pilihanKategori2.setText("Pemasukan dari Layanan Jasa");
                return true;
            case R.id.item16:
                pilihanKategori2.setText("Pemasukan dari Produk Sampingan");
                return true;
            case R.id.item17:
                pilihanKategori2.setText("Pemasukan dari Kerjasama Bisnis");
                return true;
            case R.id.item18:
                pilihanKategori2.setText("Pemasukan dari Investasi");
                return true;
            case R.id.item19:
                pilihanKategori2.setText("Pemasukan Lainnya");
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}