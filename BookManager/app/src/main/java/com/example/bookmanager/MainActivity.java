package com.example.bookmanager;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ArrayList booklist;
    ArrayAdapter adapter;
    ListView listbook;
    Button btn_view_add;
    Button btn_view_edit;
    Button btn_delete;
    int bookid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewBook), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listbook = (ListView) findViewById(R.id.listViewBooks);
        btn_view_add = (Button) findViewById(R.id.btn_view_add);
        btn_view_edit = (Button) findViewById(R.id.btn_view_edit);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_view_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addBook.class);
                startActivityForResult(intent, 99);
            }
        });
        LoadBook();

        listbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookid = position;
            }
        });
        btn_view_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, editBook.class);
                intent.putExtra("BOOK_ID", bookid);
                startActivityForResult(intent, 99);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                //tao dialog
                new AlertDialog.Builder(MainActivity.this)
                        //set title cho dialog
                        .setTitle("Delete Book")
                        //set noi dung cho dialog
                        .setMessage("Are you sure you want to delete this book?")
                        //set su kien click button yes
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.delete(DatabaseHelper.TABLE_BOOKS, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(bookid)});
                                db.close();
                                LoadBook();
                            }
                        })
                        //set button no
                        .setNegativeButton(android.R.string.no, null)
                        // hien thi dialog
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int request, int result, @Nullable Intent data) {

        super.onActivityResult(request, result, data);
        if (request == 99 && (result == 33)){
            LoadBook();
        }
    }

    public void LoadBook(){
        String header = "Title     Author     Tags";
        booklist = new ArrayList<>();
        booklist.add(header);
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS, null, null,null, null, null, null);
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

// db browser for sqlite