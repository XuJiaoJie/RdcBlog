package com.android.rdc.rdcblog.blogdetail.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.blogdetail.adapter.BlogdetailCommentAdaper;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListListBean;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener , IConmmentView, PullLinearLayout.IRefreshListener {
    private TextView mtv, mTvName;
    private Button mBtnComment ,mBtnCancel;
    private   int position;
    public static CommentActivity commentActivity;
    private ListViewForScrollView listViewForScrollView;
    private PullLinearLayout pl ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commentActivity = this;
        initView();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_comment2_sure:
                comment();
                break;
            case R.id.btn_comment2_cancel:
                finish();
                break;
        }
    }

    private void initView() {
        mtv = (TextView) findViewById(R.id.tv_comment2_content);
        mTvName = (TextView) findViewById(R.id.tv_comment_name);
        listViewForScrollView = (ListViewForScrollView) findViewById(R.id.lvfs_comment) ;
        Log.d("yu",getIntent().
                getIntExtra("position", -1)+"");
        BlogdetailCommentAdaper  blogdetailCommentAdaper =
                BlogdetailCommentAdaper.getInstance(CommentActivity.this , CommentListListBean.getInstance().mlist.get(getIntent().
                getIntExtra("position", -1)).mlist);
        listViewForScrollView.setAdapter(blogdetailCommentAdaper);

        listViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        mBtnComment = (Button) findViewById(R.id.btn_comment2_sure);
        mBtnCancel  = (Button) findViewById(R.id.btn_comment2_cancel);
        mBtnComment.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        pl = (PullLinearLayout) findViewById(R.id.pl_comment);
        pl.setIRefreshListener(this);



    }
    private  void initData() {
       position = getIntent().getIntExtra("position", -1);
        if(position != -1) {
            mtv.setText(CommentListBean.getInstance().mlist.get(position).getCommenttext());
        }
    }

    @Override
    public void comment() {
        Intent intent = new Intent(CommentActivity.this, WriteCommentActivity.class);
        intent.putExtra("name",getBlogerName());
       intent.putExtra("position" ,position);
        startActivity(intent);
    }

    @Override
    public void onRefresh(PullLinearLayout view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CommentActivity.this , "更新成功", Toast.LENGTH_SHORT).show();
                pl.finishRefresh();
            }
        },1000);

    }

    @Override
    public String getBlogerName() {
        return mTvName.getText().toString();
    }
}
