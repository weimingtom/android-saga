package com.androidsaga;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;

import com.constantvalue.ConstantUtil;

public class Data extends Object{
	public Integer status;
	
	public Integer totalTime;
	public Integer level;
	public Integer satisfaction;
	public Integer hungry;
	public Integer dirty;
	public Integer sick;
	public Integer otaku;
	public Integer inelegant;
	public Integer scientist;
	public Integer strange;	
	
	protected Map materalList = new HashMap();
	
	public void increaseTotalTime(){
		++totalTime;
		
		//update level
		if(level*level*3600 < totalTime)
			++level;
	}
	
	public void clearData(boolean reserveTimeAndValue){
		satisfaction = ConstantUtil.SATISFACTION_DEFAULT;
		hungry = dirty = sick = otaku = inelegant = scientist = strange = 0;
		status = ConstantUtil.NORMAL_SAGA;
		
		if(!reserveTimeAndValue){
			totalTime = level = 0;
		}
	}
	
	public void saveData(Context ctx, String path){
		try{
			FileOutputStream fout = ctx.openFileOutput(path, ctx.MODE_PRIVATE);
			String toWrite = "";
			
			toWrite.concat("STATUS="+status.toString()+"\n");
			toWrite.concat("TOTAL_TIME="+totalTime.toString()+"\n");
			toWrite.concat("LEVEL="+level.toString()+"\n");
			toWrite.concat("SATISFACTION="+satisfaction.toString()+"\n");
			toWrite.concat("HUNGRY="+hungry.toString()+"\n");
			toWrite.concat("DIRTY="+dirty.toString()+"\n");
			toWrite.concat("SICK="+sick.toString()+"\n");
			toWrite.concat("OTAKU="+otaku.toString()+"\n");
			toWrite.concat("INELEGANT="+inelegant.toString()+"\n");
			toWrite.concat("SCIENTIST="+scientist.toString()+"\n");
			toWrite.concat("STRANGE="+strange.toString()+"\n");
			
			Iterator it = materalList.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry)it.next();
				toWrite.concat("PRESENT: "+entry.getKey().toString()+
						",AMOUNT: "+entry.getValue().toString()+"\n");
			}
			
			fout.write(toWrite.getBytes());
			fout.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadData(Context ctx, String path){
		
	}
}