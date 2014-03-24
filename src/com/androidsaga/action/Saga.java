package com.androidsaga.action;

import java.util.Calendar;

import com.androidsaga.R;
import com.androidsaga.base.*;

import android.R.integer;
import android.R.raw;
import android.content.Context;

public class Saga extends ActionBase {
	
	protected static final Integer EXTRA_TOUCH		= 0;	
	protected static final Integer EXTRA_BLACK		= 1;
	protected static final Integer EXTRA_LEVEL		= 2;
	protected static final Integer EXTRA_ANGRYTIME	= 3;
	protected static final Integer EXTRA_TOTALTIME	= 4;
	
	protected static final Integer FOOD_MILK 	= 0;
	protected static final Integer FOOD_BULL 	= 1;
	protected static final Integer FOOD_CRAB 	= 2;
	protected static final Integer FOOD_LION	= 3;
	protected static final Integer FOOD_INDIAN	= 4;
	protected static final Integer FOOD_CANDY	= 5;
	protected static final Integer FOOD_APPLE	= 6;
	protected static final Integer FOOD_CHICKEN	= 7;
	protected static final Integer FOOD_GOAT	= 8;
	protected static final Integer FOOD_ICE		= 9;
	protected static final Integer FOOD_ROSE	= 10;
	protected static final Integer FOOD_XX		= 11;
	
	// 食物的售价和改变好感值
	public static final Integer[] FOOD_RESID   = {
		R.drawable.food_01, R.drawable.food_02, R.drawable.food_03, R.drawable.food_04, 
		R.drawable.food_05, R.drawable.food_06, R.drawable.food_07, R.drawable.food_08, 
		R.drawable.food_09, R.drawable.food_10, R.drawable.food_11, R.drawable.food_12
	};	
			
	public static final Integer[] FOOD_SATISFY = {1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 10, 99};	
	public static final Float  [] FOOD_HP	   = {0.1f, 0.1f, 0.1f, 0.15f, 0.15f, 0.2f, 0.2f, 0.25f, 0.25f, 0.3f, 0.3f, 0.5f};
				
	public static final Integer[] FOOD_DESCRIPTION = {
		R.string.food1, R.string.food2, R.string.food3, R.string.food4, R.string.food5, R.string.food6, 
		R.string.food7, R.string.food8, R.string.food9, R.string.food10, R.string.food11, R.string.food12
	};
	
	public static final Integer[][] IMG_ID = { 
		
		//lv0
		{
			R.drawable.sagalv0_normal,		//0-idle;
			R.drawable.sagalv0_sleep, 		//1-sleep;
			R.drawable.sagalv0_shy,  		//2-happy
			R.drawable.sagalv0_angry,  		//3-angry;
			R.drawable.sagalv0_hungry, 		//4-sad			
			R.drawable.sagalv0_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv0_death,		//7-death
		},
		
		//lv1
		{
			R.drawable.sagalv1_normal,		//0-idle;
			R.drawable.sagalv1_sleep, 		//1-sleep;
			R.drawable.sagalv1_shy,  		//2-happy
			R.drawable.sagalv1_angry,  		//3-angry;
			R.drawable.sagalv1_hungry, 		//4-sad			
			R.drawable.sagalv1_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv1_death,		//7-death
		},
		
		//lv2
		{
			R.drawable.sagalv2_normal,		//0-idle;
			R.drawable.sagalv2_sleep, 		//1-sleep;
			R.drawable.sagalv2_shy,  		//2-happy
			R.drawable.sagalv2_angry,  		//3-angry;
			R.drawable.sagalv2_hungry, 		//4-sad			
			R.drawable.sagalv2_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv2_death,		//7-death
		},
		
		//lv3
		{
			R.drawable.sagalv3_normal,		//0-idle;
			R.drawable.sagalv3_sleep, 		//1-sleep;
			R.drawable.sagalv3_shy,  		//2-happy
			R.drawable.sagalv3_angry,  		//3-angry;
			R.drawable.sagalv3_hungry, 		//4-sad			
			R.drawable.sagalv3_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv3_death,		//7-death
		},
		
		//lv4
		{
			R.drawable.sagalv4_normal,		//0-idle;
			R.drawable.sagalv4_sleep, 		//1-sleep;
			R.drawable.sagalv4_shy,  		//2-happy
			R.drawable.sagalv4_angry,  		//3-angry;
			R.drawable.sagalv4_hungry, 		//4-sad			
			R.drawable.sagalv4_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv4_death,		//7-death
		},		
		
		//lv5
		{
			R.drawable.sagalv5_normal,		//0-idle;
			R.drawable.sagalv5_sleep, 		//1-sleep;
			R.drawable.sagalv5_shy,  		//2-happy
			R.drawable.sagalv5_angry,  		//3-angry;
			R.drawable.sagalv5_hungry, 		//4-sad			
			R.drawable.sagalv5_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv5_death,		//7-death
		},
				
		//lv6
		{
			R.drawable.sagalv6_normal,		//0-idle;
			R.drawable.sagalv6_sleep, 		//1-sleep;
			R.drawable.sagalv6_shy,  		//2-happy
			R.drawable.sagalv6_angry,  		//3-angry;
			R.drawable.sagalv6_hungry, 		//4-sad			
			R.drawable.sagalv6_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv6_death,		//7-death
		},		
				
		//lv7
		{
			R.drawable.sagalv7_normal,		//0-idle;
			R.drawable.sagalv7_sleep, 		//1-sleep;
			R.drawable.sagalv7_shy,  		//2-happy
			R.drawable.sagalv7_angry,  		//3-angry;
			R.drawable.sagalv7_hungry, 		//4-sad			
			R.drawable.sagalv7_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv7_death,		//7-death
		},	
		
		//lv8
		{
			R.drawable.sagalv8_normal,		//0-idle;
			R.drawable.sagalv8_sleep, 		//1-sleep;
			R.drawable.sagalv8_shy,  		//2-happy
			R.drawable.sagalv8_angry,  		//3-angry;
			R.drawable.sagalv8_hungry, 		//4-sad			
			R.drawable.sagalv8_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv8_death,		//7-death
		},	
		
		//lv9
		{
			R.drawable.sagalv9_normal,		//0-idle;
			R.drawable.sagalv9_sleep, 		//1-sleep;
			R.drawable.sagalv9_shy,  		//2-happy
			R.drawable.sagalv9_angry,  		//3-angry;
			R.drawable.sagalv9_hungry, 		//4-sad			
			R.drawable.sagalv9_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.sagalv9_death,		//7-death
		},	
	};
	
