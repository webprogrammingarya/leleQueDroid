package com.example.leleque;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.leleque.R;

public class Video extends AppCompatActivity {

    ImageView lihatvideo, buttonvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        lihatvideo = findViewById(R.id.imagevideo);

        // Menambahkan OnClickListener untuk membuka video di YouTube
        lihatvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL video YouTube yang ingin ditampilkan
                String youtubeVideoUrl = "https://www.youtube.com/watch?v=EQkcwrXBY0g";

                // Membuka video di aplikasi YouTube atau browser
                watchYoutubeVideo(youtubeVideoUrl);
            }
        });
    }

    private void watchYoutubeVideo(String url) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + Uri.parse(url).getQueryParameter("v")));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(appIntent);
        } catch (Exception ex) {
            // Jika aplikasi YouTube tidak terinstal, buka di browser
            startActivity(webIntent);
        }
    }
}