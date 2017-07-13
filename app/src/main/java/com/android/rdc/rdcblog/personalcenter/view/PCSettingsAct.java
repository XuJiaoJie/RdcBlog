package com.android.rdc.rdcblog.personalcenter.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.android.rdc.rdcblog.R;

public class PCSettingsAct extends Activity{
    private ImageView ivBack;
    private Switch sendPassageSwitch;
    private Switch bindGithubSwitch;

    public static void actionStart(Context context){
        Intent intent = new Intent(context, PCSettingsAct.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_setting_layout);
        initView();
    }
    private void initView(){
        ivBack = (ImageView)findViewById(R.id.pc_iv_setting_back);
        sendPassageSwitch = (Switch)findViewById(R.id.pc_switch_send_passage);
        bindGithubSwitch = (Switch)findViewById(R.id.pc_switch_bind_git);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PCSettingsAct.this.finish();
            }
        });
        sendPassageSwitch.setChecked(true);
        bindGithubSwitch.setChecked(true);
    }
}