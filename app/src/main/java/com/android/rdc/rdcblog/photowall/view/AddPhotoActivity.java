package com.android.rdc.rdcblog.photowall.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.config.ConstantData;
import com.android.rdc.rdcblog.photowall.presenter.FinishPresenterImp;
import com.android.rdc.rdcblog.photowall.presenter.IFinishPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.android.rdc.rdcblog.config.ConstantData.PERMISSION_WRITE;

public class AddPhotoActivity extends Activity implements View.OnClickListener {


    private static final String TAG = "AddPhotoActivity";
    private static final int TAKE_PHOTO = 0;
    private static final int GET_PHOTO = 1;
    private static final int CROP_PHOTO = 2;

    private TextView tvFinish;
    private ImageView ivSee;
    private FloatingActionButton fabAdd;
    private EditText etInfo;
    private ImageButton ibBack;
    private Uri imageUri;
    private Bitmap mBitmap;
    private File imageFile;
    private String fileName;

    private IFinishPresenter finishPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        init();
    }

    //申请权限
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_WRITE);
        }
    }

    private void init() {
        tvFinish = (TextView) findViewById(R.id.tv_photo_finish);
        ivSee = (ImageView) findViewById(R.id.iv_photo_see);
        etInfo = (EditText) findViewById(R.id.et_photo_info);
        ibBack = (ImageButton) findViewById(R.id.ib_photo_add_back);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_photo_add);

        finishPresenter = new FinishPresenterImp();
        tvFinish.setOnClickListener(this);
        fabAdd.setOnClickListener(this);
        ibBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_photo_add_back:
                finish();
                break;
            case R.id.tv_photo_finish:
                if (mBitmap != null && etInfo.getText().toString() != null && !"".equals(etInfo.getText().toString())) {
                    Intent intent = finishPresenter.saveData(etInfo.getText().toString(), mBitmap, fileName, handler);
                    AddPhotoActivity.this.setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.fab_photo_add:
                getPermission();
                addPhoto();
                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ConstantData.UPLOAD_SUCCESS:
                    Toast.makeText(AddPhotoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    break;
                case ConstantData.UPLOAD_ERROR:
                    Toast.makeText(AddPhotoActivity.this, "上传失败," + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void addPhoto() {
        Log.d(TAG, "addPhoto: ");
        String[] items = {"相机", "相册"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddPhotoActivity.this);
        dialog.setTitle("请选择一项");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //相机拍照
                        creatFile();
                        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + fileName)));
                        if (takePhoto.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePhoto, TAKE_PHOTO);
                        }
                        break;
                    case 1:
                        //相册选择
                        Intent getPhoto = new Intent("android.intent.action.PICK");
                        getPhoto.setType("image/*");
                        if (getPhoto.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(getPhoto, GET_PHOTO);
                        }
                        break;
                }
            }
        });
        dialog.show();
    }

    private void creatFile() {
        fileName = System.currentTimeMillis() + ".jpg";
        Log.i(TAG, "creatFile: " + fileName);
        File newPhoto = new File(Environment.getExternalStorageDirectory(), fileName);
        Log.d("file", newPhoto.getPath());
        if (newPhoto.exists()) {
            newPhoto.delete();
        }
        try {
            newPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageFile = newPhoto;
        imageUri = Uri.fromFile(newPhoto);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                    /*Intent cropPhoto = new Intent("com.android.camera.action.CROP");
                    cropPhoto.setDataAndType(imageUri,"image*//*");
					cropPhoto.putExtra("scale",true);
					cropPhoto.putExtra("crop",true);
					cropPhoto.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
					startActivityForResult(cropPhoto,CROP_PHOTO_REQUEST_CODE);*/
                try {
                    Bitmap bitmap = null;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 8;
                    bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + fileName, options);

                    ivSee.setImageBitmap(bitmap);
                    ivSee.setVisibility(View.VISIBLE);
                    mBitmap = bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case GET_PHOTO:
                Uri selectPhoto = data.getData();
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectPhoto), null, options);
                    ivSee.setImageBitmap(bitmap);
                    mBitmap = bitmap;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case CROP_PHOTO:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    ivSee.setImageBitmap(bitmap);
                    mBitmap = bitmap;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void savePhoto(Bitmap bitmap) {
        FileOutputStream fos = null;

        try {
            fos = AddPhotoActivity.this.openFileOutput("YourPhoto.jpg", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_WRITE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "如有需要,请到设置申请权限", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
