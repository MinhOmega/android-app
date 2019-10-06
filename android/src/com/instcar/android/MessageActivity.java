package com.instcar.android;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.Line;
import com.instcar.android.entry.MessagePerson;
import com.instcar.android.entry.NetDataEntry;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.entry.Room;
import com.instcar.android.floatwindow.FloatWindowBigView;
import com.instcar.android.floatwindow.FloatWindowBigViewPassager;
import com.instcar.android.im.ChatMsgEntity;
import com.instcar.android.im.ChatMsgViewAdapter;
import com.instcar.android.im.Expressions;
import com.mycommonlib.android.common.util.JSONUtils;

public class MessageActivity extends BaseActivity implements OnClickListener {
	private Context mCon;
	private ViewPager viewPager;
	private ArrayList<GridView> grids;
	private int[] expressionImages;
	private String[] expressionImageNames;
	private int[] expressionImages1;
	private String[] expressionImageNames1;
	private int[] expressionImages2;
	private String[] expressionImageNames2;
	private Button mBtnSend;
	private ImageButton voiceBtn;
	private ImageButton keyboardBtn;
	private ImageButton biaoqingBtn;
	private ImageButton biaoqingfocuseBtn;
	private LinearLayout ll_fasong;
	private LinearLayout ll_yuyin;
	private LinearLayout page_select;
	private ImageView page0;
	private ImageView page1;
	private ImageView page2;
	private EditText mEditTextContent;
	private ListView mListView;
	private GridView gView1;
	private GridView gView2;
	private GridView gView3;
	private String roomid;
	private String openfire;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private static LayoutParams smallWindowParams;
	XMPPConnection connection;
	MultiUserChat muc;

	//需要初始化才能用的信息
	TextView numberSeat;
	PopupWindow mPop;
	Room room = new Room();// 存放房间信息
	List<MessagePerson> person = new ArrayList<MessagePerson>();
	MessagePerson owner = new MessagePerson();
	MessagePerson currentPerson = new MessagePerson();
	Line line = new Line();
	

	public int currentUserNum = 0;
	private static FloatWindowBigView bigWindow;

