package com.portfolio.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.portfolio.app.R;
import com.portfolio.app.models.DesignerProfile;
import com.portfolio.app.utils.DataManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private CircleImageView ivProfile;
    private EditText etName, etTagline, etBio, etEmail, etPhone, etWebsite, etInstagram, etBehance;
    private DataManager dataManager;
    private String profileImageUri;

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    getContentResolver().takePersistableUriPermission(uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    profileImageUri = uri.toString();
                    Glide.with(this).load(uri).circleCrop().into(ivProfile);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dataManager = DataManager.getInstance(this);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivProfile = findViewById(R.id.iv_profile);
        etName = findViewById(R.id.et_name);
        etTagline = findViewById(R.id.et_tagline);
        etBio = findViewById(R.id.et_bio);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etWebsite = findViewById(R.id.et_website);
        etInstagram = findViewById(R.id.et_instagram);
        etBehance = findViewById(R.id.et_behance);
        Button btnSave = findViewById(R.id.btn_save);

        DesignerProfile profile = dataManager.getDesignerProfile();
        loadProfile(profile);

        ivProfile.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        btnSave.setOnClickListener(v -> {
            DesignerProfile updated = new DesignerProfile();
            updated.setName(etName.getText().toString());
            updated.setTagline(etTagline.getText().toString());
            updated.setBio(etBio.getText().toString());
            updated.setEmail(etEmail.getText().toString());
            updated.setPhone(etPhone.getText().toString());
            updated.setWebsite(etWebsite.getText().toString());
            updated.setInstagram(etInstagram.getText().toString());
            updated.setBehance(etBehance.getText().toString());
            updated.setProfileImageUri(profileImageUri != null ? profileImageUri : profile.getProfileImageUri());
            dataManager.saveDesignerProfile(updated);
            Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadProfile(DesignerProfile profile) {
        etName.setText(profile.getName());
        etTagline.setText(profile.getTagline());
        etBio.setText(profile.getBio());
        etEmail.setText(profile.getEmail());
        etPhone.setText(profile.getPhone());
        etWebsite.setText(profile.getWebsite());
        etInstagram.setText(profile.getInstagram());
        etBehance.setText(profile.getBehance());
        profileImageUri = profile.getProfileImageUri();
        if (profileImageUri != null && !profileImageUri.isEmpty()) {
            Glide.with(this).load(Uri.parse(profileImageUri)).circleCrop().into(ivProfile);
        }
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
