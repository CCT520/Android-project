package entity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDBManager {

    private SQLiteDBHelper helper;
    private SQLiteDatabase db;

    public SQLiteDBManager(Context context){
        helper = new SQLiteDBHelper(context);
        db = helper.getWritableDatabase();
    }
    public void add(Contacts contacts){
            ContentValues values = new ContentValues();
            values.put("last_name",contacts.last_name);
            values.put("first_name",contacts.first_name);
            values.put("phone_number",contacts.phone_number);
            values.put("email",contacts.email);
            db.insert("Contact_Table",null,values);
        
    }
    public void updateContacts(Contacts contacts){
        ContentValues cv = new ContentValues();
        cv.put("_id",contacts.id);
        cv.put("first_name", contacts.first_name);
        cv.put("last_name", contacts.last_name);
        cv.put("phone_number", contacts.phone_number);
        cv.put("email", contacts.email);
        System.out.println(contacts.id);
        System.out.println("Show upadte infor!!! "+contacts.first_name+" "+contacts.last_name+" "+contacts.phone_number);
        db.update("Contact_Table", cv, "_id = ?", new String[]{String.valueOf(contacts.id)});
    }
    public void deleteContacts(String phone){
    	String[] args = new String[]{String.valueOf(phone)};
    	System.out.println("this record should be deleted "+String.valueOf(args));
        db.delete("Contact_Table", "phone_number=?", args);
    }
    public List<Contacts> query(){
        ArrayList<Contacts> contact = new ArrayList<Contacts>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()){
        	Contacts contacts = new Contacts();
        	contacts.id = c.getInt(c.getColumnIndex("_id"));
        	contacts.first_name = c.getString(c.getColumnIndex("first_name"));
        	contacts.last_name = c.getString(c.getColumnIndex("last_name"));
        	contacts.phone_number = c.getString(c.getColumnIndex("phone_number"));
        	contacts.email = c.getString(c.getColumnIndex("email"));
        	contact.add(contacts);
        	System.out.println("草 数据库类查询的结果"+contacts.last_name+" "+contacts.first_name+" "+contacts.phone_number+" "+contacts.email);
        }
        c.close();
        return contact;
    }
    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM Contact_Table",null);
    	
        return c;
    }
    
    public Cursor getrecord(String email){
    	String sql = "select * from Contact_Table where email = "+email; //select * from 表 where 姓名 = '张三'
    	Cursor c = db.rawQuery("SELECT * FROM Contact_Table where email = ?" , new String [] {String.valueOf(email)});//"SELECT * FROM Contact_Table where email = ?" , new String [] {String.valueOf(email)}
    	return c;
    	}
    public void closeDB(){
        db.close();
    }
}