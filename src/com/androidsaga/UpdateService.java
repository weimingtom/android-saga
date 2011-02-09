package com.androidsaga;

import java.util.LinkedList;
import java.util.Queue;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;
import android.widget.RemoteViews;

public class UpdateService extends Service implements Runnable {
	private static Queue<Integer> sAppWidgetIds = new LinkedList<Integer>();  
	public static final String ACTION_UPDATE_ALL = "com.xxxx.news.UPDATE_ALL";  
	private static boolean sThreadRunning = false;  
	private static Object sLock = new Object();  
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 public static void updateAppWidgetIds(int[] appWidgetIds){  
		 synchronized (sLock) {  
		 	for (int appWidgetId : appWidgetIds) {  
		    	sAppWidgetIds.add(appWidgetId);  
		 	}  
		 }  
	}  
		       
	public static int getNextWidgetId(){  
		synchronized (sLock) {  
			if (sAppWidgetIds.peek() == null) {  
				return AppWidgetManager.INVALID_APPWIDGET_ID;  
			} 
			else {  
				return sAppWidgetIds.poll(); 		                   
			}  
		}  
	}  
		       
	private static boolean hasMoreUpdates() {  
		synchronized (sLock) {  
			boolean hasMore = !sAppWidgetIds.isEmpty();  
			if (!hasMore) {  
				sThreadRunning = false;  
			}  
			return hasMore;  
		}  
	}  
		   
	@Override  
	public void onCreate() {  
		super.onCreate();		
	}  
		   
	@Override  
	public void onStart(Intent intent, int startId) {  
		super.onStart(intent, startId);  
		if (null != intent) {  
			if (ACTION_UPDATE_ALL.equals(intent.getAction())) {  
				AppWidgetManager widget = AppWidgetManager.getInstance(this);  
				updateAppWidgetIds(widget.getAppWidgetIds(new ComponentName(this, SagaWidget.class)));  
			}  
		}  
		synchronized (sLock) {  
			if (!sThreadRunning) {  
				sThreadRunning=true;  
				new Thread(this).start();  
			}  
		}  
	}  
		   
	public void run() {		           
		AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);  
		RemoteViews updateViews=null;  
		           
		while (hasMoreUpdates()) {  
			int appWidgetId=getNextWidgetId(); 			
			updateViews = SagaWidget.updateAppWidget(this);  
			  
			if (updateViews != null) {  
				appWidgetManager.updateAppWidget(appWidgetId, updateViews);  
			}  
		}  
		           
		Intent updateIntent=new Intent(ACTION_UPDATE_ALL);  
		updateIntent.setClass(this, UpdateService.class);  
		PendingIntent pending=PendingIntent.getService(this, 0, updateIntent, 0);  
		           
		Time time = new Time();  
		long nowMillis = System.currentTimeMillis(); 
		long updatePeriod = Long.parseLong(getSharedPreferences(
				ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE).
				getString(getString(R.string.selected_auto_update_option), 
						  getString(R.string.auto_update_default_value)));
		
		time.set(nowMillis+updatePeriod);  
		long updateTimes = time.toMillis(true);  		
		           
		AlarmManager alarm=(AlarmManager)getSystemService(Context.ALARM_SERVICE);  
		alarm.set(AlarmManager.RTC_WAKEUP, updateTimes, pending);  
		stopSelf();  
	}
}
