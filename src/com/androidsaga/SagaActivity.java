package com.androidsaga;

import android.app.Activity;
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
	private UpdateThread updateThr;
	
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
        width=dm.widthPixels;         
               
        height = dm.heightPixels;
        SayView.setHeight(height / 8);
        
        height -= (25 + height/8 +
        	BitmapFactory.decodeResource(this.getResources(), R.drawable.icon).getHeight());
       
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
        HomeBtn.setImageResource(R.drawable.icon);   
        BathBtn.setImageResource(R.drawable.bathbtn);        
        FeedBtn.setImageResource(R.drawable.feedbtn);    
        ExerciseBtn.setImageResource(R.drawable.exercisebtn);
        QABtn.setImageResource(R.drawable.qabtn);
        SleepBtn.setImageResource(R.drawable.sleepbtn);
        HospitalBtn.setImageResource(R.drawable.hospitalbtn);
        PresentBtn.setImageResource(R.drawable.presentbtn);             
        
        sagaData = new Data(this);
        sagaData.setRunningFlag(true);
        updateThr = new UpdateThread(this, sagaData);
        updateThr.start();
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
    	sagaData.saveData();
    	sagaData.setRunningFlag(false);    	
    }
    
    @Override
    public void onResume(){
    	super.onResume();    	
    	sagaData.loadData();
    	sagaData.setRunningFlag(true);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);     
    }
     
    public void clickHome(View target)
    {
    	Alert.showAlert("Saga", "Home", this);
    }    
    
    public void clickBath(View target)
    {
    	Alert.showAlert("Saga", "Bath", this);
    }   
    
    public void clickHospital(View target)
    {
    	Alert.showAlert("Saga", "Hospital", this);
    }   
    
    public void clickFeed(View target)
    {
    	Alert.showAlert("Saga", "Feed", this);
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