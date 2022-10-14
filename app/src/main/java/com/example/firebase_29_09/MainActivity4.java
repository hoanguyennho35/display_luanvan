package com.example.firebase_29_09;

import androidx.annotation.NonNull;
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

public class MainActivity4 extends AppCompatActivity {
    String tk,mk;
    EditText edtk,edmk,eddmk;
    Button doi,huy;
    int solan=0;
    FirebaseDatabase firedb = FirebaseDatabase.getInstance();
     DatabaseReference lightRef = firedb.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent i1= getIntent();

        tk = i1.getStringExtra("taikhoan");
        mk = i1.getStringExtra("matkhau");
        init();
    }
    public void init(){
        edtk = findViewById(R.id.edtk);
        edmk = findViewById(R.id.edmk);
        eddmk = findViewById(R.id.eddmk);

        doi = findViewById(R.id.doi);
        huy = findViewById(R.id.huy);

        doi.setOnClickListener(click);
        huy.setOnClickListener(click);
    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int c =v.getId();
            Intent i = new Intent();
            switch (c){
                case R.id.doi:
                    if(tk.equals(edtk.getText().toString())&&mk.equals(edmk.getText().toString())){
                        setlisten("sw2/mk",eddmk.getText().toString());
                        Toast.makeText(MainActivity4.this,"chuc mung doi thanh cong",Toast.LENGTH_SHORT).show();
                        i.putExtra("taikhoan1",edtk.getText().toString());
                        i.putExtra("matkhau1",eddmk.getText().toString());
                        setResult(50,i);
                        finish();
                    }else{
                        solan++;
                        Toast.makeText(MainActivity4.this,"ban nhap sai tai khoan mat khau",Toast.LENGTH_SHORT).show();}
                    if(solan==3){
                        i.putExtra("taikhoan1","");
                        i.putExtra("matkhau1","");
                        i.putExtra("block_qmk",0);
                        setResult(50,i);
                        finish();
                    }
                    break;
                case R.id.huy:
                    i.putExtra("taikhoan1","");
                    i.putExtra("matkhau1","");
                    setResult(50,i);
                    finish();
                    break;
            }

        }
    };

    public void setlisten(String src, String a){
        lightRef.child(src).setValue(a);
    }
}