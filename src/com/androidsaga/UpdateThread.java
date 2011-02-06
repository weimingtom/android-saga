package com.androidsaga;

import android.content.Context;

public class UpdateThread extends Thread{
	private Data data;
	private Context ctx;
	private long autoSaveTime = 0L;
	
	public UpdateThread(Context ctx, Data data){
		this.ctx = ctx;
		this.data = data;
	}
	
	public void run(){
		while(true){
			data.updatePerSecond();
			autoSaveTime += 1000L;
			if(autoSaveTime == data.autoUpdatePeriod){
				data.saveData();	
				data.autoUpdatePeriod = Long.parseLong(ctx.getSharedPreferences(
						ctx.getString(R.string.default_sharedpref), Context.MODE_PRIVATE).
						getString(ctx.getString(R.string.selected_auto_update_option), 
								  ctx.getString(R.string.auto_update_default_value)));
			}
				
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
