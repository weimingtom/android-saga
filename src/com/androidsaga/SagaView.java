package com.androidsaga;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SagaView extends ImageView {
	
	public SagaView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		event.recycle();
		return true;
	}
}
