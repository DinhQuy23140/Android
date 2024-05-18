package com.example.calendarnotes;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TextView tv_day;
    private EditText edt_name_work;
    private EditText edt_hour;
    private EditText edt_minute;
    private Button btn_add;
    private ListView lv_work;

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
        tv_day = (TextView) findViewById(R.id.tv_day);
        edt_hour = (EditText) findViewById(R.id.edt_hour);
        edt_minute = (EditText) findViewById(R.id.edt_minute);
        edt_name_work = (EditText) findViewById(R.id.edt_name_work);
        btn_add = (Button) findViewById(R.id.btn_add);
        lv_work = (ListView) findViewById(R.id.lv_work);

        Date date = Calendar.getInstance().getTime();
        //String date_format = android.text.format.DateFormat.format("dd/mm/yyyy",date).toString();
        java.text.SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        tv_day.setText("Hôm nay: " + date_format.format(date));

        final ArrayList<String> arrWork = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrWork);
        lv_work.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = edt_hour.getText().toString();
                String minute = edt_minute.getText().toString();
                String nameWork = edt_name_work.getText().toString();
                if (nameWork.equals("") || hour.equals("") || minute.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Required");
                    builder.setPositiveButton("continues", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
                else{
                    String work = "+ " + nameWork + " - " + hour + ":" + minute;
                    arrWork.add(work);
                    adapter.notifyDataSetChanged();
                    edt_hour.setText("");
                    edt_minute.setText("");
                    edt_name_work.setText("");
                }
            }
        });

        lv_work.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrWork.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
}