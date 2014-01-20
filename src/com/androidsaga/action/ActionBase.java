package com.androidsaga.action;

import java.util.Random;

import android.R.integer;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.androidsaga.Alert;
import com.androidsaga.R;
import com.androidsaga.base.*;

public class ActionBase {
	protected int  hits = 0;
	protected long lastTouch;
	protected Random rnd = new Random(System.currentTimeMillis());	
	protected SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	protected Context ctx;
	protected int[][] soundPoolMap = new int[ConstantValue.MAX_LEVEL][4];	
	
	protected float hpStep = 0.0005f;
	protected float satisfyStep = 0.002f;
	
	protected float periodHP;
	protected float periodSatisfy;	
	
	protected String[][] dialogStrings = new String[ConstantValue.MAX_LEVEL][];
	protected String[] maxLevelStrings;
	protected String[] foodStrings;
	
	public static final Integer DIALOG_TOUCH_HAPPY   	= 0;
	public static final Integer DIALOG_TOUCH_UNHAPPY 	= 1;
	public static final Integer DIALOG_LONELY		  	= 2;
	public static final Integer DIALOG_HUNGRY			= 3;
	public static final Integer DIALOG_AWAKEN			= 4;
	public static final Integer DIALOG_LEVELUP			= 5;
	public static final Integer DIALOG_NOTHUNGRY		= 6;
	public static final Integer DIALOG_DROP			= 7;
	public static final Integer DIALOG_DROPDEAD		= 8;
	public static final Integer DIALOG_ANGRY		= 9;
	public static final Integer DIALOG_DEAD			= 15;	
	
	protected static int TOUCH = 0;
	protected static int LEVELUP = 1;
	
	public boolean updateLibraryDead  = false;
	public boolean updateLibraryMaxLv = false;
	
	public ActionBase(Context _ctx) {
		ctx = _ctx;
		lastTouch = System.currentTimeMillis();		
	}
	
	protected void setupUpdateStep(Data petData) {
		
	}
	
	protected void getPeriodStep(Data petData, int elapsedTime) {
		periodHP = (float)elapsedTime*hpStep;
		periodSatisfy = (float)elapsedTime*satisfyStep;
	}
	
	protected void updateExtra(PetBase pet, int elapsedTime) {
		
	}
	
	protected void killPet(Data data) {
		data.HP = 0.f;
		data.status = ConstantValue.STATUS_DEAD;	
		data.setPreMaxLevel(data.curCharactor, data.getMaxLevel(data.curCharactor));
		data.setMaxLevel(data.curCharactor, 0);	
		data.saveData();
		
		updateLibraryDead = true;
	}
	
	public void onUpdatePerSecond(PetBase pet) {
		Data data = pet.petData;
		
		setupUpdateStep(pet.petData);
		
		// do nothing if level max	 
		if(pet.petData.isLevelMax())  return;
		
		//if charactor is valid, and status is normal
		if (data.curCharactor != ConstantValue.NONE &&
			data.status == ConstantValue.STATUS_NORMAL) {				
					
			// ¼¢¶öÖµ
			data.HP -= hpStep;	
					
			// ¼õºÃ¸Ð
			data.satisfy -= satisfyStep;
			if(data.satisfy < 0.f) data.satisfy = 0.f;					
				
			if(data.satisfy > ConstantValue.EXP_LEVEL[data.level]) {				
				pet.showString(dialogStrings[pet.petData.level][DIALOG_LEVELUP], 5000);				
				onLevelUp(pet);
			}
					
			if (data.HP <= 0.f) {
				killPet(data);
			}		
		}
		updateExtra(pet, 1);
	}
	
	public void onUpdatePeriod(PetBase pet, int elapsedTime) {
		Data data = pet.petData;
		
		// do nothing if level max	 
		if(pet.petData.isLevelMax())  return;
		
		// system time has been changed
		if(elapsedTime < 0) {
			clearPetData(pet.petData, 0);
			//for(int i = 0; i < 32; i++) {
			//	pet.petData.charactorInfo[i] = ConstantValue.DEFAULT_CHARACTOR_SETTING[i];
			//}
			pet.petData.setCharactorFeed(pet.petData.curCharactor);
			Alert.showAlert("JOJO", ctx.getResources().getString(R.string.change_system_time), ctx);
			return;
		}
		getPeriodStep(pet.petData, elapsedTime);
		
		if(data.curCharactor != ConstantValue.NONE) {			
			
			// decrease the HP/satisfy if is not sleeping
			if (data.status == ConstantValue.STATUS_NORMAL) {
				data.satisfy -= periodSatisfy;
				if(data.satisfy < 0.f) data.satisfy = 0.f;						
				data.HP -= periodHP;
			}	
			
			if (data.HP < 0.f) {
				killPet(data);
			}
			
			updateExtra(pet, elapsedTime);
		}	
	}
	
