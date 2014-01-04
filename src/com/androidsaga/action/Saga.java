package com.androidsaga.action;

import com.androidsaga.R;
import com.androidsaga.base.*;

import android.content.Context;

public class Saga extends ActionBase {
	
	/*protected static final Integer EXTRA_BRANDY		= 0;	
	protected static final Integer EXTRA_TOUCH		= 1;
	protected static final Integer EXTRA_UNHAPPY	= 2;
	protected static final Integer EXTRA_TOTALTIME	= 3;
	protected static final Integer EXTRA_WOMAN		= 4;
	*/
	
	// 食物的售价和改变好感值
	public static final Integer[] FOOD_RESID   = {
		R.drawable.food_annasui, R.drawable.food_blood_tofu, R.drawable.food_bodybear, 
		R.drawable.food_bodycarrot, R.drawable.food_bomb, R.drawable.food_caesar_salad,
		R.drawable.food_chocolate, R.drawable.food_cigarette, R.drawable.food_coffee,
		R.drawable.food_diego_tail, R.drawable.food_diettea, R.drawable.food_bodyhead
	};	
			
	public static final Integer[] FOOD_SATISFY = {1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 10, 99};	
	public static final Float  [] FOOD_HP	   = {0.1f, 0.1f, 0.1f, 0.15f, 0.15f, 0.2f, 0.2f, 0.25f, 0.25f, 0.3f, 0.3f, 0.5f};
				
	public static final Integer[] FOOD_DESCRIPTION = {
		R.string.aries, R.string.taurus, R.string.cancer, R.string.leo, R.string.virgo, R.string.libra,
		R.string.scorpio, R.string.sagittarius, R.string.capricorn, R.string.aquarius, R.string.pisces, R.string.gemini
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
	
	public static final int END_NORMAL  	= 0;
	public static final int END_BRANDY 	 	= 1;
	public static final int END_NOPLUME 	= 2;
	public static final int END_WOMANBODAY 	= 3;
	public static final int END_MARIO		= 4;
	
	// subspecies images
	public static final Integer[] SUBSPECIES_ID = {
		R.drawable.caesar_blood,
		R.drawable.caesar_brandy,
		R.drawable.caesar_noplume,
		R.drawable.caesar_girl,
		R.drawable.caesar_mario
	};
	
	public Saga(Context _ctx) {
		super(_ctx);
		
		soundPoolMap[TOUCH]   = soundPool.load(ctx, R.raw.saga_touched, 1);  
        soundPoolMap[LEVELUP] = soundPool.load(ctx, R.raw.saga_levelup, 2);      
        
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
	public void onLevelMax(PetBase pet) {
		int subspecies = 0;
		//int[] availSubspecies = new int[SUBSPECIES_ID.length];		
		subspecies = rnd.nextInt(SUBSPECIES_ID.length); //availSubspecies[rnd.nextInt(availSubspeciesCount)];
		
		pet.petData.setSubspeciesFeed(subspecies);
		pet.updatePetImages(pet.petData.curCharactor, false);
		pet.setMaxLevelString(maxLevelStrings[pet.petData.subSpecises]);
		pet.resetStatus(-1);
		pet.showString("", -1);		
	}	
}
