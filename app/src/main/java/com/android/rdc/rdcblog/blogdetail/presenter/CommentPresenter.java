package com.android.rdc.rdcblog.blogdetail.presenter;

import com.android.rdc.rdcblog.blogdetail.model.BlogdetailCommentModel;
import com.android.rdc.rdcblog.blogdetail.model.CommentModel;
import com.android.rdc.rdcblog.blogdetail.view.IBlogCommentView;
import com.android.rdc.rdcblog.blogdetail.view.IConmmentView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zjz on 2016/8/6.
 */
public class CommentPresenter {

    private CommentModel commentModel ;
    private IBlogCommentView view;
    private  int position ;


    public  CommentPresenter(IBlogCommentView view, int position){
        commentModel = new CommentModel();
        this.view = view;
        this.position = position ;
    }
    public void comment (String text) {
        //系统时间
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        String time = ""+ month+"月" + day+"日  " + hour+ ":" + min;

        commentModel.writeIn(text ,time , position);

    }

}
