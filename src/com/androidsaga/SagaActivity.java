package com.androidsaga;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.widget.*;

public class SagaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Display display = this.getWindowManager().getDefaultDisplay();
        int height = display.getHeight();
        
        ImageView mainImage = (ImageView)findViewById(R.id.ImageView01);
        mainImage.setMinimumHeight(4*height/5);
        
        TextView toShow = (TextView)findViewById(R.id.TextView01);
        toShow.setHeight(height/5);
        toShow.setText(getResources().getString(R.string.testString));
    }
}