	private boolean ischengke = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_message);
		roomid = getIntent().getExtras().getString("roomid");
		openfire = getIntent().getExtras().getString("openfire");
		ischengke = getIntent().getExtras().getBoolean("ischengke", true);// 默认是成个角色

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mCon = MessageActivity.this;
		ll_fasong = (LinearLayout) findViewById(R.id.ll_fasong);
		ll_yuyin = (LinearLayout) findViewById(R.id.ll_yuyin);
		page_select = (LinearLayout) findViewById(R.id.page_select);
		page0 = (ImageView) findViewById(R.id.page0_select);
		page1 = (ImageView) findViewById(R.id.page1_select);
		page2 = (ImageView) findViewById(R.id.page2_select);
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		// 引入表情
		expressionImages = Expressions.expressionImgs;
		expressionImageNames = Expressions.expressionImgNames;
		expressionImages1 = Expressions.expressionImgs1;
		expressionImageNames1 = Expressions.expressionImgNames1;
		expressionImages2 = Expressions.expressionImgs2;
		expressionImageNames2 = Expressions.expressionImgNames2;
		// 创建ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// 发送
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		// 返回

		// 个人信息

		// 语音
		voiceBtn = (ImageButton) findViewById(R.id.chatting_voice_btn);
		voiceBtn.setOnClickListener(this);
		// 键盘
		keyboardBtn = (ImageButton) findViewById(R.id.chatting_keyboard_btn);
		keyboardBtn.setOnClickListener(this);
		// 表情
		biaoqingBtn = (ImageButton) findViewById(R.id.chatting_biaoqing_btn);
		biaoqingBtn.setOnClickListener(this);
		biaoqingfocuseBtn = (ImageButton) findViewById(R.id.chatting_biaoqing_focuse_btn);
		biaoqingfocuseBtn.setOnClickListener(this);

		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		numberSeat = (TextView) findViewById(R.id.numberseat);
		initSPopupView();
		numberSeat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPop.isShowing()) {
					mPop.dismiss();
				} else {
					mPop.showAsDropDown(numberSeat, 0, -50);
				}
			}
		});

		initViewPager();
		initData();
		initHandle();
		initBaseNav();
		btn_right.setVisibility(View.GONE);
		showProcessDialog("正在进入聊天室");
		new Thread(new SmackThread()).start();
		// 获取room详情
		getroominfo(roomid);

	}

	private void initViewPager() {
		LayoutInflater inflater = LayoutInflater.from(this);
		grids = new ArrayList<GridView>();
		gView1 = (GridView) inflater.inflate(R.layout.grid1, null);
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		// 生成24个表情
		for (int i = 0; i < 24; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image", expressionImages[i]);
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(mCon, listItems,
				R.layout.singleexpression, new String[] { "image" },
				new int[] { R.id.image });
		gView1.setAdapter(simpleAdapter);
		gView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(),
						expressionImages[arg2 % expressionImages.length]);
				ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
				SpannableString spannableString = new SpannableString(
						expressionImageNames[arg2].substring(1,
								expressionImageNames[arg2].length() - 1));
				spannableString.setSpan(imageSpan, 0,
						expressionImageNames[arg2].length() - 2,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				// 编辑框设置数据
				mEditTextContent.append(spannableString);
				System.out.println("edit的内容 = " + spannableString);
			}
		});
		grids.add(gView1);

		gView2 = (GridView) inflater.inflate(R.layout.grid2, null);
		grids.add(gView2);

		gView3 = (GridView) inflater.inflate(R.layout.grid3, null);
		grids.add(gView3);

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return grids.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(grids.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(grids.get(position));
				return grids.get(position);
			}
		};

		viewPager.setAdapter(mPagerAdapter);
		// viewPager.setAdapter();

		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	}

	void initMucRoom(){

	}
	/*
	 * 
	 */
	
	void initSmack() {
		try {

			if(connection==null){
				ConnectionConfiguration config = new ConnectionConfiguration(
						"115.28.231.132", 13000);
				config.setSASLAuthenticationEnabled(false);  
				config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);  
				connection = new XMPPConnection(config);

			}
			if(!connection.isConnected()){
				connection.connect();
				connection.login(av.getUserData().phone, "123456");
				Presence presence = new Presence(Presence.Type.unavailable);
				connection.sendPacket(presence);
			}
			if(muc==null){
				muc = new MultiUserChat(connection, openfire);
			}
			if(!muc.isJoined()){
				muc.join(av.getUserData().phone);
			}
			
			muc.addMessageListener(new PacketListener() {

				@Override
				public void processPacket(Packet packet) {
					// TODO Auto-generated method stub
					
					Message message = (Message) packet;
					System.out.println(message.getFrom()+"---"+message.getBody());
					ChatMsgEntity entity = new ChatMsgEntity();
					entity.setDate(getDate());
					String[] names = message.getFrom().split("/");
					if (!names[1].equals(av.getUserData().phone)) {
						entity.setName(names[1]);
						entity.setMsgType(true);
						entity.setText(message.getBody());
						mDataArrays.add(entity);
						mAdapter.notifyDataSetChanged();
						mListView.setSelection(mListView.getBottom());
						if(!names[1].equals("admin")){
							
							
						}
					}
				}
			});
			mHandler.sendEmptyMessage(4);
		} catch (Exception e) {
			dismissProcessDialog();
			// TODO: handle exception
			mHandler.sendEmptyMessage(3);
			e.printStackTrace();
		}

	}

	void uodateSmallView(int number) {
		numberSeat.setText(number + "");
		int have = number;
		int all = Integer.valueOf(room.max_seat_num);
		int background = R.drawable.a1number0;
		if (all == 2 && have == 0) {
			background = R.drawable.a2number0;
		}
		if (all == 2 && have == 1) {
			background = R.drawable.a2number1;
		}
		if (all == 2 && have == 2) {
			background = R.drawable.a2number2;
		}
		if (all == 3 && have == 0) {
			background = R.drawable.a3number0;
		}
		if (all == 3 && have == 1) {
			background = R.drawable.a3number1;
		}
		if (all == 3 && have == 2) {
			background = R.drawable.a3number2;
		}
		if (all == 3 && have == 3) {
			background = R.drawable.a3number3;
		}
		if (all == 4 && have == 0) {
			background = R.drawable.a4number0;
		}
		if (all == 4 && have == 1) {
			background = R.drawable.a4number1;
		}
		if (all == 4 && have == 2) {
			background = R.drawable.a4number2;
		}
		if (all == 4 && have == 3) {
			background = R.drawable.a4number3;
		}
		if (all == 4 && have == 4) {
			background = R.drawable.a4number4;
		}

		numberSeat.setBackgroundResource(background);
	}

	private void initData() {

		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);
		return sbBuffer.toString();
	}

	public class SmackThread implements Runnable {

		@Override
		public void run() {
			initSmack();
		}
	}

	@Override
	public void onClick(View v) {
		boolean isFoused = false;
		switch (v.getId()) {
		// 返回

		// 发送
		case R.id.btn_send:
			String content = mEditTextContent.getText().toString();
			if (content.length() > 0) {
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setName(av.getUserData().phone);
				entity.setMsgType(false);
				entity.setText(MessageActivity.this.mekeMessage(content));
				
				try {
					if(muc!=null&&muc.isJoined()){
						muc.sendMessage(content);
					}else{
						showToastError("聊天室无链接");
					}
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDataArrays.add(entity);
				// 更新listview
				mEditTextContent.setText("");
				viewPager.setVisibility(ViewPager.GONE);
				page_select.setVisibility(page_select.GONE);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mListView.getCount() - 1);
			} else {
				Toast.makeText(mCon, "不能发送空消息", Toast.LENGTH_LONG).show();
			}
			break;
		// 个人信息

		// 语音
		case R.id.chatting_voice_btn:
			voiceBtn.setVisibility(voiceBtn.GONE);
			keyboardBtn.setVisibility(keyboardBtn.VISIBLE);
			ll_fasong.setVisibility(ll_fasong.GONE);
			ll_yuyin.setVisibility(ll_yuyin.VISIBLE);
			break;
		// 键盘
		case R.id.chatting_keyboard_btn:
			voiceBtn.setVisibility(voiceBtn.VISIBLE);
			keyboardBtn.setVisibility(keyboardBtn.GONE);
			ll_fasong.setVisibility(ll_fasong.VISIBLE);
			ll_yuyin.setVisibility(ll_yuyin.GONE);
			break;
		// 表情
		case R.id.chatting_biaoqing_btn:
			biaoqingBtn.setVisibility(biaoqingBtn.GONE);
			biaoqingfocuseBtn.setVisibility(biaoqingfocuseBtn.VISIBLE);
			viewPager.setVisibility(viewPager.VISIBLE);
			page_select.setVisibility(page_select.VISIBLE);

			break;
		case R.id.chatting_biaoqing_focuse_btn:
			biaoqingBtn.setVisibility(biaoqingBtn.VISIBLE);
			biaoqingfocuseBtn.setVisibility(biaoqingfocuseBtn.GONE);
			viewPager.setVisibility(viewPager.GONE);
			page_select.setVisibility(page_select.GONE);
			break;
		}

	}

	// 点击小黑图像
	public void head_xiaohei(View v) { // 标题栏 返回按钮
	}

	// ** 指引页面改监听器 */
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));

				break;
			case 1:
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
				// 生成24个表情
				for (int i = 0; i < 24; i++) {
					Map<String, Object> listItem = new HashMap<String, Object>();
					listItem.put("image", expressionImages1[i]);
					listItems.add(listItem);
				}

				SimpleAdapter simpleAdapter = new SimpleAdapter(mCon,
						listItems, R.layout.singleexpression,
						new String[] { "image" }, new int[] { R.id.image });
				gView2.setAdapter(simpleAdapter);
				gView2.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Bitmap bitmap = null;
						bitmap = BitmapFactory.decodeResource(getResources(),
								expressionImages1[arg2
										% expressionImages1.length]);
						ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
						SpannableString spannableString = new SpannableString(
								expressionImageNames1[arg2]
										.substring(1,
												expressionImageNames1[arg2]
														.length() - 1));
						spannableString.setSpan(imageSpan, 0,
								expressionImageNames1[arg2].length() - 2,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						// 编辑框设置数据
						mEditTextContent.append(spannableString);
					}
				});
				break;
			case 2:
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				List<Map<String, Object>> listItems1 = new ArrayList<Map<String, Object>>();
				// 生成24个表情
				for (int i = 0; i < 24; i++) {
					Map<String, Object> listItem = new HashMap<String, Object>();
					listItem.put("image", expressionImages2[i]);
					listItems1.add(listItem);
				}

				SimpleAdapter simpleAdapter1 = new SimpleAdapter(mCon,
						listItems1, R.layout.singleexpression,
						new String[] { "image" }, new int[] { R.id.image });
				gView3.setAdapter(simpleAdapter1);
				gView3.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Bitmap bitmap = null;
						bitmap = BitmapFactory.decodeResource(getResources(),
								expressionImages2[arg2
										% expressionImages2.length]);
						ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
						SpannableString spannableString = new SpannableString(
								expressionImageNames2[arg2]
										.substring(1,
												expressionImageNames2[arg2]
														.length() - 1));
						spannableString.setSpan(imageSpan, 0,
								expressionImageNames2[arg2].length() - 2,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						// 编辑框设置数据
						mEditTextContent.append(spannableString);
					}
				});
				break;

			}
		}
	}

	/*
	 * 返回的原始数据为{"status":200,"data":{"id":"209","openfire":"",
	 * "user_id":"18600869986","line_id":"2","price":"10.00","status":"0",
	 * "start_time":"2014-05-22","max_seat_num":"4",
	 * "description":"\u54c8\u54c8","addtime":"2014-05-23 00:15:04",
	 * "modtime":"2014-05-23 00:15:04"
	 * },"msg":"\u83b7\u53d6\u623f\u95f4\u4fe1\u606f\u6210\u529f"}
	 */
	void updteRoomInfo(String data) {
		try {

			JSONObject obj = new JSONObject(data).getJSONObject("data");
			JSONObject tmproom = obj.getJSONObject("room");
			JSONObject tmpuser = obj.getJSONObject("user");

			room.roomid = tmproom.getString("id");
			room.roomdesc = tmproom.getString("description");
			room.createTime = tmproom.getString("addtime");
			room.openfireid = tmproom.getString("openfire");
			room.userid = tmproom.getString("user_id");
			room.lineid = tmproom.getString("line_id");
			room.max_seat_num = tmproom.getString("max_seat_num");
			room.have_seat_num = JSONUtils.getString(tmproom,
					"booked_seat_num", "0");
			room.roomkongwei = (Integer.valueOf(room.max_seat_num) - Integer
					.valueOf(room.have_seat_num)) + "";

			owner.id = tmpuser.getString("id");
			owner.name = tmpuser.getString("name");
			owner.phone = tmpuser.getString("phone");

			uodateSmallView(Integer.valueOf(room.have_seat_num));
			// 更新条小图标
			if (currentUserNum != Integer.valueOf(room.have_seat_num)) {// 如果俩不一样，就更新userlist
				currentUserNum = Integer.valueOf(room.have_seat_num);
				QueryRoomUsers(roomid);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void initRoomData(){
		
		
	}
	void initSPopupView() {
		currentPerson.id = av.getUserData().id;
		currentPerson.phone = av.getUserData().phone;
		currentPerson.pic = av.getUserData().headpic;
		if (ischengke == true) {
			bigWindow = new FloatWindowBigViewPassager(this, owner,
					currentPerson, room);
			setOptClicker();//设置optbutton的点击
		} else {
			// bigWindow =new FloatWindowBigViewDriver(this,4,2);
		}
		mPop = new PopupWindow(bigWindow, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPop.setBackgroundDrawable(new BitmapDrawable());
		mPop.setOutsideTouchable(true);
		// mPop.showAsDropDown(findViewById(R.id.small_window_layout),0,-50);

	}

	void initHandle() {

		mHandler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);

				switch (msg.what) {
				case HandleConfig.GETROOMINFO:// 获取room详情，并且1分钟更新一次
					dismissProcessDialog();
					NetEntry entry = decodePointList(msg.getData().getString(
							"data"));
					if (NetEntry.CODESUCESS.equals(entry.status)) {
						updteRoomInfo(msg.getData().getString("data"));

					} else {
						showToastError(entry.msg);
					}
					android.os.Message mg = android.os.Message.obtain();
					mg.what = HandleConfig.REFRESHROOMINFO;
					mHandler.sendMessageDelayed(mg, 10000);

					break;
				case HandleConfig.REFRESHROOMINFO:
					if(isFinishing()==false){
						getroominfo(roomid);
					}
					break;
				case HandleConfig.QUERYROOMUSERS:// 更新room user list

					NetEntry entry1 = decodePointList(msg.getData().getString(
							"data"));

					if (NetEntry.CODESUCESS.equals(entry1.status)) {
						JSONObject jsonobj;
						try {
							jsonobj = new JSONObject(msg.getData().getString(
									"data"));

							JSONObject tmp = jsonobj.getJSONObject("data");
							JSONArray tmpList = tmp.getJSONArray("list");
							person.clear();
							for (int i = 0; i < tmpList.length(); i++) {
								JSONObject t = tmpList.getJSONObject(i);
								String phone = t.getString("phone");
								String id = t.getString("id");
								String name = t.getString("name");
								String headpic = t.getString("headpic");
								if (phone.equals(owner.phone)) {// 房主
									continue;
								}
								MessagePerson p = new MessagePerson();
								p.id = id;
								p.name=name;
								p.pic = headpic;
								p.phone = phone;
								person.add(p);
							}

							bigWindow.UpdateRoomPerson(person);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						showToastError(entry1.msg);
					}

					break;
				case 3:
					showToastError("聊天室链接失败");
				case 4:
					dismissProcessDialog();
					break;
				default:
					break;
				}
			}

		};
	}

	public String mekeMessage(String Content){
		String msg="";
		try {
		JSONObject job = new JSONObject();
			job.put("name", currentPerson.name);
			job.put("user_id", currentPerson.id);
			job.put("phone", currentPerson.phone);
			job.put("head_pic", currentPerson.pic);
			job.put("message", Content);
			msg = job.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
		
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		showProcessDialog("正在进入聊天室");
		new Thread(new SmackThread()).start();
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	void setOptClicker(){
		if(ischengke==true){
			bigWindow.opt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(bigWindow.personlist.get(currentPerson.phone)!=null){
						quitroom(roomid, currentPerson.id);
					}else{
						joinroom(roomid, currentPerson.id);
					}
					
				}
			});
			
		}else{
			
		}
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		try {
			if(muc!=null){
				
				muc.leave();
			}
			if(connection!=null){
				
				connection.disconnect();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		super.finish();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
		super.onBackPressed();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(connection.isConnected()){
			connection.disconnect();
			connection=null;
		}
		mHandler.removeMessages(HandleConfig.REFRESHROOMINFO);
		mHandler.removeMessages(HandleConfig.GETROOMINFO);
		System.out.println("---onDestroy---");
		super.onDestroy();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		System.out.println("---onStop---");
		super.onStop();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		System.out.println("---onResume---");
		super.onResume();
	}
}