package com.android.rdc.rdcblog.personalcenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.rdc.rdcblog.R;


/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class PCAboutAct extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_about_layout);
        ImageView backAboutIv = (ImageView) findViewById(R.id.pc_iv_about_back);

        backAboutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PCAboutAct.this.finish();
            }
        });
    }
    public static void actionStart(Context context ){
        Intent intent = new Intent(context, PCAboutAct.class);
        context.startActivity(intent);
    }

}
