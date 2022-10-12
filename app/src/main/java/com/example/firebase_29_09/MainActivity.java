package com.example.firebase_29_09;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView[] value = new TextView[3];
    TextView[] textview = new TextView[3];
    ImageView[] imageview = new ImageView[2];
    boolean[] iv_bo = new boolean[2];
    int[] iv= new int[2];       //luu gia tri den
    EditText et1 ;
    int im,tv;
    int ten;
    String mau ="im1 tv3\n1\nswitch1 \ncambien1 \ncambien2";
    String[] name = new String[2];
    boolean a = false;
    FirebaseDatabase firedb = FirebaseDatabase.getInstance();
    final DatabaseReference lightRef = firedb.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        readtxt();
        name[0] = "sw1/cb1";
        name[1] = "sw1/cb2";
        rename(textview,0);
        rename(textview,1);
        rename(textview,2);
        evenlisten(name[0],1);
        evenlisten(name[1],2);





    }
    public void initView(){
        value[1] = findViewById(R.id.value1);
        value[2] = findViewById(R.id.value2);

        imageview[0] = findViewById(R.id.imageView);

        textview[0] = findViewById(R.id.textview0);
        textview[1] = findViewById(R.id.textView1);
        textview[2] = findViewById(R.id.textView2);
        imageview[0].setOnClickListener(click);
    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int c =v.getId();
            switch (c){
                case R.id.imageView:
                    iv_bo[0] = !iv_bo[0];
                    if(iv_bo[0])
                    {imageview[0].setImageResource(R.mipmap.lamp_on);
                        setlisten("sw1/red",1);
                        iv[0]=1;
                    }
                    else
                    {imageview[0].setImageResource(R.mipmap.lamp_off);
                        iv[0]=0;
                        setlisten("sw1/red",0);}
                        break;
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String data;
        data = "im"+String.valueOf(im)+" tv"+String.valueOf(tv)
                +"\n";
        for(int i=0;i<im;i++){
            if(i==im-1)
            data+=String.valueOf(iv[i])+"";
            else
                data+=String.valueOf(iv[i]);
        }
        data+="\n";
        for(int i=0;i<tv;i++){
            data+=textview[i].getText();
            if(i==tv-1)
                continue;
            data+="\n";
        }
        writetxt("data.txt",data,selection.taomoi);
    }
    public void loaddata(){
        try{
            String buff;
        FileInputStream fin = openFileInput("data.txt");
            Scanner sc = new Scanner(fin);
            if(sc.hasNextLine()){
                buff=sc.nextLine();
                im = Integer.parseInt(String.valueOf(buff.charAt(2)));
                tv = Integer.parseInt(String.valueOf(buff.charAt(6)));
            }

            for(int i=0;i<im;i++){
                if(sc.hasNext()){
                    if(Integer.parseInt(sc.next())==1){
                        imageview[i].setImageResource(R.mipmap.lamp_on);
                        iv[i]=1;
                        iv_bo[i]=true;
                    }
                    else{
                        imageview[i].setImageResource(R.mipmap.lamp_off);
                        iv[i]=0;
                        iv_bo[i]=false;
                    }
                }
            }
            sc.nextLine();
            for (int i=0;i<tv;i++){
                if(sc.hasNextLine())
                textview[i].setText(sc.nextLine());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void readtxt(){
        File fin = new File(getApplicationContext().getFilesDir(),"data.txt");
        if(fin.exists()){
            loaddata();
        }else {
            writetxt("data.txt",mau,selection.taomoi);
            readtxt();
        }
    }
    enum selection{
        taomoi,ghitiep
    }
    public void writetxt(String filename, String data, selection sl){
        try {
            switch (sl) {
                case taomoi:
                    FileOutputStream fout = openFileOutput(filename, MODE_PRIVATE);
                    fout.write(data.getBytes(StandardCharsets.UTF_8));
                    fout.close();
                    break;
                case ghitiep:
                    FileOutputStream fout1 =openFileOutput(filename,MODE_APPEND);
                    fout1.write(data.getBytes(StandardCharsets.UTF_8));
                    fout1.close();
                    break;
            }
        }catch (Exception ex){
            Toast.makeText(MainActivity.this,"Error: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
    //doi ten cac cam bien
    public void rename(TextView[] t,int stt){
        t[stt].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,MainActivity2.class);
                String data="a";
                i.putExtra("data",data);
                ten = stt;
                startActivityForResult(i,100);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Boolean sl = data.getBooleanExtra("selection",false);
            String k=data.getStringExtra("data");
            if(sl){
                textview[ten].setText(k);
            }
        }
    }

    //doc tu firebase luu vao src duong dan get firebase a la cam bien nao cam nhat
    public void evenlisten(String src, int a){

        lightRef.child(src).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer cb1 = snapshot.getValue(Integer.class);
                value[a].setText("cbien "+a+": " + cb1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //thiet lap tu firebase
    public void setlisten(String src, int a){
        lightRef.child(src).setValue(a);
    }
}