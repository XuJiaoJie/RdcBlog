package com.android.rdc.rdcblog.bloglist.view.defined;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 图文混排编辑器
 */

public class PictureAndTextEditorView extends EditText {
    private static final String TAG = "PictureAndTextEditorVie";
    private Context mContext;
    private static final String mBitmapTag = "<img";
    private static final String mBitmapTag2 = "img>";
    private int picNum = 0;
    private String mNewLineTag = "\n";

    public PictureAndTextEditorView(Context context) {
        super(context);
        init(context);
    }

    public PictureAndTextEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PictureAndTextEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
//        mContentList = getmContentList();
//        insertData();
    }

//    /**
//     * 设置数据
//     */
//    private void insertData(){
//        if(mContentList.size() > 0){
//            for(String str : mContentList){
//                if(str.indexOf(mBitmapTag) != -1){ //判断是否是图片地址
//                    String path = str.replace(mBitmapTag,""); //还原地址字符串
//                    Bitmap bitmap = getSmallBitmap(path,300,400);
//                    //插入图片
//                    insertBitmap(path,bitmap);
//                }else {
//                    //插入文字
//                    SpannableString ss = new SpannableString(str);
//                    append(ss);
//                }
//            }
//        }
//    }

    /**
     * 插入图片
     */
    private SpannableString insertBitmap(String path, Bitmap bitmap){
        Editable editable = getEditableText();
        int index = getSelectionStart();   //获取光标所在位置
        //插入换行符，使图片单独占一行
        SpannableString newLine = new SpannableString("\n");
        editable.insert(index,newLine);
        //创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        path = mBitmapTag+picNum + path + mBitmapTag2;
        picNum++;
        SpannableString spannableString = new SpannableString(path);
        //根据Bitmap对象创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(mContext,bitmap);
        //用ImageSpan对象替代你指定的字符串
        spannableString.setSpan(imageSpan,0,path.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //将选择的图片追加到EditText中光标所在位置
        if(index < 0 || index >= editable.length()){
            editable.append(spannableString);
        }else {
            editable.insert(index,spannableString);
        }
        editable.insert(index,newLine);//插入图片后换行
        return spannableString;
    }

    /**
     *插入图片
     */
    public void insertBitmap(String path){
        Bitmap bitmap = getSmallBitmap(path,300,400);
        insertBitmap(path,bitmap);
    }

    /**
     * picNum置零
     */
    public void setZero(){
        picNum = 0;
    }

    private float oldY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                oldY = event.getY();
                requestFocus();
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = event.getY();
                if(Math.abs(oldY - newY) > 20){
                    clearFocus();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     */
    public Bitmap getSmallBitmap(String filePath, int reqWith, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWith,reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath,options);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int w_screen = 2*dm.widthPixels/3;
        int w_width = w_screen;
        int b_width = bitmap.getWidth();
        int b_heighr = bitmap.getHeight();
        int w_height = w_width * b_heighr / b_width;
        bitmap = Bitmap.createScaledBitmap(bitmap,w_width,w_height,false);
        return bitmap;
    }

    /**
     * 计算图片的缩放值
     */
    public int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeighr){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeighr || width > reqHeighr){
            final int heightRatio = Math.round((float)height/(float)reqHeighr);
            final int widthRatio = Math.round((float)width/(float)reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }




















}
