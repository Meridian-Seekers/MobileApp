package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<VideoItem> videoList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public VideoAdapter(Context context, List<VideoItem> videoList) {
        this.inflater = LayoutInflater.from(context);
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoItem videoItem = videoList.get(position);
        holder.videoTitle.setText(videoItem.getTitle());

        // Use Glide to load the thumbnail image
        Glide.with(holder.itemView.getContext())
                .load(videoItem.getThumbnailUrl()) // Replace this with the URL of your thumbnail
                .placeholder(R.drawable.ic_video_placeholder) // Placeholder image while loading
                .error(R.drawable.ic_error) // Error image if loading fails
                .into(holder.videoThumbnail);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public VideoItem getItem(int position) {
        return videoList.get(position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView videoThumbnail;
        TextView videoTitle;

        ViewHolder(View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            videoTitle = itemView.findViewById(R.id.video_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
