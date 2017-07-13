package com.android.rdc.rdcblog.personalcenter.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.model.CollectionCategory;
import com.android.rdc.rdcblog.personalcenter.model.MCollection;
import com.android.rdc.rdcblog.personalcenter.presenter.MBaseExpandableListAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class PCCollectionAct extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "PCCollectionAct";
    private ExpandableListView expandableLv;
    private SwipeRefreshLayout collectRefresh;
    //获取子项的行列位置
    private int groupPosition;
    private int childPosition;

    private ArrayList<ArrayList<MCollection>> item;
    private ArrayList<MCollection> lData;
    private ArrayList<CollectionCategory> categories;
    private MBaseExpandableListAdapter expandableLvAdapter;
    private CollectionCategory collectionCategory;
    private ImageView collectBackIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_collection_layout);
        initView();
        initData();
        bindExpandableLv();
    }

    //expandableLv的设置
    private void bindExpandableLv(){
        expandableLvAdapter = new MBaseExpandableListAdapter(categories,item, PCCollectionAct.this);
        expandableLv.setAdapter(expandableLvAdapter);
        expandableLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i,int i1, long l) {
                groupPosition = i;
                childPosition = i1;
                return false;
            }
        });
        /*长按监听*/
        expandableLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PCCollectionAct.this);
                dialog.setMessage("确认删除？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                item.get(groupPosition).remove(childPosition);
                                expandableLvAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return false;
            }
        });
    }

    //ExpandableListView箭头位置设置
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        expandableLv.setIndicatorBounds(expandableLv.getWidth()- 50, expandableLv.getWidth());
    }

    private void initView(){
        expandableLv = (ExpandableListView)findViewById(R.id.pc_collect_list_view);
        collectRefresh = (SwipeRefreshLayout)findViewById(R.id.pc_collect_swipe_refresh);

        /*下拉刷新 圆形旋转图 的定义*/
        collectRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        collectRefresh.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        collectRefresh.setOnRefreshListener(this);

        /*导航栏 “返回按钮” 的监听*/
        collectBackIv = (ImageView)findViewById(R.id.pc_iv_collection_back);
        collectBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PCCollectionAct.this.finish();
            }
        });
    }

    private void initCategoryData(){
        collectionCategory = new CollectionCategory("安卓");
        categories.add(collectionCategory);
        collectionCategory = new CollectionCategory("C++");
        categories.add(collectionCategory);
        collectionCategory = new CollectionCategory("前端");
        categories.add(collectionCategory);
        collectionCategory = new CollectionCategory("后台");
        categories.add(collectionCategory);
    }
    //初始化子条目数据
    private void initItemData(){
        /*数据准备*/
        lData = new ArrayList<MCollection>();

        for (int j=0; j<3; j++) {
            for (int i=0 ;i <7; i++) {
                lData.add(new MCollection("RDC Blog"+i, "12:3"+i, i+"", i+"","安卓",0,0));
            }
            item.add(lData);
        }
        MCollection mCollection = new MCollection("RDC Blog"+1, "12:3"+1, 1+"", 1+"","后台",0,0);
        for(int i=0; i<4; i++){
            if(lData.get(i).getCollectTag().equals(mCollection.getCollectTag())){
                lData.add(mCollection);
            }
        }

        lData.add(new MCollection("RDC Blog"+1, "12:3"+4, 1+"", 8+"","安卓",0,0));
        lData.add(new MCollection("RDC Blog"+1, "12:3"+5, 2+"", 7+"","安卓",0,0));
        lData.add(new MCollection("RDC Blog"+2, "12:3"+6, 3+"", 7+"","安卓",0,0));
        lData.add(new MCollection("RDC Blog"+3, "12:3"+7, 5+"", 6+"","安卓",0,0));
        item.add(lData);
    }
    //初始化数据
    private void initData(){
        categories = new ArrayList<CollectionCategory>();
        item = new ArrayList<ArrayList<MCollection>>();
        initCategoryData();
        initItemData();

    }

    /*活动启动的方法*/
    public static void actionStart(Context context ){
        Intent intent = new Intent(context, PCCollectionAct.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               collectRefresh.setRefreshing(false);
               Toast.makeText(PCCollectionAct.this, " 刷新完成", Toast.LENGTH_SHORT).show();
               Log.d("onRefresh", "run: ");
           }
       },2500);
    }
}
