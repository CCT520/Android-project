package com.example.book_address;

import java.util.List;

import entity.Contacts;
import entity.SQLiteDBHelper;
import entity.SQLiteDBManager;

import android.content.Intent;

public class MyApplication extends android.app.Application {

	
//	private List<ContactBean> contactBeanList;
//	
//	public List<ContactBean> getContactBeanList() {
//		return contactBeanList;
//	}
//	public void setContactBeanList(List<ContactBean> contactBeanList) {
//		this.contactBeanList = contactBeanList;
//	}
	private SQLiteDBHelper mDbHelper;
    private List<Contacts> mPersons;
    private SQLiteDBManager mManager;

	public void onCreate() {
		
		System.out.println("项目启动");
		
//		Intent startService = new Intent(MyApplication.this, T9Service.class);
//		startService(startService);
	}
}
