package com.androidsaga.base;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.Log;

public class PetBase {
	public Data petData;
	protected Context ctx;
	
	protected Bitmap bmp;	
	protected Canvas canvas;
	
	public boolean isUpdate = true;
	public boolean isBathing = false;
	
	protected Paint  paint;	
	protected Bitmap[] petImages = new Bitmap[8];
	
	public int width;
	public int height;
	
	protected int headerHeight;
	protected int x;
	protected int y;
	
	protected int mouseDownY;
	protected int mouseUpY;
	
	protected int targetX = -1;
	protected int targetY = -1;
	
	protected int origX;
	protected int origY;
	
	protected int petWidth;
	protected int petHeight;
	protected boolean isRunning = false;
	protected boolean isUpdateStatus = true;
	protected boolean isShowString = false; // «∑Òœ‘ æÃ®¥ 
	protected String  dialogString;
	protected String  maxLevelString;
	
	protected Timer statusTimer;
	protected Timer stringTimer;	
	
	protected int dropStep = 1;
	
	public int status;
	
	public PetBase(Context _ctx, int _width, int _height, int _headerHeight) {
		ctx 	= _ctx;			
		petData = new Data(ctx);           
        
        width  = _width;
        height = _height;
        headerHeight = _headerHeight;                  
        
        bmp    = Bitmap.createBitmap(width, height, Config.ARGB_8888);  
        canvas = new Canvas(bmp);        
        paint  = new Paint();        
        
        PetImageDepot.initPetImageDepot();
        statusTimer = new Timer();
        stringTimer = new Timer();        
	}
	
	public void resetTargetX() {
		targetX = -1;
	}
	
	public void resetTargetY() {
		targetY = -1;
	}
	
	public void setTargetX(int _targetX) {
		if(_targetX < 0) _targetX = width/2;
		if(_targetX > width-petWidth) _targetX = width-petWidth-40;
		
		targetX = _targetX;
	}
	
	public void setTargetY(int _targetY) {
		if(_targetY < 0) _targetY = 0;
		if(_targetY > height - petHeight) 	 _targetY = height-petHeight;
		
		targetY = _targetY;
	}
	
	public void setStatus(int newStatus) {
		isUpdateStatus = false;
		status = newStatus;		
	}
	
	public int getTargetX() {
		return targetX;
	}
	
	public int getTargetY() {
		return targetY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public void setDropStep(int newStep) {
		dropStep = newStep;
	}
	
	public int getDropStep() {
		return dropStep;
	}
	
	public int getPetWidth() {
		return petWidth;
	}
	
	public int getPetHeight() {
		return petHeight;
	}
	
	public void setMaxLevelString(String levelString) {
		maxLevelString = levelString;
	}
	
	// reset status after millisec
	public void resetStatus(int millisec) {	
		if(millisec > 0) {	        
	        statusTimer.schedule(new TimerTask() {				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					isUpdateStatus = true;
				}			
	        }, millisec);
		}
		else {
			isUpdateStatus = true;
		}
	}
	
	public boolean isStatusUpdate() {
		return isUpdateStatus;
	}
	
