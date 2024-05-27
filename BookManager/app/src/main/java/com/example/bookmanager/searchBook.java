package com.example.bookmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class searchBook extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ArrayList booklist;
    ArrayAdapter adapter;
    ListView listbook;
    EditText edt_search;
    Button btn_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbook);
        listbook = (ListView) findViewById(R.id.lv_search);
        edt_search = (EditText) findViewById(R.id.edt_search);
        btn_search = (Button) findViewById(R.id.btn_search_view);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadBook();
            }
        });
    }

    public void LoadBook(){
        String header = "Title     Author     Tags";
        booklist = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS, null, DatabaseHelper.COLUMN_AUTHOR + "=?",new String[]{edt_search.getText().toString()}, null, null, null);
        if (cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TAGS));
                String result  = title + " - " + author + " - " + tags;
                booklist.add(result);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, booklist);
        listbook.setAdapter(adapter);
    }
}
