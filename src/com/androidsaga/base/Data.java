package com.androidsaga.base;


import android.content.Context;
import android.content.SharedPreferences; 

public class Data {
	protected Context ctx;
	
	public int curCharactor = 0; //which charactor is feeding
	public int subSpecises;	 
	
	// the 0~3 bit represent highest level (0~5)
	// the 4~7 bit represent the availability: 
	//0xf-unavailable, 0x0-available, but hasn't be feed before, 0x1-available, and has been feed
	// the 8~15 bit represent subspecies
	
	public int[] charactorInfo = new int[32];
	
	public int   level;			
	public float satisfy;		
	public float HP;			
	public int   money;			//money that can be used to buy food
	public int   frozenMoney;	//money that can only be used in game
	
	public int 	 fat;			
	public int[] extra = new int[8];	//other values, different meaning for different charactor
	public boolean quiet;
	public int   version;
	
	public int	 eggTime;
	public int   eggChangeTemperature;
	public int	 eggGold;
	public int 	 eggDark;
	public int 	 eggTemperature;
	public int   eggCurTemperature;
	public int 	 eggHatchTime;
	
	public int   status;	
	
	public Data(Context _ctx) {
		this.ctx = _ctx;
		loadData();
	}
	
	public int getMaxLevel(int charactor) {
		return (charactorInfo[charactor] & ConstantValue.MAX_LEVEL_MASK);
	}
	
	public void setMaxLevel(int charactor, int maxLevel) {
		charactorInfo[charactor] &= (~ConstantValue.MAX_LEVEL_MASK);
		charactorInfo[charactor] |= maxLevel;
	}
	
	public int getPreMaxLevel(int charactor) {
		int preMaxLv = (charactorInfo[charactor] & ConstantValue.PRE_MAX_LV_MASK);
		return ( preMaxLv >> ConstantValue.PRE_MAX_LV_SHFBIT);
	}
	
	public void setPreMaxLevel(int charactor, int maxLevel) {
		charactorInfo[charactor] &= (~ConstantValue.PRE_MAX_LV_MASK);
		charactorInfo[charactor] |= (maxLevel << ConstantValue.PRE_MAX_LV_SHFBIT);
	}
	
	public int getCharactorAvailable(int charactor) {
		if(charactor < 0) return ConstantValue.NOT_AVAILABLE;
		
		int availability = (charactorInfo[charactor] & ConstantValue.AVAILABLITIY_MASK);
		return ( availability >> ConstantValue.AVAILABLITIY_SHFBIT);
	}
	
	public void setCharactorAvailable(int charactor) {		
		int availability = (ConstantValue.NOT_FEED << ConstantValue.AVAILABLITIY_SHFBIT);
		charactorInfo[charactor] &= (~ConstantValue.AVAILABLITIY_MASK);
		charactorInfo[charactor] |= availability;
	}
	
	public void clearCharactorAvailable(int charactor) {		
		int availability = (ConstantValue.NOT_AVAILABLE << ConstantValue.AVAILABLITIY_SHFBIT);
		charactorInfo[charactor] &= (~ConstantValue.AVAILABLITIY_MASK);
		charactorInfo[charactor] |= availability;
	}
	
	public void setCharactorFeed(int charactor) {
		int availability = (ConstantValue.IS_FEED << ConstantValue.AVAILABLITIY_SHFBIT);
		charactorInfo[charactor] &= (~ConstantValue.AVAILABLITIY_MASK);
		charactorInfo[charactor] |= availability;
	}
	
	public boolean getSubspeciesFeed(int charactor, int subspecies) {
		int subSpeciesInfo = (charactorInfo[charactor] & ConstantValue.SUBSPECIES_MASK);
		subSpeciesInfo >>= ConstantValue.SUBSPECIES_SHFBIT;
		
		int mask = 1 << subspecies;		
		return ( (subSpeciesInfo & mask) != 0);
	}
	
	public void setSubspeciesFeed(int subspecies) {
		subSpecises = subspecies;
		
		int mask = 1 << (subspecies + ConstantValue.SUBSPECIES_SHFBIT);		
		charactorInfo[curCharactor] |= mask;
	}	
	
	public boolean isLevelMax() {
		return level == ConstantValue.MAX_LEVEL;
	}
	
	protected void updateVersion() {
		/*for(int i = 0; i < ConstantValue.TOTAL_CHARACTOR; i++) {
			if(getCharactorAvailable(i) == ConstantValue.NOT_AVAILABLE)
				charactorInfo[i] = ConstantValue.DEFAULT_CHARACTOR_SETTING[i];	
		}*/
	}
	
