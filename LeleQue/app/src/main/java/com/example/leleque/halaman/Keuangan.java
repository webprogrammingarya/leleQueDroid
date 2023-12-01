package com.example.leleque.halaman;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.leleque.MainActivity;
import com.example.leleque.R;
import com.example.leleque.pembukuanPemasukan;
import com.example.leleque.pembukuanPengeluaran;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Keuangan extends AppCompatActivity {

    private TextView kategoriTextView, jumlahTextView, keteranganTextView, tanggalTextView;
    private LinearLayout dataLinearLayout;
    private Spinner monthSpinner;

    private TextView selectedMonthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuangan);
        dataLinearLayout = findViewById(R.id.data);
        kategoriTextView = findViewById(R.id.kategori);
        jumlahTextView = findViewById(R.id.jumlah);
        keteranganTextView = findViewById(R.id.keterangan);
        tanggalTextView = findViewById(R.id.tanggal);

        getDataPembukuan(null);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.keuangan);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Action for home item click
                    // Misalnya, navigasi ke halaman home
                    Intent homeIntent = new Intent(Keuangan.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (id == R.id.grafik) {
                    // Action for grafik item click
                    // Misalnya, navigasi ke halaman grafik
                    Intent grafikIntent = new Intent(Keuangan.this, Grafik.class);
                    startActivity(grafikIntent);
                    return true;
                } else if (id == R.id.keuangan) {
                    // Action for keuangan item click
                    // Misalnya, navigasi ke halaman keuangan
                    Intent keuanganIntent = new Intent(Keuangan.this, Keuangan.class);
                    startActivity(keuanganIntent);
                    return true;
                } else if (id == R.id.robot) {
                    // Action for robot item click
                    // Misalnya, navigasi ke halaman robot
                    Intent robotIntent = new Intent(Keuangan.this, LeleBot.class);
                    startActivity(robotIntent);
                    return true;
                } else if (id == R.id.profil) {
                    // Action for profil item click
                    // Misalnya, navigasi ke halaman profil
                    Intent profilIntent = new Intent(Keuangan.this, Profil.class);
                    startActivity(profilIntent);
                    return true;
                }
                return false;
            }
        });

        monthSpinner = findViewById(R.id.monthSpinner);
        selectedMonthTextView = findViewById(R.id.selectedMonthTextView);

        // Menyiapkan data bulan untuk Spinner
        List<String> monthList = new ArrayList<>();
        monthList.add("Januari");
        monthList.add("Februari");
        monthList.add("Maret");
        // ... tambahkan bulan lainnya sesuai kebutuhan

        // Membuat adapter untuk Spinner
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthList);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Menetapkan adapter ke Spinner
        monthSpinner.setAdapter(adapters);

        // Set teks awal pada TextView
        selectedMonthTextView.setText("Pilih Bulan");

        // Menambahkan listener untuk menangani pilihan bulan
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Ketika pilihan bulan berubah, mengubah teks pada TextView
                String selectedMonth = (String) parentView.getItemAtPosition(position);
                selectedMonthTextView.setText(selectedMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Tidak ada tindakan khusus jika tidak ada yang dipilih
            }
        });

        // Ambil referensi ke spinner dari layout
        Spinner monthSpinner = findViewById(R.id.monthSpinner);

        // Data bulan
        String[] months = getResources().getStringArray(R.array.months);

        // Buat adapter untuk spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);

        // Atur tata letak untuk adapter spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Terapkan adapter ke spinner
        monthSpinner.setAdapter(adapter);
    }

    public void showFilter(View view) {
        // Membangun pop-up dialog dengan menggunakan LayoutInflater
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filter, null);
        builder.setView(dialogView);

        // Mengakses elemen-elemen di dalam layout dialog_catatan
        TextView opsi = dialogView.findViewById(R.id.opsiFilter);
        TextView kategori = dialogView.findViewById(R.id.kategoriFilter);
        TextView jumlah = dialogView.findViewById(R.id.jumlahFilter);
        TextView keterangan = dialogView.findViewById(R.id.keteranganFilter);
        TextView tanggal = dialogView.findViewById(R.id.tanggalFilter);
        ImageButton btn_close = dialogView.findViewById(R.id.btn_close);

        // Membuat objek AlertDialog dari builder
        AlertDialog alertDialog = builder.create();

        // Menambahkan listener untuk tombol close
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menutup dialog ketika tombol close diklik
                alertDialog.dismiss();
            }
        });

        // Menambahkan OnClickListener ke TextView opsi
        opsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan search box ketika TextView opsi diklik
                showSearchBox("Pencarian Opsi", alertDialog);
            }
        });

        // Menambahkan OnClickListener ke TextView kategori
        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan search box ketika TextView kategori diklik
                showSearchBox("Pencarian Kategori", alertDialog);
            }
        });

        // Menambahkan OnClickListener ke TextView jumlah
        jumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan search box ketika TextView jumlah diklik
                showSearchBox("Pencarian Jumlah", alertDialog);
            }
        });

        // Menambahkan OnClickListener ke TextView keterangan
        keterangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan search box ketika TextView keterangan diklik
                showSearchBox("Pencarian Keterangan", alertDialog);
            }
        });

        // Menambahkan OnClickListener ke TextView tanggal
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan search box ketika TextView tanggal diklik
                showSearchBox("Pencarian Tanggal", alertDialog);
            }
        });

        // Menampilkan dialog
        alertDialog.show();
    }

    private void showSearchBox(String hintText, AlertDialog alertDialog) {
        // Membuat AlertDialog untuk dialog pencarian
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pencarian");
        builder.setMessage("Masukkan kata kunci:");

        // Menginflate layout XML untuk mendapatkan EditText
        LayoutInflater inflater = getLayoutInflater();
        View searchView = inflater.inflate(R.layout.search_box, null);
        builder.setView(searchView);

        // Mendapatkan EditText dari layout
        final EditText searchEditText = searchView.findViewById(R.id.searchEditText);
        searchEditText.setHint(hintText);

        // Menetapkan tombol "Cari" pada dialog pencarian
        builder.setPositiveButton("Cari", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Mengambil teks dari EditText pencarian
                String searchText = searchEditText.getText().toString();
                // Proses pencarian sesuai dengan kebutuhan Anda
                // ...
                // Menampilkan pesan Toast dengan teks pencarian
                Toast.makeText(Keuangan.this, "Anda mencari: " + searchText, Toast.LENGTH_SHORT).show();
            }
        });

        // Menetapkan tombol "Batal" pada dialog pencarian
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Menampilkan dialog pencarian
        builder.show();
    }

    public void showCatatan(View view) {
        // Membangun pop up dialog dengan menggunakan LayoutInflater
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_catatan, null);
        builder.setView(dialogView);

        // Mengakses elemen-elemen di dalam layout dialog_catatan
        Button btn_pemasukan = dialogView.findViewById(R.id.btn_pemasukan);
        Button btn_pengeluaran = dialogView.findViewById(R.id.btn_pengeluaran);
        ImageButton btn_close = dialogView.findViewById(R.id.btn_close);

        // Menambahkan listener untuk button Pemasukan
        btn_pemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke halaman activity_main3.xml
                Intent intent = new Intent(Keuangan.this, pembukuanPemasukan.class);
                startActivity(intent);
            }
        });

        // Menambahkan listener untuk button Pengeluaran
        btn_pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Keuangan.this, pembukuanPengeluaran.class);
                startActivity(intent);
            }
        });

        // Menambahkan listener untuk tanda silang (btnClose)
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke halaman activity_main.xml
                Intent intent = new Intent(Keuangan.this, Keuangan.class);
                startActivity(intent);
            }
        });

        // Membuat dan menampilkan dialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void getDataPembukuan(View view) {
        String urlEndPoints = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/getAllDataPembukuan";
        StringRequest sr = new StringRequest(
                Request.Method.GET,
                urlEndPoints,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray pembukuan = new JSONArray(response);

                            // Clear existing views
                            dataLinearLayout.removeAllViews();

                            // Iterasi melalui setiap objek JSON
                            for (int i = 0; i < pembukuan.length(); i++) {
                                JSONObject obj = pembukuan.getJSONObject(i);

                                // Dapatkan nilai dari kunci "kategori"
                                String kategori = obj.optString("kategori", "");
                                String jumlah = obj.optString("jumlah", "");
                                String keterangan = obj.optString("keterangan", "");
                                String tanggal = obj.optString("tanggal", "");

                                // Create a new LinearLayout for each data entry
                                LinearLayout dataEntryLayout = new LinearLayout(Keuangan.this);
                                dataEntryLayout.setOrientation(LinearLayout.HORIZONTAL);
                                dataEntryLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));

                                // Create TextViews for each data field
                                TextView kategoriTextView = new TextView(Keuangan.this);
                                kategoriTextView.setText("Kategori: " + kategori);
                                kategoriTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        0,
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        1));

                                TextView jumlahTextView = new TextView(Keuangan.this);
                                jumlahTextView.setText("Jumlah: " + jumlah);
                                jumlahTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        0,
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        1));

                                TextView keteranganTextView = new TextView(Keuangan.this);
                                keteranganTextView.setText("Keterangan: " + keterangan);
                                keteranganTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        0,
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        1));

                                TextView tanggalTextView = new TextView(Keuangan.this);
                                tanggalTextView.setText("Tanggal: " + tanggal);
                                tanggalTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        0,
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        1));

                                // Create a Button for deleting data
                                Button deleteButton = new Button(Keuangan.this);
                                deleteButton.setText("Hapus");
