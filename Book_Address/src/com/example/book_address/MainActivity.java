package com.example.book_address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import entity.Contacts;
import entity.SQLiteDBHelper;
import entity.SQLiteDBManager;

import view.ui.QuickAlphabeticBar;
import view.ui.SlideCutListView;
import view.ui.SlideCutListView.RemoveDirection;
import view.ui.SlideCutListView.RemoveListener;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CallLog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

	private ViewPager viewPager; 
    private ArrayList<View> pageViews; 
    private ViewGroup buttonsLine;  
    private Button button01; 
    private Button button02; 
    private Button button03; 
    private Button[] buttons;
    public SimpleAdapter simpleadapter;
    //AllContacts component
    private Button addContactBtn;
	//private ListView menuList;
	private SlideCutListView contactListview;
	private List<Map<String,Object>>listitems;
	private SearchView mysearchview;
    QuickAlphabeticBar alpha;
    private SQLiteDBHelper mDbHelper;
    private List<Contacts> mPersons;
    private SQLiteDBManager mManager;
    
    private boolean first= true;
    private boolean delete = false;
    private RemoveDirection mydirection; 
    private int myposition;
    
    //dialpage
    private ListView callLogList;
    private AsyncQueryHandler asyncQuery;
	
	private LinearLayout bohaopan;
	private LinearLayout keyboard_show_ll;
	private Button keyboard_show;
	
	private Button phone_view;
	private Button deletebt;
	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	private SoundPool spool;
	private AudioManager am = null;
	
	private MyApplication application;
	private ListView listView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new SQLiteDBHelper(this);
        mManager = new SQLiteDBManager(this);
        LayoutInflater inflater = getLayoutInflater(); 
        pageViews = new ArrayList<View>(); 
        //每页de界面
        View page01=inflater.inflate(R.layout.activity_contacts, null);
        View page02=inflater.inflate(R.layout.dial, null);
        View page03=inflater.inflate(R.layout.message, null);
       
        pageViews.add(page01);  //lee
        pageViews.add(page02);  //lee
        pageViews.add(page03);  //lee
        
       
        //按钮栏
        buttons = new Button[4]; 
        buttonsLine = (ViewGroup)inflater.inflate(R.layout.activity_main, null); 
        button01 = (Button) buttonsLine.findViewById(R.id.contact);
        button02 = (Button) buttonsLine.findViewById(R.id.dial);
        button03 = (Button) buttonsLine.findViewById(R.id.message);

        buttons[0] = button01;
        buttons[1] = button02;
        buttons[2] = button03;

        
        button01.setOnClickListener(new GuideButtonClickListener(0));
        button02.setOnClickListener(new GuideButtonClickListener(1));
        button03.setOnClickListener(new GuideButtonClickListener(2));
        
       
        viewPager = (ViewPager)buttonsLine.findViewById(R.id.guidePages); 
        setContentView(buttonsLine);
 
        viewPager.setAdapter(new GuidePageAdapter()); 
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        
        //set press button voice
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        spool = new SoundPool(11, AudioManager.STREAM_SYSTEM, 5);
		map.put(0, spool.load(MainActivity.this, R.raw.dtmf0, 0));
		map.put(1, spool.load(MainActivity.this, R.raw.dtmf1, 0));
		map.put(2, spool.load(MainActivity.this, R.raw.dtmf2, 0));
		map.put(3, spool.load(MainActivity.this, R.raw.dtmf3, 0));
		map.put(4, spool.load(MainActivity.this, R.raw.dtmf4, 0));
		map.put(5, spool.load(MainActivity.this, R.raw.dtmf5, 0));
		map.put(6, spool.load(MainActivity.this, R.raw.dtmf6, 0));
		map.put(7, spool.load(MainActivity.this, R.raw.dtmf7, 0));
		map.put(8, spool.load(MainActivity.this, R.raw.dtmf8, 0));
		map.put(9, spool.load(MainActivity.this, R.raw.dtmf9, 0));
		map.put(11, spool.load(MainActivity.this, R.raw.dtmf11, 0));
		map.put(12, spool.load(MainActivity.this, R.raw.dtmf12, 0));


    }

    class GuidePageAdapter extends PagerAdapter  { 
        
        @Override 
        public int getCount() { 
            return pageViews.size(); 
        } 
 
        @Override 
        public boolean isViewFromObject(View arg0, Object arg1) { 
            return arg0 == arg1; 
        } 
 
        @Override 
        public int getItemPosition(Object object) { 
            // TODO Auto-generated method stub 
            return super.getItemPosition(object); 
        } 
 
        @Override 
        public void destroyItem(View arg0, int arg1, Object arg2) { 
            // TODO Auto-generated method stub 
            ((ViewPager) arg0).removeView(pageViews.get(arg1)); 
        } 
 
        @Override 
        public Object instantiateItem(View arg0, int page) { 
            // TODO Auto-generated method stub 
            ((ViewPager) arg0).addView(pageViews.get(page)); 
            if(page==0)AllContacts();
            if(page==1)DialPage();
            if(page==2)setting();
            
            return pageViews.get(page); 
        } 
    	
        
    	void DialPage(){
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
    		
    		
    		phone_view = (Button) findViewById(R.id.phone_view);
    		phone_view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		
    		Button bt1 = (Button)findViewById(R.id.dialNum1);
    		bt1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt2 = (Button)findViewById(R.id.dialNum2);
    		bt2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt3 = (Button)findViewById(R.id.dialNum3);
    		bt3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt4 = (Button)findViewById(R.id.dialNum4);
    		bt4.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt5 = (Button)findViewById(R.id.dialNum5);
    		bt5.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt6 = (Button)findViewById(R.id.dialNum6);
    		bt6.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt7 = (Button)findViewById(R.id.dialNum7);
    		bt7.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt8 = (Button)findViewById(R.id.dialNum8);
    		bt8.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt9 = (Button)findViewById(R.id.dialNum9);
    		bt9.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		Button bt0 = (Button)findViewById(R.id.dialNum0);
    		bt5.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		
    		Button btx = (Button)findViewById(R.id.dialx);
    		btx.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		
    		Button btj = (Button)findViewById(R.id.dialj);
    		btj.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myClickevent(arg0);
				}
    			
    		});
    		
    		deletebt = (Button) findViewById(R.id.delete);
    		deletebt.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					delete();
				}
    			
    		});
    		deletebt.setOnLongClickListener(new OnLongClickListener() {
    			public boolean onLongClick(View v) {
    				phone_view.setText("");
    				return false;
    			}
    		});
    		
    		for (int i = 0; i < 12; i++) {
    			View v = findViewById(R.id.dialNum1 + i);
    			//v.setOnClickListener(this);
    		}
    		
    		init();
    	}

    	void AllContacts(){

            Button addContactBtn = (Button) findViewById(R.id.addContactBtn);
    		contactListview = (SlideCutListView)findViewById(R.id.contactlist);
    		mysearchview = (SearchView)findViewById(R.id.searchView);
    		alpha = (QuickAlphabeticBar)findViewById(R.id.fast_scroller);
    		
    		writeTableInfo(mManager.query());//get contacts info from database
    		
    		/*int id = mysearchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
    		TextView textView = (TextView) mysearchview.findViewById(id);
    		textView.setTextColor(Color.WHITE);*/
    		
    		contactListview.setTextFilterEnabled(true);//设置lv可以被过虑
    		mysearchview.setIconifiedByDefault(false);
    		mysearchview.setOnQueryTextListener(MainActivity.this);
    		mysearchview.setSubmitButtonEnabled(true);
    		mysearchview.setQueryHint("Search"); 
    		
    		addContactBtn.setOnClickListener(new OnClickListener() {
    			public void onClick(View v) {
    				Intent intent = new Intent();
    				intent.setClass(MainActivity.this, AddContacts.class);
    				startActivity(intent);
    				first = false;
    			}
    		});
    		
    		
    		contactListview.setRemoveListener(new RemoveListener(){

				@Override
				public void removeItem(RemoveDirection direction, int position) {
					// TODO Auto-generated method stub
					mydirection = direction;
					myposition = position;
					new AlertDialog.Builder(MainActivity.this).setTitle("Reminder")  
					  
				     .setMessage("Delete this guy?")//content of dialog  
				  
				     .setPositiveButton("Yes",new DialogInterface.OnClickListener() {//Yes button  
				    	 @Override  
				         public void onClick(DialogInterface dialog, int which) {//event of yes 
				             // TODO Auto-generated method stub  
				        	delete=true;   
				        	if(delete){
						    	mManager.deleteContacts(listitems.get(myposition).get("phonenumber").toString());
						    	//simpleadapter.remove(simpleadapter.getItem(position)); 
								listitems.remove(myposition);
								simpleadapter.notifyDataSetChanged(); 
						        switch (mydirection) {  
						        case RIGHT:  
						            //Toast.makeText(this, "向右删除  "+ position, Toast.LENGTH_SHORT).show();  
						            break;  
						        case LEFT:  
						            Toast.makeText(MainActivity.this, "Contact has been deleted", Toast.LENGTH_SHORT).show();  
						            break;  
						  
						        default:  
						            break;  
						        }
						    }
				         }  
				     }).setNegativeButton("No",new DialogInterface.OnClickListener() {//No button  
				         @Override  
				         public void onClick(DialogInterface dialog, int which) {//event of no  
				             // TODO Auto-generated method stub  
				  
				         }  
				     }).show();//show dialog
				  
				      
				    
				}
    			
    		});  
    		
    		
    		contactListview.setOnItemClickListener(new OnItemClickListener(){
 				@Override
 				public void onItemClick(AdapterView<?> arg0, View view,
 						int position, long arg3) {
 					// TODO Auto-generated method stub
 					TextView contactidtext=(TextView)view.findViewById(R.id.item_id);
 					String contactid = contactidtext.getText().toString();
 					TextView fnametext=(TextView)view.findViewById(R.id.item_fn);
 					String fname = fnametext.getText().toString();
 					TextView lnametext=(TextView)view.findViewById(R.id.item_ln);
 					String lname = lnametext.getText().toString();
 					TextView phonetext=(TextView)view.findViewById(R.id.item_phone);
 					String phone = phonetext.getText().toString();
 					TextView emailtext = (TextView)view.findViewById(R.id.item_email);
 					String email = emailtext.getText().toString();
 					Intent intent = new Intent();
					Bundle bundle= new Bundle();
					bundle.putString("contactId", contactid);
					System.out.println("Get id fail?? "+contactid);
					bundle.putString("fname", fname);
					bundle.putString("lname", lname);
					bundle.putString("phone", phone);
					bundle.putString("email", email);
					intent.putExtras(bundle);
					intent.setClass(MainActivity.this, Details.class);
				    startActivity(intent);
				    first = false;
 				}
     			
     		});
    	}
        
    	
    	void setting(){
        	
        }
    	 
        @Override 
        public void restoreState(Parcelable arg0, ClassLoader arg1) { 
            // TODO Auto-generated method stub 
 
        } 
 
        @Override 
        public Parcelable saveState() { 
            // TODO Auto-generated method stub 
            return null; 
        } 
 
        @Override 
        public void startUpdate(View arg0) { 
            // TODO Auto-generated method stub 
 
        } 
 
        @Override 
        public void finishUpdate(View arg0) { 
            // TODO Auto-generated method stub 
 
        } 
    }
    
    public void writeTableInfo(List<Contacts> contact){
		listitems= new ArrayList<Map<String,Object>>();
		for(int i=0;i<contact.size();i++)     
		{
			Map<String,Object>listitem= new HashMap<String,Object>();
			listitem.put("name", contact.get(i).first_name+" "+contact.get(i).last_name);
			listitem.put("fname", contact.get(i).first_name);
			listitem.put("lname", contact.get(i).last_name);
			listitem.put("phonenumber", contact.get(i).phone_number);
			listitem.put("ID", contact.get(i).id);
			listitem.put("email", contact.get(i).email);
			listitems.add(listitem);
			
		}
//		simpleadapter= new SimpleAdapter(MainActivity.this,listitems,
//				R.layout.contact_item,new String[] {"name", "fname", "lname", "phonenumber","ID","email"},
//				new int[] {R.id.iname, R.id.ifname, R.id.ilname, R.id.iphonenumber, R.id.iID, R.id.iEmail});
//		contactListview.setAdapter(simpleadapter);
		simpleadapter= new SimpleAdapter(MainActivity.this,listitems,
				R.layout.item,new String[] {"name", "fname", "email", "phonenumber","lname","ID",},
				new int[] {R.id.item_name, R.id.item_fn, R.id.item_email, R.id.item_phone, R.id.item_ln, });
		contactListview.setAdapter(simpleadapter);
    }
    
    //After adding a new contact it will be executed to refresh listview
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        
        System.out.println("If it has been executed, my onresume method");
    	System.out.println("If it has been executed, my onresume method");
    	System.out.println("If it has been executed, my onresume method");
    	System.out.println("If it has been executed, my onresume method");
    	if(!first)
    		writeTableInfo(mManager.query());
    	
    }
    
    
    
    class GuidePageChangeListener implements OnPageChangeListener { 
    	 
        @Override 
        public void onPageScrollStateChanged(int arg0) { 
            // TODO Auto-generated method stub 
        } 
 
        @Override 
        public void onPageScrolled(int arg0, float arg1, int arg2) { 
            // TODO Auto-generated method stub 
        } 
 
        @Override 
        public void onPageSelected(int arg0) { 
            
        } 
    }

    class GuideButtonClickListener implements OnClickListener { 
        private int index = 0;
       
           public GuideButtonClickListener(int i) {
               index = i;
           }

           @Override
           public void onClick(View v) {
            viewPager.setCurrentItem(index, true);
           }
      }
    //filter text during search
	@SuppressLint("ShowToast") @Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(arg0)) {
			contactListview.clearTextFilter();
        } else {
            contactListview.setFilterText(arg0);
        }
        return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
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
	
	public void init(){
		Uri uri = CallLog.Calls.CONTENT_URI;
		
		String[] projection = { 
				CallLog.Calls.DATE,
				CallLog.Calls.NUMBER,
				CallLog.Calls.TYPE,
				CallLog.Calls.CACHED_NAME,
				CallLog.Calls._ID
		}; // 查询的列
		//asyncQuery.startQuery(0, null, uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);  
	}
	
    public void myClickevent(View v){
    	switch (v.getId()) {
		case R.id.dialNum0:
			if (phone_view.getText().length() < 12) {
				play(0);
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
				System.out.println("我摁了5打扫的苏");
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
			//if (phone_view.getText().toString().length() >= 4) {
				call(phone_view.getText().toString());
			//}
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
}
