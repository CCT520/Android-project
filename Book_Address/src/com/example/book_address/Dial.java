package com.example.book_address;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Dial extends Activity implements OnClickListener {
	
	private AsyncQueryHandler asyncQuery;
	
	private ListView callLogList;
	
	
	private LinearLayout bohaopan;
	private LinearLayout keyboard_show_ll;
	private Button keyboard_show;
	
	private Button phone_view;
	private Button delete;
	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	private SoundPool spool;
	private AudioManager am = null;
	
	private MyApplication application;
	private ListView listView;
	//private T9Adapter t9Adapter;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.dial);
		
		//application = (MyApplication)getApplication();
		listView = (ListView) findViewById(R.id.contact_list);
		
		bohaopan = (LinearLayout) findViewById(R.id.bohaopan);
		keyboard_show_ll = (LinearLayout) findViewById(R.id.keyboard_show_ll);
		keyboard_show = (Button) findViewById(R.id.keyboard_show);
		callLogList = (ListView)findViewById(R.id.call_log_list);
		
		
		keyboard_show.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialPadShow();
			}
		});
		
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		spool = new SoundPool(11, AudioManager.STREAM_SYSTEM, 5);
		map.put(0, spool.load(this, ToneGenerator.TONE_DTMF_1, 0));
		map.put(1, spool.load(this, ToneGenerator.TONE_DTMF_2, 0));
		map.put(2, spool.load(this, ToneGenerator.TONE_DTMF_3, 0));
		map.put(3, spool.load(this, ToneGenerator.TONE_DTMF_4, 0));
		map.put(4, spool.load(this, ToneGenerator.TONE_DTMF_5, 0));
		map.put(5, spool.load(this, ToneGenerator.TONE_DTMF_6, 0));
		map.put(6, spool.load(this, ToneGenerator.TONE_DTMF_7, 0));
		map.put(7, spool.load(this, ToneGenerator.TONE_DTMF_8, 0));
		map.put(8, spool.load(this, ToneGenerator.TONE_DTMF_9, 0));
		map.put(9, spool.load(this, ToneGenerator.TONE_DTMF_0, 0));
		map.put(11, spool.load(this, ToneGenerator.TONE_DTMF_P, 0));
		map.put(12, spool.load(this, ToneGenerator.TONE_DTMF_S, 0));
		
		phone_view = (Button) findViewById(R.id.phone_view);
		phone_view.setOnClickListener(this);
		
		delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(this);
		delete.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				phone_view.setText("");
				return false;
			}
		});
		
		for (int i = 0; i < 12; i++) {
			View v = findViewById(R.id.dialNum1 + i);
			v.setOnClickListener(this);
		}
		
		init();
		
	}
	
	private void init(){
		Uri uri = CallLog.Calls.CONTENT_URI;
		
		String[] projection = { 
				CallLog.Calls.DATE,
				CallLog.Calls.NUMBER,
				CallLog.Calls.TYPE,
				CallLog.Calls.CACHED_NAME,
				CallLog.Calls._ID
		}; // ²éÑ¯µÄÁÐ
		asyncQuery.startQuery(0, null, uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);  
	}
	


	
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialNum0:
			if (phone_view.getText().length() < 12) {
				play(1);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum1:
			if (phone_view.getText().length() < 12) {
				play(1);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum2:
			if (phone_view.getText().length() < 12) {
				play(2);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum3:
			if (phone_view.getText().length() < 12) {
				play(3);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum4:
			if (phone_view.getText().length() < 12) {
				play(4);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum5:
			if (phone_view.getText().length() < 12) {
				play(5);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum6:
			if (phone_view.getText().length() < 12) {
				play(6);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum7:
			if (phone_view.getText().length() < 12) {
				play(7);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum8:
			if (phone_view.getText().length() < 12) {
				play(8);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum9:
			if (phone_view.getText().length() < 12) {
				play(9);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialx:
			if (phone_view.getText().length() < 12) {
				play(11);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialj:
			if (phone_view.getText().length() < 12) {
				play(12);
				input(v.getTag().toString());
			}
			break;
		case R.id.delete:
			delete();
			break;
		case R.id.phone_view:
			if (phone_view.getText().toString().length() >= 4) {
				call(phone_view.getText().toString());
			}
			break;
		default:
			break;
		}
	}
	private void play(int id) {
		int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);

		float value = (float)0.7 / max * current;
		spool.setVolume(spool.play(id, value, value, 0, 0, 1f), value, value);
	}
	private void input(String str) {
		String p = phone_view.getText().toString();
		phone_view.setText(p + str);
	}
	private void delete() {
		String p = phone_view.getText().toString();
		if(p.length()>0){
			phone_view.setText(p.substring(0, p.length()-1));
		}
	}
	private void call(String phone) {
		//Uri uri = Uri.parse("tel:" + phone);
		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));  
        startActivity(intent);  
	}
	
	
	
	
	
	

	public void dialPadShow(){
		if(bohaopan.getVisibility() == View.VISIBLE){
			bohaopan.setVisibility(View.GONE);
			keyboard_show_ll.setVisibility(View.VISIBLE);
		}else{
			bohaopan.setVisibility(View.VISIBLE);
			keyboard_show_ll.setVisibility(View.INVISIBLE);
		}
	}
	
	
	
}
