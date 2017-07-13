package com.android.rdc.rdcblog.blogdetail.view;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.blogdetail.adapter.FaceAdapter;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListBean;
import com.android.rdc.rdcblog.blogdetail.presenter.BlogCommentPresenter;
import com.android.rdc.rdcblog.blogdetail.presenter.CommentPresenter;
import com.android.rdc.rdcblog.photowall.view.listview.RefreshAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class WriteCommentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,IBlogCommentView {

    private Button mbtnCancel, mBtnSure;
    private TextView mTvTitleName;
    private EditText editText;
    private Toast toast;
    private BlogCommentPresenter blogCommentPresenter;
    private CommentPresenter commentPresenter;
    private ImageButton mImFace;
    private GridView mGvFace;
    private InputMethodManager inputMethodManager;
    private RelativeLayout rl;
    private int heightDifference;  //软键盘高度
    private int softKeyHeight;
    private int screenHeight; //屏幕高度
    private boolean isFace = false;
    private  ArrayList<String> facesName;//表情名称

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_comment_cancel:
                finish();
                break;
            case R.id.btn_comment_sure:
                if (mTvTitleName.getText().toString().equals("评论")) {
                    comment();
                } else {
                    reply();
                }
                break;
            case R.id.im_comment_face:
                showOrCloseFace();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        initView();
        initData();
        blogCommentPresenter = new BlogCommentPresenter(this);
        commentPresenter = new CommentPresenter(this, getIntent().getIntExtra("position", -1));
    }


    private void initView() {
        mbtnCancel = (Button) findViewById(R.id.btn_comment_cancel);
        editText = (EditText) findViewById(R.id.et_writecomment_write);
        mBtnSure = (Button) findViewById(R.id.btn_comment_sure);
        mTvTitleName = (TextView) findViewById(R.id.tv_writeComment_title);
        mGvFace = (GridView) findViewById(R.id.gv_writecomment_face);
        mImFace = (ImageButton) findViewById(R.id.im_comment_face);
        rl = (RelativeLayout) findViewById(R.id.rl_writecomment_above);

        final RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.rl_writecomment_rootView);
        myLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            /**
             * the result is pixels
             */
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                myLayout.getWindowVisibleDisplayFrame(r);

                screenHeight = myLayout.getRootView().getHeight();

                heightDifference = screenHeight - (r.bottom - r.top);
                Log.e("haha", "Size: " + heightDifference);

                //boolean visible = heightDiff > screenHeight / 3;
            }
        });

        mbtnCancel.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
        mImFace.setOnClickListener(this);


        try {
            if (!getIntent().getStringExtra("name").equals("")) {
                mTvTitleName.setText("回复" + getIntent().getStringExtra("name"));
                editText.setHint("回复" + getIntent().getStringExtra("name") + ":");
            }
        } catch (Exception e) {
            mTvTitleName.setText("评论");
            editText.setHint("写评论");
            e.printStackTrace();
        }
    }

    private void initData() {
        facesName = new ArrayList<>();
        facesName.add("f01");
        facesName.add("f02");
        facesName.add("f03");
        facesName.add("f04");
        facesName.add("f05");
        facesName.add("f06");
        facesName.add("f07");
        facesName.add("f08");
        facesName.add("f09");
        facesName.add("f01");
        facesName.add("f02");
        facesName.add("f03");
        facesName.add("f04");
        facesName.add("f05");
        facesName.add("f06");
        facesName.add("f07");
        facesName.add("f08");
        facesName.add("f09");
        FaceAdapter faceAdapter = FaceAdapter.getInstance(this, facesName);
        mGvFace.setAdapter(faceAdapter);
        faceAdapter.notifyDataSetChanged();
        mGvFace.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Field f;
        int cursor = editText.getSelectionStart();
        try {
            //根据资源名字得到Resource和对应的Drawable

            f = (Field) R.drawable.class.getDeclaredField(facesName.get(i));
            int j = f.getInt(R.drawable.class);
            Drawable d = getResources().getDrawable(j);
            d.setBounds(0, 0, 40, 40);//设置表情图片的显示大小
            //显示在EditText中
            String str="\\"+facesName.get(i);
            SpannableString ss = new SpannableString(str);
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);

            ss.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

          editText.getText().insert(cursor,ss);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void comment() {
        if (!getText().equals("")) {
            blogCommentPresenter.comment(getText());
            Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
            BlogDetailActivity.blogDetailActivity.finish();
            Intent intent = new Intent(this, BlogDetailActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(this, "评论不能为空", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void showOrCloseFace() {
        if(!isFace){//没有表情时
            if(softKeyHeight<100)
            { softKeyHeight = heightDifference;
            }
            isFace = true ;
            inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mImFace.getWindowToken(),0);
//                RelativeLayout.LayoutParams layoutParams  = (RelativeLayout.LayoutParams) mImFace.getLayoutParams();
//                layoutParams.height = 100;
//                mImFace.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams layoutParams = rl.getLayoutParams();

            layoutParams.height = screenHeight - softKeyHeight;
            Log.e("haha","屏幕高度"+screenHeight+"软键盘高度"+softKeyHeight);

            rl.setLayoutParams(layoutParams);
        }else {//有表情时
            isFace = false;
            ViewGroup.LayoutParams layoutParams = rl.getLayoutParams();
            layoutParams.height = screenHeight;
            Log.e("haha","屏幕高度"+screenHeight+"软键盘高度"+softKeyHeight);
            rl.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void reply() {
        if (!getText().equals("")) {
            commentPresenter.comment(getText());
            Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
            CommentActivity.commentActivity.finish();
            Intent intent = new Intent(this, CommentActivity.class);
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
            startActivity(intent);
            finish();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(this, "评论不能为空", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public String getText() {
        return editText.getText().toString();
    }
}
