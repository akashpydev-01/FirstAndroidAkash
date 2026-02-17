package com.portfolio.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.portfolio.app.R;
import com.portfolio.app.adapters.PortfolioAdapter;
import com.portfolio.app.models.DesignerProfile;
import com.portfolio.app.models.PortfolioItem;
import com.portfolio.app.utils.DataManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPortfolio;
    private PortfolioAdapter adapter;
    private ChipGroup chipGroupCategories;
    private DataManager dataManager;
    private String currentCategory = "All";
    private CircleImageView ivProfile;
    private TextView tvDesignerName, tvDesignerTagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = DataManager.getInstance(this);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivProfile = findViewById(R.id.iv_profile);
        tvDesignerName = findViewById(R.id.tv_designer_name);
        tvDesignerTagline = findViewById(R.id.tv_designer_tagline);
        chipGroupCategories = findViewById(R.id.chip_group_categories);
        rvPortfolio = findViewById(R.id.rv_portfolio);

        rvPortfolio.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new PortfolioAdapter(this, new ArrayList<>(), item -> {
            Intent intent = new Intent(this, ProjectDetailActivity.class);
            intent.putExtra("item_id", item.getId());
            startActivity(intent);
        });
        rvPortfolio.setAdapter(adapter);

        loadProfile();
        setupCategories();
        loadPortfolio();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfile();
        loadPortfolio();
    }

    private void loadProfile() {
        DesignerProfile profile = dataManager.getDesignerProfile();
        tvDesignerName.setText(profile.getName());
        tvDesignerTagline.setText(profile.getTagline());
        if (profile.getProfileImageUri() != null && !profile.getProfileImageUri().isEmpty()) {
            Glide.with(this).load(profile.getProfileImageUri()).into(ivProfile);
        }
    }

    private void setupCategories() {
        chipGroupCategories.removeAllViews();
        List<String> categories = dataManager.getCategories();
        for (String cat : categories) {
            Chip chip = new Chip(this);
            chip.setText(cat);
            chip.setCheckable(true);
            chip.setChecked(cat.equals(currentCategory));
            chip.setChipBackgroundColorResource(R.color.chip_background_selector);
            chip.setTextColor(getResources().getColorStateList(R.color.chip_text_selector, null));
            chip.setOnClickListener(v -> {
                currentCategory = cat;
                filterByCategory(cat);
            });
            chipGroupCategories.addView(chip);
        }
    }

    private void filterByCategory(String category) {
        List<PortfolioItem> all = dataManager.getPortfolioItems();
        if (category.equals("All")) {
            adapter.updateData(all);
        } else {
            List<PortfolioItem> filtered = new ArrayList<>();
            for (PortfolioItem item : all) {
                if (category.equals(item.getCategory())) filtered.add(item);
            }
            adapter.updateData(filtered);
        }
    }

    private void loadPortfolio() {
        setupCategories();
        filterByCategory(currentCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_admin) {
            startActivity(new Intent(this, AdminLoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
