package com.example.cute2048;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//执行所有与数据库相关的操作
public class UserInfoDeal {

	private UserInfoHelper dbHelper;
	private SQLiteDatabase db;
	
	//初始化、创建数据库
	public UserInfoDeal(Context context) {
		Log.i("TAG", "before new");
		dbHelper = new UserInfoHelper(context);
		Log.i("TAG", "after new");
		db = dbHelper.getWritableDatabase();
		Log.i("TAG", "creat");
	}
	
	//添加一行记录
	public void add_record(UserInformation info) {
		String sql = "CREATE TABLE IF NOT EXISTS " + UserInfoHelper.USER_INFO
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + UserInfoHelper.USER_NAME
				+ " VARCHAR(20)," + UserInfoHelper.USER_PWD + " VARCHAR(20),"
				+ UserInfoHelper.BEST_SCORE + " VARCHAR(100),"+ UserInfoHelper.RANK + " VARCHAR(100))";
		db.execSQL(sql);
		
		ContentValues cv = new ContentValues();
		info.best_score = "0";
		info.rank = "0";
		
		cv.put(UserInfoHelper.USER_NAME, info.user_name);
		cv.put(UserInfoHelper.USER_PWD, info.user_pwd);
		cv.put(UserInfoHelper.BEST_SCORE, info.best_score);
		cv.put(UserInfoHelper.RANK, info.rank);
		
		long num = db.insert(UserInfoHelper.USER_INFO, UserInfoHelper.USER_NAME, cv);
		Log.i("TAG", num+"");
	}
	
	//更新
	public void update_record(UserInformation info) {
		ContentValues cv = new ContentValues();
		cv.put(UserInfoHelper.USER_NAME, info.user_name);
		cv.put(UserInfoHelper.USER_PWD, info.user_pwd);
		cv.put(UserInfoHelper.BEST_SCORE, info.best_score);
		cv.put(UserInfoHelper.RANK, info.rank);
		Log.i("MyTAG", "666");
		db.update(UserInfoHelper.USER_INFO, cv, UserInfoHelper.USER_NAME + "=?", 
				new String[] { info.user_name } );
	}
	
	//查询
	public Cursor query_record(UserInformation info) {
	/*	ContentValues cv = new ContentValues();
		cv.put(UserInfoHelper.USER_NAME, info.user_name);*/
		//Log.i("TAG", "before_query"+" "+info.user_name);
		/*db.query(true, UserInfoHelper.USER_INFO, null,
				UserInfoHelper.USER_NAME + " like ?",
				new String[] { info.user_name }, null, null,
				UserInfoHelper.USER_NAME + " desc", null);*/
	
		 /*db.query(true, UserInfoHelper.USER_INFO, null,
				UserInfoHelper.USER_NAME + " like ?",
				new String[] { info.user_name }, null,
				null,UserInfoHelper.USER_NAME + " desc", null);*/
		//db.rawQuery("SELECT * FROM UserInfo", null);
		
		//db.rawQuery("SELECT * FROM "+ UserInfoHelper.USER_INFO + " WHERE " + UserInfoHelper.USER_NAME + " = ?" , new String[]{info.user_name});
		 Log.i("TAG", "after_query");
		 
		 return db.rawQuery("SELECT * FROM UserInfo", null);
	}
	
	
}
