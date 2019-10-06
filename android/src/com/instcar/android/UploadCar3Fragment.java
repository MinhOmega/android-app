package com.instcar.android;

import java.io.File;
import java.io.IOException;

import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.util.ImageTools;
import com.instcar.android.view.UploadCustomDialog;
import com.instcar.android.view.UploadCustomDialog.Builder;
import com.mycommonlib.android.common.util.StringUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 *用户选择的煮界面
 * @author dj880618
 *
 */
@SuppressLint("NewApi")
public class UploadCar3Fragment  extends BaseFrangment{
	
	private static final int RETURN_PICURL = 2;//选择照片返回的图片地址
	private static final int TAKE_PIC_TRTURN = 3;//照相返回
	private static final int CROP_PIC_TRTURN = 4;//照相返回
	
	private static final int SCALE = 5;//照片缩小比例
	
	private String url1;
	
	private String url2;
	
	private String url3;
	
	private Button upbutton1;
	private TextView tbutton2;
	private TextView tbutton3;
	
	private Button uploadSubmit;
	
	
	private ImageView imageview1;
	private ImageView imageview2;
	private ImageView imageview3;
	
	private int whichImage=-1;
	private String name;
	private String band;
	private String carid;
	private String xilie;
	private String pic;
	
	
	private ImageView cariconimg;
	private TextView textviewcarband;
	private TextView textviewcarmodel;
	
