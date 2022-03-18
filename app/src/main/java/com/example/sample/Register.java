package com.example.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity
{
    Button b1;
    EditText e1,e2,e3,e4;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        b1=(Button)findViewById(R.id.button);
        e1=(EditText)findViewById(R.id.editTextfn);
        e2=(EditText)findViewById(R.id.editTextln);
        e3=(EditText)findViewById(R.id.editTextPhone);
        e4=(EditText)findViewById(R.id.editTextPassword);
        db= openOrCreateDatabase("shoppings", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customerdetail(name VARCHAR,username VARCHAR,phone NUMBER,password NUMBER);");

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(e1.getText().equals("") || e2.getText().equals("") || e3.getText().equals("") || e4.getText().equals(""))
                {
                    showMessage("Alert!","Fill all credentials");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM customerdetail WHERE phone='"+e3.getText()+"'",null);
                StringBuffer buffer=new StringBuffer();
                if(c.getCount()!=0)
                {
                    showMessage("Repetition","Already Registered Details");
                    clearText();
                    return;
                }
                db.execSQL("INSERT INTO customerdetail VALUES(' " + e1.getText() + " ',' " + e2.getText() + " ',' " + e3.getText() + " ',' " + e4.getText() + " ');");
                Cursor c1=db.rawQuery("SELECT * FROM customerdetail WHERE phone='"+e3.getText()+"'",null);
                while(c1.moveToNext())
                {
                    buffer.append("Username:"+c1.getString(1)+"\n");
                    buffer.append("Password:"+c1.getString(3)+"\n");
                }
                showMessage("Login Details ",buffer.toString());
                clearText();
            }
        });

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder  builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        e1.requestFocus();
    }
}