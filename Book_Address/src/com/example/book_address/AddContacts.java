package com.example.book_address;

import java.util.List;

import entity.Contacts;
import entity.SQLiteDBHelper;
import entity.SQLiteDBManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddContacts extends Activity {

	private EditText f_name;
	private EditText l_name;
	private EditText email;
	private EditText phonenumber;
	private Button cancelbt;
	private Button donebt;
	private SQLiteDBHelper mDbHelper;
    private List<Contacts> mPersons;
    private SQLiteDBManager mManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new SQLiteDBHelper(this);
        mManager = new SQLiteDBManager(this);
		setContentView(R.layout.activity_add_contacts);
		f_name = (EditText)findViewById(R.id.firstname);
		l_name = (EditText)findViewById(R.id.lastname);
		email = (EditText)findViewById(R.id.email);
		phonenumber = (EditText)findViewById(R.id.phonenumber);
		cancelbt = (Button)findViewById(R.id.cancel);
		donebt = (Button)findViewById(R.id.done);
		
		cancelbt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		System.out.println(f_name.getText().toString()+"  "+
				l_name.getText().toString()+"  "+phonenumber.getText().toString()+" "+
				email.getText().toString());
		
		donebt.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(phonenumber.getText().toString()!=null){
					if(f_name.getText().toString()!=null||l_name.getText().toString()!=null){
						mManager.add(new Contacts(l_name.getText().toString(),
								f_name.getText().toString(),phonenumber.getText().toString(),
								email.getText().toString()));
					}
				}
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contacts, menu);
		return true;
	}

}
