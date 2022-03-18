package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanItem extends AppCompatActivity implements View.OnClickListener{
    Button scanbtn;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);
        scanbtn=(Button)findViewById(R.id.scanbtn);
        scanbtn.setOnClickListener(this);
        db= openOrCreateDatabase("shoppings", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS scanresult(itemname VARCHAR,itemcode VARCHAR,itemrate NUMBER);");
        db.execSQL("INSERT INTO scanresult VALUES('HORLICKS','1234567890',5);");
        db.execSQL("INSERT INTO scanresult VALUES('MANGALDEEP AGARBATTIS','39123439',50);");
        db.execSQL("INSERT INTO scanresult VALUES('SUNDHARI SAMBRANI','8904030999315',22);");
        db.execSQL("INSERT INTO scanresult VALUES('MYSORE SANDAL POWDER','8901287308013',25);");
        db.execSQL("INSERT INTO scanresult VALUES('MR FEVICOL','8901860630616',70);");
        db.execSQL("INSERT INTO scanresult VALUES('UNIVERSAL HEADPHONES','1566416135426',100);");
        db.execSQL("INSERT INTO scanresult VALUES('TWINKLE COCKROACH KILL CHALK','8906029850430',15);");

    }

    @Override
    public void onClick(View v) {
        scancode();
    }
    private void scancode()
    {
        IntentIntegrator integrator=new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() != null)
            {
                final Cursor c1 = db.rawQuery("SELECT * FROM scanresult ", null);
                while (c1.moveToNext())
                {
                    if (result.getContents().equals(c1.getString(1)))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(c1.getString(0)+" "+c1.getString(2));
                        builder.setTitle("Scanning Result ");
                        builder.setPositiveButton("ADD TO CART ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent in=new Intent(ScanItem.this,Billing.class);
                                in.putExtra("items",c1.getString(0));
                                in.putExtra("price",c1.getString(2));
                                startActivity(in);
                                //scancode();
                            }
                        });
                        builder.setNegativeButton("finish", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }
            }
            else
            {
                Toast.makeText(this, "No Result", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    }
