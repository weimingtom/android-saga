package com.androidsaga;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;

public class SagaWidget extends AppWidgetProvider{
	 public static Long idleTime = 0L;
	 public static Long notExerciseTime = 0L;	 
	 private static final int NOTICE_ID = 1222;	
	 @Override  
     public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {  
   
         UpdateService.updateAppWidgetIds(appWidgetIds);  
         context.startService(new Intent(context, UpdateService.class));  
         Log.v("tag", "update");
     }  
	 
	 @Override
	 public void onReceive(Context context, Intent intent){
		 super.onReceive(context, intent);
	 }
	 
	 public static void setNotify(Context ctx, Data data){
		 SharedPreferences prefs = ctx.getSharedPreferences(
				 ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);
		 if(!prefs.getBoolean(ctx.getString(R.string.allow_notify_key), true)){
			 return;
		 }
		 
		 final NotificationManager manager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		 Notification notify = new Notification(R.drawable.homebtn, ctx.getString(R.string.notify_title), System.currentTimeMillis());
		 PendingIntent intent = PendingIntent.getActivity(ctx, 0, new Intent(ctx, SagaActivity.class), 0);
		 notify.ledARGB = Color.RED;
		 notify.ledOffMS = 900;
		 notify.ledOnMS = 100;
		 boolean notifyOn = false;
		 
		 if(data.status == ConstantUtil.STATUS_DEAD){
			 notifyOn = true;
			 notify.setLatestEventInfo(ctx,
					 ctx.getString(R.string.notify_title), 
					 ctx.getString(R.string.notify_dead), intent);
		 }
		 else if(data.hungry > 80){		 
			 notifyOn = true;
			 notify.setLatestEventInfo(ctx,
					 ctx.getString(R.string.notify_title), 
					 ctx.getString(R.string.notify_hungry), intent);			 
		 }
		 else if(data.sick > 80){	
			 notifyOn = true;
			 notify.setLatestEventInfo(ctx,
					 ctx.getString(R.string.notify_title), 
					 ctx.getString(R.string.notify_sick), intent);	
		 }			 
		 
		 if(notifyOn){
			 manager.notify(NOTICE_ID, notify);
		 }
		 else{
			 manager.cancel(NOTICE_ID);
		 }
	 }
	 
     public static RemoteViews updateAppWidget(Context context) {     	 
    	 RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.saga_widget_layout);  
    	         
    	 Data data = new Data(context);
    	 SharedPreferences prefs = context.getSharedPreferences(
    			 ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);   	 
    	 
    	 Boolean allowRunBackground = prefs.getBoolean(context.getString(R.string.run_background_key), true);
    	 Boolean isRunning = prefs.getBoolean(ConstantUtil.KEY_IS_RUNNING, false);
    	 if(isRunning)
    	 {
    		 idleTime = 0L;
    		 notExerciseTime = 0L;    		
    	 }
    	 else if(allowRunBackground){    	
	    	 Long elapsedTime = prefs.getLong(ConstantUtil.KEY_LAST_CLOSE, 
					 System.currentTimeMillis());
			 elapsedTime = (System.currentTimeMillis() - elapsedTime) / 1000; 
			 idleTime += elapsedTime;
			 notExerciseTime += elapsedTime;
			 
			 data.updateForPeriod(elapsedTime, notExerciseTime);     	 
	    	 data.saveData();
	    	 data.setRunningFlag(false);  
    	 }
    	 
    	 views.setTextViewText(R.id.widget_level, data.level.toString());
    	 views.setTextViewText(R.id.widget_satisfy, data.satisfaction.toString());
    	 views.setTextViewText(R.id.widget_hungry, data.hungry.toString());
    	 views.setTextViewText(R.id.widget_dirty, data.dirty.toString());
    	 views.setTextViewText(R.id.widget_sick, data.sick.toString());  
    	 
    	 SagaWidget.setNotify(context, data);
    	 
         data = null;  
         
         Intent detailIntent = new Intent(context, SagaActivity.class);
    	 PendingIntent pending = PendingIntent.getActivity(context, 0, detailIntent, 0);           
         views.setOnClickPendingIntent(R.id.widget_saga_imageView, pending);  
         
         return views;          
     } 
}
