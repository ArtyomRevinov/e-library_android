package com.example.elibrary.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elibrary.R;
import com.example.elibrary.entity.Volume;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder> {
    private List<Volume> volumes;

     static class VolumeViewHolder extends RecyclerView.ViewHolder {
         ImageView volumeImage;
         TextView volumeTitle;
         TextView volumeAuthors;

         VolumeViewHolder(@NonNull View itemView) {
            super(itemView);

            this.volumeImage = itemView.findViewById(R.id.volume_poster);
            this.volumeTitle = itemView.findViewById(R.id.volume_title);
            this.volumeAuthors = itemView.findViewById(R.id.volume_authors);
        }

    }

    public VolumeAdapter(List<Volume> volumes){
        this.volumes = volumes;
    }

    @NonNull
    @Override
    public VolumeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.volume_card, viewGroup, false);

        return new VolumeViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull VolumeViewHolder volumeViewHolder, int i) {

        Volume currentItem = volumes.get(i);

        try{

        Picasso.get().load(currentItem
                .getVolumeInfo()
                .getImage()
                .getSmallThumbnail())
                .into(volumeViewHolder.volumeImage);

        volumeViewHolder.volumeTitle.setText(currentItem
                .getVolumeInfo()
                .getTitle());

        String authorsEnum;

            if (currentItem.getVolumeInfo().getAuthors().length > 1){
                StringBuilder sb = new StringBuilder();

                for (String author : currentItem
                        .getVolumeInfo()
                        .getAuthors()){

                    sb.append(author).append(", ");
                }
                authorsEnum = sb.toString();
            } else{
                authorsEnum = currentItem
                        .getVolumeInfo()
                        .getAuthors()[0];
            }

            volumeViewHolder.volumeAuthors.setText(authorsEnum);

        }catch (NullPointerException e){

            if (currentItem.getVolumeInfo().getTitle() == null){
                volumeViewHolder.volumeTitle
                        .setText(R.string.title_not_found);
            }
            if (currentItem.getVolumeInfo().getImage() == null){
                volumeViewHolder.volumeImage.setImageResource
                                (R.drawable.ic_launcher_background);
            }
            if (currentItem.getVolumeInfo().getAuthors() == null){
                volumeViewHolder.volumeAuthors
                        .setText(R.string.author_not_found);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (volumes != null){
            return volumes.size();
        }
            return 0;
    }
}
