package com.example.university.crud;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.university.FirebaseDataHelper;
import com.example.university.R;
import com.example.university.Student;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addStudent extends AppCompatActivity {

    EditText edt_msv, edt_tensv, edt_day, edt_month, edt_year, edt_email, edt_phone;
    Button btn_add;
    Spinner sp_lop, sp_khoa;

    RadioGroup rdi;

    FirebaseDataHelper database;
    DatabaseReference databaseReference;

    List<String> listKhoa;
    List<String> listLop = new ArrayList<>();

    ArrayAdapter<String> adapterKhoa, adapterLop;

    List<String> khoaCNTT = new ArrayList<>();
    List<String> khoaKinhTe = new ArrayList<>();
    List<String> khoaKhoaHocTuNhien = new ArrayList<>();
    List<String> khoaKyThuat = new ArrayList<>();
    List<String> khoaKhoaHocXaHoiVaNhanVan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database = new FirebaseDataHelper();
        edt_msv = (EditText) findViewById(R.id.edt_msv);
        edt_tensv = (EditText) findViewById(R.id.edt_tensv);
        edt_day = (EditText) findViewById(R.id.edt_day);
        edt_month = (EditText) findViewById(R.id.edt_month);
        edt_year = (EditText) findViewById(R.id.edt_year);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        rdi = (RadioGroup) findViewById(R.id.rdo_gt);
        sp_lop = (Spinner) findViewById(R.id.sp_lop);
        sp_khoa = (Spinner) findViewById(R.id.sp_khoa);
        btn_add = (Button) findViewById(R.id.btn_add_student);

        listKhoa = new ArrayList<>(Arrays.asList("CNTT", "Kinh Tế", "Khoa học Tự nhiên", "Kỹ thuật", "Khoa học Xã hội và Nhân văn"));
        khoaCNTT.addAll(Arrays.asList("Công nghệ thông tin", "Hệ thống thông tin", "Khoa học máy tính"));
        khoaKinhTe.addAll(Arrays.asList("Kinh doanh quốc tế", "Tài chính ngân hàng", "Quản trị kinh doanh"));
        khoaKhoaHocTuNhien.addAll(Arrays.asList("Vật lý", "Hóa học", "Toán học", "Khoa học môi trường"));
        khoaKyThuat.addAll(Arrays.asList("Cơ khí", "Điện tử viễn thông", "Xây dựng", "Kỹ thuật máy tính"));
        khoaKhoaHocXaHoiVaNhanVan.addAll(Arrays.asList("Ngôn ngữ học", "Lịch sử", "Văn học", "Tâm lý học"));

        adapterKhoa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listKhoa);
        adapterKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_khoa.setAdapter(adapterKhoa);
        sp_khoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                switch (value) {
                    case "CNTT":{
                        listLop.clear();
                        listLop.addAll(khoaCNTT);
                        break;
                    }
                    case "Kinh Tế":{
                        listLop.clear();
                        listLop.addAll(khoaKinhTe);
                        break;
                    }
                    case "Khoa học Tự nhiên":{
                        listLop.clear();
                        listLop.addAll(khoaKhoaHocTuNhien);
                        break;
                    }
                    case "Kỹ thuật":{
                        listLop.clear();
                        listLop.addAll(khoaKyThuat);
                        break;
                    }
                    case "Khoa học Xã hội và Nhân văn":{
                        listLop.clear();
                        listLop.addAll(khoaKhoaHocXaHoiVaNhanVan);
                        break;
                    }
                }
                adapterLop = new ArrayAdapter<>(addStudent.this, android.R.layout.simple_spinner_item, listLop);
                adapterLop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_lop.setAdapter(adapterLop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String birthday = edt_day.getText().toString() + "/" + edt_month.getText().toString() + "/" + edt_year.getText().toString();
                edt_msv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if (TextUtils.isEmpty(edt_msv.getText().toString())) {
                                show("Mã số không được để trống");
                            }
                        }
                    }
                });

                String msv = edt_msv.getText().toString();
                String name = edt_tensv.getText().toString();
                String birthday = edt_day.getText().toString() + "/" + edt_month.getText().toString() + "/" + edt_year.getText().toString();
                int checkedRadioButtonId = rdi.getCheckedRadioButtonId();
                String gender = "";
                if (checkedRadioButtonId == R.id.radioButton) {
                    gender = "Nam";
                } else if (checkedRadioButtonId == R.id.radioButton2) {
                    gender = "Nữ";
                }
                String email = edt_email.getText().toString();
                String phone = edt_phone.getText().toString();
                String lop = sp_lop.getSelectedItem().toString();
                String khoa = sp_khoa.getSelectedItem().toString();
                String id = database.getMyRef().push().getKey();
                Student student = new Student(lop, khoa, id, msv, name, gender, birthday, email, phone);
                database.add_Student(student);
            }
        });
    }

    public void show(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}