package com.portfolio.app.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;
import com.portfolio.app.R;
import com.portfolio.app.models.PortfolioItem;

public class FullScreenMediaActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;
    private ImageView ivFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen_media);

        playerView = findViewById(R.id.player_view);
        ivFullscreen = findViewById(R.id.iv_fullscreen_image);
        ImageView ivClose = findViewById(R.id.iv_close);

        ivClose.setOnClickListener(v -> finish());

        String uriString = getIntent().getStringExtra("media_uri");
        int type = getIntent().getIntExtra("media_type", PortfolioItem.MediaItem.TYPE_IMAGE);

        if (type == PortfolioItem.MediaItem.TYPE_VIDEO) {
            ivFullscreen.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(uriString));
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        } else {
            playerView.setVisibility(View.GONE);
            ivFullscreen.setVisibility(View.VISIBLE);
            Glide.with(this).load(Uri.parse(uriString))
                    .placeholder(R.drawable.placeholder_image)
                    .into(ivFullscreen);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
