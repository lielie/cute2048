package com.example.cute2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	private int number = 0;  //��������
	private TextView tvNumber;  //��ʾ����
	
	public Card(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		//��ʼ��
		tvNumber = new TextView(getContext());
		tvNumber.setTextSize(32);
		tvNumber.setGravity(Gravity.CENTER); //����
		tvNumber.setBackgroundColor(0x33FFFFFF);  //���ñ���
		//tvNumber.setText(number);
		
		//���tvNumber
		LayoutParams lp = new LayoutParams(-1, -1);  //��͸�  1����LayoutParams.MATCH_PARENT��
													//-2����LayoutParams.WRAP_CONTENT
		lp.setMargins(10, 10, 0, 0);  //����ƫ�������ø��ӳ���
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
	
	public boolean equals(Card card) { //�ж�����Card��ֵ�Ƿ����
		return getNumber() == card.getNumber();
	}

}