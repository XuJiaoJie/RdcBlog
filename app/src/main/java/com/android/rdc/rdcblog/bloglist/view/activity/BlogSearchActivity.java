package com.android.rdc.rdcblog.bloglist.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.model.HistorySearchDB;
import com.android.rdc.rdcblog.bloglist.presenter.OnClickHistory;
import com.android.rdc.rdcblog.bloglist.presenter.OnClickSuggest;
import com.android.rdc.rdcblog.bloglist.view.defined.DeletableEditText;
import com.android.rdc.rdcblog.bloglist.view.defined.SearchTipLinearLayout;
import com.android.rdc.rdcblog.bloglist.view.fragment.SingleFragment;

import java.util.List;

/**
 * Created by PC on 2016/7/30.
 */
public class BlogSearchActivity extends BaseActivity implements View.OnClickListener,OnClickHistory,OnClickSuggest{
    private SearchTipLinearLayout historyLayout;
    private SearchTipLinearLayout suggestLayout;
    private DeletableEditText deletableEditText;
    private RelativeLayout historyRelativeLayout;
    private TextView suggestTextview;
    private ImageButton deleteHistory;
    private ImageButton backActivity;
    private ImageButton blogSearch;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> historySearchList;
    private HistorySearchDB historySearchDB;
    private String items[] = {"博客","web前端","安卓","java后台","c++","最新"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloglist_search_main);
        init();
        //流式布局搜索效果
        historyLayout.initView(historySearchList,this);
        suggestLayout.initView(items,this);
        //EditView自动匹配搜索历史效果
        arrayAdapter = new ArrayAdapter<>(this,R.layout.bloglist_search_et_item,historySearchList);
        deletableEditText.setAdapter(arrayAdapter);
    }

    private void init(){
        historyLayout = (SearchTipLinearLayout)findViewById(R.id.bloglist_search_tip);
        suggestLayout = (SearchTipLinearLayout)findViewById(R.id.blogsearch_search_suggest);
        deletableEditText = (DeletableEditText)findViewById(R.id.blogsearch_deletable_edittext);
        blogSearch = (ImageButton) findViewById(R.id.blogsearch_imgbtn_search);
        backActivity = (ImageButton)findViewById(R.id.blogsearch_back);
        deleteHistory = (ImageButton)findViewById(R.id.blogsearch_delete_history);
        historyRelativeLayout = (RelativeLayout)findViewById(R.id.blogsearch_history_relativelayout);
        suggestTextview = (TextView)findViewById(R.id.blogsearch_suggest_textview);

        historySearchDB = HistorySearchDB.newInstance(getApplicationContext());
        historySearchList = historySearchDB.loadHistory();

        backActivity.setOnClickListener(this);
        blogSearch.setOnClickListener(this);
        deleteHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.blogsearch_back:
                onBackPressed();
                break;
            case R.id.blogsearch_imgbtn_search:
                blogSearchOnClick();
                break;
            case R.id.blogsearch_delete_history:
                deleteAllHistory();
                break;
        }
    }

    /**
     *点击历史纪录响应
     */
    @Override
    public void onClickHistoryItem(int position) {
        deletableEditText.setText(historySearchList.get(position));
        deletableEditText.setSelection(historySearchList.get(position).length());
    }

    /**
     * 点击系统推荐响应事件
     */
    @Override
    public void onClickSuggestItem(int position) {
        deletableEditText.setText(items[position]);
        deletableEditText.setSelection(items[position].length());
    }

    /**
     * 点击搜索按钮搜索博客
     */
    private void blogSearchOnClick(){
        String key = deletableEditText.getText().toString();
        if(TextUtils.isEmpty(key)){
            Toast.makeText(this,"亲，输入不能为空哦",Toast.LENGTH_SHORT).show();
            return;
        }
        if (historySearchDB.hasData(key)){
            historySearchDB.deleteSingleHistory(key);
        }
        historySearchDB.saveHistory(key);
        deletableEditText.setText("");
        deletableEditText.setHint(key);
        historySearchList = historySearchDB.loadHistory();
        //隐藏键盘
        InputMethodManager imm = (InputMethodManager)getSystemService(BlogSearchActivity.this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(deletableEditText.getWindowToken(), 0);
        //布局调整，设置为不可见
        historyRelativeLayout.setVisibility(View.GONE);
        historyLayout.setVisibility(View.GONE);
        suggestTextview.setVisibility(View.GONE);
        suggestLayout.setVisibility(View.GONE);
        //不同的索搜结果显示博客类型
        String searchResult = resultFromKey(key);
        SingleFragment singleFragment = SingleFragment.newInstance(searchResult);
        getSupportFragmentManager().beginTransaction().replace(R.id.bloglist_flt_search_result,
                singleFragment).commit();
    }

    /**
     * 根据索搜的关键字选择不同的博客类型
     */
    private String resultFromKey(String key){
        if(key.contains("博客")||key.contains("最新")){
            return "全部";
        }else if(key.contains("前端")){
            return "web前端";
        }else if(key.contains("安卓")){
            return "安卓";
        }else if (key.contains("后台")){
            return "java后台";
        }else if (key.contains("C++")||key.contains("c++")){
            return "c++";
        }else {
            return null;
        }
    }

    /**
     * 删除所有历史记录
     */
    private void deleteAllHistory(){
        final AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("删除记录");
        builder.setCancelable(false);
        builder.setMessage("亲，你确定要删除所有搜索记录？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                historySearchDB.deleteAllHistory();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        historyLayout.setVisibility(View.GONE);
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

}