//                                deleteButton.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        // Handle delete button click
//                                        // You can use obj or its data to identify and delete the corresponding data
//                                        String urlEndPoints = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/deletePembukuan";
//                                        StringRequest sr = new StringRequest(
//                                                Request.Method.DELETE,
//                                                urlEndPoints,
//                                                new Response.Listener<String>() {
//                                                    @Override
//                                                    public void onResponse(String response) {
//                                                        Toast.makeText(Keuangan.this, response, Toast.LENGTH_SHORT).show();
//                                                    }
//                                                },
//                                                new Response.ErrorListener() {
//                                                    @Override
//                                                    public void onErrorResponse(VolleyError error) {
//                                                        Toast.makeText(Keuangan.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                        );
//                                        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
//                                        rq.add(sr);
//
//                                    }
//                                });

                                // Add TextViews and Button to the data entry layout
                                deleteButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Mendapatkan id dokumen dari objek JSON
                                        String documentId = obj.optString("_id", "");

                                        // Handle delete button click
                                        String urlEndPoints = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/deletePembukuan";

                                        // Menyertakan id dokumen dalam URL atau sebagai parameter sesuai kebutuhan endpoint
                                        String deleteUrl = urlEndPoints + "?id=" + documentId;

                                        StringRequest sr = new StringRequest(
                                                Request.Method.DELETE,
                                                deleteUrl,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(Keuangan.this, response, Toast.LENGTH_SHORT).show();
                                                        // Refresh data setelah menghapus jika diperlukan
                                                        getDataPembukuan(view);
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(Keuangan.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                        );

                                        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
                                        rq.add(sr);

                                    }
                                });

                                dataEntryLayout.addView(kategoriTextView);
                                dataEntryLayout.addView(jumlahTextView);
                                dataEntryLayout.addView(keteranganTextView);
                                dataEntryLayout.addView(tanggalTextView);
                                dataEntryLayout.addView(deleteButton);

                                // Add the data entry layout to the main LinearLayout
                                dataLinearLayout.addView(dataEntryLayout);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Keuangan.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(sr);
    }

//    public void getDataPembukuan(View view) {
//        String urlEndPoints = "https://asia-south1.gcp.data.mongodb-api.com/app/application-leleque-emabj/endpoint/getAllDataPembukuan";
//        StringRequest sr = new StringRequest(
//                Request.Method.GET,
//                urlEndPoints,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
////                        Toast.makeText(Keuangan.this, response, Toast.LENGTH_SHORT).show();
//                        try {
//                            JSONArray pembukuan = new JSONArray(response);
//
//                            for (int i = 0; i < pembukuan.length(); i++) {
//                                JSONObject obj = pembukuan.getJSONObject(i);
//
//                                // Dapatkan nilai dari kunci "kategori"
//                                String kategori = obj.optString("kategori", "");
//
//                                // Lakukan sesuatu dengan nilai kategori, contohnya bisa mencetak atau memprosesnya
//
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Keuangan.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
//        rq.add(sr);
//    }
}