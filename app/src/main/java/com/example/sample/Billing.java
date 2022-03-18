package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Billing extends AppCompatActivity {
        SQLiteDatabase db;
        Button b,b1,b2;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
            db= openOrCreateDatabase("shoppings", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS scanbills(itemname VARCHAR,price VARCHAR);");
            Intent in=getIntent();
            String items=in.getStringExtra("items");
            String price=in.getStringExtra("price");
            b=(Button)findViewById(R.id.button7);
            b1=(Button)findViewById(R.id.button8);
            b2=(Button)findViewById(R.id.button9);
            db.execSQL("INSERT INTO scanbills VALUES ('"+items+"','"+price+"');");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor c=db.rawQuery("SELECT * FROM scanbills",null);
                    if(c.getCount()==0)
                    {
                        showMessage("ERROR","NO RECORDS FOUND");
                        return;
                    }
                    StringBuffer buffer=new StringBuffer();
                    while(c.moveToNext())
                    {
                        buffer.append("ITEM NAME : "+c.getString(0)+"\n");
                        buffer.append("PRICE : "+c.getString(1)+"\n");
                    }
                    showMessage("BILL",buffer.toString());


                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer Total=0,i=0;
                    Integer[] s=new Integer[100];
                    Cursor c=db.rawQuery("SELECT * FROM scanbills",null);
                    if(c.getCount()==0)
                    {
                        showMessage("ERROR","NO RECORDS FOUND");
                        return;
                    }
                    StringBuffer buffer=new StringBuffer();
                    while(c.moveToNext())
                    {
                        s[i]=Integer.parseInt(c.getString(1));
                        Total+=s[i];
                        i++;
                    }
                    String np=Integer.toString(Total);
                    showMessage("BILL AMOUNT",np);

                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer Total=0,i=0;
                    Integer[] s=new Integer[100];
                    Cursor c=db.rawQuery("SELECT * FROM scanbills",null);
                    if(c.getCount()==0)
                    {
                        showMessage("ERROR","NO RECORDS FOUND");
                        return;
                    }
                    StringBuffer buffer=new StringBuffer();
                    while(c.moveToNext())
                    {
                        s[i]=Integer.parseInt(c.getString(1));
                        Total+=s[i];
                        i++;
                    }
                    String np=Integer.toString(Total);
                    db.execSQL("DROP TABLE scanbills ");
                    Intent in=new Intent(Billing.this,Payment.class);
                    in.putExtra("np",np);
                    startActivity(in);
                }
            });
        }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}