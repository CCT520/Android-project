package entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="test.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    //���ݿ��һ�α�����ʱonCreate�ᱻ����
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String command=  "CREATE TABLE IF NOT EXISTS Contact_Table"+
    	        "(_id INTEGER PRIMARY KEY AUTOINCREMENT,last_name VARCHAR,first_name VARCHAR,phone_number VARCHAR, email VARCHAR);";
        db.execSQL(command);
    }
    //���ݿ�����
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Contact_Table");
        onCreate(db);
    }
    //���ݿ⽵��
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
