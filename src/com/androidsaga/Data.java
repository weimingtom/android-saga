package com.androidsaga;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Data extends Object{
	public Integer charactor;
	public Integer status;
	
	public Integer totalTime;
	public Integer level;
	public Float satisfaction;
	public Float hungry;
	public Float dirty;
	public Float sick;
	public Float otaku;
	public Float inelegant;
	public Float scientist;
	public Float strange;	
		
	protected HashMap<String, Integer> materialList = new HashMap<String, Integer>();	
	
	public Integer idleSeconds = 0;
	public Integer afterLastExercise = 0;
		
	Float hungryStep = 0.005f; //increase 0.005 per second, about 5.5 hours to be 100
	Float sickStep = 0.0045f;   //increase 0.0045 per second, about 6 hours to be 100
	Float dirtyStep = 0.01f;  //increase 0.01 per second, about 2 hours to be 100
	Float satisfyStep = -0.005f; //decrease 0.005 per second
	Float otakuStep = 0.005f;  //increase 0.005 per second if not exercise for 3 hours
	
	Context ctx;
		
	Random rnd = new Random();
	
	public Data(Context ctx){
		this.ctx = ctx;
		loadData();
	}
	
		
	public void clearIdleSeconds(){
		idleSeconds = 0;
	}	
	
	public void clearOtakuSeconds(){
		afterLastExercise = 0;
	}	
		
	public void setDirtyStep(Float step){
		dirtyStep = step;
	}
	
	public void setSickStep(Float step){
		sickStep = step;
	}
	
	public void setHungryStep(Float step){
		hungryStep = step;
	}
	
	public void updateSatisfaction(Float delta){
		if(hungry > ConstantUtil.HUNGRY_AFFECT_SATISFY){
			delta -= hungryStep*0.5f;
		}
		if(sick > ConstantUtil.SICK_AFFECT_SATISFY){
			delta -= sickStep*0.5f;
		}
		if(dirty > ConstantUtil.DIRTY_AFFECT_SATISFY){
			delta -= dirtyStep*0.5f;
		}
		
		satisfaction += delta;
		if(satisfaction < 0.f){
			satisfaction = 0.f;
		}
		else if(satisfaction > 100.f){
			satisfaction = 100.f;		
		}
	}
	
	public void updateSick(float delta){
		if(hungry > ConstantUtil.HUNGRY_AFFECT_SICK){
			delta += hungryStep*0.5f;
		}
		if(dirty > ConstantUtil.DIRTY_AFFECT_SICK){
			delta += dirtyStep*0.5f;
		}
		
		sick += delta;
		if(sick < 0.f){
			sick = 0.f;
		}
		else if(sick > 100.f){
			sick = 100.f;
			charactor = ConstantUtil.STATUS_DEAD;
		}
	}
	
	public void increaseTotalTime(Integer sec){
		totalTime += sec;
		
		//update level
		if(level*level*3600 < totalTime)
			++level;
	}	
	
	public void updateHungry(Float delta){
		hungry += delta;
		
		if(hungry < 0.f){
			hungry = 0.f;
		}
		else if(hungry > 100.f){
			hungry = 100.f;
			charactor = ConstantUtil.STATUS_DEAD;
		}
	}
	
	public void updateDirty(Float delta){
		dirty += delta;
		if(dirty < 0.f){
			dirty = 0.f;
		}
		else if(dirty > 100.f){
			dirty = 100.f;
		}
	}
	
	public void updateOtaku(Float delta){
		otaku += delta;		
	}
	
	public void updateInelegant(Float delta){
		inelegant += delta;		
	}
	
	public void updateStrange(Float delta){
		strange += delta;		
	}
	
	public void updateScientist(Float delta){
		scientist += delta;		
	}
	
	public void updatePerSecond(){
		//if saga is dead, no action
		if(charactor == ConstantUtil.STATUS_DEAD){
			return;
		}		
		
		++idleSeconds;	
		
		increaseTotalTime(1);
		
		//too much idle time		
		updateSatisfaction(satisfyStep);	
		updateDirty(dirtyStep);
		updateHungry(hungryStep);
		updateSick(sickStep);
		
		//to much time without exercise
		if(afterLastExercise >= ConstantUtil.OTAKU_TIME){
			updateOtaku(otakuStep);			
		}		
	}
	
	public void updateForPeriod(Long lPeriod, Long lNotExercise){
		//if saga is dead, no action
		if(charactor == ConstantUtil.STATUS_DEAD){
			return;
		}
		int period = lPeriod.intValue();
		increaseTotalTime(period);
		
		//too much idle time		
		updateSatisfaction(satisfyStep*period);	
		updateSick(sickStep*period);
		updateHungry(hungryStep*period);
		updateDirty(dirtyStep*period);		
		
		//to much time without exercise
		if(lNotExercise.floatValue() >= ConstantUtil.OTAKU_TIME){
			updateOtaku(otakuStep*period);			
		}		
	}
	
	public void clearData(boolean clearAll){		
		otaku = inelegant = scientist = strange = 0.f;
		if(clearAll){
			charactor = ConstantUtil.NORMAL_SAGA;
			status = ConstantUtil.STATUS_NORMAL;
			totalTime = 0;
			level = 0;
			satisfaction = ConstantUtil.SATISFACTION_DEFAULT;
			hungry = 0.f;
			sick = 0.f;
			dirty = 0.f;
		}	
		
		saveData();
	}
	
	public void saveData(){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(
				ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE).edit();
		
		editor.putInt(ConstantUtil.KEY_CHARACTOR, charactor);
		editor.putInt(ConstantUtil.KEY_STATUS, status);
		editor.putInt(ConstantUtil.KEY_TOTAL_TIME, totalTime);
		editor.putInt(ConstantUtil.KEY_LEVEL, level);
		editor.putFloat(ConstantUtil.KEY_SATISFY, satisfaction);
		editor.putFloat(ConstantUtil.KEY_HUNGRY, hungry);
		editor.putFloat(ConstantUtil.KEY_DIRTY, dirty);
		editor.putFloat(ConstantUtil.KEY_SICK, sick);
		editor.putFloat(ConstantUtil.KEY_OTAKU, otaku);
		editor.putFloat(ConstantUtil.KEY_INELEGANT, inelegant);
		editor.putFloat(ConstantUtil.KEY_SCIENTIST, scientist);
		editor.putFloat(ConstantUtil.KEY_STRANGE, strange);
		
		editor.commit();
	}
	
	public void loadData(){	
		SharedPreferences prefs = ctx.getSharedPreferences(
			ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);
		
		charactor = prefs.getInt(ConstantUtil.KEY_CHARACTOR, ConstantUtil.NORMAL_SAGA);
		status = prefs.getInt(ConstantUtil.KEY_STATUS, ConstantUtil.STATUS_NORMAL);		
		totalTime = prefs.getInt(ConstantUtil.KEY_TOTAL_TIME, 0);
		level = prefs.getInt(ConstantUtil.KEY_LEVEL, 0);
		satisfaction = prefs.getFloat(ConstantUtil.KEY_SATISFY, ConstantUtil.SATISFACTION_DEFAULT);
		hungry = prefs.getFloat(ConstantUtil.KEY_HUNGRY, 0.f);
		dirty = prefs.getFloat(ConstantUtil.KEY_DIRTY, 0.f);
		sick = prefs.getFloat(ConstantUtil.KEY_SICK, 0.f);
		otaku = prefs.getFloat(ConstantUtil.KEY_OTAKU, 0.f);
		scientist = prefs.getFloat(ConstantUtil.KEY_SCIENTIST, 0.f);
		inelegant = prefs.getFloat(ConstantUtil.KEY_INELEGANT, 0.f);
		strange = prefs.getFloat(ConstantUtil.KEY_STRANGE, 0.f);	
	}
	
	public void setRunningFlag(boolean isRunning){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(
				ConstantUtil.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE).edit();
		if(isRunning){
			editor.putBoolean(ConstantUtil.KEY_IS_RUNNING, true);
			SagaWidget.idleTime = 0L;
			SagaWidget.notExerciseTime = 0L;
		}
		else
		{
			editor.putBoolean(ConstantUtil.KEY_IS_RUNNING, false);
			editor.putLong(ConstantUtil.KEY_LAST_CLOSE, System.currentTimeMillis());
		}
		editor.commit();
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