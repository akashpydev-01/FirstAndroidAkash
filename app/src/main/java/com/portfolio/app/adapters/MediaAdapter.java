package com.portfolio.app.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.portfolio.app.R;
import com.portfolio.app.models.PortfolioItem;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    public interface OnMediaClickListener {
        void onMediaClick(PortfolioItem.MediaItem item, int position);
    }

    private final Context context;
    private final List<PortfolioItem.MediaItem> items;
    private final OnMediaClickListener listener;

    public MediaAdapter(Context context, List<PortfolioItem.MediaItem> items, OnMediaClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_media_thumbnail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PortfolioItem.MediaItem item = items.get(position);

        if (item.getType() == PortfolioItem.MediaItem.TYPE_VIDEO) {
            holder.ivPlay.setVisibility(View.VISIBLE);
            // Load video thumbnail
            Glide.with(context)
                    .load(Uri.parse(item.getUri()))
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_video)
                    .into(holder.ivMedia);
        } else {
            holder.ivPlay.setVisibility(View.GONE);
            Glide.with(context)
                    .load(Uri.parse(item.getUri()))
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.ivMedia);
        }

        holder.itemView.setOnClickListener(v -> listener.onMediaClick(item, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMedia, ivPlay;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMedia = itemView.findViewById(R.id.iv_media);
            ivPlay = itemView.findViewById(R.id.iv_play);
        }
    }
}
