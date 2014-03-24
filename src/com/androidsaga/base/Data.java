package com.androidsaga.base;


import android.content.Context;
import android.content.SharedPreferences; 

public class Data {
	protected Context ctx;
	
	public int subSpecises;	 
	
	// the 0~3 bit represent highest level (0~10)
	// the 4~7 bit represent pre-level 
	// the 8~31 bit represent subspecies got before
	
	public int charactorInfo;
	
	public int   level;			
	public float satisfy;		
	public float HP;			
	public float clean;
	public int	 cleanfactor;
	public int   money;			//money that can be used to buy food
	public int   frozenMoney;	//money that can only be used in game
	
	public int 	 fat;			
	public int[] extra = new int[8];	//other values, different meaning for different charactor
	public int[] food  = new int[16];
	public boolean quiet;
	public int   version;
		
	public int   status;	
	
	public Data(Context _ctx) {
		this.ctx = _ctx;
		loadData();
	}
	
	public int getMaxLevel() {
		return (charactorInfo & ConstantValue.MAX_LEVEL_MASK);
	}
	
	public void setMaxLevel(int maxLevel) {
		charactorInfo &= (~ConstantValue.MAX_LEVEL_MASK);
		charactorInfo |= maxLevel;
	}
	
	public int getPreMaxLevel() {
		int preMaxLv = (charactorInfo & ConstantValue.PRE_MAX_LV_MASK);
		return ( preMaxLv >> ConstantValue.PRE_MAX_LV_SHFBIT);
	}
	
	public void setPreMaxLevel(int maxLevel) {
		charactorInfo &= (~ConstantValue.PRE_MAX_LV_MASK);
		charactorInfo |= (maxLevel << ConstantValue.PRE_MAX_LV_SHFBIT);
	}
				
	public boolean getSubspeciesFeed(int subspecies) {
		int subSpeciesInfo = (charactorInfo & ConstantValue.SUBSPECIES_MASK);
		subSpeciesInfo >>= ConstantValue.SUBSPECIES_SHFBIT;
		
		int mask = 1 << subspecies;		
		return ( (subSpeciesInfo & mask) != 0);
	}
	
	public void setSubspeciesFeed(int subspecies) {
		subSpecises = subspecies;
		
		int mask = 1 << (subspecies + ConstantValue.SUBSPECIES_SHFBIT);		
		charactorInfo |= mask;
	}	
	
	public boolean isLevelMax() {
		return level == ConstantValue.MAX_LEVEL;
	}
	
	protected void updateVersion(SharedPreferences prefs, int ver) {
		if(ver < 2) {
			charactorInfo	= prefs.getInt("char00", 0);
			subSpecises  = prefs.getInt("charactor", 0);
		}
	}
	
	public long loadData()
	{
		SharedPreferences prefs = ctx.getSharedPreferences(
			ConstantValue.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);		
		
		int curversion = ConstantValue.getVersionCode(ctx);
		version  	= prefs.getInt(ConstantValue.KEY_VERSION, 0);
		if(version < curversion) {
			updateVersion(prefs,version);
			version = curversion;
		}
		else {
			charactorInfo 	= prefs.getInt(ConstantValue.KEY_CHARACTORINFO, 0);
			subSpecises  = prefs.getInt(ConstantValue.KEY_SUBSPECISES, 0);
		}
		
		level		= prefs.getInt(ConstantValue.KEY_LEVEL, 0);
		status		= prefs.getInt(ConstantValue.KEY_STATUS, ConstantValue.STATUS_NORMAL);
		money		= prefs.getInt(ConstantValue.KEY_MONEY, 0);		
		frozenMoney = prefs.getInt(ConstantValue.KEY_MONEY_FROZEN, 0);
				
		for(int i = 0; i < extra.length; i++) {
			extra[i]= prefs.getInt(ConstantValue.KEY_EXTRA[i]	, 0);	
		}
		
		for(int i = 0; i < food.length; i++) {
			food[i] = prefs.getInt(ConstantValue.KEY_FOOD[i], 0);
		}
		
		fat		= prefs.getInt(ConstantValue.KEY_FAT		, 0);	
		
		clean	= prefs.getFloat(ConstantValue.KEY_CLEAN	, ConstantValue.MAX_CLEAN);
		cleanfactor	= prefs.getInt(ConstantValue.KEY_CLEANFACTOR	, 1);
		satisfy	= prefs.getFloat(ConstantValue.KEY_SATISFY	, ConstantValue.INIT_EXP_LEVEL[0]);
		HP		= prefs.getFloat(ConstantValue.KEY_HP		, ConstantValue.HP_LEVEL[0]);			
		
		
		Long lastCloseTime = prefs.getLong(ConstantValue.KEY_LAST_CLOSE, 
			System.currentTimeMillis());
		
		return lastCloseTime;  	
	}
	
	public void saveData(){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(
				ConstantValue.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE).edit();		
		
		editor.putInt(ConstantValue.KEY_CHARACTORINFO, charactorInfo);
			
		for(int i = 0; i < extra.length; i++) {
			editor.putInt(ConstantValue.KEY_EXTRA[i], extra[i]);
		}
		
		for(int i = 0; i < food.length; i++) {
			editor.putInt(ConstantValue.KEY_FOOD[i], food[i]);
		}
		
		editor.putInt(ConstantValue.KEY_FAT, fat);
		
		editor.putInt(ConstantValue.KEY_SUBSPECISES, subSpecises);		
		
		editor.putInt(ConstantValue.KEY_LEVEL, level);
		editor.putInt(ConstantValue.KEY_STATUS, status);
		editor.putInt(ConstantValue.KEY_MONEY, money);
		editor.putInt(ConstantValue.KEY_MONEY_FROZEN, frozenMoney);
		
		editor.putFloat(ConstantValue.KEY_CLEAN, clean);		
		editor.putInt(ConstantValue.KEY_CLEANFACTOR, cleanfactor);		
		editor.putFloat(ConstantValue.KEY_SATISFY, satisfy);		
		editor.putFloat(ConstantValue.KEY_HP, HP);		
		editor.putInt(ConstantValue.KEY_VERSION, version);
		editor.putLong(ConstantValue.KEY_LAST_CLOSE, System.currentTimeMillis());
		
		editor.commit();
	}
	
	public void setCharactor(int charactor) {
		subSpecises  = 0;
	}
}
