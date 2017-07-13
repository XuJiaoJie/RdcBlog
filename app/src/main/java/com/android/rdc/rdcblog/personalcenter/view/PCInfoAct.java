package com.android.rdc.rdcblog.personalcenter.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.model.PCUserBean;
import com.android.rdc.rdcblog.personalcenter.util.CircleImageView;
import com.android.rdc.rdcblog.personalcenter.util.HttpHelper;
import com.android.rdc.rdcblog.personalcenter.util.ImageHelper;
import com.android.rdc.rdcblog.personalcenter.util.PCToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class PCInfoAct extends Activity{

    private static final String TAG = "PCInfoAct";
    private LinearLayout setIconLl;
    private LinearLayout setNickNameLl;
    private LinearLayout setNameLl;
    private LinearLayout setPositionLl;
    private CircleImageView headIconIv;
    private TextView tvNickname;
    private TextView tvName;
    private TextView tvPosition;
    private Uri imgUri;

    public static final int TAKE_PHOTO_REQUEST_CODE = 0;
    public static final int CHOOSE_PHOTO_REQUEST_CODE = 1;
    public static final int CROP_PHOTO_REQUEST_CODE = 2;

    public static void actionStart(Context context){
        Intent intent = new Intent(context, PCInfoAct.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_info_layout);
        initView();
        initData();
    }

    public void initView(){
        setIconLl = (LinearLayout)findViewById(R.id.pc_set_icon);
        setNameLl = (LinearLayout)findViewById(R.id.pc_set_name);
        setNickNameLl = (LinearLayout)findViewById(R.id.pc_set_nickname);
        setPositionLl = (LinearLayout)findViewById(R.id.pc_set_position);
        headIconIv = (CircleImageView)findViewById(R.id.pc_iv_head_icon_set);
        tvNickname = (TextView)findViewById(R.id.pc_tv_nickname_set);
        tvName = (TextView)findViewById(R.id.pc_tv_name_set);
        tvPosition = (TextView)findViewById(R.id.pc_tv_position_set);
    }

    private void initData(){
        PCUserBean mPcUserBean =  HttpHelper.getRequest("address");
        tvName.setText(mPcUserBean.getName());
        tvNickname.setText(mPcUserBean.getNickname());
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.pc_iv_info_back:
//                this.finish();
                HttpHelper.getRequest("A");
                break;
            case R.id.pc_set_icon:
                setIconDialog();
                break;
            case R.id.pc_set_nickname:
                setNickNameDialog();
                break;
            case R.id.pc_set_name:
                setNameDialog();
                break;
            case R.id.pc_set_position:
                Map<String, String> strParams = new HashMap<>();
                strParams.put("userId","3");
                strParams.put("username", "123456");
                strParams.put("email", "123456@163.com");
                strParams.put("nickname", "Jerry");
                strParams.put("gender", "1");
                strParams.put("grade", "2015");
                strParams.put("field", "unknown");
                strParams.put("direction", "C++");
                strParams.put("phone", "13760700555");
                strParams.put("workPlace","guangzhou");
                strParams.put("website","baidu.com");

                Map<String, File> fileParams = new HashMap<>();
//                fileParams.put("icon",file);
                HttpHelper.postRequest(strParams,fileParams);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imgUri, "image/*");
                    startActivityForResult(intent, CROP_PHOTO_REQUEST_CODE);
                }

                break;
            case CHOOSE_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imgUri, "image/*");
                    startActivityForResult(intent, CROP_PHOTO_REQUEST_CODE);
                }

                break;
            case CROP_PHOTO_REQUEST_CODE:
                crop();
                break;
            default:
                break;
        }
    }

    private void crop(){
        try {
            Bitmap bitmap2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
            headIconIv.setImageBitmap(bitmap2);
            saveImg(bitmap2);
            Log.d(TAG, "onActivityResult: "+imgUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "onActivityResult: "+"FileNotFoundException");
        }
    }

    private void setNickNameDialog(){
        View editView  = LayoutInflater.from(PCInfoAct.this).inflate(R.layout.pc_edit_nickname_layout, null);
        final EditText nicknameEt = (EditText)editView.findViewById(R.id.pc_nickname_edit_text);
        nicknameEt.setText(tvNickname.getText().toString());
        new AlertDialog.Builder(PCInfoAct.this)
                .setTitle("修改昵称")
                .setView(editView)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                tvNickname.setText(nicknameEt.getText());
                            }
                        })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private void setNameDialog(){
        View nameView  = LayoutInflater.from(PCInfoAct.this).inflate(R.layout.pc_edit_nickname_layout, null);
        final EditText nameEt = (EditText)nameView.findViewById(R.id.pc_nickname_edit_text);
        nameEt.setText(tvName.getText());
        new AlertDialog.Builder(PCInfoAct.this)
                .setTitle("修改姓名")
                .setView(nameView)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                //事件
                                tvName.setText(nameEt.getText());
                            }
                        })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private void setPositionDialog(){
        View positionView  = LayoutInflater.from(PCInfoAct.this).inflate(R.layout.pc_edit_nickname_layout, null);
        final EditText positionEt = (EditText)positionView.findViewById(R.id.pc_nickname_edit_text);
        positionEt.setText(tvPosition.getText());
        new AlertDialog.Builder(PCInfoAct.this)
                .setTitle("修改职位名称")
                .setView(positionView)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                //事件
                                tvPosition.setText(positionEt.getText());
                            }
                        }).setNegativeButton("取消", null).create().show();
    }

    private void setIconDialog(){

        String[] items = {"相机","相册"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(PCInfoAct.this);
        dialog.setTitle("设置头像");
        dialog.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0://调用相机
                        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //指定存储照片路径
                        imgUri = Uri.fromFile(ImageHelper.getTempImage());
                        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        startActivityForResult(takePicIntent, TAKE_PHOTO_REQUEST_CODE);
/*                                之前的方法
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                                startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST_CODE);
                                }*/
                        break;
                    case 1://调用相册
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CHOOSE_PHOTO_REQUEST_CODE);
/*                                Intent intent1 = new Intent("android.intent.action.PICK");
                                intent1.setType("image*//*");
                                intent1.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                                startActivityForResult(intent1, CHOOSE_PHOTO_REQUEST_CODE);*/
                        break;
                }
            }
        });
        dialog.show();
    }

    private String saveImg(Bitmap bitmap){
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"研发博客");
        if ( ! dir.exists() ){
            dir.mkdir();
        }
        final File file = new File(dir,"/"+System.currentTimeMillis()+ ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            PCToastUtil.showNormalToast(this, "保存成功");
            Map<String, String> strParams = new HashMap<>();
            strParams.put("userId","3");
            strParams.put("username", "123456");
            strParams.put("email", "123456@163.com");
            strParams.put("nickname", "Jerry");
            strParams.put("gender", "1");
            strParams.put("grade", "2015");
            strParams.put("field", "unknown");
            strParams.put("direction", "C++");
            strParams.put("phone", "18022857102");
            strParams.put("workPlace","guangzhou");
            strParams.put("website","baidu.com");

            Map<String, File> fileParams = new HashMap<>();
            fileParams.put("icon.jpeg",file);
            HttpHelper.postRequest(strParams,fileParams);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.toString();
    }
}
