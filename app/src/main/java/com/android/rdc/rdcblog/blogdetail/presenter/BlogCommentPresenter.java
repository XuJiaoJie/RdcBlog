package com.android.rdc.rdcblog.blogdetail.presenter;

import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.blogdetail.adapter.BlogdetailAdapter;
import com.android.rdc.rdcblog.blogdetail.model.BlogdetailCommentModel;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListBean;
import com.android.rdc.rdcblog.blogdetail.view.IBlogCommentView;
import com.android.rdc.rdcblog.blogdetail.view.IBlogDetailView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zjz on 2016/7/30.
 */
public class BlogCommentPresenter {

    private BlogdetailCommentModel blogdetailCommentModel ;
    private IBlogCommentView view;


    public  BlogCommentPresenter(IBlogCommentView view){
        blogdetailCommentModel = new BlogdetailCommentModel(new ArrayList<String>());
        this.view = view;
    }
    public void comment (String text) {
        //系统时间
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

      String time = ""+ month+"月" + day+"日  " + hour+ ":" + min;

        blogdetailCommentModel.writeIn(text ,time);

    }


}
