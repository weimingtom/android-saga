package com.androidsaga;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.*;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;

public class SagaActivity extends Activity {
    /** Called when the activity is first created. **/
	private ImageView MainView;
	private ImageView HomeBtn;
	private ImageView BathBtn;
	private ImageView FeedBtn;
	private ImageView ExerciseBtn;
	private ImageView QABtn;
	private ImageView SleepBtn;
	private ImageView HospitalBtn;
	private ImageView PresentBtn;
	
	private TextView SayView;
	
	private Integer width;
	private Integer height;
	private Bitmap MainImage;	
	private Data sagaData;
	private Thread updateThr;
	boolean running = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature( Window.FEATURE_NO_TITLE );        
        setContentView(R.layout.main);
        
        MainView = (ImageView)findViewById(R.id.MainView);
        SayView = (TextView)findViewById(R.id.SayView);
        SayView.setText("Hello");
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;               
        height = dm.heightPixels;
        int btnSize = width / 8;
        SayView.setHeight(height / 6);
        SayView.setWidth(7*width / 8);
        
        height -= (40 + height/6 + btnSize);
       
        //set main view size according to resolution        
		MainImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        MainView.setImageBitmap(MainImage);        
        
        HomeBtn = (ImageView)findViewById(R.id.HomeButton);
        BathBtn = (ImageView)findViewById(R.id.BathButton);
        FeedBtn = (ImageView)findViewById(R.id.FeedButton);        
        ExerciseBtn = (ImageView)findViewById(R.id.ExerciseButton);
        QABtn = (ImageView)findViewById(R.id.QAButton);
        SleepBtn = (ImageView)findViewById(R.id.SleepButton);
        HospitalBtn = (ImageView)findViewById(R.id.HospitalButton);
        PresentBtn = (ImageView)findViewById(R.id.PresentButton);        
        
        //add button image         
        HomeBtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
        						this, R.drawable.homebtn, btnSize, btnSize));   
        
        BathBtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
        						this, R.drawable.bathbtn, btnSize, btnSize)); 
        
        FeedBtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
								this, R.drawable.feedbtn, btnSize, btnSize));   
        
        ExerciseBtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
								this, R.drawable.exercisebtn, btnSize, btnSize));  
        
        QABtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
								this, R.drawable.qabtn, btnSize, btnSize));  
        
        SleepBtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
								this, R.drawable.sleepbtn, btnSize, btnSize));  
        
        HospitalBtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
								this, R.drawable.hospitalbtn, btnSize, btnSize));  
        
        PresentBtn.setImageBitmap(ConstantUtil.scaleButtonBitmap(
								this, R.drawable.presentbtn, btnSize, btnSize));             
        
        sagaData = new Data(this);
        sagaData.setRunningFlag(true);
        running = true;
        updateThr = new Thread(){
        	@Override
        	public void run(){
        		while(true){
        			if(running){
        				sagaData.updatePerSecond();        			
        			    Log.i("Saga", "update");
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
        updateThr.start();        
        
        ConstantUtil.setNotify(this, sagaData);
        Alert.showAlert("Saga", sagaData.totalTime.toString(), this);         
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = this.getMenuInflater();
    	inflater.inflate(R.menu.mainmenu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId() == R.id.menu_prefs){
    		Intent intent = new Intent()
    			.setClass(this, com.androidsaga.SagaPreferenceActivity.class);
    		this.startActivityForResult(intent, 0);
    	}
    	else if(item.getItemId() == R.id.menu_clearall){
    		sagaData.clearData(true);
    	}
    	return true;
    }
    
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data){
    	super.onActivityResult(reqCode, resCode, data);    	
    }
    
    @Override
    public void onPause(){
    	super.onPause();     	
    	running = false;
    	sagaData.saveData(); 
    	sagaData.setRunningFlag(false); 
    }
    
    @Override
    public void onResume(){
    	super.onResume();   
    	if(ConstantUtil.CUR_NOTICE != 0){
    		final NotificationManager manager = 
    			(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    		manager.cancel(ConstantUtil.NOTICE_ID[ConstantUtil.CUR_NOTICE-1]);
			ConstantUtil.CUR_NOTICE = 0;
		}
    	
    	sagaData.loadData(); 
    	running = true;
    	sagaData.setRunningFlag(true);
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	updateThr = null;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);     
    }
     
    public void clickHome(View target)
    {
    	Alert.showAlert("Saga", "Home", this);
    	sagaData.onHome();
    }    
    
    public void clickBath(View target)
    {
    	Alert.showAlert("Saga", "Bath", this);
    	sagaData.onBath();
    }   
    
    public void clickHospital(View target)
    {
    	Alert.showAlert("Saga", "Hospital", this);
    	sagaData.onHospital();
    }   
    
    public void clickFeed(View target)
    {
    	Alert.showAlert("Saga", "Feed", this);
    	sagaData.onFeed();
    }   
    
    public void clickPresent(View target)
    {
    	Alert.showAlert("Saga", "Present", this);
    }   
    
    public void clickExercise(View target)
    {
    	Alert.showAlert("Saga", "Exercise", this);
    }  
    
    public void clickQA(View target)
    {
    	Alert.showAlert("Saga", "Answer Question", this);
    }   
    
    public void clickSleep(View target)
    {
    	Alert.showAlert("Saga", "Sleep", this);
    }
}