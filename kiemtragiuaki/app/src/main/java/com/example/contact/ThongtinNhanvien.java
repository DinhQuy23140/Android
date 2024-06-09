package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThongtinNhanvien extends AppCompatActivity {

    TextView tv_to_edit_nhanvien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thongtin_nhanvien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv_to_edit_nhanvien = findViewById(R.id.tv_view_edit_nhanvien);
        tv_to_edit_nhanvien.setOnClickListener(v -> {
            Intent intent = new Intent(ThongtinNhanvien.this, EditNhanvien.class);
            startActivity(intent);
        });
    }
}