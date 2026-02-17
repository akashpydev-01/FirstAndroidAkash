package com.portfolio.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.portfolio.app.R;
import com.portfolio.app.adapters.AdminPortfolioAdapter;
import com.portfolio.app.models.PortfolioItem;
import com.portfolio.app.utils.DataManager;

import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    private AdminPortfolioAdapter adapter;
    private DataManager dataManager;
    private TextView tvItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Admin Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataManager = DataManager.getInstance(this);
        tvItemCount = findViewById(R.id.tv_item_count);
        RecyclerView rvProjects = findViewById(R.id.rv_projects);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        rvProjects.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminPortfolioAdapter(this, dataManager.getPortfolioItems(),
                item -> {
                    // Edit
                    Intent intent = new Intent(this, AddProjectActivity.class);
                    intent.putExtra("item_id", item.getId());
                    startActivity(intent);
                },
                item -> {
                    // Delete
                    new AlertDialog.Builder(this)
                            .setTitle("Delete Project")
                            .setMessage("Delete \"" + item.getTitle() + "\"?")
                            .setPositiveButton("Delete", (d, w) -> {
                                dataManager.deletePortfolioItem(item.getId());
                                refreshList();
                                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                });
        rvProjects.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> startActivity(new Intent(this, AddProjectActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        List<PortfolioItem> items = dataManager.getPortfolioItems();
        adapter.updateData(items);
        tvItemCount.setText(items.size() + " Projects");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        if (item.getItemId() == R.id.action_edit_profile) {
            startActivity(new Intent(this, EditProfileActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_change_password) {
            showChangePasswordDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChangePasswordDialog() {
        android.widget.EditText etNew = new android.widget.EditText(this);
        etNew.setHint("New password");
        etNew.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new AlertDialog.Builder(this)
                .setTitle("Change Password")
                .setView(etNew)
                .setPositiveButton("Save", (d, w) -> {
                    String newPass = etNew.getText().toString().trim();
                    if (!newPass.isEmpty()) {
                        dataManager.setAdminPassword(newPass);
                        Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
