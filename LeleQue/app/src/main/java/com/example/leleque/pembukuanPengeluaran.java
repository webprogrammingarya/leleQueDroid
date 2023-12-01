package com.example.leleque;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.leleque.halaman.Keuangan;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class pembukuanPengeluaran extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private EditText editTextJumlah, editTextKeterangan;
    private TextView pilihanKategori, currentDateTextView;
    private Button btnSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembukuan_pengeluaran);

        editTextJumlah = findViewById(R.id.editTextJumlah);
        editTextKeterangan = findViewById(R.id.editTextKeterangan);
        pilihanKategori = findViewById(R.id.pilihanKategori);
        currentDateTextView = findViewById(R.id.currentDateTextView);
        btnSimpan = findViewById(R.id.btnSimpan);



        TextView currentDateTextView = findViewById(R.id.currentDateTextView);

        // Mendapatkan tanggal dan waktu saat ini
        Calendar calendar = Calendar.getInstance();

        // Menggunakan SimpleDateFormat untuk mengatur format tampilan tanggal
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        String currentDate = dateFormat.format(calendar.getTime());

        // Menetapkan tanggal saat ini ke TextView
        currentDateTextView.setText(currentDate);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get data from the input fields

                String kategori = pilihanKategori.getText().toString();
                String jumlah = editTextJumlah.getText().toString();
                String keterangan = editTextKeterangan.getText().toString();
                String tanggal = currentDateTextView.getText().toString();

                new CatatPengeluaranTask().execute(kategori, jumlah, keterangan, tanggal);
            }
        });
    }

    private static final String PEMBUKUAN_ENDPOINT = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/postPembukuan";

    private class CatatPengeluaranTask extends AsyncTask<String, Void, String> {

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
                Toast.makeText(pembukuanPengeluaran.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(pembukuanPengeluaran.this, Keuangan.class);
                startActivity(intent);
            } else {
                Toast.makeText(pembukuanPengeluaran.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView pilihanKategori = findViewById(R.id.pilihanKategori);

        switch (item.getItemId()) {
            case R.id.item1:
                pilihanKategori.setText("Pembelian Benih Lele");
                return true;
            case R.id.item2:
                pilihanKategori.setText("Peralatan dan Infrastruktur");
                return true;
            case R.id.item3:
                pilihanKategori.setText("Pakan dan Nutrisi");
                return true;
            case R.id.item4:
                pilihanKategori.setText("Tenaga Kerja");
                return true;
            case R.id.item5:
                pilihanKategori.setText("Pengobatan dan Kesehatan Ikan");
                return true;
            case R.id.item6:
                pilihanKategori.setText("Pengelolaan Air");
                return true;
            case R.id.item7:
                pilihanKategori.setText("Listrik dan Energi");
                return true;
            case R.id.item8:
                pilihanKategori.setText("Biaya Transportasi");
                return true;
            case R.id.item9:
                pilihanKategori.setText("Pajak dan Perizinan");
                return true;
            case R.id.item10:
                pilihanKategori.setText("Pemeliharaan dan Perawatan");
                return true;
            case R.id.item11:
                pilihanKategori.setText("Asuransi");
                return true;
            case R.id.item12:
                pilihanKategori.setText("Biaya Liannya");
                return true;
            default:
                return false;
        }
    }
}