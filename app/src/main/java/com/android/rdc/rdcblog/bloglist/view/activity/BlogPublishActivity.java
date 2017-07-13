package com.android.rdc.rdcblog.bloglist.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.model.UILImageLoader;
import com.android.rdc.rdcblog.bloglist.view.defined.PictureAndTextEditorView;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.lqr.imagepicker.ui.ImageGridActivity;
import com.lqr.imagepicker.ui.ImagePreviewActivity;
import com.lqr.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class BlogPublishActivity extends BaseActivity {
    private PictureAndTextEditorView mEditorView;
    public static final int IMAGE_PICKER = 100;
    private ImageButton mBackButton;
    private Button mPublishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloglist_public_blog);
        initSwipeBackLayout();
        mEditorView = (PictureAndTextEditorView)findViewById(R.id.et_publish_content);
        mBackButton = (ImageButton)findViewById(R.id.ibtn_back);
        mPublishButton = (Button)findViewById(R.id.btn_publish);
        init();
    }

    private void init(){
        //初始化ImageLoader
        ImageLoader.getInstance().init(
                ImageLoaderConfiguration.createDefault(getApplicationContext()));
        //初始化仿微信控件ImagePicker
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new UILImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    //选择图片按钮点击事件
    public void pickImage(View view){
        Intent intent =new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent,IMAGE_PICKER);
    }

    //返回点击事件
    public void backClick(View view){
        onBackPressed();
    }

    //发表按钮点击事件
    public void publishOnClick(View view){
        mEditorView.setZero();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//返回多张照片
            if (data != null) {
                //是否发送原图
                boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

                Log.e("CSDN_LQR", isOrig ? "发原图" : "不发原图");//若不发原图的话，需要在自己在项目中做好压缩图片算法
                for (ImageItem imageItem : images) {
                    Log.e("CSDN_LQR", imageItem.path);
                    mEditorView.insertBitmap(imageItem.path);
                }
            }
        }
    }
}