	Dialog uploaddialog;
	View v ;
	private CallBack3Listener callBackListener;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setCallBack3Listener(CallBack3Listener listener){
		this.callBackListener = listener;
	}
	 public interface CallBack3Listener {
	        public void click(int code);
	   }
	/**
	 * The Fragment's UI is just a simple text view showing its instance
	 * number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 v = inflater.inflate(
				R.layout.verfy_car_main_frangment3, container,
				false);
		Bundle b = getArguments();
//		Intent mintent = getIntent();
		this.name= b.getString("name");
		this.band= b.getString("band");
		this.carid= b.getString("carid");
		this.xilie= b.getString("xilie");
		this.pic= b.getString("pic");

		initHandle();
		initViews();
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
	private void initViews() {
		cariconimg  = (ImageView) v.findViewById(R.id.imageview_car_img);
		Drawable da =null;
		try {
			da = Drawable.createFromStream( getActivity().getAssets().open("pic/"+this.pic), null);
			da.setBounds(0, 0, da.getIntrinsicWidth(), da.getIntrinsicHeight());
			cariconimg.setImageDrawable(da);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textviewcarband = (TextView) v.findViewById(R.id.textview_car_band);
		textviewcarband.setText(band);
		textviewcarmodel = (TextView) v.findViewById(R.id.textview_carmodel);
		textviewcarmodel.setText(name);
		
		
		upbutton1 = (Button) v.findViewById(R.id.tbutton1);
		upbutton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				whichImage=1;
				showDialogUpload();
				
			}
		});
		tbutton2 = (TextView) v.findViewById(R.id.tbutton2);
		tbutton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				whichImage=2;
				showDialogUpload();
			}
		});
		tbutton3 = (TextView) v.findViewById(R.id.tbutton3);
		tbutton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				whichImage=3;
				showDialogUpload();
			}
		});
		
		uploadSubmit = (Button) v.findViewById(R.id.uploadsubmit);
		uploadSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showProcessDialog("正在增加汽车信息");
				if(StringUtils.isEmpty(url1)){
					
					showToastError("请上传行驶本照片");
					return;
				}
				if(StringUtils.isEmpty(url2)){
					
					showToastError("请上传侧面车照");
						return;
				}
				if(StringUtils.isEmpty(url3)){
					
					showToastError("请上传正面车照");
					return;
				}
				addcar(carid, url1, url2, url3);
				
			}
		});
		
		imageview1 =(ImageView) v.findViewById(R.id.imageview1);
		imageview2 =(ImageView) v.findViewById(R.id.imageview2);
		imageview3 =(ImageView) v.findViewById(R.id.imageview3);
		
	}
	
	void initHandle(){
		 mHandler = new Handler(){ 
		        
		        public void handleMessage(Message msg) { 
		        	String data ;
		        	NetEntry entry;
		        	
		            switch (msg.what) { 
		            case HandleConfig.UPLOADIMAGE: 
		            	dismissProcessDialog();
		              	data = msg.getData().getString("data");
		            	entry = new NetEntry(data);
		            	
		            	if(NetEntry.CODESUCESS.equals(entry.status)){//
		            		switch (whichImage) {
							case 1:
								url1 = entry.netentry.uploadfile;
								break;
							case 2:
								url2 = entry.netentry.uploadfile;
								break;
							case 3:
								url3 = entry.netentry.uploadfile;
								break;

							default:
								break;
							}
		            		if(!"".equals(url1)&&(!"".equals(url2)||!"".equals(url3))){
		            			
		            			uploadSubmit.setEnabled(true);
		            			
		            		}
		            		
		            	}else{//
		            		showToastError(entry.msg);
		            	}
		            	
		            	
		            	
		                break; 
		            case HandleConfig.ADDCAR: 
		            	dismissProcessDialog();
		            	data = msg.getData().getString("data");
		            	entry = new NetEntry(data);
		            	
		            	if(NetEntry.CODESUCESS.equals(entry.status)){//
		            		showToastError("增加汽车信息成功");
		            		callBackListener.click(1);
		            	}else{//
		            		showToastError(entry.msg);
		            	}
		            	
		            	
		            	
		            	break; 
		          
	
		            } 
		        }; 
		    }; 
		
	}
	private void showDialogUpload(){
		
		Builder customBuilder = new UploadCustomDialog.Builder(
				getActivity());
		
		customBuilder.setCancel(true);
		
		customBuilder.setbutton1ClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), SampleActivity.class);
				startActivity(i);
				
			}
		});
		customBuilder.setbutton2ClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				String fileName = "image.jpg";
			Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
			//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				
				startActivityForResult(openCameraIntent, TAKE_PIC_TRTURN);
				uploaddialog.dismiss();
			}
		});
		customBuilder.setbutton3ClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
				
				openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(openAlbumIntent, RETURN_PICURL);
				uploaddialog.dismiss();
			}
		});
		
		
		uploaddialog  =customBuilder.create();
		uploaddialog.show();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
		case RETURN_PICURL://选择照片返回的url地址
			
			Uri originalUri = data.getData(); 
			startPhotoZoom (originalUri);
			break;
			
		case TAKE_PIC_TRTURN://照相返回
			//将保存在本地的图片取出并缩小后显示在界面上
			Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/image.jpg");
			Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
			//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
			bitmap.recycle();
			String fileName = "image.jpg";
			Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
			startPhotoZoom(imageUri);
			//将处理过的图片显示在界面上，并保存到本地
			break;
		case CROP_PIC_TRTURN://裁剪图像返回
			
			Bitmap photo = null;
		
				Bundle extra = data.getExtras();
				if (extra != null) {
	                photo = (Bitmap)extra.get("data");
	                showProcessDialog("图片上传中..");
	                switch (whichImage) {
					case 1:
					    imageview1.setImageBitmap(photo);
					    upbutton1.setVisibility(View.GONE);
			            imageupload("1", av.getUid(), photo);
						break;
					case 2:
					    imageview2.setImageBitmap(photo);
					    imageview2.setVisibility(View.VISIBLE);
					    tbutton2.setVisibility(View.GONE);
			            imageupload("1", av.getUid(), photo);
						break;
					case 3:
					    imageview3.setImageBitmap(photo);
					    imageview3.setVisibility(View.VISIBLE);
					    tbutton3.setVisibility(View.GONE);
			            imageupload("1", av.getUid(), photo);
						break;

					default:
						break;
					}
	         
			}
			//存储照片；
			break;

		}
		
	}
	 private void startPhotoZoom(Uri uri) {
	        Intent intent = new Intent("com.android.camera.action.CROP");
	        intent.setDataAndType(uri, "image/*");
	        // crop为true是设置在开启的intent中设置显示的view可以剪裁
	        intent.putExtra("crop", "true");

	        // aspectX aspectY 是宽高的比例
	        intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);

	        // outputX,outputY 是剪裁图片的宽高
	        intent.putExtra("outputX", 324);
	        intent.putExtra("outputY", 324);
	        intent.putExtra("return-data", true);
	        intent.putExtra("noFaceDetection", true);
	        startActivityForResult(intent, CROP_PIC_TRTURN);
	    }

}
