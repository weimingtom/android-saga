package com.androidsaga;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View.*;
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
	
	private Integer width;
	private Integer height;
	private Bitmap MainImage;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        
        setContentView(R.layout.main);
        
        MainView = (ImageView)findViewById(R.id.MainView);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;         
               
        height = dm.heightPixels - 25 - 
        	BitmapFactory.decodeResource(this.getResources(), R.drawable.icon).getHeight();
        
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
}