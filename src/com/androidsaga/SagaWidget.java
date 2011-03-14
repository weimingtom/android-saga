package com.androidsaga;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class SagaWidget extends AppWidgetProvider{	 	 
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
    	         
    	 Data data = new Data(context);  
    	 data.autoSwitchStatus();	
    	 
    	 views.setTextViewText(R.id.widget_level, data.level.toString());
    	 views.setTextViewText(R.id.widget_satisfy, data.satisfaction.toString());
    	 views.setTextViewText(R.id.widget_hungry, data.hungry.toString());
    	 views.setTextViewText(R.id.widget_dirty, data.dirty.toString());
    	 views.setTextViewText(R.id.widget_sick, data.sick.toString());     	 
    	 
    	 data.saveData();    	 
    	 data.setRunningFlag(false); 
    	 
    	 ConstantUtil.setNotify(context, data);   	 
                  
         Intent detailIntent = new Intent(context, SagaActivity.class);
    	 PendingIntent pending = PendingIntent.getActivity(context, 0, detailIntent, 0);           
         views.setOnClickPendingIntent(R.id.widget_saga_imageView, pending);  
         
         data = null;
         return views;          
     } 
}
