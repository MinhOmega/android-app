package com.instcar.android.floatwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.instcar.android.R;
import com.instcar.android.entry.MessagePerson;
import com.instcar.android.view.CarTouXiangImg;

public class SeatView  extends LinearLayout {
	
	CarTouXiangImg touxiang;
	TextView name;
	MessagePerson person;
	public static int STATUS_KONGZUO=1;
	public static int STATUS_HAVE=2;
	public static int STATUS_CLOSE=3;
	public static int STATUS_ADD=4;
	public int status=STATUS_KONGZUO;
	
	private StatucChangeListener listener;
	public boolean isbianji=false;
    public SeatView(Context context) {
        super(context, null);
    	init(context);
    }
    public SeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        // 导入布局
    	
        
    }
    public interface StatucChangeListener{
    	
    	void onchange(int status);
    }

    public void setStatusChangeListener(StatucChangeListener listener){
    	this.listener = listener;
    }

    public void init(Context context){
    	String infService = Context.LAYOUT_INFLATER_SERVICE;
    	
    	        LayoutInflater li;
    	 
    	
    	        li = (LayoutInflater) getContext().getSystemService(infService);
    	
    	                /*这句很关键，解析反射资源文件，然后将布局附加到当前的控件，也就是this*/
    	
    	        li.inflate(R.layout.seatview, this, true);
    	
    	 

        touxiang = (CarTouXiangImg) findViewById(R.id.room_user_photo_ss);
        System.out.println(touxiang+"------");
        
        name = (TextView) findViewById(R.id.room_user_name_ss);
        this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isbianji==true&&status!=STATUS_HAVE){//如果是可以编辑并且是空座状态  才可以点击，其他效果点击无效
					if(status==STATUS_CLOSE){
						setOpenStatus();
						if(listener!=null){
							
						listener.onchange(status);
						}
					}else 
					if(status==STATUS_ADD){
						setCloseStatus();
						if(listener!=null){
							
							listener.onchange(status);
						}
					}
				}
				
			}
		});
    }


    public void setPerson(MessagePerson person){
    	this.person = person;
    	//touxiang.setImageBitmap(person.);
    	
    	name.setText(person.phone);
    	
    }
    public void setKongweiStatus(){
    	status=STATUS_KONGZUO;
    	touxiang.setImageResource(R.drawable.seat_empty);
    	name.setText("空位");
    	
    }
    public void setCloseStatus(){
    	status=STATUS_CLOSE;
    	touxiang.setImageResource(R.drawable.seat_close);
    	name.setText("关闭空位");
    }
    
    public void setOpenStatus(){
    	status=STATUS_ADD;
    	touxiang.setImageResource(R.drawable.seat_add);
    	name.setText("打开空位");
    }
    
}
