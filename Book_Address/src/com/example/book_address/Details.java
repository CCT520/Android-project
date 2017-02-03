package com.example.book_address;

import java.util.List;

import view.ui.SlideCutListView;

import entity.Contacts;
import entity.SQLiteDBHelper;
import entity.SQLiteDBManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Details extends Activity {

	private String contactid;
	private String fname;
	private String lname;
	private String phone;
	private String email;
	private EditText fnametext;
	private EditText lnametext;
	private EditText phonetext;
	private EditText emailtext;
	private Button deletebt;
	private Button savebt;
	private Button callbt;
	
	private SQLiteDBHelper mDbHelper;
    private SQLiteDBManager mManager;
    private Cursor cursor;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new SQLiteDBHelper(this);
        mManager = new SQLiteDBManager(this);
		setContentView(R.layout.activity_details);
		Bundle bundle= new Bundle();
		bundle=this.getIntent().getExtras();//
		//contactid=bundle.getString("contactId");	
		fname=bundle.getString("fname");
		lname=bundle.getString("lname");
		phone=bundle.getString("phone");
		email=bundle.getString("email");
		System.out.println("details     "+phone);
		fnametext = (EditText)findViewById(R.id.dfname);
		lnametext = (EditText)findViewById(R.id.dlname);
		phonetext = (EditText)findViewById(R.id.dphone);
		emailtext = (EditText)findViewById(R.id.demail);
		deletebt = (Button)findViewById(R.id.deletebt);
		savebt = (Button)findViewById(R.id.savebt);
		callbt = (Button)findViewById(R.id.call_bt);
		
		cursor = mManager.getrecord(email);
		while (cursor.moveToNext()) {
		System.out.println("��һ������һ�� "+cursor.getString(cursor.getColumnIndex("_id")));
		contactid = cursor.getString(cursor.getColumnIndex("_id"));
		}
		fnametext.setText(fname);
		lnametext.setText(lname);
		phonetext.setText(phone);
		emailtext.setText(email);
		
		deletebt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Details.this).setTitle("Reminder")//���öԻ������  
				  
			     .setMessage("Delete this guy?")//������ʾ������  
			  
			     .setPositiveButton("Yes",new DialogInterface.OnClickListener() {//���ȷ����ť  
			    	 @Override  
			         public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
			             // TODO Auto-generated method stub  
			    		 mManager.deleteContacts(phone);
							//System.out.println("Did not pass successfully "+Integer.parseInt(contactid));
							finish();
			         }  
			     }).setNegativeButton("No",new DialogInterface.OnClickListener() {//��ӷ��ذ�ť  
			         @Override  
			         public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�  
			             // TODO Auto-generated method stub  
			  
			         }  
			     }).show();
				
			}
			
		});
		
		savebt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Contacts contacts = new Contacts(fnametext.getText().toString(),
						lnametext.getText().toString(), phonetext.getText().toString(),emailtext.getText().toString());
				contacts.id=Integer.parseInt(contactid);
				mManager.updateContacts(contacts);
				//slideview
				finish();
			}
			
		});
		
		callbt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));  
                startActivity(intent); 
			}
			
		});
	}

	

}
