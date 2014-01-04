package com.androidsaga;
import java.util.Calendar;

import com.androidsaga.action.*;
import com.androidsaga.base.*;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SagaActivity extends Activity {
	
	 /** Called when the activity is first created. **/
	private LinearLayout activityLayout;
	
	private ImageView MainView;
	private final static int BK_DAY   = 0;
	private final static int BK_NIGHT = 1;
	private final static int BK_DAWN  = 2;
	private final static int BK_DEAD  = -1;
	
	private int currentBK = BK_DAY;
	
	private ImageView HomeBtn;
	private ImageView FoodBtn;
	private ImageView GameBtn;
	private ImageView SleepBtn;
	//private ImageView unlockBtn;
	private ImageView libraryBtn;
	private ImageView StatusView;	
	
	private TextView[] foodTextViews 		= new TextView[ConstantValue.MAX_LEVEL+2];
	private ImageView[] foodImageViews 		= new ImageView[ConstantValue.MAX_LEVEL+2];
	private LinearLayout[] foodItemLayouts 	= new LinearLayout[ConstantValue.MAX_LEVEL+2];

	private Integer width;
	private Integer height;
	private Integer btnSize;
	
	private PetBase pet;
	private ActionBase action;
	
	private ScrollView 		libraryView;
	private ScrollView 		foodView;
	private ScrollView 		gameView;	
	private LinearLayout 	mainLayout;
	private StatusBase 		statusBase;
	//private LinearLayout 	unlockView;
	
	private ImageView[] charactorSelectImage = new ImageView[32];
	private ImageView charactorDetailImage;
	private TextView libraryCharactorName;
	private TextView libraryCharactorDetail;
	private Button librarySelectCharactor;	
	
	private Button hP2MoneyButton;
	private Button principalButton;
	private TextView gameDescription;
	private TextView gameResult;
	private ImageView opponentImage;
	private ImageView opponentResult;
	private ImageView myImage;
	private ImageView myResult;
	
	private ImageView stoneBtn;
	private ImageView clothBtn;
	private ImageView scissorsBtn;
	
	private Handler drawHandle;		
	private Runnable mRunnable;
		
	private boolean isRunning = false;
	private Vibrator vibrator;
	
	private int isHomeView 		= 1;
	private int isFoodView		= 0;
	private int isLibraryView	= 0;
	private int isGameView		= 0;
	//private int isUnlock 		= 0;
	
	private int[] homeBtnImage 		= {R.drawable.home1		, R.drawable.home2};
	private int[] gameBtnImage		= {R.drawable.game1		, R.drawable.game2};
	private int[] foodBtnImage		= {R.drawable.food1		, R.drawable.food2};
	private int[] libraryBtnImage	= {R.drawable.library1	, R.drawable.library2};
	
	private PetGame petGame;
	private PetLibrary petLibrary;
	
	private int curLevel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);    
        
        MainView = (ImageView)findViewById(R.id.MainView);            
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);   
        
        
        width = dm.widthPixels;               
        height = dm.heightPixels;
        btnSize = width / 4;        
        height -= (40 + height/6 + btnSize);          
        
        // set background
        activityLayout = (LinearLayout)findViewById(R.id.LinearLayout01);
        activityLayout.setBackgroundResource(R.drawable.background);
        
        initMainButtons();
    	
    	// init charactor
        // set main view size according to resolution   
        pet = new PetBase(this, width, height, 40+dm.heightPixels/6);          
        //pet.petData.loadData();
        //long elapsedTime = (System.currentTimeMillis() - lastclose) / 1000;         
        action = ActionFactory.getAction(pet.petData.curCharactor, this);        
		//action.onUpdatePeriod(pet, (int)elapsedTime);	
		
		//petUnlock.updateEggPeriod(elapsedTime);
        action.updatePetImage(pet); 
    	
    	// if it's quiet mode
    	String quietKey 	= getResources().getString(R.string.quiet);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);  
    	petGame = new PetGame(this, width/3, width/3);				
		pet.petData.quiet = settings.getBoolean(quietKey, false);
		
		petLibrary = new PetLibrary(this, width/ConstantValue.IMG_PER_ROW);		
    	
    	// init food depot
    	FoodDepot.initFoodDepot(this);
    	
    	// init textviews in food view
    	initFoodView();
    	initLibraryView();    
    	initGameView();
    	//initUnlockView();
		    	
    	// init vibrator
    	vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);    	
    	
    	drawHandle = new Handler() {
    		public void handleMessage (Message msg) {    			
    			updateView();
    		}
    	};
    	
    	mRunnable = new Runnable() {
    		@Override
    		public void run() {
    			while(true){ 
    				if(isRunning) drawHandle.obtainMessage().sendToTarget();  
        			try{
        				Thread.sleep(100);
        			}
        			catch(Exception e){
        				e.printStackTrace();
        			}
        		}    		
    		}
    	};
    	
    	Thread mThread = new Thread(mRunnable);
    	mThread.start();
    	
    	Thread updateThread = new Thread() {    		
    		@Override
            public void run(){
    			while(true){        
    			
    				if(isRunning) {
    					action.onUpdatePerSecond(pet);  
    					//petUnlock.updateEggPerSecond();
    				}    				
    			
    				try{
    					Thread.sleep(1000);
    				}
    				catch(Exception e){
    					e.printStackTrace();
    				}
    			}
    		}
    	};
    	
    	updateThread.start();
    	
    	if(pet.isCharactorInited()) {
    		clickHome(HomeBtn);
    	}
    	else {
    		clickLibrary(libraryBtn);
    	}
    }
   
    protected void initMainButtons() {
    	// get main layout
        mainLayout = (LinearLayout)findViewById(R.id.MainLayout);        
        
        ViewGroup.LayoutParams laParams = MainView.getLayoutParams();
    	laParams.width  = width;
    	laParams.height = height;
    	MainView.setLayoutParams(laParams);    	    	
                
        HomeBtn		= (ImageView)findViewById(R.id.HomeButton);
        FoodBtn 	= (ImageView)findViewById(R.id.FoodButton);
        GameBtn 	= (ImageView)findViewById(R.id.GameButton);        
        SleepBtn 	= (ImageView)findViewById(R.id.SleepButton);
        libraryBtn 	= (ImageView)findViewById(R.id.LibraryButton);
        //unlockBtn 	= (ImageView)findViewById(R.id.UnlockButton);
       
        StatusView	= (ImageView)findViewById(R.id.Status_View);  
        
        // set button size  
        laParams = SleepBtn.getLayoutParams();
        laParams.width = laParams.height = ConstantValue.scalePix(this, 128);
        SleepBtn.setLayoutParams(laParams);
        
        statusBase = new StatusBase(width-laParams.width, laParams.height);
        /*laParams = StatusView.getLayoutParams();
        laParams.width = statusBase.width;
        laParams.height = statusBase.height;        
        StatusView.setLayoutParams(laParams); */
        
        laParams = HomeBtn.getLayoutParams();  
        laParams.width = laParams.height = btnSize; 
        HomeBtn.setLayoutParams(laParams);   
        
        laParams = FoodBtn.getLayoutParams();
        laParams.width = laParams.height = btnSize; 
        FoodBtn.setLayoutParams(laParams);   
        
        laParams = GameBtn.getLayoutParams();
        laParams.width = laParams.height = btnSize; 
        GameBtn.setLayoutParams(laParams);         
        
        laParams = libraryBtn.getLayoutParams();
        laParams.width = laParams.height = btnSize; 
        libraryBtn.setLayoutParams(laParams); 
        
        //laParams = unlockBtn.getLayoutParams();
        //laParams.width = laParams.height = btnSize;
        //unlockBtn.setLayoutParams(laParams);
        
        // init button images
        HomeBtn.setImageResource(homeBtnImage[isHomeView]);       
        FoodBtn.setImageResource(foodBtnImage[isFoodView]);
        GameBtn.setImageResource(gameBtnImage[isGameView]);
        libraryBtn.setImageResource(libraryBtnImage[isLibraryView]);
        //unlockBtn.setImageResource(unlockBtnImage[isUnlock]);
    }
    
    protected void updateView() {
    	if(isHomeView == 1) {
    		if(pet.isStatusUpdate()) action.onUpdateStatus(pet);
    		if(pet.isUpdate) MainView.setImageBitmap(pet.getPetImage()); 
    	}
    	
    	if(pet.isCharactorInited()) {
			StatusView.setImageBitmap(statusBase.GenerateStatus(this, pet.petData, 
					petLibrary.satisfyNames[pet.petData.curCharactor]));
			
			if(pet.petData.status == ConstantValue.STATUS_SLEEP) {
	    		SleepBtn.setImageResource(R.drawable.sleep02);
	    	}
	    	else {
	    		SleepBtn.setImageResource(R.drawable.sleep01);
	    	}   
    	}		
		
    	//if(isUnlock == 1) {
    	//	petUnlock.updatePetUnlock();
    	//}
		updateBackground(false);
	}
    
    protected void updateBackground(boolean updateNow) {
    	
    	if(pet.petData.status == ConstantValue.STATUS_DEAD) 
    	{
    		if(updateNow || currentBK != BK_DEAD) {
	    		activityLayout.setBackgroundResource(R.drawable.background_dead);
	    		currentBK = BK_DEAD;   		
    		}
    		return;
    	}
    	
    	Calendar c = Calendar.getInstance();
    	int hour = c.get(Calendar.HOUR_OF_DAY);
    	//it's night now
    	if(hour < 6 || hour > 18) {
    		if(updateNow || currentBK != BK_NIGHT) {
    			activityLayout.setBackgroundResource(R.drawable.background_night);
    			currentBK = BK_NIGHT;
    		}
    		return;
    	}
    	
    	//it's day now
    	if(hour > 6 && hour < 18) {
    		if(updateNow || currentBK != BK_DAY) {
    			activityLayout.setBackgroundResource(R.drawable.background);
    			currentBK = BK_DAY;
    		}
    		return;
    	}
    	
    	//we blend the two images    	
    	currentBK = BK_DAWN;
    	int minute = c.get(Calendar.MINUTE);
    	int second = c.get(Calendar.SECOND);
    	int millis = c.get(Calendar.MILLISECOND);
    	
    	// update per 10 minutes
    	if(updateNow || (minute % 10 == 0 && second == 0 && millis < 100 )) {
    		int blendFactorDay = 0;
    		int blendFactorNight = 0;    		
    		
    		// morning
    		if(hour < 12) {
    			blendFactorDay   = minute*255/60;
    			blendFactorNight = 255-blendFactorDay;
    		}
    		//dawn
    		else {
    			blendFactorNight = minute*255/60;
    			blendFactorDay 	 = 255-blendFactorNight;
    		}
    		
    		Bitmap bkDay   = ConstantValue.scaleButtonBitmap(this, R.drawable.background, width, Integer.MAX_VALUE);
    		Bitmap bkNight = ConstantValue.scaleButtonBitmap(this, R.drawable.background_night, width, Integer.MAX_VALUE);
    		
    		Bitmap blendImg = Bitmap.createBitmap(bkDay.getWidth(), bkDay.getHeight(), Config.ARGB_4444);    		
    		Canvas canvas = new Canvas(blendImg);
    		Paint paint = new Paint();
    		paint.setAntiAlias(true);    		
    		paint.setAlpha(blendFactorDay);
    		canvas.drawBitmap(bkDay	 , 0, 0, paint);
    		paint.setAlpha(blendFactorNight);
    		canvas.drawBitmap(bkNight, 0, 0, paint);
    		activityLayout.setBackgroundDrawable(new BitmapDrawable(blendImg));
    	}   	
    }
    
    protected void initLibraryView()
    {
    	// init library view
        libraryView = (ScrollView)findViewById(R.id.LibraryView);
        ViewGroup.LayoutParams laParams = libraryView.getLayoutParams();    	
    	laParams.width = width;
    	laParams.height = height;
    	libraryView.setLayoutParams(laParams);     	
    	
    	LinearLayout libraryLayout = (LinearLayout)findViewById(R.id.LibraryLayout);    	
    	int rows = (ConstantValue.TOTAL_CHARACTOR + ConstantValue.IMG_PER_ROW - 1)/ConstantValue.IMG_PER_ROW;
    	// let's create radio button in dynamic
    	for(int row = 0; row < rows; row++) {
    		LinearLayout rowLayout = new LinearLayout(this); 
    		rowLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    		rowLayout.setOrientation(LinearLayout.HORIZONTAL);
    		
    		for(int col = 0; col < ConstantValue.IMG_PER_ROW; col++) {
    			if(row*ConstantValue.IMG_PER_ROW + col >= charactorSelectImage.length)
    				break;    			
    			
        		int idx = row*ConstantValue.IMG_PER_ROW + col;
        		charactorSelectImage[idx] = new ImageView(this);
        		charactorSelectImage[idx].setId(idx);
        		charactorSelectImage[idx].setLayoutParams(new ViewGroup.LayoutParams(width/ConstantValue.IMG_PER_ROW, width/ConstantValue.IMG_PER_ROW));
        		charactorSelectImage[idx].setScaleType(ScaleType.CENTER_INSIDE);
        		charactorSelectImage[idx].setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						updateLibraryView(v.getId());
					}
				});
        		
        		rowLayout.addView(charactorSelectImage[idx]);
    		}
    		libraryLayout.addView(rowLayout);
    	}
    	
    	int unitSize = ConstantValue.scalePix(this, 48);
    	
    	int libraryDetailImgHeight = height- rows*petLibrary.thumbnailSize - unitSize;
    	if(libraryDetailImgHeight < height*3/5 - unitSize ) {
    		libraryDetailImgHeight = height*3/5 - unitSize;
    	}
    	
    	petLibrary.initDetailView(width/2, libraryDetailImgHeight);
    	
    	charactorDetailImage = (ImageView)findViewById(R.id.LibraryDetailImage);
    	laParams = charactorDetailImage.getLayoutParams();
    	laParams.height = petLibrary.height;
    	laParams.width = petLibrary.width;
    	charactorDetailImage.setLayoutParams(laParams);        	
    	
    	libraryCharactorName = (TextView)findViewById(R.id.LibraryCharactorName);
    	laParams = libraryCharactorName.getLayoutParams();        	
    	laParams.width = width/2;
    	laParams.height = unitSize;
    	libraryCharactorName.setLayoutParams(laParams);
    
    	libraryCharactorDetail = (TextView)findViewById(R.id.LibraryDescription);
    	librarySelectCharactor = (Button)findViewById(R.id.selectBtn);
    	
    	laParams = libraryCharactorDetail.getLayoutParams();
    	laParams.width = width/2;     	
    	laParams.height = petLibrary.height+unitSize-ConstantValue.scalePix(this, 64);  	
    	libraryCharactorDetail.setLayoutParams(laParams);
    	
    	librarySelectCharactor.setTextSize(12);
    	librarySelectCharactor.setHeight(ConstantValue.scalePix(this, 64));
    	librarySelectCharactor.setWidth(width/2);
    	
    	for(int i = 0; i < ConstantValue.TOTAL_CHARACTOR; i++) {      					
	    	charactorSelectImage[i].setImageBitmap(petLibrary.getThumbnailImage(pet.petData, i, pet.petData.curCharactor==i));	    		
    	} 	    	
    	charactorDetailImage.setImageBitmap(petLibrary.getCharactorSelection(pet.petData.curCharactor, pet.petData, pet.petData.level));
    	
    	libraryCharactorName.setText(R.string.name_unknown);
    	libraryCharactorDetail.setText(R.string.library_no_selection);
    	librarySelectCharactor.setText(petLibrary.getSelectBtnText(-1, pet.petData, 0));
    	librarySelectCharactor.setClickable(false);
    }
    
    protected void updateLibraryView(int checkID)
    {    	
    	if(checkID < 0) {
    		return;
    	}   	 	
    	
    	if(curLevel < pet.petData.level && curLevel < ConstantValue.MAX_LEVEL) {
    		for(int i = curLevel+1; i <= pet.petData.level; i++) {    	
	    		charactorSelectImage[i].setImageBitmap(
	    			petLibrary.getThumbnailImage(pet.petData, i, false));	    
	    	}      		
    	}
    	else if(curLevel == ConstantValue.MAX_LEVEL) {
    		for(int i = 0; i < Saga.SUBSPECIES_ID.length; i++) {    			
    			charactorSelectImage[i+ConstantValue.MAX_LEVEL].setImageBitmap(
    				petLibrary.getThumbnailImage(pet.petData, ConstantValue.MAX_LEVEL+i, false));    			
    		}    		
    	}
    	curLevel = pet.petData.level;
    	
    	String libraryLevel = getResources().getString(R.string.library_lv);    		
    	if(petLibrary.lastIdx >= 0) {
    		int uncheckID = petLibrary.lastIdx;
    		charactorSelectImage[uncheckID].setImageBitmap(petLibrary.getThumbnailImage(pet.petData, uncheckID, false));
    	}
    	petLibrary.lastIdx = checkID;
    	charactorSelectImage[checkID].setImageBitmap(petLibrary.getThumbnailImage(pet.petData, checkID, true));       		
    	 		
    	libraryCharactorName.setText(petLibrary.charactorNames[pet.petData.curCharactor]);
    	libraryCharactorName.append(String.format(libraryLevel, pet.petData.level));
    	librarySelectCharactor.setClickable(true);
    	
    	charactorDetailImage.setImageBitmap(petLibrary.getCharactorSelection(pet.petData.curCharactor, pet.petData, checkID));    		
    	libraryCharactorDetail.setText(petLibrary.getCharactorDescription(pet.petData.curCharactor, checkID, pet.petData));     	
    	
    	librarySelectCharactor.setText(petLibrary.getSelectBtnText(pet.petData.curCharactor, pet.petData, checkID));    	
    }
    
    protected void changeCharactor(int newLevel) {
    	
    	action.clearPetData(pet.petData, newLevel);
    	pet.petData.subSpecises  = 0;
    	
    	action.updatePetImage(pet);
    	pet.resetStatus(-1);
		pet.showString("", -1);	
		
    	petGame.resetGame();
    	updateBackground(true);
    }
    
    public void onClickSelectCharactor(View target) {   	
    	String alertString = getString(R.string.charactor_selector_prompt);  
    	alertString = String.format(alertString, petLibrary.charactorNames[pet.petData.curCharactor]);    	
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(alertString);
		builder.setTitle("JOJO");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub		
				if( pet.petData.getMaxLevel(pet.petData.curCharactor) == ConstantValue.MAX_LEVEL) {					
					changeCharactor(Math.min(petLibrary.lastIdx, ConstantValue.MAX_LEVEL-1)); 
				}
				else {
					changeCharactor(0);
				}
				updateLibraryView(pet.petData.level);
	        	
				isHomeView = 1;
		    	isFoodView = isGameView = isLibraryView = 0;
		    	
		    	HomeBtn.setImageResource(homeBtnImage[isHomeView]);		        
		        FoodBtn.setImageResource(foodBtnImage[isFoodView]);
		        GameBtn.setImageResource(gameBtnImage[isGameView]);
		        libraryBtn.setImageResource(libraryBtnImage[isLibraryView]);
		        
	        	mainLayout.setVisibility(View.VISIBLE);
	        	libraryView.setVisibility(View.GONE);  
			}			
		});
		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
			}				
		});
		
		AlertDialog ad = builder.create();
		ad.show();   	
    }
    
    public void clickLibrary(View target)
    {
    	isLibraryView = 1;
    	isFoodView = isGameView = isHomeView = 0;
    	
    	if(pet.petData.level < ConstantValue.MAX_LEVEL) {
    		updateLibraryView(pet.petData.level);
    	}
    	else if(pet.petData.level == ConstantValue.MAX_LEVEL) {
    		updateLibraryView(ConstantValue.MAX_LEVEL + pet.petData.subSpecises);
    	}
    	
    	mainLayout.setVisibility(View.GONE);
    	gameView.setVisibility(View.GONE);
    	foodView.setVisibility(View.GONE);
    	libraryView.setVisibility(View.VISIBLE); 
    	//unlockView.setVisibility(View.GONE);
    	
    	HomeBtn.setImageResource(homeBtnImage[isHomeView]);        
        FoodBtn.setImageResource(foodBtnImage[isFoodView]);
        GameBtn.setImageResource(gameBtnImage[isGameView]);
        libraryBtn.setImageResource(libraryBtnImage[isLibraryView]);
        //unlockBtn.setImageResource(unlockBtnImage[isUnlock]);
    }   
    
    public void clickHome(View target)
    {
    	isHomeView = 1;
    	isFoodView = isGameView = isLibraryView = 0;
    	
    	HomeBtn.setImageResource(homeBtnImage[isHomeView]);        
        FoodBtn.setImageResource(foodBtnImage[isFoodView]);
        GameBtn.setImageResource(gameBtnImage[isGameView]);
        libraryBtn.setImageResource(libraryBtnImage[isLibraryView]);
        //unlockBtn.setImageResource(unlockBtnImage[isUnlock]);
        
    	mainLayout.setVisibility(View.VISIBLE);
    	foodView.setVisibility(View.GONE);    	
    	gameView.setVisibility(View.GONE);
    	libraryView.setVisibility(View.GONE);
    	//unlockView.setVisibility(View.GONE);
    }
    
    public void clickSleep(View target)
    {    	
    	action.onSleep(pet);
    }  
    
    protected void initFoodView()
    {
    	foodView = (ScrollView)findViewById(R.id.FoodView);
    	ViewGroup.LayoutParams laParams = foodView.getLayoutParams();
    	laParams.width = width;
    	laParams.height = height;
    	foodView.setLayoutParams(laParams);  
    	
    	LinearLayout foodLayout = (LinearLayout)findViewById(R.id.FoodLinearView);
    	
    	for(int i = 0; i < foodItemLayouts.length; i++) {
    		foodItemLayouts[i] = new LinearLayout(this);
    		laParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    		foodItemLayouts[i].setLayoutParams(laParams);
    		foodItemLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
    		
    		foodImageViews[i] = new ImageView(this);
    		laParams = new ViewGroup.LayoutParams(width/3, width/3);
    		foodImageViews[i].setLayoutParams(laParams);
    		foodImageViews[i].setId(i);
    		foodImageViews[i].setScaleType(ScaleType.CENTER_INSIDE);
    		foodImageViews[i].setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					chooseFoods(v.getId());
				}
			});
    		
    		foodItemLayouts[i].addView(foodImageViews[i]);
			
    		foodTextViews[i] = new TextView(this);
    		laParams = new ViewGroup.LayoutParams(width*2/3, width/3);
    		foodTextViews[i].setLayoutParams(laParams);
    		foodTextViews[i].setGravity(Gravity.CENTER);
    		foodTextViews[i].setTextColor(Color.BLACK);
    		foodItemLayouts[i].addView(foodTextViews[i]);
    		foodLayout.addView(foodItemLayouts[i]);
    	}    	
    }   
    protected void updateFoodInfo() {
    	//update food info
    	if(pet.isCharactorInited()) {        	
	        // set food description here  
    		int showFoodLevel = Math.min(ConstantValue.MAX_LEVEL+2, pet.petData.getMaxLevel(pet.petData.curCharactor)+3);
    		
	        for(int i = 0; i < showFoodLevel; i++) {	
	        	foodItemLayouts[i].setVisibility(View.VISIBLE);
		        foodImageViews[i].setImageBitmap(
		        	FoodDepot.getFoodImage(this, pet.petData.curCharactor, i, width/3, width/3));
		        
		        String description = FoodDepot.getFoodDescription(pet.petData.curCharactor, i);	        
		        int cost 	= FoodDepot.getFoodCost(pet.petData.curCharactor, i);
		        int satisfy = FoodDepot.getFoodSatisfy(pet.petData.curCharactor, i);
		        int foodHP	= FoodDepot.getFoodHP(pet.petData.curCharactor, i);
		        
		        String strSatisfy = (satisfy > 0 ? "+" : "") + Integer.toString(satisfy);
		        strSatisfy += petLibrary.satisfyNames[pet.petData.curCharactor];
		        
		        String strHP = (foodHP > 0 ? "+" : "") + Integer.toString(foodHP);
		        
		        String formatString = getString(R.string.food_cost);
		        description += "\n" + String.format(formatString, strSatisfy, strHP, cost);  
		        
		        formatString = getString(R.string.money_you_have);
		        description += String.format(formatString, pet.petData.money);
		    	foodTextViews[i].setText(description);
	        }
	        
	        for(int i = showFoodLevel; i < foodItemLayouts.length; i++) {
	        	foodItemLayouts[i].setVisibility(View.GONE);
	        }
        }
    }
    
    protected void chooseFoods(final int foodIdx)
    {
    	if(!pet.isCharactorInited() || pet.petData.status == ConstantValue.STATUS_DEAD)
    		return;
    	
    	final FoodList.Food food = FoodDepot.getFood(pet.petData.curCharactor, foodIdx);
    	if(food.cost <= pet.petData.money) {
	    	String alertString = getResources().getString(R.string.food_alert);
	    	alertString = String.format(alertString, food.cost);
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(alertString);
			builder.setTitle("JOJO");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub					
					action.onFood(pet, foodIdx);  						
					
		        	isHomeView = 1;
			    	isFoodView = isGameView = isLibraryView = 0;
			    	
			    	HomeBtn.setImageResource(homeBtnImage[isHomeView]);			        
			        FoodBtn.setImageResource(foodBtnImage[isFoodView]);
			        GameBtn.setImageResource(gameBtnImage[isGameView]);
			        libraryBtn.setImageResource(libraryBtnImage[isLibraryView]);
			        
		        	mainLayout.setVisibility(View.VISIBLE);
		        	foodView.setVisibility(View.GONE);  
		        	gameView.setVisibility(View.GONE);
		        	libraryView.setVisibility(View.GONE);
				}			
			});
			builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub				
				}				
			});
			
			AlertDialog ad = builder.create();
			ad.show();   
    	}
    	else {
    		String alertString = getResources().getString(R.string.food_no_enough_money);
    		Alert.showAlert("JOJO", alertString, this);
    	}
    }
    
    public void clickFood(View target)
    {    	
    	
    	isFoodView = 1;
    	isHomeView = isGameView = isLibraryView = 0;
    	
    	HomeBtn.setImageResource(homeBtnImage[isHomeView]);        
        FoodBtn.setImageResource(foodBtnImage[isFoodView]);
        GameBtn.setImageResource(gameBtnImage[isGameView]);
        libraryBtn.setImageResource(libraryBtnImage[isLibraryView]);       
        //unlockBtn.setImageResource(unlockBtnImage[isUnlock]);
        
        if(pet.isCharactorInited() && pet.petData.status != ConstantValue.STATUS_DEAD) {
        	updateFoodInfo();
        }
        
        foodView.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        gameView.setVisibility(View.GONE);
        libraryView.setVisibility(View.GONE);
        //unlockView.setVisibility(View.GONE);
    }   
    
    
    protected void initGameView() 
    {      	
    	gameView = (ScrollView)findViewById(R.id.GameScroll);
    	ViewGroup.LayoutParams laParams = gameView.getLayoutParams();
    	laParams.width = width;
    	laParams.height = height;
    	gameView.setLayoutParams(laParams);    	
    	
    	hP2MoneyButton  = (Button)findViewById(R.id.HP2MoneyBtn);    	
    	principalButton = (Button)findViewById(R.id.PrincipalBtn);
    	
    	hP2MoneyButton.setWidth(width/2);
    	principalButton.setWidth(width/2);
    	
    	gameDescription = (TextView)findViewById(R.id.GameDescription);
    	gameResult		= (TextView)findViewById(R.id.GameResult);
    	
    	opponentImage   = (ImageView)findViewById(R.id.OpponentImage);
    	opponentResult  = (ImageView)findViewById(R.id.OpponentResult);    	
    	myImage			= (ImageView)findViewById(R.id.MyImage);
    	myResult 		= (ImageView)findViewById(R.id.MyResult);   
    	
    	laParams = opponentImage.getLayoutParams();
    	laParams.width = laParams.height = width/3 + opponentImage.getPaddingTop()*2;
    	opponentImage.setLayoutParams(laParams);
    	
    	laParams = opponentResult.getLayoutParams();
    	laParams.width = width/3;
    	laParams.height = width/3 + opponentResult.getPaddingBottom() + opponentResult.getPaddingTop();
    	opponentResult.setLayoutParams(laParams);   	
    	
    	laParams = myImage.getLayoutParams();
    	laParams.width = laParams.height = width/3 + myImage.getPaddingTop()*2;
    	myImage.setLayoutParams(laParams);
    	
    	laParams = myResult.getLayoutParams();
    	laParams.width = width/3;
    	laParams.height = width/3 + myResult.getPaddingBottom() + myResult.getPaddingTop();
    	myResult.setLayoutParams(laParams);
    	
    	stoneBtn = (ImageView)findViewById(R.id.StoneBtn);
    	scissorsBtn = (ImageView)findViewById(R.id.ScissorBtn);
    	clothBtn = (ImageView)findViewById(R.id.ClothBtn);
    	
    	int btnWidth = width - width*2/3 - myImage.getPaddingTop()*2;
    	int btnHeight = (width/3 + myImage.getPaddingTop()*2)/3;
    	
    	laParams = stoneBtn.getLayoutParams();
    	laParams.width = btnWidth;
    	laParams.height = btnHeight;
    	stoneBtn.setLayoutParams(laParams);
    	
    	laParams = scissorsBtn.getLayoutParams();
    	laParams.width = btnWidth;
    	laParams.height = btnHeight;
    	scissorsBtn.setLayoutParams(laParams);
    	
    	laParams = clothBtn.getLayoutParams();
    	laParams.width = btnWidth;
    	laParams.height = btnHeight;
    	clothBtn.setLayoutParams(laParams);
    	
    	petGame.resetGame(); 	
    }   
    
    protected void updateGameView() {    	
    	if(petGame.principal > pet.petData.money + pet.petData.frozenMoney) {
			petGame.principal = pet.petData.money + pet.petData.frozenMoney;
		}
    	
    	String moneyStatus = getResources().getString(R.string.money_status);
    	moneyStatus = String.format(moneyStatus, pet.petData.money+pet.petData.frozenMoney, pet.petData.money);
    	gameDescription.setText(moneyStatus);
    	
    	String oddsStatus = getResources().getString(R.string.odds_status);
    	oddsStatus = String.format(oddsStatus, petGame.principal);
    	gameDescription.append(oddsStatus);
    	
    	gameResult.setTextColor(Color.BLACK);
    	gameResult.setText(R.string.game_start);
    	
    	opponentImage.setImageBitmap(petGame.getOpponentFace());
    	myImage.setImageBitmap(petGame.getMyFace(pet));
    	
    	opponentResult.setImageBitmap(petGame.getOpponentHand());
    	myResult.setImageBitmap(petGame.getMyHand());    
    }    
    
    
    protected void finishGame(int result) {       	
		int money  = petGame.getGameMoney();
		int[] stringID = {R.string.game_win, R.string.game_draw, R.string.game_lose};
		int[] textColor = {Color.RED, Color.DKGRAY, Color.GREEN};
		
		if(result == PetGame.WIN) {
		  	pet.petData.money += money;
		  	if(pet.petData.money >= 9999) pet.petData.money = 9999;
		}
		else if(result == PetGame.LOSE) {
		   	if(money > pet.petData.frozenMoney) {
		   		money -= pet.petData.frozenMoney;
		   		pet.petData.frozenMoney = 0;		    		
		   		pet.petData.money -= money;
		   		if(pet.petData.money < 0) pet.petData.money = 0;
		   	}
		   	else {
		   		pet.petData.frozenMoney -= money;
		   	}		    	
		}
		else {
		}
		
		updateGameView();
		
		gameResult.setTextColor(textColor[result]);
		gameResult.setText(stringID[result]);
		petGame.resetGame();
    }
    
    public void onClickStone(View target) {
    	if(!pet.isCharactorInited() || pet.petData.status == ConstantValue.STATUS_DEAD)
    		return;
    	
    	if(pet.petData.money + pet.petData.frozenMoney <= 0) {
    		Alert.showAlert("JOJO", getResources().getString(R.string.game_no_money), this);
    		return;
    	}
    	
    	finishGame(petGame.onMyHandStone());
    }
    
    public void onClickCloth(View target) {
    	if(!pet.isCharactorInited() || pet.petData.status == ConstantValue.STATUS_DEAD)
    		return;
    	
    	if(pet.petData.money + pet.petData.frozenMoney <= 0) {
    		Alert.showAlert("JOJO", getResources().getString(R.string.game_no_money), this);
    		return;
    	}
    	
    	finishGame(petGame.onMyHandCloth());
    }
    
    public void onClickScissors(View target) {    	
    	if(!pet.isCharactorInited() || pet.petData.status == ConstantValue.STATUS_DEAD)
    		return;
    		
    	if(pet.petData.money + pet.petData.frozenMoney <= 0) {
    		Alert.showAlert("JOJO", getResources().getString(R.string.game_no_money), this);
    		return;
    	}
    	    	
    	finishGame(petGame.onMyHandScissors());
    }
    public void clickHP2Money(View target) {
    	LayoutInflater factory = LayoutInflater.from(this);
        View textEntryView = factory.inflate(R.layout.game_dialog, null);
        
        final EditText HP2Money = (EditText)textEntryView.findViewById(R.id.editText1);
        TextView HP2MoneyDescripton = (TextView)textEntryView.findViewById(R.id.TextView1);       
        String description = getResources().getString(R.string.hp2money_description);
        HP2MoneyDescripton.setText(description);
        HP2MoneyDescripton.setTextColor(Color.WHITE);

        // create a dialog
        new AlertDialog.Builder(SagaActivity.this)
        	.setTitle("JOJO")        	
            .setView(textEntryView)
            .setNegativeButton("Cancel",
            	new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}
            )
            .setPositiveButton("OK",
            	new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int hp2money = 0;
            			int textLen = HP2Money.getText().toString().length();
            			if(textLen >= 8) {
            				hp2money = (int)pet.petData.HP;
            			}
            			else if(textLen == 0) {
            				hp2money = 1;
            			}
            			else {
            				hp2money = Integer.parseInt(HP2Money.getText().toString());
            			}            		
            			
                        if(hp2money >= pet.petData.HP) {
                        	hp2money = (int)pet.petData.HP;
                        }
                        
                        pet.petData.HP -= hp2money;
                        pet.petData.frozenMoney += 5*hp2money;
                        
                        updateGameView();                   

					}
				}
            ).show();    	
    }
    
    public void clickPrincipal(View target) {
    	LayoutInflater factory = LayoutInflater.from(this);
        View textEntryView = factory.inflate(R.layout.game_dialog, null);
        
        final EditText Principal = (EditText)textEntryView.findViewById(R.id.editText1);
        Principal.setText(Integer.toString(petGame.principal));
        TextView principalDescripton = (TextView)textEntryView.findViewById(R.id.TextView1);       
        String description = getResources().getString(R.string.principal_description);
        principalDescripton.setText(description);
        principalDescripton.setTextColor(Color.WHITE);

        // create a dialog
        new AlertDialog.Builder(SagaActivity.this)
        	.setTitle("JOJO")        	
            .setView(textEntryView)
            .setNegativeButton("Cancel",
            	new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}
            )
            .setPositiveButton("OK",
            	new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int textLen = Principal.getText().toString().length();
            			if(textLen >= 8) {
            				petGame.principal = pet.petData.money + pet.petData.frozenMoney;
            			}
            			else if (textLen == 0) {
            				petGame.principal = 1;
            			}
            			else {            				
            				petGame.principal = Integer.parseInt(Principal.getText().toString());
            				if(petGame.principal > pet.petData.money + pet.petData.frozenMoney) {
            					petGame.principal = pet.petData.money+pet.petData.frozenMoney;
            				}
            			}           			
            			
            			updateGameView();
						
					}
				}
            ).show();    	
    }
    
    public void clickGame(View target)
    {
    	if(pet.isCharactorInited() && pet.petData.status != ConstantValue.STATUS_DEAD &&
    	   !pet.petData.isLevelMax()) {
    		
    		isGameView = 1;
        	isFoodView = isHomeView = isLibraryView = 0;
	    	petGame.resetGame();
	    	updateGameView();
	    	

	    	HomeBtn.setImageResource(homeBtnImage[isHomeView]);        
	        FoodBtn.setImageResource(foodBtnImage[isFoodView]);
	        GameBtn.setImageResource(gameBtnImage[isGameView]);
	        libraryBtn.setImageResource(libraryBtnImage[isLibraryView]);
	        //unlockBtn.setImageResource(unlockBtnImage[isUnlock]);
	        
	        gameView.setVisibility(View.VISIBLE);
	        mainLayout.setVisibility(View.GONE);
	        libraryView.setVisibility(View.GONE);
	        foodView.setVisibility(View.GONE);
	        //unlockView.setVisibility(View.GONE);       
    	}       
    }
    
    protected void onResurrection() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String alertString;
		if(pet.petData.money > 0) 
			alertString = getResources().getString(R.string.do_resurrection);
		else
			alertString = getResources().getString(R.string.cannot_resurrection);
		
		alertString = String.format(alertString, petLibrary.charactorNames[pet.petData.curCharactor]);
		builder.setMessage(alertString);
		builder.setTitle("JOJO");		    			
		
		if(pet.petData.money > 0)
		{
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub	
					action.onResurrection(pet.petData);					
					updateBackground(true);
				}			
			});
		}
		
		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
			}				
		});
		
		AlertDialog ad = builder.create();
		ad.show();  	
    }
    
  //实现onTouchEvent方法 
    public boolean onTouchEvent(MotionEvent event) {   
    	if(isHomeView == 1 && pet.isCharactorInited()) {
	    	int x = (int)event.getX();
	    	int y = (int)event.getY();
	    	
	    	if(pet.isPetClicked(x, y)) {    	
		    	switch (event.getAction()) {
		    	case MotionEvent.ACTION_DOWN:
		    		//record the initial position
		    		//ask for resurrection
		    		if(pet.petData.status == ConstantValue.STATUS_DEAD) {
		    			onResurrection();	
		    			updateBackground(true);
		    		}
		    		else {
			    		pet.setOrigPos(x, y);	
			    		pet.setMouseDownY();
			    		action.onTouchDown(pet, x, y);
			    		vibrator.vibrate(25);
		    		}
		    		Log.i("jojo", "Mouse down" + Integer.toString(x) + ","+ Integer.toString(y));
		    		break;
		    		
		    	case MotionEvent.ACTION_MOVE:
		    		pet.movePet(x, y);
		    		pet.setOrigPos(x, y);
		    		Log.i("jojo", "Mouse move" + Integer.toString(x) + ","+ Integer.toString(y));
		    		break;
		    	case MotionEvent.ACTION_UP:
		    		pet.setMouseUpY();
		    		action.onTouchUp(pet);
		    		Log.i("jojo", "Mouse up" + Integer.toString(x) + ","+ Integer.toString(y));
		    		break;
		    	}
	    	}
    	}    	
    	
    	
	    return super.onTouchEvent(event); 
    } 

   
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pet_main, menu);
        return true;
    }   
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId() == R.id.menu_prefs) {
    		Intent intent = new Intent()
			.setClass(this, com.jojopet.JoJoPreferenceActivity.class);
    		this.startActivityForResult(intent, 0);
    	}
    	else if(item.getItemId() == R.id.menu_about) {
    		LayoutInflater factory = LayoutInflater.from(this);
            View aboutView = factory.inflate(R.layout.about_dialog, null);   
            TextView aboutText = (TextView)aboutView.findViewById(R.id.AboutText);
            String aboutString = getResources().getString(R.string.about_text);
            aboutText.setText(String.format(aboutString, ConstantValue.getVersion(this)));

            // create a dialog
            new AlertDialog.Builder(PetMainActivity.this)
            	.setTitle("About")        	
                .setView(aboutView)
                
                .setPositiveButton("OK",
                	new DialogInterface.OnClickListener() {
                		@Override
                        public void onClick(DialogInterface dialog, int which) {  
                        }

                	}
                ).show();  
    	}
    	return true;
    }
    */
    
    @Override
    public void onResume() {
    	super.onResume();  
    	
    	isRunning = true;
    	long lastCloseTime = pet.petData.loadData();
    	long elapsedTime = (System.currentTimeMillis() - lastCloseTime) / 1000; 			 	 
		action.onUpdatePeriod(pet, (int)elapsedTime);  
		//petUnlock.updateEggPeriod(elapsedTime);
		updateBackground(true);
    }
    
    @Override
    public void onPause() {
    	super.onPause();    	
    	isRunning = false;
    	
    	pet.petData.saveData();
    }   
    
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data){
    	if(reqCode == 0) {
    		String quietKey = getResources().getString(R.string.quiet);
    		
    		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);      		
    		pet.petData.quiet = settings.getBoolean(quietKey, false);
    	}
    	
    	super.onActivityResult(reqCode, resCode, data);  
    	
    }

}
