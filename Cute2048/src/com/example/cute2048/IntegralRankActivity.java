package com.example.cute2048;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class IntegralRankActivity extends ListActivity {
	public class UserInfoPoint{
		int i;
		String user_name;
		int user_score;
		int user_rank;
		
		 public String toString(){
			 return i+"";
		}
	}
	
	class MyComparator implements Comparator {

		@Override
		public int compare(Object obj1, Object obj2) {
			// TODO Auto-generated method stub
			UserInfoPoint tmp1 = (UserInfoPoint)obj1;
			UserInfoPoint tmp2 = (UserInfoPoint)obj2;
			if(tmp1.user_score > tmp2.user_score) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}
	UserInfoPoint users[];
	UserInfoDeal info_deal;
	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		info_deal = new UserInfoDeal(getApplicationContext());
		users = new UserInfoPoint[105];
		count = 0;
		Log.i("TAG", "before display");
		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.activity_integral_rank,
								new String[]{"name", "best score", "rank"},
								new int[]{R.id.name, R.id.score, R.id.rank});
		setListAdapter(adapter);
		
	}

	private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        Log.i("TAG", "before query");
  
        Cursor cursor = info_deal.query_record(null);
        if(cursor == null) {
			Log.i("TAG", "cursor");
		}
        
        ArrayList arrayList = new ArrayList();
        while(cursor.moveToNext()) {
        	//String s = cursor.getString(1);
        	users[count] = new UserInfoPoint();
        	users[count].i = count;
        	users[count].user_name = cursor.getString(1);
        	Integer tmp = Integer.valueOf(cursor.getString(3));
        	Log.i("TAG", cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3));
        	users[count].user_score = tmp.intValue();
        	Log.i("TAG", users[count].user_score+"");
        	arrayList.add(users[count]);
        	count ++;
        }
        
        Log.i("TAG", "before sort");
        
        Object[] tmp_users = arrayList.toArray();
        java.util.Arrays.sort(tmp_users, new MyComparator());
        
        Log.i("TAG", "after sort");
        
        Integer tmp = Integer.valueOf(tmp_users[0].toString());
        int rank_number = 1,total = 0;
        users[tmp.intValue()].user_rank = 1;
        int last = tmp.intValue();
        Log.i("TAGS", last+"t");
        
        for(int j = 1; j < count; j++){
        	tmp = Integer.valueOf(tmp_users[j].toString());
        	int i = tmp.intValue();
        	Log.i("TAGS", i+"");
        	Log.i("TAGS", users[i].user_score+" "+users[last].user_score);
        	if(users[i].user_score == users[last].user_score) {
        		users[i].user_rank = rank_number;
        		total ++;
        	}
        	else {
        		rank_number += (total+1);
        		total = 0;
        		users[i].user_rank = rank_number;
        	}
        	last = i;
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "name");
    	map.put("best score", "best score");
    	map.put("rank", "rank");
    	list.add(map);
   
        for(int j = 0;j < count; j++) {
        	tmp = Integer.valueOf(tmp_users[j].toString());
        	int i = tmp.intValue();
        	
        	map = new HashMap<String, Object>();
        	map.put("name", users[i].user_name);
        	map.put("best score", users[i].user_score);
        	map.put("rank", users[i].user_rank);
        	list.add(map);
        }
         
        return list;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.integral_rank, menu);
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
}
