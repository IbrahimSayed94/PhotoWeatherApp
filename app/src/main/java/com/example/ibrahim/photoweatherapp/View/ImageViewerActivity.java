package com.example.ibrahim.photoweatherapp.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.ibrahim.photoweatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewerActivity extends AppCompatActivity {


    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    private static String photopath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        ButterKnife.bind(this);

        showImage();
    } // function of onCreate

    private void showImage(){
        Bitmap photobitmap = BitmapFactory.decodeFile(photopath);
        ivPhoto.setImageBitmap(photobitmap);
    }

    public static void start(Context context, String photo) {
        photopath=photo;
        Intent starter = new Intent(context, ImageViewerActivity.class);
        context.startActivity(starter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
} // class of ImageViewerActivity
