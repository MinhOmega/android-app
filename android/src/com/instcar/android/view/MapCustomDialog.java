package com.instcar.android.view;
/**
 * 
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.instcar.android.R;
import com.instcar.android.util.MyLog;
public class MapCustomDialog extends Dialog {




	public MapCustomDialog(Context context, int theme) {
        super(context, theme);
    }
    public MapCustomDialog(Context context) {
        super(context);
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {

    	
        private Context context;
        private View dialogBodyView;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private Boolean cancelFlag;
        private Button uploadButton1;
        private Button uploadButton2;
        private Button uploadButton3;
        private View.OnClickListener click1;
        private View.OnClickListener click2;
        private View.OnClickListener click3;
        
        private DialogInterface.OnCancelListener cancelListener;

        private DialogInterface.OnClickListener 
                        positiveButtonClickListener,
                        negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
            this.cancelFlag=false;
        }
        
        public Builder(Context context,boolean cancel) {
            this.context = context;
            this.cancelFlag=cancel;
        }

        /**
         * Set the Dialog message from String
         * @param title
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         * @param title
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        /**
         * Set the cancel
         * @param title
         * @return
         */
        public Builder setCancel(Boolean b) {
            this.cancelFlag = b;
            return this;
        }
        
        /**
         * 
         * @param listener
         * @return
         */
        public Builder setCancelListener(DialogInterface.OnCancelListener listener) {
            this.cancelListener = listener;
            return this;
        }
        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }
       
        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         * @param v
         * @return
         */
        public Builder setDialogView(View v) {
            this.dialogBodyView = v;
            return this;
        }
        /**
         * Set the positive button resource and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public MapCustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MapCustomDialog dialog = new MapCustomDialog(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_upload_select, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            // set the confirm button
            dialog.setCancelable(cancelFlag);
            dialog.setOnCancelListener(cancelListener);
            // set the content message
            uploadButton1 =(Button) layout.findViewById(R.id.uploadbutton1);
            uploadButton1.setOnClickListener(click1);
            
            uploadButton2 =(Button) layout.findViewById(R.id.uploadbutton2);
            uploadButton2.setOnClickListener(click2);
            
            uploadButton3 =(Button) layout.findViewById(R.id.uploadbutton3);
            uploadButton3.setOnClickListener(click3);

           
            dialog.setContentView(layout);
            return dialog;
        }

        public void setbutton1ClickListener(View.OnClickListener s1){
        	click1=s1;
        	
        }
        public void setbutton2ClickListener(View.OnClickListener s2){
         	click2=s2;
        	
        }
        public void setbutton3ClickListener(View.OnClickListener s3){
         	click3=s3;
        	
        }
    }

}