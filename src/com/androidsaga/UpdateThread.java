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
			
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
