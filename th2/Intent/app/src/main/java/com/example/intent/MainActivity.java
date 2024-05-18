package com.example.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn_open;
    Button camera;
    EditText edt_input;
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
        //tham chieu button toi bien btn_open
        btn_open = (Button) findViewById(R.id.btn_open_intent);
        camera = (Button) findViewById(R.id.camera);
        edt_input = (EditText) findViewById(R.id.edt_input);

        //add su kien cho button
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tao 1 activity bang intent, activity khac tao ra tu activity main va tham chieu
                // toi activity Child_activity (class bat dau vaf class dich)
                Intent inten = new Intent(MainActivity.this, Child_activity.class);
                String send_str = edt_input.getText().toString();
                Bundle bundle    = new Bundle();
                bundle.putString("key", send_str);
                inten.putExtra("packet", bundle);
                startActivity(inten);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Slide slide = new Slide(Gravity.START);
//                    slide.setDuration(500);
//                    getWindow().setEnterTransition(slide);
//                }

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

            }
        });
    }
}