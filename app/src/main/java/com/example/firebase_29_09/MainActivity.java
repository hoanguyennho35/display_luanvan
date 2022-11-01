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
    TextView[] value = new TextView[4];
    TextView[] textview = new TextView[7];
    ImageView[] imageview = new ImageView[3];
    boolean[] iv_bo = new boolean[3];
    int[] iv= new int[3];       //luu gia tri den
    EditText et1 ;
    int im,tv;
    int ten;
    String mau ="im3 tv7\n0 0 0\nswitch0 \nswitch1\nswitch2" +
            "\ncambien0 \ncambien1\ncambien2\ncambien3";
    String[] name = new String[4];
    boolean a = false;
    FirebaseDatabase firedb = FirebaseDatabase.getInstance();
    final DatabaseReference lightRef = firedb.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        readtxt();
        name[0] = "sw1/cb0";
        name[1] = "sw1/cb1";
        name[2] = "sw1/cb2";
        name[3] = "sw1/cb3";
        for(int i=0;i<7;i++){
            rename(textview,i);     //cap nhat ten
        }
        for(int i=0;i<4;i++){
        evenlisten(name[i],i);}






    }
    public void initView(){
        value[0] = findViewById(R.id.value0);
        value[1] = findViewById(R.id.value1);
        value[2] = findViewById(R.id.value2);
        value[3] = findViewById(R.id.value3);

        imageview[0] = findViewById(R.id.imageView);
        imageview[1] = findViewById(R.id.imageView1);
        imageview[2] = findViewById(R.id.imageView2);

        textview[0] = findViewById(R.id.textview0);
        textview[1] = findViewById(R.id.textView1);
        textview[2] = findViewById(R.id.textView2);
        textview[3] = findViewById(R.id.textView3);
        textview[4] = findViewById(R.id.textView4);
        textview[5] = findViewById(R.id.textView5);
        textview[6] = findViewById(R.id.textView6);

        imageview[0].setOnClickListener(click);
        imageview[1].setOnClickListener(click);
        imageview[2].setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int c =v.getId();
            switch (c){
                case R.id.imageView:
                    iv_bo[0] = !iv_bo[0];
                    if(iv_bo[0])
                    {imageview[0].setImageResource(R.mipmap.motor_on);
                        setlisten("sw1/sw0",1);
                        iv[0]=1;
                    }
                    else
                    {imageview[0].setImageResource(R.mipmap.motor_off);
                        iv[0]=0;
                        setlisten("sw1/sw0",0);}
                        break;
                case R.id.imageView1:
                    iv_bo[1] = !iv_bo[1];
                    if(iv_bo[1])
                    {imageview[1].setImageResource(R.mipmap.motor_on);
                        setlisten("sw1/sw1",1);
                        iv[1]=1;
                    }
                    else
                    {imageview[1].setImageResource(R.mipmap.motor_off);
                        iv[1]=0;
                        setlisten("sw1/sw1",0);}
                    break;
                case R.id.imageView2:
                    iv_bo[2] = !iv_bo[2];
                    if(iv_bo[2])
                    {imageview[2].setImageResource(R.mipmap.bell_on);
                        setlisten("sw1/sw2",1);
                        iv[2]=1;
                    }
                    else
                    {imageview[2].setImageResource(R.mipmap.bell_off);
                        iv[2]=0;
                        setlisten("sw1/sw2",0);}
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        String data;
        data = "im"+String.valueOf(im)+" tv"+String.valueOf(tv)
                +"\n";
        for(int i=0;i<im;i++){
            if(i==im-1)
                data+=String.valueOf(iv[i])+"";
            else
            {data+=String.valueOf(iv[i])+" ";}
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                if(i<2){
                    if(sc.hasNext()){
                        if(Integer.parseInt(sc.next())==1){
                            imageview[i].setImageResource(R.mipmap.motor_on);
                            iv[i]=1;
                            iv_bo[i]=true;
                        }
                        else{
                            imageview[i].setImageResource(R.mipmap.motor_off);
                            iv[i]=0;
                            iv_bo[i]=false;
                        }
                    }
                }
                else{
                    if(sc.hasNext()){
                        if(Integer.parseInt(sc.next())==1){
                            imageview[i].setImageResource(R.mipmap.bell_on);
                            iv[i]=1;
                            iv_bo[i]=true;
                        }
                        else{
                            imageview[i].setImageResource(R.mipmap.bell_off);
                            iv[i]=0;
                            iv_bo[i]=false;
                        }
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
                value[a].setText("" + cb1);
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