package com.example.firebase_29_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity2 extends AppCompatActivity {
    Button luu,bo;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        luu = findViewById(R.id.luu);
        bo = findViewById(R.id.bo);
        et = findViewById(R.id.et_name);
        luu.setOnClickListener(mListener);
        bo.setOnClickListener(mListener);
    }
    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent i = new Intent(MainActivity2.this,MainActivity.class);
            switch (id) {
                case R.id.luu:
                    String s = et.getText().toString();
                    i.putExtra("data",s);
                    i.putExtra("selection",true);
                    setResult(100,i);
                    break;
                case R.id.bo:
                    String a = "1";
                    i.putExtra("data",a);
                    i.putExtra("selection",false);
                    setResult(100,i);
                    break;

            }
            finish();
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}