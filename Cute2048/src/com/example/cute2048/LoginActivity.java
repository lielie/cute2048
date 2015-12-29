package com.example.cute2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	EditText user_name,user_pwd;
	Button login_btn,register_btn;
	UserInformation info,user_info;
	UserInfoDeal info_deal;
	Cursor cursor;
	int flag = 0; //1表示用户密码符合，-1表示密码错误，0表示无此用户
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		info_deal = new UserInfoDeal(getApplicationContext());
		login_btn = (Button)this.findViewById(R.id.login_btn);
		register_btn = (Button)this.findViewById(R.id.btn_register);
		
		login_btn.setOnClickListener(this);
		register_btn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		user_name = (EditText)this.findViewById(R.id.user_name);
		user_pwd = (EditText)this.findViewById(R.id.user_pwd);
		
		info = new UserInformation();
		user_info = new UserInformation();
		
		//传入每次输入的内容
		info.user_name = user_name.getText().toString();
		info.user_pwd = user_pwd.getText().toString();
		
		if(info.user_name.equals("")) {
			Dialog dialog = new AlertDialog.Builder(this)
							.setTitle("提示")
							.setMessage("请输入账号")
							.setPositiveButton("确定", null)
				            .setNegativeButton("取消", null)
							//创建
						    .create();
			dialog.show();
		}
		else if(info.user_name.equals("")) {
			Dialog dialog = new AlertDialog.Builder(this)
							.setTitle("提示")
							.setMessage("请输入密码")
							.setPositiveButton("确定", null)
				            .setNegativeButton("取消", null)
							//创建
						    .create();
			dialog.show();
		}
		else{
			int id = v.getId();
			switch(id) {
			case R.id.login_btn:
				Login();
				break;
			case R.id.btn_register:
				Register();
				break;
			}
		}
			
	}

	public void Register() {
		// TODO Auto-generated method stub
		flag = 0;
		query();
		Log.i("TAG", flag+"");
		if(flag == 0) {
			//注册，并跳转到2048游戏主界面
			Log.i("TAG", "r1");
			info_deal.add_record(info);
			Log.i("TAG", "r2");
			
			user_info.best_score = "0";
			user_info.rank = "0";
			
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("user_name", user_info.user_name);
			intent.putExtra("user_pwd", user_info.user_pwd);
			intent.putExtra("best_score", user_info.best_score);
			intent.putExtra("rank", user_info.rank);	
			startActivity(intent);
		}
		else {
			Dialog dialog = new AlertDialog.Builder(this)
							.setTitle("提示")
							.setMessage("账号已存在")
							.setPositiveButton("确定", null)
				            .setNegativeButton("取消", null)
							//创建
						    .create();
			dialog.show();
		}

	}

	public void Login() {
		// TODO Auto-generated method stub
		flag = 0;
		query();
		if(flag == -1) {
			Dialog dialog = new AlertDialog.Builder(this)
							.setTitle("提示")
							.setMessage("密码错误")
							.setPositiveButton("确定", null)
				            .setNegativeButton("取消", null)
							//创建
						    .create();
			dialog.show();
		}
		else if(flag == 0) {
			Dialog dialog = new AlertDialog.Builder(this)
							.setTitle("提示")
							.setMessage("此账号不存在")
							.setPositiveButton("确定", null)
				            .setNegativeButton("取消", null)
							//创建
						    .create();
			dialog.show();
		}
		else {
			//跳转至2048游戏界面
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("user_name", user_info.user_name);
			intent.putExtra("user_pwd", user_info.user_pwd);
			intent.putExtra("best_score", user_info.best_score);
			intent.putExtra("rank", user_info.rank);	
			startActivity(intent);
		}
	}
	
	public void query() {
		Log.i("TAG", "query1");
		cursor = info_deal.query_record(info);
		Log.i("TAG", "query");
		if(cursor == null) {
			flag = 0;
			Log.i("TAG", "cursor");
		}
		else {
			while(cursor.moveToNext()) {
				Log.i("TAG", "name");
				String name = cursor.getString(1);
				String pwd = cursor.getString(2);
				Log.i("TAG", name+" "+pwd);
				if(name.equals(info.user_name)) {
					if(pwd.equals(info.user_pwd)) {
						Log.i("TAG", info.user_pwd);
						flag = 1;
						
						user_info.user_name = name;
						user_info.user_pwd = pwd;
						user_info.best_score = cursor.getString(3);
						user_info.rank = cursor.getString(4);
						
						break;
					}
					else {
						flag = -1;
						break;
					}
				}
				
				if(cursor.isLast()) {
					break;
				}
			}
			
		}
	}
}
