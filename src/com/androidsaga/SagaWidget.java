package com.androidsaga;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class SagaWidget extends AppWidgetProvider{
	 public static Long idleTime = 0L;
	 public static Long notExerciseTime = 0L;
	
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
       
     public static RemoteViews updateAppWidget(Context context) {     	 
    	 RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.saga_widget_layout);  
    	 Intent detailIntent = new Intent(context, SagaActivity.class);
    	 PendingIntent pending = PendingIntent.getActivity(context, 0, detailIntent, 0);           
         views.setOnClickPendingIntent(R.id.widget_saga_imageView, pending);  
         
    	 Data data = new Data(context);
    	 SharedPreferences prefs = context.getSharedPreferences(
    			 ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);   	 
    	 
    	 Boolean allowRunBackground = prefs.getBoolean(context.getString(R.string.run_background_key), true);
    	 if(!allowRunBackground){
    		 return views;
    	 }
    	 
    	 Boolean isRunning = prefs.getBoolean(ConstantUtil.KEY_IS_RUNNING, false);
    	 if(isRunning)
    	 {
    		 idleTime = 0L;
    		 notExerciseTime = 0L;    		
    	 }
    	 else{    	
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
    	 
         data = null;         
         return views;          
     } 
}
