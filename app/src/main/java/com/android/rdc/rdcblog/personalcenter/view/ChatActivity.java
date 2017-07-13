package com.android.rdc.rdcblog.personalcenter.view;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.config.ConstantData;
import com.android.rdc.rdcblog.personalcenter.util.MsgData;
import com.android.rdc.rdcblog.personalcenter.util.ChatHttpData;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity implements View.OnClickListener {


	private ListView lvChat;
	private Button btnSend;
	private EditText etContent;
	private ChatListViewAdapter adapter;
	private List<MsgData> datas;
	private ImageButton ibBack;
	private ChatHttpData httpData=new ChatHttpData();
	private int receiverID=30;
	private InputMethodManager mInput;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		init();
		initData();
	}

	private void init() {
		lvChat=(ListView) findViewById(R.id.lv_chat);
		btnSend =(Button)findViewById(R.id.btn_send);
		etContent=(EditText)findViewById(R.id.et_chat);
		ibBack=(ImageButton)findViewById(R.id.ib_chat_back);
		mInput= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		lvChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);   	//
		ibBack.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		etContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});


	}




	private void initData() {
		datas=new ArrayList<>();
		adapter=new ChatListViewAdapter(this,datas);
		lvChat.setAdapter(adapter);
		httpData.getMsg(ConstantData.userID,receiverID,handler,true);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_send:
				sendMsg();
				break;
			case R.id.ib_chat_back:
				finish();
				break;
		}
	}

	private void sendMsg() {
		String mContent=etContent.getText().toString();
		if(mContent.length()>0){
			MsgData msgData=new MsgData();
			msgData.setmContent(mContent);
			msgData.setComeMsg(false);
			datas.add(msgData);
			httpData.sendMsg(msgData.getmContent(),receiverID,handler);
			etContent.setText("");
			if(mInput.isActive()){
				mInput.hideSoftInputFromWindow(etContent.getWindowToken(),0);
			}
		}

	}

	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case ConstantData.GET_SUCCESS:
					List<MsgData> httpDatas= (List<MsgData>)msg.obj;
					if(datas.size()==0||datas.size()!=httpDatas.size()){
						adapter=new ChatListViewAdapter(ChatActivity.this,httpDatas);
						lvChat.setAdapter(adapter);
						lvChat.setSelection(lvChat.getCount()-1);
						datas = httpDatas;
					}
					break;
				case ConstantData.SEND_SUCCESS:
					adapter=new ChatListViewAdapter(ChatActivity.this,datas);
					lvChat.setAdapter(adapter);
					lvChat.setSelection(lvChat.getCount()-1);
					break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		httpData.getMsg(ConstantData.userID,31,handler,false);
	}
}
