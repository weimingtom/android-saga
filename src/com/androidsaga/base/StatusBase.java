package com.androidsaga.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;

public class StatusBase {
	protected Bitmap statusImg;
	protected Canvas canvas;
	protected Paint  paint;
	
	public int width;
	public int height;
	
	public StatusBase(int w, int h) {
		// TODO Auto-generated constructor stub
		width 	= w;
		height 	= h;
		
		statusImg = Bitmap.createBitmap(width, height, Config.ARGB_4444);   
		canvas = new Canvas(statusImg); 
		paint  = new Paint();
	}
	
	public Bitmap GenerateStatus(Context ctx, Data petData, String name) {
		statusImg.eraseColor(Color.TRANSPARENT);		
		int textSize  = ConstantValue.scalePix(ctx, 24);
		int startPosX = ConstantValue.scalePix(ctx, width/20);		
		int startPosY = height/6 + ConstantValue.scalePix(ctx, 12);			
		
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);		
		paint.setTextSkewX(-0.25f);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		canvas.drawText("Lv: " + Integer.toString(petData.level), 
			width - ConstantValue.scalePix(ctx, 64), startPosY, paint);
		
		startPosY += height/3;
		paint.setTextSkewX(0.f);		
		canvas.drawText("HP",startPosX, startPosY, paint);
		
		startPosY += height/3;
		canvas.drawText(name, startPosX, startPosY, paint);
		
		int barWidth  = ConstantValue.scalePix(ctx, 24);	
		startPosX += ConstantValue.scalePix(ctx, 80);
		int barLength = width*2/3;
		if(petData.curCharactor != ConstantValue.NONE)
		{
			// draw bar			
			startPosY = height/2 - ConstantValue.scalePix(ctx, 8);			
			float barLengthFilled = (float)barLength*petData.HP/ConstantValue.HP_LEVEL[petData.level];
			if(petData.status != ConstantValue.STATUS_DEAD) {
				paint.setColor(Color.argb(255, 0, 128, 255));                                               
			}
			else {
				paint.setColor(Color.GRAY);
			}
			canvas.drawRect(startPosX, startPosY, startPosX+barLengthFilled, startPosY+barWidth, paint);
			
			startPosY += ConstantValue.scalePix(ctx, 40);
			barLengthFilled = (float)barLength*petData.satisfy/ConstantValue.EXP_LEVEL[petData.level];
			canvas.drawRect(startPosX, startPosY, startPosX+barLengthFilled, startPosY+barWidth, paint);
		}
		
		startPosY = height/2 - ConstantValue.scalePix(ctx, 8);
		paint.setColor(Color.BLACK);                    
	    canvas.drawColor(Color.TRANSPARENT);                  
	    paint.setStrokeWidth((float) 3.0);              
	    paint.setStyle(Style.STROKE);    
		canvas.drawRect(startPosX, startPosY, startPosX+barLength, startPosY+barWidth, paint);
		
		startPosY += ConstantValue.scalePix(ctx, 40);
		canvas.drawRect(startPosX, startPosY, startPosX+barLength, startPosY+barWidth, paint);
		
		// draw number
		if(petData.curCharactor != ConstantValue.NONE) {
			paint.setStyle(Style.FILL);
			paint.setTextSkewX(-0.25f);
			startPosY = height/2 + ConstantValue.scalePix(ctx, 6);	
			startPosX += barLength*4/5;
			
			if(petData.status != ConstantValue.STATUS_DEAD) {
				paint.setColor(Color.RED);                                               
			}
			else {
				paint.setColor(Color.DKGRAY);
			}
			int HP = Math.min((int)(petData.HP+0.9), ConstantValue.HP_LEVEL[petData.level]);			
			canvas.drawText(Integer.toString(HP), startPosX, startPosY, paint);
			
			startPosY += ConstantValue.scalePix(ctx, 40);
			int satisfy = Math.min((int)(petData.satisfy+0.9), ConstantValue.EXP_LEVEL[petData.level]);
			canvas.drawText(Integer.toString(satisfy), startPosX, startPosY, paint);			
		}
		return statusImg;
	}
}
