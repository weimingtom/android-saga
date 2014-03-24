package com.androidsaga.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;

public class CleanGauge {
	protected Bitmap gaugeImg;
	protected Canvas canvas;
	protected Paint  paint;
	
	public int width;
	public int height;
	
	public CleanGauge(int w, int h) {
		// TODO Auto-generated constructor stub
		width 	= w;
		height 	= h;
		
		gaugeImg = Bitmap.createBitmap(width, height, Config.ARGB_4444);   
		canvas = new Canvas(gaugeImg); 
		paint  = new Paint();
	}
	
	public Bitmap DrawGauge(Context ctx, Data petData) {
		int textSize  = ConstantValue.scalePix(ctx, 24);
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);		
		paint.setTextSkewX(0.f);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.DKGRAY);
		
		gaugeImg.eraseColor(Color.TRANSPARENT);		
		int startPosX = width/4-ConstantValue.scalePix(ctx, 60);		
		int startPosY = ConstantValue.scalePix(ctx, 40);			
		
		canvas.drawText("Çå½à¶È",startPosX, startPosY, paint);
		
		startPosX += ConstantValue.scalePix(ctx, 80);
		startPosY -= ConstantValue.scalePix(ctx, 20);

		int barWidth  = ConstantValue.scalePix(ctx, 24);	
		int barLength = width/2;
		// draw bar			
		float barLengthFilled = barLength*petData.clean/ConstantValue.MAX_CLEAN;
		paint.setColor(Color.GREEN);                                               

		canvas.drawRect(startPosX, startPosY, startPosX+barLengthFilled, startPosY+barWidth, paint);

		paint.setColor(Color.BLACK);                    
	    canvas.drawColor(Color.TRANSPARENT);                  
	    paint.setStrokeWidth((float) 3.0);              
	    paint.setStyle(Style.STROKE);    
		canvas.drawRect(startPosX, startPosY, startPosX+barLength, startPosY+barWidth, paint);

		paint.setStyle(Style.FILL);
		paint.setColor(Color.DKGRAY);
		paint.setTextSkewX(-0.25f);
		
		startPosX += width/2;
		startPosY += ConstantValue.scalePix(ctx, 20);			
		int clean = Math.min((int)(petData.clean+0.9), ConstantValue.MAX_CLEAN);			
		canvas.drawText(Integer.toString(clean), startPosX, startPosY, paint);

		return gaugeImg;
	}
}
