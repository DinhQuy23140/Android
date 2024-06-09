package com.example.contact;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ThongtinNhanvien extends AppCompatActivity {

    TextView tv_to_edit_nhanvien, tv_mnv, tv_tennv, tv_chucvu, tv_email, tv_sdt, tv_madonvi, tv_title;
    ImageView img_avatar;
    ListView tv_inf_lvdonvi;
    ArrayList<Donvi> listDonvi = new ArrayList<>();
    AdapterDonvi adapter;
    SQLiteDatabase db;
    Bundle bundle;


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
        img_avatar = findViewById(R.id.iv_edit_nv_avatar);
        tv_title = findViewById(R.id.tv_tendonvi);
        tv_mnv = findViewById(R.id.tv_inf_manhanvien);
        tv_tennv = findViewById(R.id.tv_inf_tennhanvien);
        tv_chucvu = findViewById(R.id.tv_inf_chucvunhanvien);
        tv_email = findViewById(R.id.tv_inf_sdtnhanvien);
        tv_sdt = findViewById(R.id.tv_infemailnhanvien);
        tv_madonvi = findViewById(R.id.tv_inf_madonvi);
        tv_to_edit_nhanvien = findViewById(R.id.tv_view_edit_nhanvien);
        tv_inf_lvdonvi = findViewById(R.id.tv_inf_lvdonvi);
        adapter = new AdapterDonvi(this, listDonvi);
        tv_inf_lvdonvi.setAdapter(adapter);

        db = openOrCreateDatabase("contact.db", MODE_PRIVATE, null);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("Nhanvien");
        if (bundle != null) {
            String manhanvien = bundle.getString("manhanvien");
            String tennhanvien = bundle.getString("hoten");
            String chucvu = bundle.getString("chucvu");
            String email = bundle.getString("email");
            String sdt = bundle.getString("sdt");
            String avata = bundle.getString("avata");
            String madonvi = bundle.getString("madonvi");
            tv_mnv.setText(manhanvien);
            tv_tennv.setText(tennhanvien);
            tv_chucvu.setText(chucvu);
            tv_email.setText(email);
            tv_sdt.setText(sdt);
            tv_title.setText(tennhanvien);
            Bitmap bitmap = getImageView(avata);
            if (bitmap != null) {
                //img_avatar.setImageBitmap(bitmap);
                Glide.with(ThongtinNhanvien.this)
                        .load(bitmap) // Replace with your image source
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(img_avatar);
            }
            else{
                //img_avatar.setImageResource(R.drawable.user_ic);
                Glide.with(ThongtinNhanvien.this)
                        .load(R.drawable.user_ic) // Replace with your image source
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(img_avatar);
            }

            if (madonvi != null) {
                tv_madonvi.setText(madonvi);
                Cursor cursor = db.rawQuery("SELECT * FROM tb_donvi WHERE madonvi = ?", new String[]{madonvi});
                if (cursor.moveToFirst()) {
                    String madonvicon = cursor.getString(0);
                    String tendonvicon = cursor.getString(1);
                    String emaildonvicon = cursor.getString(2);
                    String websitedonvicon = cursor.getString(3);
                    String diachidonvicon = cursor.getString(4);
                    String sdtdonvicon = cursor.getString(5);
                    String madonvicha_ = cursor.getString(6);
                    String logodonvicon = cursor.getString(7);
                    Donvi donvi = new Donvi(madonvicon, tendonvicon, emaildonvicon, websitedonvicon, diachidonvicon, sdtdonvicon, madonvicha_, logodonvicon);
                    listDonvi.add(donvi);
                    adapter.notifyDataSetChanged();
                }
                cursor.close();
            }
            else{
                tv_madonvi.setText("Chưa có đơn vị");
            }
        }

        tv_to_edit_nhanvien.setOnClickListener(v -> {
            Intent view_edit = new Intent(ThongtinNhanvien.this, EditNhanvien.class);
            view_edit.putExtra("Nhanvien", bundle);
            startActivity(view_edit);
        });


    }

    private Bitmap getImageView(String encodeImage) {
        if (encodeImage == null || encodeImage.isEmpty()) {
            // Trả về một hình ảnh mặc định hoặc null nếu encodeImage là null hoặc trống
            return null;
        }
        byte[] bytes = Base64.decode(encodeImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}