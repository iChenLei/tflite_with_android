package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by yangjingchou on 2018/4/2.
 */

public class MenuActivity extends Activity {

    private Button B_album,B_url,B_video;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        B_album = (Button)findViewById(R.id.toAlbum);
//        B_camera = (Button)findViewById(R.id.toCamera);
        B_url = (Button)findViewById(R.id.toURL);
        B_video = (Button)findViewById(R.id.toVideo);

        B_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,UrlActivity.class);
                startActivity(intent);
            }
        });

        B_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });

        B_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,AlbumActivity.class);
                startActivity(intent);
            }
        });

//        B_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MenuActivity.this,TakePhotoActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
