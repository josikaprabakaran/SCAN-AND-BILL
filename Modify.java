package com.example.attendence;



import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Modify extends Activity {
	EditText e1,e2,e3;
	Button b1,b2,b3,b4,b5;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);

	 e1=(EditText)findViewById(R.id.editText1);
	 e2=(EditText)findViewById(R.id.editText2);
	
	 b1=(Button)findViewById(R.id.button1);
     b2=(Button)findViewById(R.id.button2);
     b3=(Button)findViewById(R.id.button3);
     b4=(Button)findViewById(R.id.button4);
     b5=(Button)findViewById(R.id.button5);
    
     db= openOrCreateDatabase("studentattendsn",Context.MODE_PRIVATE,null);
	 db.execSQL("CREATE TABLE IF NOT EXISTS exampletable (rollno NUMBER,name VARCHAR);");
     b1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 
			if(e1.getText().toString().trim().length()==0||e2.getText().toString().trim().length()==0)
			{
				showMessage("ERROR","Please enter all values");
				return;
			}
			Cursor c=db.rawQuery("SELECT * FROM exampletable WHERE rollno='"+e1.getText()+"'",null);
			if(c.getCount()==0)
			{
			db.execSQL("INSERT INTO exampletable VALUES(' "+e1.getText()+" ',' "+e2.getText()+" ');");
			showMessage("SUCCESS","RECORD ADDED");
			clearText();
			}
			else
			{
				showMessage("Repetition","Already Registered Rollno");
			}
		}
	});
   
     b2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(e1.getText().toString().length()==0)
			{
				showMessage("ERROR","PLEASE ENTER ROLLNO");
				return;
			}
			Cursor c=db.rawQuery("SELECT * FROM exampletable WHERE rollno=' "+e1.getText()+" ' ",null);
			if(c.moveToFirst())
			{
				db.execSQL("UPDATE exampletable SET name=' "+e2.getText()+" ' where rollno=' "+e1.getText()+" ' ");
				showMessage("SUCCESS","RECORD MODIFIED");
			}
			
			else
			{
				showMessage("ERROR","Invalid DATA to Modify.");
			}
			clearText();
		}
	});
     b3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(e1.getText().toString().length()==0)
			{
				showMessage("ERROR","PLEASE ENTER ROLLNO");
				return;
			}
			Cursor c=db.rawQuery("SELECT * FROM exampletable WHERE rollno=' "+e1.getText()+" ' ",null);
			if(c.moveToFirst())
			{
				db.execSQL("DELETE FROM exampletable where rollno=' "+e1.getText()+" ' ");
				showMessage("SUCCESS","RECORD DELETED");
				
			}
			else
			{
				showMessage("ERROR","No Records Found.");
			}
			clearText();
		
		}
	});
     b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(e1.getText().toString().length()==0)
				{
					showMessage("ERROR","PLEASE ENTER ROLLNO");
					return;
				}
				
			Cursor c=db.rawQuery("SELECT * FROM exampletable WHERE rollno=' "+e1.getText()+" ' ",null);
			if(c.moveToFirst())
			{
			e1.setText(c.getString(0));
			e2.setText(c.getString(1));
			}
			else
			{
				showMessage("ERROR","No Records Found.");
				clearText();
			}
			
			}
		});
     b5.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			Cursor c=db.rawQuery("SELECT * FROM exampletable",null);
			if(c.getCount()==0)
			{
				showMessage("ERROR","NO RECORDS FOUND");
				return;
			}
			StringBuffer buffer=new StringBuffer();
			while(c.moveToNext())
			{
			buffer.append("ROLLNO:"+c.getString(0)+"\n");
			buffer.append("NAME:"+c.getString(1)+"\n");
						}
			showMessage("STUDENT DETAILS",buffer.toString());
		
		}
	});
}
public void showMessage(String title,String message)
{
	Builder builder=new Builder(this);
	builder.setCancelable(true);
	builder.setTitle(title);
	builder.setMessage(message);
	builder.show();
}
public void clearText()
{
	e1.setText("");
	e2.setText("");
	e1.requestFocus();
}
}
