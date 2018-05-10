package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by yangjingchou on 2018/4/2.
 */

public class AlbumActivity extends Activity {

    private Button B_select;
    private ImageView imageView;
    private static final int REQUESTCODE_PICK = 0;
    private String path;
    private ImageClassifier classifier;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_from_album);

        B_select = (Button) findViewById(R.id.select_image);
        imageView = (ImageView) findViewById(R.id.album_image_placeholder);
        textView = (TextView)findViewById(R.id.album_class);

        B_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_image_from_album();
            }
        });

    }
    public void get_image_from_album(){
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult(intent,REQUESTCODE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("[tensorflow]",String.valueOf(requestCode));
        if(resultCode == Activity.RESULT_OK && requestCode == REQUESTCODE_PICK){
            try {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    Log.d("[tensorflow lite]---->", path);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    imageView.setImageBitmap(bitmap);
                    try{
                        classifier = new ImageClassifierQuantizedMobileNet(AlbumActivity.this);
                    }catch (IOException e){
                        textView.setText("不能初始化识别模型");
                    }
                    if(classifier != null){
                        try {
                            Bitmap bit = Bitmap.createBitmap(bitmap, bitmap.getHeight()/2, bitmap.getHeight()/2, classifier.getImageSizeX(), classifier.getImageSizeY());
                            String textToShow = classifier.classifyFrame(bit);
                            String[] strs = textToShow.split("\\n");
                            textView.setText("耗时： "+strs[0]+" 判定类别："+strs[1]);
                            Log.e("What the fuck??",textToShow);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(),"请选择一张图片",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        classifier.close();
        super.onDestroy();
    }
}
