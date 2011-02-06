package com.androidsaga;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.http.util.EncodingUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.util.Log;

public class Data extends Object{
	public Integer charactor = ConstantUtil.NORMAL_SAGA;	
	
	public Integer totalTime = 0;
	public Integer level = 0;
	public Integer satisfaction = ConstantUtil.SATISFACTION_DEFAULT;
	public Integer hungry = 0;
	public Integer dirty = 0;
	public Integer sick = 0;
	public Integer otaku = 0;
	public Integer inelegant = 0;
	public Integer scientist = 0;
	public Integer strange = 0;	
	public Boolean loadFromFile = true;
	public Long	autoUpdatePeriod = 900000L;
	
	protected HashMap<String, Integer> materialList = new HashMap<String, Integer>();	
	
	Integer afterLastExercise = 0;
	Integer idleSeconds = 0;
	Integer afterLastFeed = 0;
	Integer afterLastHospital = 0;
	Integer afterLastBath = 0;
	
	Integer dirtyStep = 1;
	Integer sickStep = 1;
	Integer hungryStep = 1;
	
	Context ctx;
		
	Random rnd = new Random();
	
	public Data(Context ctx){
		this.ctx = ctx;
		loadData();
	}
	
	public void clearExerciseSeconds(){
		afterLastExercise = 0;
	}
	
	public void clearIdleSeconds(){
		idleSeconds = 0;
	}
	
	public void clearFeedSeconds(){
		afterLastFeed = 0;
	}
	
	public void clearHospitalSeconds(){
		afterLastHospital = 0;
	}
	
	public void clearBathSeconds(){
		afterLastBath = 0;
	}	
	
	public void setDirtyStep(int step){
		dirtyStep = step;
	}
	
	public void setSickStep(int step){
		sickStep = step;
	}
	
	public void setHungryStep(int step){
		hungryStep = step;
	}
	
	public void updateSatisfaction(int delta){
		if(hungry > ConstantUtil.HUNGRY_AFFECT_SATISFY){
			delta -= 1;
		}
		if(sick > ConstantUtil.SICK_AFFECT_SATISFY){
			delta -= 1;
		}
		if(dirty > ConstantUtil.DIRTY_AFFECT_SATISFY){
			delta -= 1;
		}
		
		satisfaction += delta;
		if(satisfaction < 0){
			satisfaction = 0;
		}
		else if(satisfaction > 100){
			satisfaction = 100;		
		}
	}
	
	public void updateSick(int delta){
		if(hungry > ConstantUtil.HUNGRY_AFFECT_SICK){
			delta += 1;
		}
		if(dirty > ConstantUtil.DIRTY_AFFECT_SICK){
			delta += 1;
		}
		
		sick += delta;
		if(sick < 0){
			sick = 0;
		}
		else if(sick > 100){
			sick = 0;
			charactor = ConstantUtil.DEAD_SAGA;
		}
	}
	
	public void increaseTotalTime(){
		++totalTime;
		
		//update level
		if(level*level*3600 < totalTime)
			++level;
	}	
	
	public void updateHungry(int delta){
		hungry += delta;
		
		if(hungry < 0){
			hungry = 0;
		}
		else if(hungry > 100){
			hungry = 100;
			charactor = ConstantUtil.DEAD_SAGA;
		}
	}
	
	public void updateDirty(int delta){
		dirty += delta;
		if(dirty < 0){
			dirty = 0;
		}
		else if(dirty > 100){
			dirty = 100;
		}
	}
	
	public void updateOtaku(int delta){
		otaku += delta;		
	}
	
	public void updateInelegant(int delta){
		inelegant += delta;		
	}
	
	public void updateStrange(int delta){
		strange += delta;		
	}
	
	public void updateScientist(int delta){
		scientist += delta;		
	}
	
	public void updatePerSecond(){
		//if saga is dead, no action
		if(charactor == ConstantUtil.DEAD_SAGA){
			return;
		}
		
		++afterLastExercise;
		++afterLastHospital;
		++afterLastBath;
		++afterLastFeed;
		++idleSeconds;	
		
		increaseTotalTime();
		
		//too much idle time
		if(idleSeconds >= ConstantUtil.IDLE_THRESHOLD){
			updateSatisfaction(-rnd.nextInt(3));	
			clearIdleSeconds();
		}
		//to much time without exercise
		if(afterLastExercise >= ConstantUtil.OTAKU_TIME){
			updateOtaku(rnd.nextInt(5));
			updateSick(rnd.nextInt(10) * sickStep);
			clearExerciseSeconds();
		}
		
		//too much time without bathing
		if(afterLastBath >= ConstantUtil.BATH_TIME){
			updateDirty(rnd.nextInt(5) * dirtyStep);
			clearBathSeconds();
		}
		
		//too much time without feed
		if(afterLastFeed >= ConstantUtil.HUNGRY_TIME){
			updateHungry(rnd.nextInt(3) * hungryStep);
			clearFeedSeconds();
		}
	}
	
