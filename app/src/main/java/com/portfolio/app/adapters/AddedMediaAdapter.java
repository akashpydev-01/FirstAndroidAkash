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

public class AddedMediaAdapter extends RecyclerView.Adapter<AddedMediaAdapter.ViewHolder> {

    public interface OnRemoveListener {
        void onRemove(int position);
    }

    private final Context context;
    private final List<PortfolioItem.MediaItem> items;
    private final OnRemoveListener removeListener;

    public AddedMediaAdapter(Context context, List<PortfolioItem.MediaItem> items, OnRemoveListener removeListener) {
        this.context = context;
        this.items = items;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_added_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PortfolioItem.MediaItem item = items.get(position);

        if (item.getType() == PortfolioItem.MediaItem.TYPE_VIDEO) {
            holder.ivTypeIcon.setVisibility(View.VISIBLE);
            holder.ivTypeIcon.setImageResource(R.drawable.ic_play_circle);
        } else {
            holder.ivTypeIcon.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(Uri.parse(item.getUri()))
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.ivPreview);

        holder.ivRemove.setOnClickListener(v -> removeListener.onRemove(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPreview, ivRemove, ivTypeIcon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPreview = itemView.findViewById(R.id.iv_preview);
            ivRemove = itemView.findViewById(R.id.iv_remove);
            ivTypeIcon = itemView.findViewById(R.id.iv_type_icon);
        }
    }
}
