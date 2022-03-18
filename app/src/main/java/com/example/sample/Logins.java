package com.example.sample;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.CharArrayWriter;

public class Logins extends AppCompatActivity
{
    Button b1,b2,b3,b4;
    EditText e1,e2;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);
        b2=(Button)findViewById(R.id.button2);
        b1=(Button)findViewById(R.id.button3);
        b3=(Button)findViewById(R.id.button4);
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        db= openOrCreateDatabase("shoppings", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customerdetail(name VARCHAR,username VARCHAR,phone NUMBER,password NUMBER);");

        b1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(e1.getText().toString().equals("")||e2.getText().toString().equals(""))
                {
                    showMessage("Alert!", "Please Enter All Credentials");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM customerdetail WHERE password='"+e2.getText()+"' ;",null);
                StringBuffer buffer=new StringBuffer();
               if(c.getCount()==0)
                {
                    clearText();
                    showMessage("LoginFAiled", "InvalidLogin");
                    return;
                }
                else
                {
                    clearText();
                    showMessage("LoginSuccess", "Entering into ur account");
                    Intent in=new Intent(Logins.this,ScanItem.class);
                    startActivity(in);
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent in=new Intent(Logins.this,Register.class);
                startActivity(in);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Logins.this,Upcheck.class);
                startActivity(in);
            }
        });
    }

    public void clearText()
    {
        e1.setText("");
        e2.setText("");
        e1.requestFocus();
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder  builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}