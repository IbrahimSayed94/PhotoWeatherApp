package com.example.ibrahim.photoweatherapp.View;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Request.ApiClient;
import com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Request.ApiInterface;
import com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Response.WeatherResponse;
import com.example.ibrahim.photoweatherapp.R;
import com.example.ibrahim.photoweatherapp.Utils.CurrentLocation;
import com.example.ibrahim.photoweatherapp.Utils.TakePhoto;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class AddPhotoActivity extends AppCompatActivity {


    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.loadingimage)
    ProgressBar loadingimage;

    private CurrentLocation currentLocation;
    private TakePhoto takePhoto;
    private String currentlatitude, currentlongitude, unit;
    private String currentcondition, currenttemperature, weathertext;
    private Bitmap drawnbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.addphoto));

        //Get current location to determine the current weather
        currentLocation = new CurrentLocation(this);
        currentLocation.getCurrentLocation();

    } // function of onCreate

    //Handle result of selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == takePhoto.REQUEST_CAMERA) {
                takePhoto.onCaptureImageResult(data);
                ivPhoto.setImageBitmap(null);
                addCurrentWeatherToPhoto();
            }
        }
    }

    private void addCurrentWeatherToPhoto() {

        currentlatitude = String.valueOf(currentLocation.currentlatitude);
        currentlongitude = String.valueOf(currentLocation.currentlongitude);
        unit = "metric"; //temperature in Celsius

        //get Current Weather Request
        loadingimage.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        Call<WeatherResponse> call = apiService.getCurrentWeather(currentlatitude, currentlongitude, unit);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                loadingimage.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        WeatherResponse weatherResponse = response.body();
                        currentcondition = weatherResponse.getWeatherCondition().get(0).getCondition();
                        currenttemperature = String.valueOf(weatherResponse.getWeatherTemperature().getTemperature()) + "°C";
                        weathertext = currentcondition + " - " + currenttemperature;

                        //set bitmap to imageview
                        drawnbitmap = drawWeatherInfoOverPhoto(weathertext);
                        ivPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                        ivPhoto.setImageBitmap(drawnbitmap);
                        saveImageInExternalStorage(drawnbitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddPhotoActivity.this,
                                getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddPhotoActivity.this,
                            getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                loadingimage.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(AddPhotoActivity.this,
                        getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }

        });
    }

    //send the weather text as a parameter and return the drawn bitmap
    private Bitmap drawWeatherInfoOverPhoto(String text) {

        Paint textPaint = new Paint(ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        textPaint.setTextSize((int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_SP, 5, getResources().getDisplayMetrics())); //Convert SP to PX
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        Paint.FontMetrics metric = textPaint.getFontMetrics();
        int textHeight = (int) Math.ceil(metric.descent - metric.ascent);
        int y = (int) (textHeight - metric.descent);
        Bitmap b = takePhoto.bitmap;
        Bitmap mutableBitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(mutableBitmap);
        c.drawText(text, 0, y, textPaint);

        return mutableBitmap;
    }

    private void saveImageInExternalStorage(Bitmap photobitmap) {

        File photosHistoryDirectory = new File(getFilesDir(), "PhotosHistory");

        if (!photosHistoryDirectory.exists()) {
            photosHistoryDirectory.mkdirs();
        }

        File file = new File(photosHistoryDirectory, System.currentTimeMillis()+"_weatherphoto");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            photobitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Share Photo with All Apps
    private void sharePhoto(Bitmap bitmap) {
        try {
            File file = new File(this.getExternalCacheDir(), "sharephoto.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, getString(R.string.sharephoto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Interactions
    @OnClick({R.id.iv_photo, R.id.btn_sharephoto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo:
                takePhoto = new TakePhoto(this);
                takePhoto.takePhoto();
                break;
            case R.id.btn_sharephoto:
                if(drawnbitmap!=null) {
                    sharePhoto(drawnbitmap);
                }else {
                    Toast.makeText(this, getString(R.string.takephotofirst), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AddPhotoActivity.class);
        context.startActivity(starter);
    }

    //Apply Font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Callback of Location Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                currentLocation.getCurrentLocation();
            } else {
                Toast.makeText(this, getString(R.string.allowlocationpermission), Toast.LENGTH_SHORT).show();
            }
        }
    }

} // class of AddPhotoActivity
