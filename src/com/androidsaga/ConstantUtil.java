package com.androidsaga;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

public class ConstantUtil{	
	public static final Integer NORMAL_SAGA  = 0;
	public static final Integer GOODMAN_SAGA = 1;
	public static final Integer SCIENCE_SAGA = 2;
	public static final Integer OTAKU_SAGA   = 3;
	public static final Integer UNCLE_SAGA   = 4;
	public static final Integer GOKATERA_SAGA = 5;
	
	public static final Integer STATUS_DEAD   = -1;
	public static final Integer STATUS_NORMAL = 0;
	public static final Integer STATUS_SLEEP = 1;
	public static final Integer STATUS_BATH = 2;
	public static final Integer STATUS_EXERCISE = 3;	
	
	public static final Integer BLUE_THRSHOLD = 60;
	public static final Integer BLACK_THRESHOLD = 40;
	
	public static final Integer PRESENT_MOVE_STEP = 1;
	
	public static final Float SATISFACTION_DEFAULT = 80.f;
	public static final String  ENCODING = "UTF-8";	
	
	public static final Float OTAKU_TIME = 7200.f; //2 hours
	
	public static final Float[] SATISFY_STEP = {-0.005f, 0.f, 0.05f, -0.005f};
	public static final Float[] SICK_STEP = {0.0045f, -0.001f, -0.05f, -0.1f};
	public static final Float[] HUNGRY_STEP = {0.005f, 0.0005f, 0.005f, 0.05f};
	public static final Float[] DIRTY_STEP = {0.01f, 0.f, -0.2f, 0.05f};
	public static final Float[] OTAKU_STEP = {0.005f, 0.005f, 0.005f, -0.1f};
	
	public static final Integer AUTO_SLEEP_TIME = 1800;
	
	public static final Float DIRTY_AFFECT_SICK = 50.f;
	public static final Float HUNGRY_AFFECT_SICK = 50.f;
	public static final Float DIRTY_AFFECT_SATISFY = 40.f;
	public static final Float HUNGRY_AFFECT_SATISFY = 30.f;
	public static final Float SICK_AFFECT_SATISFY = 40.f;		
	
	public static final String DATA_PATH = "saga.dat";
	public static final String DEFAULT_SHARED_PREF = "com.androidsaga_preferences";
	
	public static final String KEY_CHARACTOR = "charactor";
	public static final String KEY_STATUS = "status";
	public static final String KEY_TOTAL_TIME = "total_time";
	public static final String KEY_LEVEL = "level";
	public static final String KEY_SATISFY = "satisfy";
	public static final String KEY_HUNGRY = "hungry";
	public static final String KEY_DIRTY = "dirty";
	public static final String KEY_SICK = "sick";
	public static final String KEY_OTAKU = "otaku";
	public static final String KEY_INELEGANT = "inelegant";
	public static final String KEY_SCIENTIST = "scientist";
	public static final String KEY_STRANGE = "strange";	
	
	public static final String KEY_IDLE_SECONDS = "idle_seconds";
	public static final String KEY_AFTER_EXERCISE = "after_exercise";
	public static final String KEY_SLEEP_SECONDS = "sleep_seconds";
		
	public static final String KEY_IS_RUNNING = "is_running";	
	public static final String KEY_LAST_CLOSE = "last_close";
	
	public static final Float FEED_VALUE = -15.f;
	
	public static final int[] NOTICE_ID = {1222, 1223, 1224};		
	public static int CUR_NOTICE = 0;
	public static final int NOTICE_DEAD = 1;
	public static final int NOTICE_HUNGRY = 2;
	public static final int NOTICE_SICK = 3;
		
	public static Bitmap scaleButtonBitmap(Context ctx, int resourceID, int scaleWidth, int scaleHeight){
		Bitmap origBitmap = BitmapFactory.decodeResource(
							ctx.getResources(), resourceID);
		
		Log.i("saga", Integer.toString(scaleWidth)+","+ Integer.toString(origBitmap.getWidth()));
		
		if(scaleWidth >= origBitmap.getWidth()){
			return origBitmap;
		}
		
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(origBitmap, scaleWidth, scaleHeight, true);
		return scaledBitmap;		
	}
	
	public static void setNotify(Context ctx, Data data){
		final NotificationManager manager = 
			(NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		
		SharedPreferences prefs = ctx.getSharedPreferences(
				 ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);
		if(!prefs.getBoolean(ctx.getString(R.string.allow_notify_key), true)){
			return;
		}		 
		
		Notification notify = new Notification();
		if(CUR_NOTICE != 0)
			manager.cancel(NOTICE_ID[CUR_NOTICE-1]);
		
		PendingIntent intent = PendingIntent.getActivity(ctx, 0, new Intent(ctx, SagaActivity.class), 0);
		notify.icon = R.drawable.homebtn;
		notify.when = System.currentTimeMillis();
		notify.ledARGB = Color.RED;		
		notify.ledOnMS = 1000;	
		notify.flags = Notification.FLAG_SHOW_LIGHTS;
		 
		if(data.status == ConstantUtil.STATUS_DEAD){			
			notify.ledOffMS = 0;
			notify.tickerText = ctx.getString(R.string.notify_dead);
			notify.setLatestEventInfo(ctx,
					ctx.getString(R.string.notify_title), 
					ctx.getString(R.string.notify_dead), intent);
			CUR_NOTICE = NOTICE_DEAD;
			manager.notify(NOTICE_ID[CUR_NOTICE-1], notify);				
			return;
		}
		if(data.hungry > 80){				
			notify.ledOffMS = 2000;
			notify.tickerText = ctx.getString(R.string.notify_hungry);
			notify.setLatestEventInfo(ctx,
					ctx.getString(R.string.notify_title), 
					ctx.getString(R.string.notify_hungry), intent);
			CUR_NOTICE = NOTICE_HUNGRY;
			manager.notify(NOTICE_ID[CUR_NOTICE-1], notify);	
			return;
		}
		if(data.sick > 80){				
			notify.ledOffMS = 2000;
			notify.tickerText = ctx.getString(R.string.notify_sick);
			notify.setLatestEventInfo(ctx,
					ctx.getString(R.string.notify_title), 
					ctx.getString(R.string.notify_sick), intent);
			CUR_NOTICE = NOTICE_SICK;
			manager.notify(NOTICE_ID[CUR_NOTICE-1], notify);	
			return;
		}		
	}
}