package com.example.qunlsinhvin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edt_malop, edt_tenlop, edt_siso;
    Button btn_query, btn_insert, btn_edit, btn_delete;
    ListView lv_lophoc;
    ArrayAdapter<String> adapter;
    ArrayList<String> dslop;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //tham chieu toi cac view
        edt_malop = (EditText) findViewById(R.id.edt_malop);
        edt_tenlop = (EditText) findViewById(R.id.edt_tenlop);
        edt_siso = (EditText) findViewById(R.id.edt_siso);

        btn_query = (Button) findViewById(R.id.btn_query);
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_edit = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        lv_lophoc = (ListView) findViewById(R.id.lv_lop);
        dslop = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dslop);
        lv_lophoc.setAdapter(adapter);
        //tao va mo co so du lieu
        db = openOrCreateDatabase("quanlysinhvien.db", MODE_PRIVATE, null);

        //tao table
        try{
            String sql = "CREATE TABLE tblophoc (malop TEXT primary key, tenlop TEXT, siso INTERGER)";
            db.execSQL(sql);
        }
        catch(Exception e){
            Log.e("Error","table da ton tai");
        }

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edt_malop.getText().toString();
                String tenlop = edt_tenlop.getText().toString();
                int siso = Integer.parseInt(edt_siso.getText().toString());
                ContentValues values = new ContentValues();
                values.put("malop", malop);
                values.put("tenlop", tenlop);
                values.put("siso", siso);
                String msg;
                if (db.insert("tblophoc", null,values) == -1){
                    msg = "Insert fal";
                }
                else{
                    msg = "Insert success";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                load_lisview();

            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edt_malop.getText().toString();
                int siso = Integer.parseInt(edt_siso.getText().toString());
                ContentValues values = new ContentValues();
                values.put("siso", siso);
                //update vaf tra ve so luong ban ghi duoc update
                int n = db.update("tblophoc", values, "malop = ?", new String[]{malop});
                String msg;
                if (n == 0){
                    msg = "No record to update";
                }
                else{
                    msg = n + " record to update";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                load_lisview();
            }
        });
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_lisview();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edt_malop.getText().toString();
                ContentValues values = new ContentValues();
                values.put("malop", malop);
                int n = db.delete("tblophoc", "malop = ?", new String[]{malop});
                String msg;
                if (n == 0){
                    msg = "No record delete";
                }
                else{
                    msg = n + " record delete";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                load_lisview();
            }
        });
    }
    public void load_lisview(){
        dslop.clear();
        Cursor cursor = db.query("tblophoc", null, null,null, null, null, null);
        cursor.moveToNext();
        String data = "";
        while (cursor.isAfterLast() == false){
            data = cursor.getString(0) + "    " + cursor.getString(1) + "    " + cursor.getString(2);
            dslop.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}