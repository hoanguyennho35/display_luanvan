package com.example.firebase_29_09;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {
    EditText mk,tk;
    Integer sl,khoa;
    String[] name =new String[2];
    String[] bien = new String[2];
    String[] g =new String[2];
    int block_qmk=1;
    Button dn,qmk;
    FirebaseDatabase firedb1 = FirebaseDatabase.getInstance();
    DatabaseReference lightRef1 = firedb1.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Init();
        name[0]="sw2/tk";
        name[1]="sw2/mk";
        evenlisten(name[0],0,selection._string,selection.solan);
        evenlisten(name[1],1,selection._string,selection.solan);
        evenlisten("sw2/sl",0,selection._int,selection.solan);
        evenlisten("sw2/khoa",0,selection._int,selection.khoa);
    }

    private void Init() {
        mk = findViewById(R.id.mk);
        tk = findViewById(R.id.tk);

        dn = findViewById(R.id.dang_nhap);
        qmk = findViewById(R.id.doi_mat_khau);
        dn.setOnClickListener(click);
        qmk.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int c = v.getId();
            if(khoa == 1){
            switch (c){
                case R.id.dang_nhap:

                    g[0] = tk.getText().toString();
                    g[1] = mk.getText().toString();
                    if(g[0].equals(bien[0])&&g[1].equals(bien[1]))
                    {   sl=3;khoa=1;
                        Toast.makeText(MainActivity3.this,"ban da dang nhap" +
                                "thanh cong",Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent();
                        i1.setClass(MainActivity3.this,MainActivity.class);
                        startActivity(i1);
                    }
                    else{
                        sl--;
                        if(sl==0)   khoa=0;
                        Toast.makeText(MainActivity3.this,"ban con " + String.valueOf(sl) + " nhap sai mat khau" +
                                " hoac tai khoan",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.doi_mat_khau:
                    if(block_qmk==1){
                    Intent i4 = new Intent();
                    i4.setClass(MainActivity3.this,MainActivity4.class);
                    i4.putExtra("taikhoan",bien[0]);
                    i4.putExtra("matkhau",bien[1]);
                    startActivityForResult(i4,50);}
                    else{
                        Toast.makeText(MainActivity3.this,"ban nhap doi sai mat khau qua nhieu lan",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }else
            Toast.makeText(MainActivity3.this,"ban da nhap sai qua so lan hay lien he nha cung cap",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==50){
            block_qmk=data.getIntExtra("block_qmk",1);
            tk.setText(data.getStringExtra("taikhoan1"));
            mk.setText(data.getStringExtra("matkhau1"));
        }
    }

    enum selection{
        _string,_int,solan,khoa
    }

    @Override
    protected void onStop() {
        super.onStop();
        setlisten("sw2/sl",sl);
        setlisten("sw2/khoa",khoa);
    }

    public void evenlisten(String src, int a, selection s, selection k){

        lightRef1.child(src).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                bien[a]="anh yeu";
                if(s==selection._string)
                bien[a] = snapshot.getValue(String.class);
                else{
                    if(k==selection.solan){
                        sl = snapshot.getValue(Integer.class);
                    }
                    else{
                        khoa = snapshot.getValue(Integer.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private long tim;
    @Override
    public void onBackPressed() {

        if(tim+2000>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else
            Toast.makeText(MainActivity3.this,"Nhap 2 click de thoat",Toast.LENGTH_SHORT).show();
        tim=System.currentTimeMillis();
    }

    public void setlisten(String src, int a){
        lightRef1.child(src).setValue(a);
    }
}