package com.example.cute2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainView extends GridLayout{

	 private Card[][] card_array = new Card [4][4]; // ��¼��Ϸ
	 private List<Point> emptyPoints = new ArrayList<Point>();    // �յ��б�
	    
	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initMainView();
	}
	
	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initMainView();
	}
	
	public MainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initMainView();
	}
	
	private void initMainView() { //��ʼ������init��ʹ��ͨ�����������캯���������ж�ִ�г�ʼ������
		setColumnCount(4);  //��������
		
		setBackgroundColor(0xFFBBADA0); // ���ñ���
		
		setOnTouchListener(new View.OnTouchListener() {  //�ж��û������������
			
			private float start_x,start_y;
			private float end_x,end_y;
			private float go_x,go_y; //ƫ����
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					start_x = event.getX();
					start_y = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					end_x = event.getX();
					end_y = event.getY();
					
					go_x = end_x-start_x;
					go_y = end_y-start_y;
					
					Log.i("TAG", "start:"+start_x+" "+start_y);
					Log.i("TAG", "end:"+end_x+" "+end_y);
					Log.i("TAG", "go:"+go_x+" "+go_y);
					
					if(Math.abs(go_x) > Math.abs(go_y)) {  //ˮƽ�ƶ�
						if(go_x < -5) {
							//MoveDown();
							MoveLeft();
						} 
						else if(go_x > 5) {
							MoveRight();
						}
					}
					else if(Math.abs(go_x) <= Math.abs(go_y)) {  //��ֱ�ƶ�
						if(go_y < -5) {
							MoveUp();
						}
						else if(go_y > 5) {
							MoveDown();
						}
					}
				}	
				
				return true;
			}
		});
	}
	
	private void MoveLeft() {
		
		boolean move = false;
        
		for(int i = 0;i < 4; i++) {
			for(int j = 0;j < 4; j++) {
				for(int k = j+1;k < 4; k++) {
					if(card_array[i][k].getNumber() > 0) {
						if(card_array[i][j].getNumber() <= 0) {
							card_array[i][j].setNumber(card_array[i][k].getNumber());  //��λ������
							card_array[i][k].setNumber(-1);  //���
							k = j + 1;  //����2 -1 2 ���ϲ�
							move = true;
						}
						else if(card_array[i][j].equals(card_array[i][k])) {
							card_array[i][j].setNumber(card_array[i][j].getNumber()*2);  //��ͬ�����ƣ��ϲ�
							card_array[i][k].setNumber(-1);  //���
							 //�ӷ�
							move = true; 
							
							updateBest(card_array[i][j].getNumber() * 2);
						}
					}
				}
			}
		}
		
		/*for(int k = 0;k < 4; k++) {
			for(int i = 1;i < 4; i++) {
				for(int j = 0;j < 4; j++) {
					if(card_array[i-1][j].getNumber() <= 0) {
						card_array[i-1][j].setNumber(card_array[i][j].getNumber());
						card_array[i][j].setNumber(-1);
					}
				}
			}
		}*/
		
		
		if(move) {
			addRandomNumber(true);
			checkGame();
		}
	}
	
	
	  private void MoveRight() {

	        boolean move = false;  

	        for(int i = 0;i < 4; i++) {   
	            for(int j = 3;j >= 0; j--) {   
	                for(int k = j-1;k >= 0; k--) {    
	                    if(card_array[i][k].getNumber() > 0) {
	                        if(card_array[i][j].getNumber() <= 0) {   
	                            card_array[i][j].setNumber(card_array[i][k].getNumber());
	                            card_array[i][k].setNumber(-1); 
	                            k = j - 1;    
	                            move = true;    
	                        } else if(card_array[i][j].equals(card_array[i][k])) {   
	                            card_array[i][j].setNumber(card_array[i][j].getNumber() * 2); 
	                            card_array[i][k].setNumber(-1); 
	                            MainActivity.getMainActivity().addScore(card_array[i][j].getNumber());
	                            move = true;   
	                           
	                            updateBest(card_array[i][j].getNumber() * 2);
	                        }
	                    }
	                }
	            }
	        }

	        if(move) {  
	            addRandomNumber(true);
	            checkGame();    
	        }
	    }	    
	  
	 private void MoveUp() {

	        boolean move = false;   

	        for(int j = 0;j < 4; j++) {   
	            for(int i = 0;i < 4; i++) {   
	                for(int k = i+1;k < 4; k++) {   
	                    if(card_array[k][j].getNumber() > 0) {
	                        if(card_array[i][j].getNumber() <= 0) {   
	                            card_array[i][j].setNumber(card_array[k][j].getNumber()); 
	                            card_array[k][j].setNumber(-1);
	                            k = i + 1;    
	                            move = true;   
	                        } else if(card_array[i][j].equals(card_array[k][j])) {   
	                            card_array[i][j].setNumber(card_array[i][j].getNumber() * 2); 
	                            card_array[k][j].setNumber(-1);
	                            
	                            move = true;    
	                            
	                            updateBest(card_array[i][j].getNumber() * 2);
	                        }
	                    }
	                }
	            }
	        }

	        if(move) { 
	            addRandomNumber(true);
	        }
	    }
	 
	    private void MoveDown() {

	        boolean move = false;   

	        for(int j = 0;j < 4; j++) {   
	            for(int i = 3;i >= 0; i--) {   
	                for(int k = i-1;k >= 0; k--) {    
	                    if(card_array[k][j].getNumber() > 0) {
	                        if(card_array[i][j].getNumber() <= 0) {    
	                            card_array[i][j].setNumber(card_array[k][j].getNumber()); 
	                            card_array[k][j].setNumber(-1); 
	                            k = i - 1;    
	                            move = true;    
	                        } else if(card_array[i][j].equals(card_array[k][j])) {    
	                            card_array[i][j].setNumber(card_array[i][j].getNumber() * 2);
	                            card_array[k][j].setNumber(-1);
	                            
	                            move = true;    
	                            
	                            updateBest(card_array[i][j].getNumber() * 2);
	                        }
	                    }
	                }
	            }
	        }

	        if(move) {  
	            addRandomNumber(true);
	        }
	    }
	    
	private void checkGame() {
		
		boolean finish = true,great = false;
		int num = 0;
		
		for(int i = 0;i < 4; i++) {
			for(int j = 0;j < 4; j++) {
				if(card_array[i][j].getNumber() >= 2048){
					great = true;
					num = card_array[i][j].getNumber();
					break;
				}
				if(card_array[i][j].getNumber() <= 0 ||
						(i > 0 && card_array[i][j].equals(card_array[i-1][j])) ||
						(i < 3 && card_array[i][j].equals(card_array[i+1][j])) ||
						(j > 0 && card_array[i][j].equals(card_array[i][j-1])) ||
						(j < 3 && card_array[i][j].equals(card_array[i][j+1])) ) {
					finish = false; //��Ϸδ������
					//break;
				}
					
			}
		}
		
		
		if(finish) {
			String str;
			if(great) {
				str = "��ϲ��Ӯ������ﵽ��"+String.valueOf(num)+"Ŷ��";
			}
			else {
				str = "���ź�Ŷ��";
			}
			new AlertDialog.Builder(getContext()).setTitle("2048")
			.setMessage(str)
			.setPositiveButton("���¿�ʼ", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					startGame();
				}
			}).show();
		}
	}
	
	protected void onSizeChanged(int weight,int height,int oldw,int oldh) {
		super.onSizeChanged(weight, height, oldw, oldh);
		
		int Size = (Math.min(weight, height) - 10) / 4;  //���㿨�Ƴߴ�
		addCards(Size);
		
		startGame();
	}
	
	private void startGame() {
		
		MainActivity.getMainActivity().clearScore();
		
		for(int i = 0;i < 4; i++) {
			for(int j = 0;j < 4; j++) {
				card_array[i][j].setNumber(-1);  //������и���
			}
		}
		
		//��ʼ��������Ƭ
		addRandomNumber(false);
		addRandomNumber(false);
	}
	
	private void addCards(int Size) {
		Card card;
		for(int i = 0;i < 4; i++) {
			for(int j = 0;j < 4; j++) {
				card = new Card(getContext());
				card.setNumber(-1);  //һ��ʼ���ɵĶ��ǿյ�
				card_array[i][j] = card;  //���µ�ǰcard
				addView(card, Size, Size);
			}
		}
		
		//��ʼ��������Ƭ
		addRandomNumber(false);
		addRandomNumber(false);
	}
		
	private void addRandomNumber(boolean flag) {
		emptyPoints.clear();  //��տո��б�
		
		for(int i = 0;i < 4; i++) {
			for(int j = 0;j < 4; j++) {
				if(card_array[i][j].getNumber() <= 0) {
					emptyPoints.add(new Point(i,j));
				}
			}
		}
		
		Point point  = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));  //�漴��ȡ�յ�
		if(flag == true) { 
			int num = Math.random() > 0.1 ? 2 : 4;
			card_array[point.x][point.y].setNumber(num);  //��1:9����2��4
		}
		else if(flag == false) {
			card_array[point.x][point.y].setNumber(2);	//��ʼ��������2
		}
	}
	
	private void updateBest(int add_s) {
        int bestScore, score;
      
        MainActivity.getMainActivity().addScore(add_s); 
        score = MainActivity.getMainActivity().getScore();
        bestScore = MainActivity.getMainActivity().getBestScore();
        
        
        if (bestScore < score) {
            MainActivity.getMainActivity().updateBestScore(score); //score��ʱ������һ��
        }
    }
	
	
}

