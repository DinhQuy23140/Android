package com.example.quuanlybanhang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;

public class home extends AppCompatActivity {
    Button btn_logout, btn_add_item ;
    EditText edt_ten, edy_mota, edt_gia;
    ImageView img;
    ListView lv;
    String endcodeedImage;
    SQLiteDatabase db;

    ArrayList<item> listItem;
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edt_ten = findViewById(R.id.edt_name_item);
        edy_mota = findViewById(R.id.edt_mota_item);
        edt_gia = findViewById(R.id.edt_price_item);
        img = (ImageView) findViewById(R.id.imgv_img);
        btn_logout = findViewById(R.id.btn_logout);
        btn_add_item = findViewById(R.id.btn_add_item);
        lv = (ListView) findViewById(R.id.lv_item);
        listItem = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, listItem);
        lv.setAdapter(itemAdapter);

        db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE IF NOT EXISTS item_tb (name TEXT, description TEXT, price TEXT, image TEXT)";
            db.execSQL(sql);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }

        edt_ten.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String ten = edt_ten.getText().toString().trim();
                    if (ten.isEmpty()) {
                        showToast("Tên không được để trống");
                    }
                }
            }
        });

        edt_gia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String gia = edt_gia.getText().toString().trim();
                    if(gia.isEmpty()){
                        showToast("Giá không được để trống");
                    }
                }
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            }
        });

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_ten.getText().toString().trim();
                String description = edy_mota.getText().toString().trim();
                String price = edt_gia.getText().toString().trim();
                String image = endcodeedImage;
                item item = new item(name, description, price, image);
                ContentValues values = new ContentValues();
                values.put("name", item.getName());
                values.put("description", item.getMota());
                values.put("price", item.getPrice());
                values.put("image", item.getImage());
                if(db.insert("item_tb", null, values) == -1){
                    showToast("Thêm thất bại");
                }
                else{
                    showToast("Thêm thành công");
                    edt_ten.setText("");
                    edy_mota.setText("");
                    edt_gia.setText("");
                }
                loadData();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        loadData();
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
                            img.setImageBitmap(bitmap);
                            endcodeedImage = enCodeImage(bitmap);
                        }
                        catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public void buttonRotate(View view) {
        Bitmap originalBitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        Bitmap rotatedBitmap = rotateImage(originalBitmap, 90);
        img.setImageBitmap(rotatedBitmap);
        endcodeedImage = enCodeImage(rotatedBitmap);
    }
    public void loadData(){
        Cursor cursor = db.query("item_tb", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String description = cursor.getString(1);
            String price = cursor.getString(2);
            String image = cursor.getString(3);
            item item = new item(name, description, price, image);
            listItem.add(item);
        }
        Log.d("LoadData", "Number of items loaded: " + listItem.size());
        itemAdapter.notifyDataSetChanged();
        cursor.close();
    }

}