	public static final int END_NORMAL  = 0;
	public static final int END_NUDE	= 1;
	public static final int END_MEGAMI 	= 2;
	public static final int END_DOCTOR 	= 3;
	public static final int END_CEO		= 4;
	public static final int END_FATHER	= 5;
	
	// subspecies images
	public static final Integer[] SUBSPECIES_ID = {
		R.drawable.ending_0,
		R.drawable.ending_1,
		R.drawable.ending_2,
		R.drawable.ending_3,
		R.drawable.ending_4,
		R.drawable.ending_5
	};
	
	protected int[] soundPoolLevelMax = new int[16];	
	
	public Saga(Context _ctx) {
		super(_ctx);
		
		
		soundPoolLvUp[0] = soundPool.load(ctx, R.raw.levelup01, 1);   
	    soundPoolLvUp[1] = soundPool.load(ctx, R.raw.levelup01, 1);
	    soundPoolLvUp[2] = soundPool.load(ctx, R.raw.levelup01, 1);
	    soundPoolLvUp[3] = soundPool.load(ctx, R.raw.levelup03, 1); 
	    soundPoolLvUp[4] = soundPool.load(ctx, R.raw.levelup04, 1); 
	    soundPoolLvUp[5] = soundPool.load(ctx, R.raw.levelup05, 1); 
	    soundPoolLvUp[6] = soundPool.load(ctx, R.raw.levelup06, 1); 
	    soundPoolLvUp[7] = soundPool.load(ctx, R.raw.levelup07, 1); 
	    soundPoolLvUp[8] = soundPool.load(ctx, R.raw.levelup08, 1);  
	    soundPoolLvUp[9] = soundPool.load(ctx, R.raw.levelup09, 1);   	    
		
	    soundPoolTouch[0][0] = 0;
		soundPoolTouch[0][1] = soundPool.load(ctx, R.raw.touch0_child, 1); soundPoolTouch[0][0]++;
		soundPoolTouch[0][2] = soundPool.load(ctx, R.raw.touch1_child, 1); soundPoolTouch[0][0]++;
		soundPoolTouch[1][0] = 0;
		soundPoolTouch[1][1] = soundPool.load(ctx, R.raw.touch0, 1); soundPoolTouch[1][0]++;
		soundPoolTouch[1][2] = soundPool.load(ctx, R.raw.touch1, 1); soundPoolTouch[1][0]++;
		
		soundPoolAngry[0][0] = 0;
		soundPoolAngry[0][1] = soundPool.load(ctx, R.raw.angry0_child, 1); soundPoolAngry[0][0]++;
		soundPoolAngry[0][2] = soundPool.load(ctx, R.raw.angry1_child, 1); soundPoolAngry[0][0]++;
		soundPoolAngry[1][0] = 0;
		soundPoolAngry[1][1] = soundPool.load(ctx, R.raw.angry0, 1); soundPoolAngry[1][0]++;
		soundPoolAngry[1][2] = soundPool.load(ctx, R.raw.angry1, 1); soundPoolAngry[1][0]++;
		
		soundPoolDying[0][0] = 0;
		soundPoolDying[0][1] = soundPool.load(ctx, R.raw.dying0_child, 1); soundPoolDying[0][0]++;
		soundPoolDying[0][2] = soundPool.load(ctx, R.raw.dying1_child, 1); soundPoolDying[0][0]++;
		soundPoolDying[0][3] = soundPool.load(ctx, R.raw.dying2_child, 1); soundPoolDying[0][0]++;
		soundPoolDying[1][0] = 0;
		soundPoolDying[1][1] = soundPool.load(ctx, R.raw.dying0, 1); soundPoolDying[1][0]++;
		soundPoolDying[1][2] = soundPool.load(ctx, R.raw.dying1, 1); soundPoolDying[1][0]++;
		soundPoolDying[1][3] = soundPool.load(ctx, R.raw.dying2, 1); soundPoolDying[1][0]++;
		
		soundPoolResurrection = soundPool.load(ctx, R.raw.resurrection, 1);
		
		soundPoolLevelMax[END_NORMAL] = soundPool.load(ctx, R.raw.ending_tomb,    1);
		soundPoolLevelMax[END_DOCTOR] = soundPool.load(ctx, R.raw.ending_doctor,  1);
		soundPoolLevelMax[END_MEGAMI] = soundPool.load(ctx, R.raw.ending_megami,  1);
		soundPoolLevelMax[END_CEO]	  = soundPool.load(ctx, R.raw.ending_ceo,     1);
		soundPoolLevelMax[END_NUDE]	  = soundPool.load(ctx, R.raw.ending_nocloth, 1);
		soundPoolLevelMax[END_FATHER] = soundPool.load(ctx, R.raw.ending_father,  1);
        
        dialogStrings[0]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv0);
        dialogStrings[1]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv1);
        dialogStrings[2]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv2);
        dialogStrings[3]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv3);
        dialogStrings[4]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv4);
        dialogStrings[5]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv5);
        dialogStrings[6]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv6);
        dialogStrings[7]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv7);
        dialogStrings[8]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv8);
        dialogStrings[9]   = ctx.getResources().getStringArray(R.array.saga_dialog_lv9);
        
        maxLevelStrings = ctx.getResources().getStringArray(R.array.saga_subspecies_alert);
        foodStrings		= ctx.getResources().getStringArray(R.array.saga_onfood);
	}	
	
	@Override
	protected void onLevelUp(PetBase pet) {
		pet.petData.extra[EXTRA_LEVEL] |= (1 << pet.petData.level);		
		super.onLevelUp(pet);
	}
	
	@Override
	protected void setupUpdateStep(Data petData) {
		if(petData.level == 9) {
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			
			// it's daylight
			if(hour > 6 && hour < 18) {
				hpStep = 0.1f;
				satisfyStep = 0.01f;
			}
			// it's night
			else if(hour < 6 || hour > 18) {
				hpStep = 0.0005f;
				satisfyStep = 0.002f;
			}
			// it's morning and dawn
			else {
				hpStep = 0.005f;
				satisfyStep = 0.005f;
			}
		}
		else {
			hpStep = 0.0005f;
			satisfyStep = 0.002f;
		}
	}
	
	@Override
	protected void getPeriodStep(Data petData, int elapsedTime) {
		if(petData.level == 9) {
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int dayPassed = elapsedTime/(24*3600);
			elapsedTime -= dayPassed*24*3600;
			
			periodHP = (float)dayPassed*24*3600*0.001f;
			periodSatisfy = (float)dayPassed*24*3600*0.002f;
			
			int previousHour = hour - elapsedTime/3600;
			if(previousHour < 0) previousHour = 0;
			
			// in daylight
			if(hour > 6 && hour < 18) {			
				if(previousHour > 6 && previousHour < 18) {
					periodHP += 0.1f*elapsedTime;
					periodSatisfy += 0.01f*elapsedTime;
				}
				else {
					periodHP += 0.005*elapsedTime;
					periodSatisfy += 0.0005*elapsedTime;
				}			
			}
			// in night
			else {			
				if(previousHour > 6 && previousHour < 18) {
					periodHP += 0.005*elapsedTime;
					periodSatisfy += 0.0005*elapsedTime;				
				}
				else {
					periodHP += 0.0005f*elapsedTime;
					periodSatisfy += 0.002f*elapsedTime;
				}			
			}
		}
		else {
			periodHP += 0.0005f*elapsedTime;
			periodSatisfy += 0.002f*elapsedTime;
		}
		periodClean += 0.005f*elapsedTime;
	}
	
	@Override
	protected void playAngryVoice(Data data) {
		if(!data.quiet) {
			int soundType = (data.level > 3 ? 1 : 0);
			int idx = soundPoolAngry[soundType][rnd.nextInt(soundPoolAngry[soundType][0]) + 1];
			playVoice(idx);
		}
	}
	
	@Override 
	protected void playDyingVoice(Data data) {
		if(!data.quiet) {
			int soundType = (data.level > 3 ? 1 : 0);
			int idx = soundPoolDying[soundType][rnd.nextInt(soundPoolDying[soundType][0]) + 1];
			playVoice(idx);
		}
	}
	
	@Override 
	protected void playTouchVoice(Data data) {
		if(!data.quiet) {
			int soundType = (data.level > 3 ? 1 : 0);
			int idx = soundPoolTouch[soundType][rnd.nextInt(soundPoolTouch[soundType][0]) + 1];
			playVoice(idx);
		}
	}
	
	@Override
	protected void onTouchExtra(Data petData) {
		petData.extra[EXTRA_TOUCH]++;
	}
	
	@Override
	protected void onTouchTooMuch(Data petData) {
		petData.extra[EXTRA_BLACK]++;
	}
	
	@Override
	protected void onFoodTooMuch(Data petData) {
		petData.extra[EXTRA_BLACK]++;
	}
	
	@Override
	protected void onAwakenExtra(Data petData) {
		petData.extra[EXTRA_BLACK]++;
	}
	
	@Override
	protected void updateExtra(PetBase pet, int elapsedTime) {
		if(pet.petData.satisfy < ConstantValue.EXP_LEVEL[pet.petData.level]*3/10) {
			pet.petData.extra[EXTRA_ANGRYTIME] += elapsedTime;
		}
		pet.petData.extra[EXTRA_TOTALTIME] += elapsedTime;
	}
	
	@Override
	protected void onFoodExtra(PetBase pet, int foodIdx) {		
		pet.petData.food[foodIdx]++;
	}
	
	@Override
	public void updatePetImage(PetBase pet) {
		if( pet.petData.isLevelMax()) {
			pet.updatePetImages(true);
		}
		else {
			pet.updatePetImages(false);
		}
		
		if(pet.petData.isLevelMax()) {
			pet.setMaxLevelString(maxLevelStrings[pet.petData.subSpecises]);
		}
	}
	
	@Override
	public void onLevelMax(PetBase pet) {
		int subspecies = 0;
		int[] availSubspecies = new int[SUBSPECIES_ID.length];		
		int availSubspeciesCount = 0;
		
		if(pet.petData.food[FOOD_XX] > 0) {
			if( pet.petData.money >= 5000 && pet.petData.food[FOOD_LION] >= 5) {
				availSubspecies[availSubspeciesCount++] = END_CEO;
			}
			
			if( pet.petData.food[FOOD_MILK] >= 10 && pet.petData.food[FOOD_CHICKEN] >= 5) {
				availSubspecies[availSubspeciesCount++] = END_FATHER;
			}
			
			if( pet.petData.food[FOOD_CANDY] >= 5 && pet.petData.extra[EXTRA_BLACK] >= 25) {
				availSubspecies[availSubspeciesCount++] = END_NUDE;
			}
			
			if( pet.petData.food[FOOD_ROSE] >= 5 && pet.petData.extra[EXTRA_BLACK] == 0 &&
				pet.petData.extra[EXTRA_ANGRYTIME] == 0) {
				availSubspecies[availSubspeciesCount++] = END_MEGAMI;
			}
			
			if( pet.petData.food[FOOD_INDIAN] >= 5 && pet.petData.extra[EXTRA_TOUCH] >= 250) {
				availSubspecies[availSubspeciesCount++] = END_DOCTOR;
			}
		}
		
		if(availSubspeciesCount == 0) {
			availSubspecies[availSubspeciesCount++] = END_NORMAL;
		}
		
		subspecies = availSubspecies[rnd.nextInt(availSubspeciesCount)];
		
		if(!pet.petData.quiet) {
			playVoice(soundPoolLevelMax[subspecies]);
		}	
		
		pet.petData.setSubspeciesFeed(subspecies);
		pet.updatePetImages(true);
		
		pet.setMaxLevelString(maxLevelStrings[pet.petData.subSpecises]);
		pet.resetStatus(-1);
		pet.showString("", -1);	
		
		updateLibraryMaxLv = true;
	}	
}
