package com.example.ibrahim.photoweatherapp.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ibrahim.photoweatherapp.Adapter.CustomItemClickListener;
import com.example.ibrahim.photoweatherapp.Adapter.PhotosHistoryAdapter;
import com.example.ibrahim.photoweatherapp.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;

    private ArrayList<String> photoFiles = new ArrayList<>();
    private File[] listFile;
    private PhotosHistoryAdapter photosHistoryAdapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.photoshistory));

        setupRecyclerView();
        getPhotosHistory();

    } // function of onCreate

    public void getPhotosHistory() {
        File photosHistoryDirectory = new File(getFilesDir(), "PhotosHistory");

        if (photosHistoryDirectory.isDirectory()) {
            //get Photos Files from PhotosHistory Directory
            listFile = photosHistoryDirectory.listFiles();

            //get List of Files Paths
            for (int i=0; i<listFile.length; i++) {
                photoFiles.add(listFile[i].getAbsolutePath());
            }
        }
        photosHistoryAdapter.notifyDataSetChanged();
        if(photoFiles.size()==0){
            Toast.makeText(this, getString(R.string.nophotosfound), Toast.LENGTH_SHORT).show();
        }
    }

    public void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPhotos.setLayoutManager(layoutManager);
        photosHistoryAdapter = new PhotosHistoryAdapter(this, photoFiles, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Show Full Image
                ImageViewerActivity.start(HistoryActivity.this,photoFiles.get(position));
            }
        });
        rvPhotos.setAdapter(photosHistoryAdapter);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HistoryActivity.class);
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

} // class of HistoryActivity
