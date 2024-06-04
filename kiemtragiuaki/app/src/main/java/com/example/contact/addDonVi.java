package com.example.contact;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class addDonVi extends AppCompatActivity {
    EditText edt_madonvi, edt_tendonvi, edt_email, edt_website, edt_diachi, edt_sdt, edt_madonvicha;
    ImageView img_logo;
    Button btn_add_donvi;
    String endcodeedImage;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_don_vi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt_madonvi = (EditText) findViewById(R.id.edt_madonvi);
        edt_tendonvi = (EditText) findViewById(R.id.edt_tendonvi);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_website = (EditText) findViewById(R.id.edt_website);
        edt_diachi = (EditText) findViewById(R.id.edt_diachi);
        edt_sdt = (EditText) findViewById(R.id.edt_sdt);
        edt_madonvicha = (EditText) findViewById(R.id.edt_madonvicha);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        btn_add_donvi = (Button) findViewById(R.id.btn_add_donvi);

        db = openOrCreateDatabase("contact.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE IF NOT EXISTS tb_donvi (madonvi TEXT PRIMARY KEY, tendonvi TEXT, email TEXT, website TEXT, diachi TEXT, sdt TEXT, madonvicha TEXT, logo TEXT)";
            db.execSQL(sql);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }

        btn_add_donvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String madonvi = edt_madonvi.getText().toString();
                String tendonvi = edt_tendonvi.getText().toString();
                String email = edt_email.getText().toString();
                String website = edt_website.getText().toString();
                String diachi = edt_diachi.getText().toString();
                String sdt = edt_sdt.getText().toString();
                String madonvicha = edt_madonvicha.getText().toString();
                String logo = endcodeedImage;
                ContentValues contentValues = new ContentValues();
                contentValues.put("madonvi", madonvi);
                contentValues.put("tendonvi", tendonvi);
                contentValues.put("email", email);
                contentValues.put("website", website);
                contentValues.put("diachi", diachi);
                contentValues.put("sdt", sdt);
                contentValues.put("madonvicha", madonvicha);
                contentValues.put("logo", logo);
                if(db.insert("tb_donvi", null, contentValues) == -1){
                    showToast("Thêm đơn vị thất bại");
                }
                else{
                    showToast("Thêm đơn vị thành công");
                    Intent intent = new Intent(addDonVi.this, DonViActivity.class);
                    startActivity(intent);
                }
            }
        });

        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            }
        });
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String enCodeImage(Bitmap bitmap){
        //set with
        int previewWith = 150;
        //set height
        int previewHeight = bitmap.getHeight() * previewWith / bitmap.getWidth();
        //scale image
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWith, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageuri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageuri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            img_logo.setImageBitmap(bitmap);
                            endcodeedImage = enCodeImage(bitmap);
                        }
                        catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );

//    private Bitmap rotateImage(Bitmap source, float angle) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
//    }
//    public void buttonRotate(View view) {
//        Bitmap originalBitmap = ((BitmapDrawable) img_logo.getDrawable()).getBitmap();
//        Bitmap rotatedBitmap = rotateImage(originalBitmap, 90);
//        img_logo.setImageBitmap(rotatedBitmap);
//        endcodeedImage = enCodeImage(rotatedBitmap);
//    }
}