	public void onLevelMax(PetBase pet) {
		
	}
	
	public void updatePetImage(PetBase pet) {
		pet.updatePetImages(pet.petData.level, false);
		if(pet.petData.isLevelMax()) {
			pet.setMaxLevelString(maxLevelStrings[pet.petData.subSpecises]);
		}
	}
	
	protected void onLevelUp(PetBase pet) {
		pet.isUpdate = false;
		
		pet.petData.level++;			
		pet.petData.HP = ConstantValue.HP_LEVEL[pet.petData.level];
		pet.petData.satisfy = ConstantValue.INIT_EXP_LEVEL[pet.petData.level];		
		if(pet.petData.getMaxLevel(pet.petData.curCharactor) < pet.petData.level) {
			pet.petData.setMaxLevel(pet.petData.curCharactor, pet.petData.level);
		}
		if(pet.petData.isLevelMax()) {
			onLevelMax(pet);
		}
		else {
			if(!pet.petData.quiet) {
				playVoice(soundPoolMap[pet.petData.level][LEVELUP]);
			}
			else {
				pet.showString(dialogStrings[pet.petData.level][DIALOG_LEVELUP], 3000);
			}			
			pet.updatePetImages(pet.petData.level, false);
		}
		
		pet.petData.saveData();
		pet.isUpdate = true;		
	}
	
	protected void playVoice(int soundID) {
		AudioManager mgr = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE); 
        //float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
        //float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       

