package com.example.ibrahim.photoweatherapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class TakePhoto extends Activity {

    public static Bitmap bitmap;
    public Context context;
    public static int REQUEST_CAMERA = 0;

    public TakePhoto(Context context){
        this.context=context;
    }

    //Take Image From Camera
    public void takePhoto() {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        ((Activity)context).startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }


    //get bitmap of takePhoto
    public void onCaptureImageResult(Intent data) {
        if (data !=null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }
    }



} // class of take photo
