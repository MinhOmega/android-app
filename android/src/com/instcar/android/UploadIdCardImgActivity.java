package com.instcar.android;


import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.util.ImageTools;
import com.instcar.android.view.UploadCustomDialog;
import com.instcar.android.view.UploadCustomDialog.Builder;

/*
 * 用户实名认证
 */

public class UploadIdCardImgActivity extends BaseActivity {

	
	
	private static final int RETURN_PICURL = 2;//选择照片返回的图片地址
	private static final int TAKE_PIC_TRTURN = 3;//照相返回
	private static final int CROP_PIC_TRTURN = 4;//照相返回
	
	private static final int SCALE = 5;//照片缩小比例
	
	private String url1;
	
	private String url2;
	
	private String url3;
	
	private Button upbutton1;

	
	private Button uploadSubmit;
	
	
	private ImageView imageview1;

	
	private int whichImage=-1;
	
	Dialog uploaddialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_upload_idcardpic);
		initBaseNav();
		navbar.setText("实名认证");
		navbar2.setText("请按照以下样例拍摄上传本人手持身份证的清晰照片");
		btn_right.setVisibility(View.GONE);

		initHandle();
		initViews();
	}

	private void initViews() {
		upbutton1 = (Button) findViewById(R.id.tbutton1);
		upbutton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				whichImage=1;
				showDialogUpload();
				
			}
		});
		
		uploadSubmit = (Button) findViewById(R.id.uploadsubmit);
		
		imageview1 =(ImageView) findViewById(R.id.imageview1);
	
		
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
		            		showToastError("上传成功");
		            		
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
				UploadIdCardImgActivity.this);
		
		customBuilder.setCancel(true);
		
		customBuilder.setbutton1ClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(UploadIdCardImgActivity.this, SampleActivity.class);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
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
			            imageupload("2", av.getUid(), photo);
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
