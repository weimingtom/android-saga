package com.androidsaga.action;

import com.androidsaga.R;
import com.androidsaga.base.*;

import android.content.Context;

public class Caesar extends ActionBase {
	
	protected static final Integer EXTRA_BRANDY		= 0;	
	protected static final Integer EXTRA_TOUCH		= 1;
	protected static final Integer EXTRA_UNHAPPY	= 2;
	protected static final Integer EXTRA_TOTALTIME	= 3;
	protected static final Integer EXTRA_WOMAN		= 4;
	
	// 食物的售价和改变好感值
	public static final Integer[] FOOD_RESID   = {
		R.drawable.food_annasui, R.drawable.food_blood_tofu, R.drawable.food_bodybear, 
		R.drawable.food_bodycarrot, R.drawable.food_bomb, R.drawable.food_caesar_salad
	};	
			
	public static final Integer[] FOOD_SATISFY = {1, 1, 2, 5, 10, 99};	
	public static final Float  [] FOOD_HP	   = {0.1f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f};
				
	public static final Integer[] FOOD_DESCRIPTION = {
		R.string.soap, R.string.brandy, R.string.joseph_nuddle,
		R.string.pigeon, R.string.girl_breath, R.string.caesar_salad
	};
	
	public static final Integer[][] IMG_ID = { 
		{
			R.drawable.caesarlv0_normal,		//0-idle;
			R.drawable.caesarlv0_sleep, 		//1-sleep;
			R.drawable.caesarlv0_touched,  	//2-happy
			R.drawable.caesarlv0_angry,  	//3-angry;
			R.drawable.caesarlv0_hungry, 	//4-sad			
			R.drawable.caesarlv0_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.caesarlv0_dead,		//7-dead
		},
		
		{
			R.drawable.caesarlv1_normal,		//0-idle;
			R.drawable.caesarlv1_sleep, 		//1-sleep;
			R.drawable.caesarlv1_touched,  	//2-happy
			R.drawable.caesarlv1_angry,  	//3-angry;
			R.drawable.caesarlv1_hungry, 	//4-sad			
			R.drawable.caesarlv1_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.caesarlv1_dead,		//7-dead
		},
		
		{
			R.drawable.caesarlv2_normal,		//0-idle;
			R.drawable.caesarlv2_sleep, 		//1-sleep;
			R.drawable.caesarlv2_touched,  	//2-happy
			R.drawable.caesarlv2_angry,  	//3-angry;
			R.drawable.caesarlv2_hungry, 	//4-sad			
			R.drawable.caesarlv2_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.caesarlv2_dead,		//7-dead
		},
		
		{
			R.drawable.caesarlv3_normal,		//0-idle;
			R.drawable.caesarlv3_sleep, 		//1-sleep;
			R.drawable.caesarlv3_touched,  	//2-happy
			R.drawable.caesarlv3_angry,  	//3-angry;
			R.drawable.caesarlv3_hungry, 	//4-sad			
			R.drawable.caesarlv3_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.caesarlv3_dead,		//7-dead
		},
		
		{
			R.drawable.caesarlv4_normal,		//0-idle;
			R.drawable.caesarlv4_sleep, 		//1-sleep;
			R.drawable.caesarlv4_touched,  	//2-happy
			R.drawable.caesarlv4_angry,  	//3-angry;
			R.drawable.caesarlv4_hungry, 	//4-sad			
			R.drawable.caesarlv4_angry,		//5-temp angry
			-1,								//6-reserved
			R.drawable.caesarlv4_dead,		//7-dead
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
	
	public Caesar(Context _ctx) {
		super(_ctx);
		
		//soundPoolMap[TOUCH]   = soundPool.load(ctx, R.raw.caesar_touched, 1);  
        //soundPoolMap[LEVELUP] = soundPool.load(ctx, R.raw.caesar_levelup, 2);    
        
        //dialogStrings   = ctx.getResources().getStringArray(R.array.caesar_dialog);
        //maxLevelStrings = ctx.getResources().getStringArray(R.array.caesar_subspecies_alert);
        //foodStrings		= ctx.getResources().getStringArray(R.array.caesar_onfood);
	}
	
	@Override
	protected void onDrop(PetBase pet) {
		if(pet.getTargetY() >= 0) {
			if(pet.getY() >= pet.getTargetY()) {
				pet.resetTargetY();
				pet.setDropStep(1);
				
				if(pet.petData.level == 0 && rnd.nextInt(3) == 0) {
					pet.petData.HP = -0.1f;
					pet.petData.satisfy /= 2;
				}
				else {
					if( pet.petData.HP >= (float)ConstantValue.HP_LEVEL[pet.petData.level]/20 && 
						rnd.nextInt(10) == 0) {
						pet.petData.HP /= 2;						
						pet.petData.satisfy /= 2;
					}
					else {
						pet.petData.HP -= (float)ConstantValue.HP_LEVEL[pet.petData.level]/20;
						pet.petData.satisfy -= (float)pet.getDropStep()/200.f;
					}
					
					pet.setDropStep(1);
				}				
				
				if(pet.petData.satisfy < 0) {
					pet.petData.satisfy = 0;
				}	
				
				pet.petData.extra[EXTRA_TOUCH]++;
				
				if(pet.petData.HP < 0) {
					killPet(pet.petData);
					pet.showString(dialogStrings[DIALOG_DROPDEAD], 2000);
					pet.setStatus(PetImageDepot.DEAD);					
				}				
				else {
					pet.setStatus(PetImageDepot.SAD);
					pet.showString(dialogStrings[DIALOG_DROP], 2000);
					pet.resetStatus(2000);					
				}
			}
			else {
				pet.setY(pet.getY() + pet.getDropStep());
				pet.setDropStep(pet.getDropStep() + 5);
			}
		}
	}
	
	@Override
	protected void onTouchTooMuch(Data petData) {
		petData.extra[EXTRA_TOUCH]++;		
	}
	
	@Override
	protected void updateExtra(PetBase pet, int time) {
		if(pet.petData.satisfy < ConstantValue.EXP_LEVEL[pet.petData.level]*3/10) {
			pet.petData.extra[EXTRA_UNHAPPY] += time;
		}
		pet.petData.extra[EXTRA_TOTALTIME] += time;
	}
	
	@Override 
	protected void onFoodExtra(PetBase pet, int foodIdx) {
		switch(foodIdx) {
		case 1:
			pet.petData.extra[EXTRA_BRANDY]++;			
			break;
			
		case 3:
			if(pet.petData.level == 2) {
				pet.setStatus(PetImageDepot.SAD);
				pet.resetStatus(2000);
				break;
			}
		case 4:
			pet.petData.extra[EXTRA_WOMAN]++;
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public void onLevelMax(PetBase pet) {
		int subspecies = 0;
		int[] availSubspecies = new int[SUBSPECIES_ID.length];
		int availSubspeciesCount = 0;		
		
		if(pet.petData.extra[EXTRA_TOTALTIME]*0.5 <= pet.petData.extra[EXTRA_UNHAPPY]) {
			availSubspecies[availSubspeciesCount++] = END_MARIO;
		}
		if( pet.petData.extra[EXTRA_BRANDY] >= 10) {
			availSubspecies[availSubspeciesCount++] = END_BRANDY;
		}
		if(pet.petData.extra[EXTRA_TOUCH] >= 20) {
			availSubspecies[availSubspeciesCount++] = END_NOPLUME;
		}
		
		if(availSubspeciesCount == 0){
			availSubspecies[availSubspeciesCount++] = END_NORMAL;			
		}

		subspecies = availSubspecies[rnd.nextInt(availSubspeciesCount)];
		
		pet.petData.setSubspeciesFeed(subspecies);
		pet.updatePetImages(pet.petData.curCharactor, false);
		pet.setMaxLevelString(maxLevelStrings[pet.petData.subSpecises]);
		pet.resetStatus(-1);
		pet.showString("", -1);		
	}	
}
