package com.portfolio.app.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.portfolio.app.R;
import com.portfolio.app.models.PortfolioItem;

import java.util.List;

public class AdminPortfolioAdapter extends RecyclerView.Adapter<AdminPortfolioAdapter.ViewHolder> {

    public interface OnEditListener { void onEdit(PortfolioItem item); }
    public interface OnDeleteListener { void onDelete(PortfolioItem item); }

    private final Context context;
    private List<PortfolioItem> items;
    private final OnEditListener editListener;
    private final OnDeleteListener deleteListener;

    public AdminPortfolioAdapter(Context context, List<PortfolioItem> items,
                                  OnEditListener editListener, OnDeleteListener deleteListener) {
        this.context = context;
        this.items = items;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public void updateData(List<PortfolioItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PortfolioItem item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.tvMediaCount.setText(item.getMediaItems().size() + " media files");

        String thumbnail = item.getThumbnailUri();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            Glide.with(context).load(Uri.parse(thumbnail)).centerCrop()
                    .placeholder(R.drawable.placeholder_image).into(holder.ivThumbnail);
        } else {
            holder.ivThumbnail.setImageResource(R.drawable.placeholder_image);
        }

        holder.btnEdit.setOnClickListener(v -> editListener.onEdit(item));
        holder.btnDelete.setOnClickListener(v -> deleteListener.onDelete(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvCategory, tvMediaCount;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvMediaCount = itemView.findViewById(R.id.tv_media_count);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
