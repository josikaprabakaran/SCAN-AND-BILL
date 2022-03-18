package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Upcheck extends AppCompatActivity {
    Button btn;
    EditText e1;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcheck);
        btn=(Button)findViewById(R.id.button5);
        e1=(EditText)findViewById(R.id.editTextPhone2);
        db= openOrCreateDatabase("shoppings", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customerdetail(name VARCHAR,username VARCHAR,phone NUMBER,password NUMBER);");

           btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().equals(""))
                {
                    showMessage("Alert!", "Enter a Number first.");
                    return;
                }

                Cursor c1=db.rawQuery("SELECT * FROM customerdetail WHERE phone='"+e1.getText()+"'",null);
                if(c1.getCount()!=0) {
                    StringBuffer buffer = new StringBuffer();
                    while (c1.moveToNext()) {
                        buffer.append("Username:" + c1.getString(1) + "\n");
                        buffer.append("Password:" + c1.getString(3) + "\n");
                    }
                    showMessage("Login Details ", buffer.toString());
                    clearText();
                }
                else
                {
                    showMessage("Error","No number found");
                    clearText();
                }
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
        e1.requestFocus();
    }

}