	public long loadData()
	{
		SharedPreferences prefs = ctx.getSharedPreferences(
			ConstantValue.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);		
		
		/*for(int i = 0; i < charactorInfo.length; i++) {
			charactorInfo[i] 	= prefs.getInt(ConstantValue.KEY_CHARACTOR_AVAILABLE[i], 0);
		}*/		
		charactorInfo[0] 	= prefs.getInt(ConstantValue.KEY_CHARACTOR_AVAILABLE[0], 0);
		
		int temp 	= prefs.getInt(ConstantValue.KEY_CHARACTOR, 0);		
		curCharactor = (temp & 0xffff0000) >> 16;
		subSpecises  = (temp & 0xffff);
		
		level		= prefs.getInt(ConstantValue.KEY_LEVEL, 0);
		status		= prefs.getInt(ConstantValue.KEY_STATUS, ConstantValue.STATUS_NORMAL);
		money		= prefs.getInt(ConstantValue.KEY_MONEY, 0);		
		frozenMoney = prefs.getInt(ConstantValue.KEY_MONEY_FROZEN, 0);
		
		eggTime 			 = prefs.getInt(ConstantValue.KEY_EGG_TIME, 0);
		eggGold 			 = prefs.getInt(ConstantValue.KEY_EGG_GOLD, 0);
		eggDark 			 = prefs.getInt(ConstantValue.KEY_EGG_DARK, 0);
		eggChangeTemperature = prefs.getInt(ConstantValue.KEY_EGG_CHANGE, 0);
		eggTemperature		 = prefs.getInt(ConstantValue.KEY_EGG_TEMPERATUE, 0);
		eggHatchTime		 = prefs.getInt(ConstantValue.KEY_EGG_HATCH_TIME, 0);
		//eggCurTemperature	 = prefs.getInt(ConstantValue.KEY_EGG_CUR_TEMP, PetUnlock.MID_TEMPERATURE);
		
		for(int i = 0; i < extra.length; i++) {
			extra[i]= prefs.getInt(ConstantValue.KEY_EXTRA[i]	, 0);	
		}
		
		fat		= prefs.getInt(ConstantValue.KEY_FAT		, 0);	
		
		satisfy	= prefs.getFloat(ConstantValue.KEY_SATISFY	, ConstantValue.INIT_EXP_LEVEL[0]);
		HP		= prefs.getFloat(ConstantValue.KEY_HP		, ConstantValue.HP_LEVEL[0]);			
		
		
		Long lastCloseTime = prefs.getLong(ConstantValue.KEY_LAST_CLOSE, 
			System.currentTimeMillis());
		
		return lastCloseTime;  	
	}
	
	public void saveData(){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(
				ConstantValue.DEFAULT_SHARED_PREF, Context.MODE_PRIVATE).edit();		
		
		for(int i = 0; i < charactorInfo.length; i++) {
			editor.putInt(ConstantValue.KEY_CHARACTOR_AVAILABLE[i], charactorInfo[i]);
		}
			
		for(int i = 0; i < extra.length; i++) {
			editor.putInt(ConstantValue.KEY_EXTRA[i], extra[i]);
		}		
		
		editor.putInt(ConstantValue.KEY_FAT, fat);
		
		int temp = ( ((curCharactor << 16) & 0xffff0000) | (subSpecises & 0xffff) );
		editor.putInt(ConstantValue.KEY_CHARACTOR, temp);		
		
		editor.putInt(ConstantValue.KEY_LEVEL, level);
		editor.putInt(ConstantValue.KEY_STATUS, status);
		editor.putInt(ConstantValue.KEY_MONEY, money);
		editor.putInt(ConstantValue.KEY_MONEY_FROZEN, frozenMoney);
		editor.putInt(ConstantValue.KEY_EGG_TIME, eggTime);
		editor.putInt(ConstantValue.KEY_EGG_GOLD, eggGold);
		editor.putInt(ConstantValue.KEY_EGG_DARK, eggDark);
		editor.putInt(ConstantValue.KEY_EGG_CHANGE, eggChangeTemperature);
		editor.putInt(ConstantValue.KEY_EGG_TEMPERATUE, eggTemperature);
		editor.putInt(ConstantValue.KEY_EGG_HATCH_TIME, eggHatchTime);
		editor.putInt(ConstantValue.KEY_EGG_CUR_TEMP, eggCurTemperature);
		
		editor.putFloat(ConstantValue.KEY_SATISFY, satisfy);		
		editor.putFloat(ConstantValue.KEY_HP, HP);		
		editor.putInt(ConstantValue.KEY_VERSION, version);
		editor.putLong(ConstantValue.KEY_LAST_CLOSE, System.currentTimeMillis());
		
		editor.commit();
	}
	
	public void setCharactor(int charactor) {
		curCharactor = charactor;
		subSpecises  = 0;
	}
}