	public void showString(String dialog, int millisec) {
		isShowString = true;		
		dialogString = dialog;
		
		if(millisec > 0) {	        
	        stringTimer.schedule(new TimerTask() {				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					isShowString = false;
				}			
	        }, millisec);
		}
		else {
			isShowString = false;
		}
	}
	
	public void updatePetImages(boolean bIsFillHeight) {	
		if(isCharactorInited()) {	
			
			petWidth  = width*2/3;
			petHeight = height*2/3;
			if(!petData.isLevelMax()) {
				petImages = PetImageDepot.initImageLevel(ctx, petData.level, petWidth, petHeight);				
			}
			else {
				if(bIsFillHeight) {
					petImages[0] = PetImageDepot.getSubSpeciesClipImage(ctx, petData.subSpecises, width, height);
				}
				else {
					petImages[0] = PetImageDepot.getSubSpeciesImage(ctx, petData.subSpecises, petWidth, petHeight);
				}
			}
			
			petWidth = petImages[0].getWidth();
			petHeight = petImages[0].getHeight();			
			
			x = (width-petWidth)/2;
			y = (height-petHeight)/2;   
			
		}		
	}	
	
	public Bitmap getPetStatusBitmap(int status) {
		if(petData.isLevelMax()) {
			return petImages[0];
		}
		else {
			return petImages[status];
		}
	}
	
	public boolean isCharactorInited() {
		//return petData.curCharactor != ConstantValue.NONE;
		return true;
	}
	
	public Bitmap getPetImage() {
		
		bmp.eraseColor(Color.TRANSPARENT);
		
		if(isCharactorInited()) {			
			if(!petData.isLevelMax()) {
				canvas.drawBitmap(petImages[status], x, y, paint);				
				if(isShowString && dialogString != "") {
					//draw dialog
					paint.setStyle(Style.FILL);
					paint.setColor(Color.WHITE);
					paint.setAntiAlias(true);
					
					// break string					
					int count = paint.breakText(dialogString, true, ConstantValue.scalePix(ctx, 120), null);	
					int stringLen = dialogString.length();					
					int textRows = (stringLen + count - 1) / count;
					
					Point coord = new Point(x+petWidth-ConstantValue.scalePix(ctx, 100),
											y-ConstantValue.scalePix(ctx, 20));
					RectF oval = new RectF(	coord.x, coord.y, 
											coord.x+ConstantValue.scalePix(ctx, 200), 
											coord.y+ConstantValue.scalePix(ctx, 100 + (textRows-1)*24) );
					canvas.drawOval(oval, paint);
					
					paint.setStyle(Style.STROKE);
					paint.setStrokeWidth(5);
					paint.setColor(Color.BLACK);
					canvas.drawOval(oval, paint);
					
					// draw string	
					paint.setStyle(Style.FILL);
					paint.setStrokeWidth(2);
					paint.setTextSize(ConstantValue.scalePix(ctx, 24));
					
					int i = 0;
					while(stringLen > count) {
						canvas.drawText(dialogString.substring(i*count, (i+1)*count), 
										coord.x+ConstantValue.scalePix(ctx, 40),
										coord.y+ConstantValue.scalePix(ctx, 64),
										paint);
						i++;
						stringLen -= count;
						coord.y += ConstantValue.scalePix(ctx, 24);
					}
					canvas.drawText(dialogString.substring(i*count, dialogString.length()), 
									coord.x+ConstantValue.scalePix(ctx, 40),
									coord.y+ConstantValue.scalePix(ctx, 64),
									paint);	
				}
			}
			else 
			{
				canvas.drawBitmap(petImages[0], x, y, paint);					
				
				Point coord = new Point(width/8, 8);		
				
				// draw string
				paint.setStyle(Style.FILL);
				paint.setStrokeWidth(2);
				paint.setColor(Color.BLACK);
				paint.setTextSize(ConstantValue.scalePix(ctx, 24));
				paint.setAntiAlias(true);							
				
				int count = paint.breakText(maxLevelString, true, width*3/5, null);	
				int stringLen = maxLevelString.length();
				int i = 0;
				while(stringLen > count) {
					canvas.drawText(maxLevelString.substring(i*count, (i+1)*count), 
									coord.x+ConstantValue.scalePix(ctx, 40),
									coord.y+ConstantValue.scalePix(ctx, 64),
									paint);
					i++;
					stringLen -= count;
					coord.y += ConstantValue.scalePix(ctx, 24);
				}
				canvas.drawText(maxLevelString.substring(i*count,maxLevelString.length()), 
								coord.x+ConstantValue.scalePix(ctx, 40),
								coord.y+ConstantValue.scalePix(ctx, 64),
								paint);
							
			}
		}
		
		return bmp;			
	}	
	
	public void setOrigPos(int _x, int _y) {
		origX = _x;
		origY = _y;
	}
	
	public void movePet(int newX, int newY) {
		x += newX - origX;
		y += newY - origY;		
		
		if(x < 0) 			     x = 0;
		if(x > width-petWidth)   x = width - petWidth;
		
		if(y < 0)			     y = 0;
		if(y > height-petHeight) y = height - petHeight;
	}
	
	public boolean isPetClicked(int _x, int _y) {
		return (_x > x && _y > y+headerHeight && 
				_x < x+petWidth && _y < y+petHeight+headerHeight);
	}
	
	public void setMouseDownY() {
		mouseDownY = y;
	}
	
	public void setMouseUpY() {
		mouseUpY = y;
	}
	
	public int getMouseDownY() {
		return mouseDownY;
	}
	
	public int getMouseUpY() {
		return mouseUpY;
	}
}
