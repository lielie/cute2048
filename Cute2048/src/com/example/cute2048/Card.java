package com.example.cute2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	private int number = 0;  //保存数字
	private TextView tvNumber;  //显示数字
	
	public Card(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		//初始化
		tvNumber = new TextView(getContext());
		tvNumber.setTextSize(32);
		tvNumber.setGravity(Gravity.CENTER); //居中
		tvNumber.setBackgroundColor(0x33FFFFFF);  //设置背景
		//tvNumber.setText(number);
		
		//添加tvNumber
		LayoutParams lp = new LayoutParams(-1, -1);  //宽和高  1代表LayoutParams.MATCH_PARENT；
													//-2代表LayoutParams.WRAP_CONTENT
		lp.setMargins(10, 10, 0, 0);  //设置偏移量，让格子出现
		addView(tvNumber, lp);
		
		//setNumber(0);
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
		if(number > 0) {
			tvNumber.setText(String.valueOf(number));
		}
		else {
			tvNumber.setText("");
		}
	}
	
	public boolean equals(Card card) { //判断两个Card的值是否相等
		return getNumber() == card.getNumber();
	}

}