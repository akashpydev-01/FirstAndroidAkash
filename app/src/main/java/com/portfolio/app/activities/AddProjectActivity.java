package com.portfolio.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.portfolio.app.R;
import com.portfolio.app.adapters.AddedMediaAdapter;
import com.portfolio.app.models.PortfolioItem;
import com.portfolio.app.utils.DataManager;

import java.util.ArrayList;
import java.util.List;

public class AddProjectActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etClient, etYear, etNewCategory;
    private AutoCompleteTextView actvCategory;
    private List<PortfolioItem.MediaItem> mediaItems = new ArrayList<>();
    private AddedMediaAdapter mediaAdapter;
    private DataManager dataManager;
    private String editItemId;

    private final ActivityResultLauncher<String[]> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), uris -> {
                if (uris != null) {
                    for (Uri uri : uris) {
                        // Persist permission
                        getContentResolver().takePersistableUriPermission(uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        mediaItems.add(new PortfolioItem.MediaItem(uri.toString(),
                                PortfolioItem.MediaItem.TYPE_IMAGE, ""));
                    }
                    mediaAdapter.notifyDataSetChanged();
                }
            });

    private final ActivityResultLauncher<String[]> videoPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), uris -> {
                if (uris != null) {
                    for (Uri uri : uris) {
                        getContentResolver().takePersistableUriPermission(uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        mediaItems.add(new PortfolioItem.MediaItem(uri.toString(),
                                PortfolioItem.MediaItem.TYPE_VIDEO, ""));
                    }
                    mediaAdapter.notifyDataSetChanged();
                }
            });

    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (!granted) Toast.makeText(this, "Permission needed to pick media", Toast.LENGTH_SHORT).show();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        dataManager = DataManager.getInstance(this);
        editItemId = getIntent().getStringExtra("item_id");
        boolean isEdit = editItemId != null;

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(isEdit ? "Edit Project" : "Add Project");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        etClient = findViewById(R.id.et_client);
        etYear = findViewById(R.id.et_year);
        actvCategory = findViewById(R.id.actv_category);
        etNewCategory = findViewById(R.id.et_new_category);
        Button btnAddCategory = findViewById(R.id.btn_add_category);
        Button btnPickImages = findViewById(R.id.btn_pick_images);
        Button btnPickVideos = findViewById(R.id.btn_pick_videos);
        Button btnSave = findViewById(R.id.btn_save);
        RecyclerView rvMedia = findViewById(R.id.rv_added_media);

        // Setup category dropdown
        List<String> categories = dataManager.getCategories();
        categories.remove("All");
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, categories);
        actvCategory.setAdapter(catAdapter);

        // Setup media RecyclerView
        rvMedia.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mediaAdapter = new AddedMediaAdapter(this, mediaItems, pos -> {
            mediaItems.remove(pos);
            mediaAdapter.notifyDataSetChanged();
        });
        rvMedia.setAdapter(mediaAdapter);

        // Load existing data if editing
        if (isEdit) {
            PortfolioItem item = dataManager.getPortfolioItemById(editItemId);
            if (item != null) {
                etTitle.setText(item.getTitle());
                etDescription.setText(item.getDescription());
                etClient.setText(item.getClient());
                etYear.setText(item.getYear());
                actvCategory.setText(item.getCategory(), false);
                mediaItems.addAll(item.getMediaItems());
                mediaAdapter.notifyDataSetChanged();
            }
        }

        btnAddCategory.setOnClickListener(v -> {
            String newCat = etNewCategory.getText().toString().trim();
            if (!newCat.isEmpty()) {
                dataManager.addCategory(newCat);
                catAdapter.add(newCat);
                actvCategory.setText(newCat, false);
                etNewCategory.setText("");
                Toast.makeText(this, "Category added", Toast.LENGTH_SHORT).show();
            }
        });

        btnPickImages.setOnClickListener(v -> {
            checkPermissionAndPick(true);
        });

        btnPickVideos.setOnClickListener(v -> {
            checkPermissionAndPick(false);
        });

        btnSave.setOnClickListener(v -> saveProject());
    }

    private void checkPermissionAndPick(boolean images) {
        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = images ? Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_MEDIA_VIDEO;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            if (images) imagePickerLauncher.launch(new String[]{"image/*"});
            else videoPickerLauncher.launch(new String[]{"video/*"});
        } else {
            permissionLauncher.launch(permission);
        }
    }

    private void saveProject() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String category = actvCategory.getText().toString().trim();
        String client = etClient.getText().toString().trim();
        String year = etYear.getText().toString().trim();

        if (title.isEmpty()) { etTitle.setError("Title required"); return; }
        if (category.isEmpty()) { actvCategory.setError("Category required"); return; }

        if (editItemId != null) {
            PortfolioItem item = dataManager.getPortfolioItemById(editItemId);
            if (item != null) {
                item.setTitle(title);
                item.setDescription(description);
                item.setCategory(category);
                item.setClient(client);
                item.setYear(year);
                item.setMediaItems(new ArrayList<>(mediaItems));
                dataManager.updatePortfolioItem(item);
                Toast.makeText(this, "Project updated!", Toast.LENGTH_SHORT).show();
            }
        } else {
            PortfolioItem item = new PortfolioItem(null, title, description, category, client, year);
            item.setMediaItems(new ArrayList<>(mediaItems));
            dataManager.addPortfolioItem(item);
            Toast.makeText(this, "Project added!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
