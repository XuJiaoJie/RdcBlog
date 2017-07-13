package com.android.rdc.rdcblog.blogdetail.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.blogdetail.adapter.BlogdetailAdapter;
import com.android.rdc.rdcblog.blogdetail.http.BlogDetailDownloader;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListBean;
import com.android.rdc.rdcblog.blogdetail.utils.BlogdetailJsonUtils;

import java.util.ArrayList;
import java.util.List;

public class BlogDetailActivity extends AppCompatActivity implements IBlogDetailView, View.OnClickListener , PullLinearLayout.IRefreshListener{
    private ImageButton shareButton;
    private TextView   tvGood ,tvBad, tvContent;
    private Button writeButton;
    private ImageButton favorImageButton,backImageButton;
    private ImageButton likeButton,hateButton ;
    private  PullLinearLayout pl;

    public static BlogDetailActivity blogDetailActivity ; //用于让其他Activity销毁

    private ListViewForScrollView mListView;
    private BlogdetailAdapter blogdetailAdapter ;


   private Toast toastSucceed ;
   private Toast toastCancel ;
    private Boolean isFavored = false ;
    private Boolean isLike = false ;
    private Boolean isHate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        initView();
       initData();
        toastSucceed = Toast.makeText(BlogDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT);
        toastCancel = Toast.makeText(BlogDetailActivity.this,"取消收藏",Toast.LENGTH_SHORT);

        BlogdetailAdapter.getInstance(this,CommentListBean.getInstance().mlist).notifyDataSetChanged();//更新数据
        blogDetailActivity = this;//用于被销毁

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_detail_share:
                share();
                break;
            case R.id.btn_detail_write:
                comment();
                break;
            case R.id.im_detail_favor :
                favor();
                break;
            case R.id.im_detail_good :
                like();
                break;
            case R.id.im_detail_bad :
                hate();
                break;
            case R.id.btn_detail_back :
                finish();
                break;
        }
    }

    @Override
    public void comment() {
        Intent intent = new Intent(BlogDetailActivity.this, WriteCommentActivity.class);

        startActivity(intent);
    }

    @Override
    public void share() {

        String text = blogdetailAdapter.getBlogDetail();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));

    }

    @Override
    public void favor() {

        if (!isFavored)
        {
            toastCancel.cancel();
            favorImageButton.setImageResource(R.drawable.blogdetail_im_favored);
            toastSucceed.show();
            isFavored = true;
        }
        else {
            toastSucceed.cancel();
            favorImageButton.setImageResource(R.drawable.blogdetail_btn_favor);
            toastCancel.show();
            isFavored = false ;
        }
    }
    @Override
    public void hate() {
        if(isLike){
            likeButton.setImageResource(R.drawable.blogdetail_im_good);
            tvGood.setText("0");
            tvGood.setTextColor(getResources().getColor(R.color.breaknumber));
            isLike = false;
        }
        if(isHate){
            hateButton.setImageResource(R.drawable.blogdetail_im_bad);
            tvBad.setText("0");
            tvBad.setTextColor(getResources().getColor(R.color.breaknumber));
            isHate = false;
        }else {
            hateButton.setImageResource(R.drawable.blogdetail_im_badred);
            tvBad.setText("1");
            tvBad.setTextColor(getResources().getColor(R.color.rednumber));
            isHate = true;
        }
    }

    @Override
    public void like() {
        if(isHate) {
            hateButton.setImageResource(R.drawable.blogdetail_im_bad);
            tvBad.setText("0");
            tvBad.setTextColor(getResources().getColor(R.color.breaknumber));
            isHate = false;
        }
        if (isLike) {
            likeButton.setImageResource(R.drawable.blogdetail_im_good);
            tvGood.setText("0");
            tvGood.setTextColor(getResources().getColor(R.color.breaknumber));
            isLike = false;
        }
        else{likeButton.setImageResource(R.drawable.blogdetail_im_goodred);
            tvGood.setText("1");
            tvGood.setTextColor(getResources().getColor(R.color.rednumber));
            isLike = true;
        }

    }

    public void initData() {
        Log.d("hehe","hahahaha0");
       BlogDetailDownloadTask blogDetailDownloadTask = new BlogDetailDownloadTask();
        blogDetailDownloadTask.execute("7","1",tvContent);
    }


    public void initView() {
        shareButton = (ImageButton) findViewById(R.id.im_detail_share);
        writeButton = (Button) findViewById(R.id.btn_detail_write);
        favorImageButton = (ImageButton) findViewById(R.id.im_detail_favor);
        likeButton = (ImageButton) findViewById(R.id.im_detail_good);
        hateButton = (ImageButton) findViewById(R.id.im_detail_bad);
        tvGood = (TextView) findViewById(R.id.tv_detail_goodnumber);
        tvBad = (TextView)findViewById(R.id.tv_detail_badnumber);
        mListView = (ListViewForScrollView) findViewById(R.id.lvfs_detail_comment);
        backImageButton = (ImageButton) findViewById(R.id.btn_detail_back);
         pl = (PullLinearLayout) findViewById(R.id.pl_blogdetail_pull);
        tvContent = (TextView) findViewById(R.id.tv_detail_content);

        List<String> list  = new ArrayList<String>();
        blogdetailAdapter = BlogdetailAdapter.getInstance(this , CommentListBean.getInstance().mlist);


        likeButton.setOnClickListener(this);
        hateButton.setOnClickListener(this);
        favorImageButton.setOnClickListener(this);
        writeButton.setOnClickListener(this);
      shareButton.setOnClickListener(this);
        pl.setIRefreshListener(this);
        mListView.setAdapter(blogdetailAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CommentListBean.getInstance().mlist.size() != 0) {
                    Intent intent = new Intent(BlogDetailActivity.this,CommentActivity.class);
                    intent.putExtra("position",CommentListBean.getInstance().mlist.size()-i-1);
                    startActivity(intent);
                }
            }
        });

        backImageButton.setOnClickListener(this);

    }

    @Override
    public void onRefresh(PullLinearLayout view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BlogDetailActivity.this , "刷新成功" , Toast.LENGTH_SHORT).show();
                pl.finishRefresh();
            }
        },1000);
    }
    class BlogDetailDownloadTask extends AsyncTask<Object,Object,Boolean> {
        String mBlogDetail = null;
        @Override
        protected Boolean doInBackground(Object[] objects) {
            Log.d("hehe","hahahaha0");
            String blogId = (String) objects[0];
            String userId = (String) objects[1];
        //    TextView mtvContent = (TextView) objects[2];
            try {
                Log.d("hehe","hahahaha1");
                mBlogDetail  = BlogDetailDownloader.request("http://10.21.20.120:8080/rdc_blog/blog/showBlogForAndroid/",blogId,userId);
            }catch (Exception e) {
                e.printStackTrace();
            }
               Log.d("hehe",mBlogDetail);
            publishProgress(mBlogDetail);
            return true;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            String mBlogDetail = (String) values[0];
            String content = null;
           content =  BlogdetailJsonUtils.getContent(mBlogDetail);
            tvContent.setText(Html.fromHtml(content));
            tvContent.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }

}
