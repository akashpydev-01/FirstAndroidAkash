package com.portfolio.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.portfolio.app.R;
import com.portfolio.app.utils.DataManager;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnBack = findViewById(R.id.btn_back);

        btnLogin.setOnClickListener(v -> {
            String password = etPassword.getText().toString();
            if (DataManager.getInstance(this).verifyAdminPassword(password)) {
                startActivity(new Intent(this, AdminDashboardActivity.class));
                finish();
            } else {
                etPassword.setError("Incorrect password");
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