        //float volume = streamVolumeCurrent/streamVolumeMax;   
		float volume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);  
        soundPool.play(soundID, volume, volume, 1, 0, 1.f);	
	}
	
	public void onUpdateStatus(PetBase pet) {
		// do nothing if level max	 
		if(pet.petData.isLevelMax())  return;
		
		if(pet.petData.curCharactor != ConstantValue.NONE) {
			if(pet.petData.status == ConstantValue.STATUS_DEAD) {
				pet.status = PetImageDepot.DEAD;
				pet.showString(dialogStrings[pet.petData.level][DIALOG_DEAD], Integer.MAX_VALUE);			
				return;
			}
			
			if(pet.petData.status == ConstantValue.STATUS_SLEEP) {
				pet.status = PetImageDepot.SLEEP;				
				return;
			}
			
			if(pet.petData.satisfy < 1.f) {
				pet.status = PetImageDepot.SAD;				
			}		
			else if(pet.petData.satisfy < ConstantValue.EXP_LEVEL[pet.petData.level]*3/10) {
				pet.status = PetImageDepot.ANGRY;				
			}
			else {
				//idle case
				if(pet.petData.HP < ConstantValue.HP_LEVEL[pet.petData.level]*2/10) {
					pet.status = PetImageDepot.SAD;
					pet.showString(dialogStrings[pet.petData.level][DIALOG_HUNGRY], Integer.MAX_VALUE);					
				}
				else {
					pet.status = PetImageDepot.IDLE;
				}
			}
			
			onEscape(pet);
			onDrop(pet);
		}
		
	}
	
	protected void onAwakenExtra(Data petData) {
		
	}
	
	protected void onTouchExtra(Data petData) {
		
	}
	
	public void onTouchDown(PetBase pet, int x, int y) {	
		// do nothing if level max	 
		if(pet.petData.isLevelMax())  return;
				
		//int charactor = pet.petData.curCharactor;
		// if it is dead, return directly
		if(pet.petData.status == ConstantValue.STATUS_DEAD) 
			return;
		
		if(pet.petData.status == ConstantValue.STATUS_SLEEP) {
			if(rnd.nextInt(3) == 0) {
				// you awake the pet, reduce satisfy			
				pet.petData.status = ConstantValue.STATUS_NORMAL;
				pet.petData.satisfy -= 0.5f;
				pet.setStatus(PetImageDepot.TEMP_ANGRY);			
				
				pet.showString(dialogStrings[pet.petData.level][DIALOG_AWAKEN], 2000);
				pet.resetStatus(2000);
				
				onAwakenExtra(pet.petData);
				return;
			}
			return;
		}	
		
		if(pet.status == PetImageDepot.TEMP_ANGRY) {
			return;
		}		
		
		// if it's happy, just change the status
		if(pet.petData.satisfy > ConstantValue.EXP_LEVEL[pet.petData.level]*3/10) {
			
			long currentTouch = System.currentTimeMillis();
			Log.i("jojo", Long.toString(currentTouch));
			// increase hits if 2 touch within 0.5s
			if(currentTouch - lastTouch < 500) {
				hits++;
			}
			else {
				hits = 0;
			}
			
			lastTouch = currentTouch;			
			if(hits > 5) {	
				if(rnd.nextBoolean()) {	
					// very low probability
					if(hits >= 10) {
						pet.petData.satisfy /= 2;
					}
					else {
						pet.petData.satisfy -= 0.5f;
					}
					pet.showString(dialogStrings[pet.petData.level][DIALOG_TOUCH_UNHAPPY], 2000);
					pet.setStatus(PetImageDepot.TEMP_ANGRY);
					pet.resetStatus(2000);
					onTouchTooMuch(pet.petData);									
				}				
			}			
			
			else {
				// it will be satified when being touched
				pet.petData.satisfy += 0.1f * (pet.petData.level + 1);
				pet.setStatus(PetImageDepot.HAPPY);
				if(!pet.petData.quiet) {
					playVoice(soundPoolMap[pet.petData.level][TOUCH]);
				}
				else {
					pet.showString(dialogStrings[pet.petData.level][DIALOG_TOUCH_HAPPY], Integer.MAX_VALUE);
				}
			}
		}		
		
		// if it's unhappy, will escape
		else if(pet.petData.satisfy > (float)ConstantValue.EXP_LEVEL[pet.petData.level]/10) {
			// decide to escape left or right
			int petX = pet.getX();
			int petWidth = pet.getPetWidth();
			if(x - petX < petWidth/2) {
				//move right
				pet.setTargetX(petX+40);
			}
			else {
				//move left
				pet.setTargetX(petX-40);
			}
			pet.showString(dialogStrings[pet.petData.level][DIALOG_ANGRY], Integer.MAX_VALUE);			
		}	
		
		// if it's very unhappy, just keep the status
		// do nothing
		else {
			pet.showString(dialogStrings[pet.petData.level][DIALOG_LONELY], Integer.MAX_VALUE);
		}	
		
		onTouchExtra(pet.petData);
	}
	
	protected void onTouchTooMuch(Data petData) {
		
	}
	
	public void onTouchUp(PetBase pet) {
		// do nothing if level max	 
		if(pet.petData.isLevelMax())  return;
				
		if( pet.status != PetImageDepot.TEMP_ANGRY) {
			
			if(pet.getMouseDownY() - pet.getMouseUpY() > pet.height/8) {
				float distance = 0.5f + 0.5f*rnd.nextFloat();				
				distance *= pet.getMouseDownY() - pet.getMouseUpY();
				pet.setTargetY((int)(pet.getMouseUpY() + distance));					
			}			
			pet.resetStatus(-1);
			pet.showString("", -1);			
		}
	}
	
	public void onMove(PetBase pet) {
		
	}
	
	protected void onDropExtra(Data petData) {
		
	}
	
	protected void onDrop(PetBase pet) {
		if(pet.getTargetY() >= 0) {
			if(pet.getY() >= pet.getTargetY()) {
				pet.resetTargetY();				
				onDropExtra(pet.petData);
				
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
				
				if(pet.petData.satisfy < 0) {
					pet.petData.satisfy = 0;
				}
				
				if(pet.petData.HP < 0) {
					killPet(pet.petData);
					pet.showString(dialogStrings[pet.petData.level][DIALOG_DROPDEAD], 2000);
					pet.setStatus(PetImageDepot.DEAD);				
				}
				else {
					pet.setStatus(PetImageDepot.SAD);
					pet.showString(dialogStrings[pet.petData.level][DIALOG_DROP], 2000);
					pet.resetStatus(2000);					
				}
			}
			else {
				pet.setY(pet.getY() + pet.getDropStep());
				pet.setDropStep(pet.getDropStep() + 5);
			}
		}
	}
	
	protected void onEscape(PetBase pet) {
		if(pet.getTargetX() >= 0) {
			if(pet.getTargetX() > pet.getX()) {	
				int moveStep = pet.getTargetX()-pet.getX() > 10 ? 10 : pet.getTargetX()-pet.getX();
				pet.setX(pet.getX() + moveStep);
			}
			else if(pet.getTargetX() < pet.getX()) {
				int moveStep =  pet.getX()-pet.getTargetX() > 10 ? 10 :  pet.getX()-pet.getTargetX();
				pet.setX(pet.getX() - moveStep);
			}
			else {
				pet.resetTargetX();
			}
		}
	}
	
	protected void onFoodTooMuch(Data petData) {
		
	}
	
	public void onFood(PetBase pet, int foodIdx) {
		// do nothing if level max	 
		if(pet.petData.isLevelMax())  return;	
				
		final FoodList.Food food = FoodDepot.getFood(pet.petData.curCharactor, foodIdx);
		
		// first decrease the money
		pet.petData.money -= food.cost;	
		if(pet.petData.money < 0) pet.petData.money = 0;		
				
		// if the pet is not hungry
		if(pet.petData.HP > ConstantValue.HP_LEVEL[pet.petData.level]*4/5) {
			// increase fat
			pet.petData.fat++;			
			
			pet.petData.satisfy -= Math.abs(food.satisfy);
			if(pet.petData.satisfy < 0) pet.petData.satisfy = 0;
			
			onFoodTooMuch(pet.petData);
			pet.setStatus(PetImageDepot.TEMP_ANGRY);
			pet.showString(dialogStrings[pet.petData.level][DIALOG_NOTHUNGRY], 2000);
			pet.resetStatus(2000);
		}
		else {
			pet.petData.satisfy += food.satisfy;			
			
			pet.setStatus(PetImageDepot.HAPPY);
			pet.resetStatus(2000);
			pet.showString(foodStrings[foodIdx], 2000);
			if(pet.petData.HP > ConstantValue.HP_LEVEL[pet.petData.level]) {
				pet.petData.HP = ConstantValue.HP_LEVEL[pet.petData.level];
			}			
		}		
		
		pet.petData.HP += ConstantValue.HP_LEVEL[pet.petData.level]*food.HP;
		if(pet.petData.HP > ConstantValue.HP_LEVEL[pet.petData.level]) {
			pet.petData.HP = ConstantValue.HP_LEVEL[pet.petData.level];
		}
		else if(pet.petData.HP < 0) {
			pet.petData.HP = 0.1f;
		}	
		
		onFoodExtra(pet, foodIdx);
		
		if(pet.petData.satisfy > ConstantValue.EXP_LEVEL[pet.petData.level]) {
			onLevelUp(pet);				
		}
	}
	
	public void onResurrection(final Data petData) {		
		// TODO Auto-generated method stub	
		petData.money = petData.frozenMoney = 0;
		petData.HP = 0.1f;		
		petData.status = ConstantValue.STATUS_NORMAL;									
							
		petData.setMaxLevel(petData.curCharactor, petData.getPreMaxLevel(petData.curCharactor));
		onResurrectionExtra(petData);		
	}
	
	protected void onResurrectionExtra(Data petData) {
		
	}
	
	public void clearPetData(Data petData, int level) {		
		if(petData.status != ConstantValue.STATUS_DEAD) {
			for(int i = 0; i < petData.extra.length; i++) petData.extra[i] = 0;
			for(int i = 0; i < petData.food.length ; i++) petData.food[i]  = 0;
			petData.fat = 0;
		}		
		
		petData.level		= level;
		petData.status		= ConstantValue.STATUS_NORMAL;
			
		petData.satisfy		= ConstantValue.INIT_EXP_LEVEL[level];
		petData.HP			= ConstantValue.HP_LEVEL[level];
			
		petData.money = petData.frozenMoney = 0;		
	}
	
	public void onSleep(PetBase pet) {
		// do nothing if level max	 
		if(pet.petData.isLevelMax())  return;
				
		if(pet.petData.status == ConstantValue.STATUS_NORMAL) {
    		pet.petData.status = ConstantValue.STATUS_SLEEP;
    	}
    	else if(pet.petData.status == ConstantValue.STATUS_SLEEP) {
    		pet.petData.status = ConstantValue.STATUS_NORMAL;
    	}
	}
	
	protected void onFoodExtra(PetBase pet, int foodIdx) {		
	}
}