	public void clearData(boolean reserveTimeAndValue){
		satisfaction = ConstantUtil.SATISFACTION_DEFAULT;
		hungry = dirty = sick = otaku = inelegant = scientist = strange = 0;
		charactor = ConstantUtil.NORMAL_SAGA;
		
		if(!reserveTimeAndValue){
			totalTime = level = 0;
		}
	}
	
	public void saveData(){
		FileOutputStream fout = null;
		try{			
			fout = ctx.openFileOutput(ctx.getString(R.string.data_path), Context.MODE_PRIVATE);
			String toWrite = "CHARACTOR="+charactor.toString()+"\n";			
			toWrite += ("TOTAL_TIME="+totalTime.toString()+"\n");
			toWrite += ("LEVEL="+level.toString()+"\n");
			toWrite += ("SATISFACTION="+satisfaction.toString()+"\n");
			toWrite += ("HUNGRY="+hungry.toString()+"\n");
			toWrite += ("DIRTY="+dirty.toString()+"\n");
			toWrite += ("SICK="+sick.toString()+"\n");
			toWrite += ("OTAKU="+otaku.toString()+"\n");
			toWrite += ("INELEGANT="+inelegant.toString()+"\n");
			toWrite += ("SCIENTIST="+scientist.toString()+"\n");
			toWrite += ("STRANGE="+strange.toString()+"\n");			
			
			Iterator<Map.Entry<String, Integer>> it = materialList.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<String, Integer> entry = it.next();
				toWrite += ("PRESENT="+entry.getKey().toString()+
						",AMOUNT="+entry.getValue().toString()+"\n");
			}			
			
			fout.write(toWrite.getBytes());				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(fout != null){
					fout.close();
					fout = null;
				}				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void loadData(){	
		if(!loadFromFile) return;
		
		loadFromFile = false;
		autoUpdatePeriod = Long.parseLong(ctx.getSharedPreferences(
				ctx.getString(R.string.default_sharedpref), Context.MODE_PRIVATE).
				getString(ctx.getString(R.string.selected_auto_update_option), 
						  ctx.getString(R.string.auto_update_default_value)));
		
		Log.v("auto_update", autoUpdatePeriod.toString());
		
		FileInputStream fin = null;
		try{		
			fin = ctx.openFileInput(ctx.getString(R.string.data_path));
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			String[] dataBlock = EncodingUtils.getString(buffer, ConstantUtil.ENCODING).split("\n");
			for(String line: dataBlock){
				if(line.indexOf("CHARACTOR=") >= 0){
					charactor = Integer.parseInt(line.substring(10));					
				}
				else if(line.indexOf("TOTAL_TIME=") >= 0){
					totalTime = Integer.parseInt(line.substring(11));					
				}
				else if(line.indexOf("LEVEL=") >= 0){
					level = Integer.parseInt(line.substring(6));					
				}
				else if(line.indexOf("SATISFACTION=") >= 0){
					satisfaction = Integer.parseInt(line.substring(13));					
				}
				else if(line.indexOf("HUNGRY=") >= 0){
					hungry = Integer.parseInt(line.substring(7));					
				}
				else if(line.indexOf("DIRTY=") >= 0){
					dirty = Integer.parseInt(line.substring(6));
				}
				else if(line.indexOf("SICK=") >= 0){
					sick = Integer.parseInt(line.substring(5));
				}
				else if(line.indexOf("OTAKU=") >= 0){
					otaku = Integer.parseInt(line.substring(6));
				}
				else if(line.indexOf("INELEGANT=") >= 0){
					inelegant = Integer.parseInt(line.substring(10));
				}
				else if(line.indexOf("SCIENTIST=") >= 0){
					scientist = Integer.parseInt(line.substring(10));
				}
				else if(line.indexOf("STRANGE=") >= 0){
					strange = Integer.parseInt(line.substring(8));
				}
				else if(line.indexOf("PRESENT=") >= 0){
					int amountIdx = line.indexOf("AMOUNT=");
					String name = line.substring(8, amountIdx - 1);
					int amount = Integer.parseInt(line.substring(amountIdx+7));
					materialList.put(name, amount);
				}					
			}						
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(fin != null){
					fin.close();
					fin = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public Integer getMaterialCount(String name){
		if(!materialList.containsKey(name)){
			return -1;
		}
		
		return (materialList.get(name));
	}
	
	public void addMaterial(String name){
		if(materialList.containsKey(name)){
			Integer curAmount = materialList.remove(name);
			materialList.put(name, curAmount+1);
		}
		else{
			materialList.put(name, 1);
		}
	}
	
	public Integer useMaterial(String name, Integer amount){
		if(getMaterialCount(name) < amount){
			return -1;
		}
		Integer curAmount = materialList.remove(name);
		curAmount -= amount;
		if(curAmount > 0){
			materialList.put(name, curAmount);
		}		
		return 1;
	}
}