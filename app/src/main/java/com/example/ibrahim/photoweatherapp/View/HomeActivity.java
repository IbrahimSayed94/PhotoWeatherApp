package com.example.ibrahim.photoweatherapp.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ibrahim.photoweatherapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.home));
    } // function of onCreate
    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    //Apply Font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick({R.id.btn_add_photo, R.id.btn_photos_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_photo:
                AddPhotoActivity.start(this);
                break;
            case R.id.btn_photos_history:
                HistoryActivity.start(this);
                break;
        }
    }
} // class of HomeActivity
