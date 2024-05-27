package com.example.bookmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class editBook extends AppCompatActivity {
    EditText edt_title;
    EditText edt_author;
    EditText edt_tags;
    Button btn_edit;
    DatabaseHelper dbHelper;
    int bookid;
    String nameBook;

    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_edit_book);

        edt_title = (EditText) findViewById(R.id.edt_title_edit);
        edt_author = (EditText) findViewById(R.id.edt_author_edit);
        edt_tags = (EditText) findViewById(R.id.edt_tags_edit);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        //tao intent
        Intent intent = getIntent();
        //lay book id thong qua key gui tu intent mainactivity
        bookid = intent.getIntExtra("BOOK_ID", -1);
        nameBook = intent.getStringExtra("BOOK_NAME");

        //khoi tao doi tuong DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        //ham cap nhap thong tin book len cac textview
        loadBookInfor();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_title.getText().toString();
                String author = edt_author.getText().toString();
                String tags = edt_tags.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TITLE, title);
                values.put(DatabaseHelper.COLUMN_AUTHOR, author);
                values.put(DatabaseHelper.COLUMN_TAGS, tags);
                Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS, null, null,null, null, null, null);
                db.update(DatabaseHelper.TABLE_BOOKS, values, DatabaseHelper.COLUMN_AUTHOR + "=?",new String[]{nameBook});
                db.close();
                Intent intent1 = new Intent(editBook.this, MainActivity.class);
                intent1.putExtra("key", "reload");
                setResult(33, intent1);
                finish();
            }
        });

    }
    public void loadBookInfor(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS, null, DatabaseHelper.COLUMN_AUTHOR + "=?", new String[]{nameBook}, null, null, null );

        if(cursor.moveToFirst()){
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
            String tags = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TAGS));
            edt_title.setText(title);
            edt_author.setText(author);
            edt_tags.setText(tags);
            //Toast.makeText(editBook.this, title + " " + author + " " + tags, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }
}
