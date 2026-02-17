package com.portfolio.app.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.portfolio.app.R;
import com.portfolio.app.adapters.MediaAdapter;
import com.portfolio.app.models.PortfolioItem;
import com.portfolio.app.utils.DataManager;

public class ProjectDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String itemId = getIntent().getStringExtra("item_id");
        PortfolioItem item = DataManager.getInstance(this).getPortfolioItemById(itemId);
        if (item == null) { finish(); return; }

        getSupportActionBar().setTitle(item.getTitle());

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvCategory = findViewById(R.id.tv_category);
        TextView tvClient = findViewById(R.id.tv_client);
        TextView tvYear = findViewById(R.id.tv_year);
        TextView tvDescription = findViewById(R.id.tv_description);
        RecyclerView rvMedia = findViewById(R.id.rv_media);

        tvTitle.setText(item.getTitle());
        tvCategory.setText(item.getCategory());
        tvClient.setText(item.getClient() != null && !item.getClient().isEmpty()
                ? "Client: " + item.getClient() : "");
        tvYear.setText(item.getYear() != null && !item.getYear().isEmpty()
                ? "Year: " + item.getYear() : "");
        tvDescription.setText(item.getDescription());

        rvMedia.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MediaAdapter mediaAdapter = new MediaAdapter(this, item.getMediaItems(), (mediaItem, position) -> {
            Intent intent = new Intent(this, FullScreenMediaActivity.class);
            intent.putExtra("media_uri", mediaItem.getUri());
            intent.putExtra("media_type", mediaItem.getType());
            startActivity(intent);
        });
        rvMedia.setAdapter(mediaAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
