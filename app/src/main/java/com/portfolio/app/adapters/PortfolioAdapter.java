package com.portfolio.app.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.portfolio.app.R;
import com.portfolio.app.models.PortfolioItem;

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PortfolioItem item);
    }

    private final Context context;
    private List<PortfolioItem> items;
    private final OnItemClickListener listener;

    public PortfolioAdapter(Context context, List<PortfolioItem> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public void updateData(List<PortfolioItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_portfolio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PortfolioItem item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());

        // Check if has video
        boolean hasVideo = false;
        for (PortfolioItem.MediaItem media : item.getMediaItems()) {
            if (media.getType() == PortfolioItem.MediaItem.TYPE_VIDEO) {
                hasVideo = true;
                break;
            }
        }
        holder.ivVideoIndicator.setVisibility(hasVideo ? View.VISIBLE : View.GONE);

        String thumbnailUri = item.getThumbnailUri();
        if (thumbnailUri != null && !thumbnailUri.isEmpty()) {
            Glide.with(context)
                    .load(Uri.parse(thumbnailUri))
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.ivThumbnail);
        } else {
            holder.ivThumbnail.setImageResource(R.drawable.placeholder_image);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail, ivVideoIndicator;
        TextView tvTitle, tvCategory;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            ivVideoIndicator = itemView.findViewById(R.id.iv_video_indicator);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }
}
