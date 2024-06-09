package com.example.contact;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
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

public class ThongtinDonvi extends AppCompatActivity {

    TextView tvmadonvi, tvtendonvi, tvdiachi, tvdienthoai, tvemail, tvwebsite, tvmadonvicha, tv_view_edit_donvi;
    ImageView ivlogo;
    ListView lv_donvicon, lv_nhanviendonvi;
    ArrayList<Donvi> listDonvicon = new ArrayList<>();
    AdapterDonvi adapterDonvi;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thongtin_donvi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = openOrCreateDatabase("contact.db", MODE_PRIVATE, null);

        tvmadonvi = findViewById(R.id.tv_madonvi);
        tvtendonvi = findViewById(R.id.tv_tendonvi);
        tvdiachi = findViewById(R.id.tv_diachi);
        tvdienthoai = findViewById(R.id.tv_sdt);
        tvemail = findViewById(R.id.tv_email);
        tvwebsite = findViewById(R.id.tv_website);
        tvmadonvicha = findViewById(R.id.tv_madonvicha);
        ivlogo = findViewById(R.id.iv_logo);
        tv_view_edit_donvi = findViewById(R.id.tv_view_edit_donvi);
        lv_donvicon = (ListView) findViewById(R.id.lv_donvicon);
        adapterDonvi = new AdapterDonvi(this, listDonvicon);
        lv_donvicon.setAdapter(adapterDonvi);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Donvi");
        if (bundle != null) {
            String madonvi = bundle.getString("madonvi");
            String tendonvi = bundle.getString("tendonvi");
            String diachi = bundle.getString("diachi");
            String dienthoai = bundle.getString("sdt");
            String email = bundle.getString("email");
            String website = bundle.getString("website");
            String logo = bundle.getString("logo");
            String getmadonvicha = bundle.getString("madonvicha");

            tvmadonvi.setText(madonvi);
            tvtendonvi.setText(tendonvi);
            tvdiachi.setText(diachi);
            tvdienthoai.setText(dienthoai);
            tvemail.setText(email);
            tvwebsite.setText(website);
            tvmadonvicha.setText(getmadonvicha);

            Bitmap imageBitmap = getImageView(logo);
            Glide.with(ThongtinDonvi.this)
                    .load(imageBitmap) // Replace with your image source
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(ivlogo);

            Cursor cursor = db.rawQuery("SELECT * FROM tb_donvi WHERE madonvicha = ?", new String[]{madonvi});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String madonvicon = cursor.getString(0);
                    String tendonvicon = cursor.getString(1);
                    String emaildonvicon = cursor.getString(2);
                    String websitedonvicon = cursor.getString(3);
                    String diachidonvicon = cursor.getString(4);
                    String sdtdonvicon = cursor.getString(5);
                    String madonvicha_ = cursor.getString(6);
                    String logodonvicon = cursor.getString(7);
                    Donvi donvicon = new Donvi(madonvicon, tendonvicon, emaildonvicon, websitedonvicon, diachidonvicon, sdtdonvicon, madonvicha_, logodonvicon);
                    listDonvicon.add(donvicon);
                }while (cursor.moveToNext());
                cursor.close();
                adapterDonvi.notifyDataSetChanged();
            }
        }

        tv_view_edit_donvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentViewEdit = new Intent(ThongtinDonvi.this, editDonvi.class);
                intentViewEdit.putExtra("Donvi", bundle);
                startActivity(intentViewEdit);
            }
        });

        lv_donvicon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Donvi donvi = listDonvicon.get(position);
                Intent viewDonvi = new Intent(ThongtinDonvi.this, ThongtinDonvi.class);
                Bundle bundle = new Bundle();
                bundle.putString("madonvi", donvi.getMadonvi());
                bundle.putString("tendonvi", donvi.getTendonvi());
                bundle.putString("email", donvi.getEmail());
                bundle.putString("website", donvi.getWebsite());
                bundle.putString("diachi", donvi.getDiachi());
                bundle.putString("sdt", donvi.getSdt());
                bundle.putString("madonvicha", donvi.getMadonvicha());
                bundle.putString("logo", donvi.getLogo());
                viewDonvi.putExtra("Donvi", bundle);
                startActivity(viewDonvi);
            }
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