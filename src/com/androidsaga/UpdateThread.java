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
			if(autoSaveTime == ConstantUtil.UPDATE_PERIOD){
				data.saveData();				
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
