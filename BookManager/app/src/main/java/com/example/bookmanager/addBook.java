package com.example.bookmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class addBook extends AppCompatActivity {

    EditText edt_title;
    EditText edt_author;
    EditText edt_tags;
    Button btn_add;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_book);
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_author = (EditText) findViewById(R.id.edt_author);
        edt_tags = (EditText) findViewById(R.id.edt_tags);
        btn_add = (Button) findViewById(R.id.btn_add);
        dbHelper = new DatabaseHelper(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(MainActivity.this, addBook.class);
//                startActivity(intent);
                String title = edt_title.getText().toString();
                String author = edt_author.getText().toString();
                String tags = edt_tags.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TITLE, title);
                values.put(DatabaseHelper.COLUMN_AUTHOR, author);
                values.put(DatabaseHelper.COLUMN_TAGS, tags);
                db.insert(DatabaseHelper.TABLE_BOOKS, null, values);
                db.close();
                Intent intent = new Intent(addBook.this, MainActivity.class);
                intent.putExtra("key", "reload");
                setResult(33, intent);
                finish();
            }
        });
    }
}
