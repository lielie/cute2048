package com.example.cute2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends Activity {

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
*/
	 private int score = 0;
	 private TextView tvScore;
	 private static TextView tvBest;
	 private TextView tvBtn;
	 private static MainActivity mainActivity = null;
	 static int best_num;
	// String user_name, user_pwd, best_score, rank;
	 
	 UserInformation info,user_info;
	 UserInfoDeal info_deal;
	 Cursor cursor;
	 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        user_info = new UserInformation();
        info = new UserInformation();
        info_deal = new UserInfoDeal(getApplicationContext());
        
        Intent intent = getIntent();	
        user_info.user_name = intent.getStringExtra("user_name");
        user_info.user_pwd = intent.getStringExtra("user_pwd");
        user_info.best_score = intent.getStringExtra("best_score");
        user_info.rank = intent.getStringExtra("rank");
        

        tvScore = (TextView)findViewById(R.id.score);
        tvBest = (TextView)findViewById(R.id.best_score);
        tvBtn = (TextView)findViewById(R.id.btn_tv);
        
        tvBest.setText(user_info.best_score);
        
        Integer best_tmp = (Integer.parseInt(user_info.best_score));
    	best_num = best_tmp.intValue();
    	
        SharedPreferences sp = getSharedPreferences("Cute2048", Context.MODE_PRIVATE); 
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("best", 0);
        mainActivity = this;   
        
        tvBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, IntegralRankActivity.class);
				intent.putExtra("user_name", user_info.user_name);
				intent.putExtra("user_pwd", user_info.user_pwd);
				intent.putExtra("best_score", user_info.best_score);
				intent.putExtra("rank", user_info.rank);	
				startActivity(intent);
			}
        	
        });
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(score + "");
        tvBest.setText(best_num + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();
    }

    public int getScore() {
        return score;
    }
    
    public void updateBestScore(int s) {
    	best_num = s;
    	//user_info.best_score = String.valueOf(s);
    	user_info.best_score = String.valueOf(best_num);
    	Log.i("TAG", best_num+"");
    	update();
    	showScore();
    }
    
    public int getBestScore() {
    	return best_num;
    }
    
    public void update() {
    	info_deal.update_record(user_info);
    }
}
