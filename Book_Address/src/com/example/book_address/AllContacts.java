package com.example.book_address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import entity.Contacts;
import entity.SQLiteDBHelper;
import entity.SQLiteDBManager;

import view.ui.QuickAlphabeticBar;



import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AllContacts extends Activity {

	Button addContactBtn;
	ListView menuList;
	ListView contactListview;
	private List<Map<String,Object>>listitems;
	private SimpleAdapter simpleadapter;
    QuickAlphabeticBar alpha;
    private SQLiteDBHelper mDbHelper;
    private List<Contacts> mPersons;
    private SQLiteDBManager mManager;
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		mDbHelper = new SQLiteDBHelper(this);
        mManager = new SQLiteDBManager(this);
		//initData();
		contactListview = (ListView)findViewById(R.id.contactlist);
		addContactBtn = (Button) findViewById(R.id.addContactBtn);
		alpha = (QuickAlphabeticBar)findViewById(R.id.fast_scroller);
		//initData();
		writeTableInfo(mManager.query());
		addContactBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AllContacts.this, AddContacts.class);
				startActivity(intent);
			}
		});
		
	}
	private void writeTableInfo(List<Contacts> contact){
		listitems= new ArrayList<Map<String,Object>>();
		for(int i=0;i<contact.size();i++)     
		{
			Map<String,Object>listitem= new HashMap<String,Object>();
			listitem.put("fisrtname", contact.get(i).first_name);
			System.out.println("show data from table"+contact.get(i).first_name);
			//listitem.put("Detail", course[i].getAttendance_rate());
			//listitem.put("ID", course[i].getCourseid());
			listitems.add(listitem);
		}
		simpleadapter= new SimpleAdapter(this,listitems,
				R.layout.contact_item,new String[] {"fisrtname", "",""},
				new int[] {R.id.name, R.id.phonenumber, R.id.ID});
		
		contactListview.setAdapter(simpleadapter);
    }
	
//	 private void initData(){
//	        String[] fnames = new String[]{"zhang","zhao","li","wu"};
//	        String[] lnames = new String[]{"h","c","d","b"};
//	        String[] phone = new String[]{"women","men","men","women"};
//
//	        for(int i = 0; i< fnames.length;i++){
//	            Contacts person = new Contacts(fnames[i],lnames[i],phone[i],null);
//	            mPersons.add(person);
//	        }
//	    }
	
}
