package com.android.rdc.rdcblog.personalcenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.model.HistoryBean;
import com.android.rdc.rdcblog.personalcenter.presenter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class PCHistoryAct extends Activity {
    private MyPagerAdapter.TimeLineAdapter adapter;
    List<HistoryBean> datas = new ArrayList<>();
    private ImageView historyBackIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_history_timeline_layout);
        initData();
        initView();
    }
    private void initView(){
        adapter = new MyPagerAdapter.TimeLineAdapter(this, R.layout.pc_history_timeline_item, datas);
        ListView listView =  (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        ImageView historyBackIv = (ImageView)findViewById(R.id.pc_iv_history_back);
        historyBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PCHistoryAct.this.finish();
            }
        });
    }

    private void initData(){

        HistoryBean item1 = new HistoryBean();
        item1.setDay("01");
        item1.setMonth("6月");

        HistoryBean item2 = new HistoryBean();
        item2.setDay("02");
        item2.setMonth("7月");

        HistoryBean item3 = new HistoryBean();
        item3.setDay("03");
        item3.setMonth("8月");

        HistoryBean item4 = new HistoryBean();
        item4.setDay("03");
        item4.setMonth("8月");
        HistoryBean item5 = new HistoryBean();
        item5.setDay("02");
        item5.setMonth("7月");

        datas.add(item1);
        datas.add(item2);
        datas.add(item3);
        datas.add(item4);
        datas.add(item5);

        for(int i=0; i<10; i++){
            HistoryBean historyBean = new HistoryBean();
            historyBean.setDay("03");
            historyBean.setMonth("9月");
            datas.add(historyBean);
        }
        Collections.sort(datas);
    }
    public static void actionStart(Context context ){
        Intent intent = new Intent(context, PCHistoryAct.class);
        context.startActivity(intent);
    }
}
