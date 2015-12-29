package com.example.cute2048;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserInfoHelper extends SQLiteOpenHelper {
	
	//数据库文件
	private static final String DB_NAME = "Cute2048.db";
	//表名和列名
	public static final String USER_INFO = "UserInfo";
	public static final String USER_NAME = "user_name";
	public static final String USER_PWD = "user_pwd";
	public static final String BEST_SCORE = "best_score";
	public static final String RANK = "rank";
	//版本号
	private static final int DB_VERSION = 1;
	
	public UserInfoHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*String sql = "CREATE TABLE IF NOT EXISTS " + USER_INFO
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME
					+ " VARCHAR(20)," + USER_PWD + " VARCHAR(20)," + BEST_SCORE
					+ " VARCHAR(20)," + RANK + " VARCHAR(20)";*/
		
		Log.i("TAG", "creat_sq1");
		
		String sql = "CREATE TABLE IF NOT EXISTS " + USER_INFO
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME
				+ " VARCHAR(20)," + USER_PWD + " VARCHAR(20),"
				+ BEST_SCORE + " VARCHAR(100),"+ RANK + " VARCHAR(100))";
		
		
		db.execSQL(sql);
		Log.i("TAG", "creat_sq");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
