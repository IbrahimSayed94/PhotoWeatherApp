package com.example.ibrahim.photoweatherapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ibrahim.photoweatherapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class PhotosHistoryAdapter extends RecyclerView.Adapter<PhotosHistoryAdapter.ViewHolder> {


    private ArrayList<String> photoFiles;
    private Context context;
    private View view;
    private CustomItemClickListener listener;

    public PhotosHistoryAdapter(Context context, ArrayList<String> photoFiles, CustomItemClickListener listener) {
        this.context = context;
        this.photoFiles = photoFiles;
        this.listener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View row) {
                listener.onItemClick(view, holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Bitmap photobitmap = BitmapFactory.decodeFile(photoFiles.get(position));
        holder.ivPhoto.setImageBitmap(photobitmap);
    }

    @Override
    public int getItemCount() {
        return photoFiles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }



} // class of PhotosHistoryAdapter
