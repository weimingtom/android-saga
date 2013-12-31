package com.androidsaga.base;

import com.androidsaga.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;

public class PetLibrary {	
	protected Bitmap lockedThumbnail;
	protected Bitmap lockedImage;	
	
	protected Context ctx;
	public int thumbnailSize;
	public int lockedX;
	public int lockedY;
	public int width;
	public int height;
	
	
	protected int lastSelection 	= -1;
	protected int lastLevel    		= 0;
	protected int lastSubspecies 	= 0;
	protected int border			= 4;
	
	public String[][] levelDescription = new String[32][];
	public String[][] subspeciesDescription = new String[32][];
	public String[] charactorNames;
	public String[] satisfyNames;
	
	public String charactor_unavailable;
	public String subspecies_unavailable;
	public String subspecies_type;	
	
	public PetLibrary(Context _ctx, int size) {
		// TODO Auto-generated constructor stub
		ctx 	= _ctx;
		thumbnailSize 	= size;
			
		lockedThumbnail = ConstantValue.scaleButtonBitmap(ctx, R.drawable.locked, thumbnailSize, thumbnailSize);	
		
		charactor_unavailable  = ctx.getResources().getString(R.string.library_unavailable);
		subspecies_unavailable = ctx.getResources().getString(R.string.subspecies_unavailabe);
		subspecies_type		   = ctx.getResources().getString(R.string.subspecies_type);
		
		charactorNames = ctx.getResources().getStringArray(R.array.charactor_name);
        satisfyNames   = ctx.getResources().getStringArray(R.array.satisfy_name);        
		
		//levelDescription[ConstantValue.CAESAR]		= ctx.getResources().getStringArray(R.array.caesar_level_description);
		//subspeciesDescription[ConstantValue.CAESAR]	= ctx.getResources().getStringArray(R.array.caesar_subspecies_description);
	}
	
	public Bitmap getThumbnailImage(Data data, int charactor, boolean isChecked) {
		Bitmap petThumbnailListImage = Bitmap.createBitmap(thumbnailSize, thumbnailSize, Config.ARGB_4444);	
		Canvas canvas = new Canvas(petThumbnailListImage);
		Paint paint  = new Paint();		
		paint.setAntiAlias(true);
		
		paint.setStyle(Style.FILL);		
		if(isChecked) {
			//paint.setColor(Color.argb(127, 255, 255, 255));	
			paint.setColor(Color.argb(255, 234, 246, 253));
		}
		else {
			paint.setColor(Color.TRANSPARENT);
		}
		
		canvas.drawRect(0, 0, thumbnailSize, thumbnailSize, paint);
		
		paint.setColor(Color.WHITE);
		int availability = data.getCharactorAvailable(charactor);
		if(availability == ConstantValue.NOT_AVAILABLE) {
			canvas.drawBitmap(lockedThumbnail, 0, 0, paint);
		}				
		else {
			Bitmap charactorThumbnailBitmap = PetImageDepot.getCharactorDefaultImage(
					ctx, charactor, 0, thumbnailSize, thumbnailSize);
			
			int x = 0;			
			if(charactorThumbnailBitmap.getHeight() > thumbnailSize) {
				charactorThumbnailBitmap = Bitmap.createScaledBitmap(
						charactorThumbnailBitmap, thumbnailSize*thumbnailSize/charactorThumbnailBitmap.getHeight(), thumbnailSize, true);
				x = (thumbnailSize - charactorThumbnailBitmap.getWidth())/2;
			}
			int y = (thumbnailSize - charactorThumbnailBitmap.getHeight())/2;
					
			if(availability == ConstantValue.IS_FEED) {
				canvas.drawBitmap(charactorThumbnailBitmap, x, y, paint);				
			}
			else {
				paint.setAlpha(128);
				canvas.drawBitmap(charactorThumbnailBitmap, x, y, paint);
				paint.setAlpha(127);
				canvas.drawBitmap(lockedThumbnail, 0, 0, paint);
			}
		}
			
		if(isChecked) {
			paint.setAlpha(255);
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.argb(255, 201, 233, 250));
			paint.setStrokeWidth(4);
			canvas.drawRect(0, 0, thumbnailSize, thumbnailSize, paint);	
			
			lastSelection = charactor;
			if(charactor == data.curCharactor) {
				lastLevel = data.level;
			}
			else {
				lastLevel = 0;
			}
		}
				
		return petThumbnailListImage;
	}
	
	public int getLastSelection() {
		return lastSelection;
	}
	
	public int getlastLevel() {
		return lastLevel;
	}
	
	public int getLastSubspecies() {
		return lastSubspecies;
	}
	
	public String getCharactorDescription(int charactor, int level, int subspecies, Data data) {
		if(data.getCharactorAvailable(charactor) == ConstantValue.NOT_AVAILABLE) {
			return charactor_unavailable;
		}
		if(level == ConstantValue.MAX_LEVEL) {			
			if(data.getSubspeciesFeed(charactor, subspecies))
				return subspeciesDescription[charactor][subspecies];
			else {
				String subspeciesStr = String.format(subspecies_type, subspecies);
				subspeciesStr += subspecies_unavailable;
				return subspeciesStr;
			}			
		}	
		
		return levelDescription[charactor][level];
	}
	
	public void initDetailView(int _width, int _height) {
		width  = _width;
		height = _height;
		lockedImage = ConstantValue.scaleButtonBitmap(ctx, R.drawable.locked, width, height);		
		
		lockedX = (width - lockedImage.getWidth()) / 2;
		lockedY = (height - lockedImage.getHeight()) / 2;	
	}
	
	public Bitmap getCharactorSelection(int charactor, Data data, int level, int subspecies) {
		Bitmap charactorDetail = Bitmap.createBitmap(width, height, Config.ARGB_4444);	
		Canvas canvas = new Canvas(charactorDetail);
		Paint paint  = new Paint();		
		paint.setAntiAlias(true);
		
		int availability = data.getCharactorAvailable(charactor);
		if(charactor < 0 || availability == ConstantValue.NOT_AVAILABLE) {		
			canvas.drawBitmap(lockedImage, lockedX, lockedY, paint);				
		}	
		
		else {
			Bitmap charactorImg;
			if(level < ConstantValue.MAX_LEVEL) {
				charactorImg = PetImageDepot.getCharactorDefaultImage(ctx, charactor, level, width-2*border, height-2*border);
			}
			else {
				charactorImg = PetImageDepot.getSubSpeciesImage(ctx, charactor, subspecies, width-2*border, height-2*border);
			}		
			
			int charactorX = (width  - charactorImg.getWidth() ) / 2;
			int charactorY = (height - charactorImg.getHeight()) / 2;
			
			if(availability == ConstantValue.IS_FEED) {
				canvas.drawBitmap(charactorImg, charactorX, charactorY, paint);
			}
			else {
				paint.setAlpha(128);
				canvas.drawBitmap(charactorImg, charactorX, charactorY, paint);
				paint.setAlpha(127);
				canvas.drawBitmap(lockedImage, lockedX, lockedY, paint);
			}
		}	
		
		paint.setAlpha(255);
		paint.setStrokeWidth(8);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, width, height, paint);
		
		return charactorDetail;
	}
	
	public Bitmap getCharactorLevel(int charactor, Data data, int level) {
		lastLevel = Math.max(0, level);
		
		Bitmap charactorDetail = Bitmap.createBitmap(width, height, Config.ARGB_4444);	
		Canvas canvas = new Canvas(charactorDetail);
		Paint paint  = new Paint();		
		paint.setAntiAlias(true);
			
		Bitmap charactorImg = PetImageDepot.getCharactorDefaultImage(ctx, charactor, level, width-2*border, height-2*border);		
		int charactorX = (width  - charactorImg.getWidth() ) / 2;
		int charactorY = (height - charactorImg.getHeight()) / 2;			
			
		canvas.drawBitmap(charactorImg, charactorX, charactorY, paint);		
		
		paint.setStrokeWidth(8);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, width, height, paint);
		
		return charactorDetail;
	}
	
	public Bitmap getCharactorSubspecies(int charactor, Data data, int subspecies) {
		lastSubspecies = Math.max(0, subspecies);
		lastLevel = ConstantValue.MAX_LEVEL;
		
		Bitmap charactorDetail = Bitmap.createBitmap(width, height, Config.ARGB_4444);	
		Canvas canvas = new Canvas(charactorDetail);
		Paint paint  = new Paint();		
		paint.setAntiAlias(true);
			
		if(data.getSubspeciesFeed(charactor, subspecies)) {
			Bitmap charactorImg = PetImageDepot.getSubSpeciesImage(ctx, charactor, subspecies, width-2*border, height-2*border);			
			int charactorX = (width  - charactorImg.getWidth() ) / 2;
			int charactorY = (height - charactorImg.getHeight()) / 2;			
				
			canvas.drawBitmap(charactorImg, charactorX, charactorY, paint);	
		}
		else {
			canvas.drawBitmap(lockedImage, lockedX, lockedY, paint);
		}
		
		paint.setStrokeWidth(8);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, width, height, paint);
		
		return charactorDetail;
	}
	
	public String getSelectBtnText(int charactor, Data petData) {
		String strBtn = "";
		if(charactor == petData.curCharactor) {
			strBtn = ctx.getResources().getString(R.string.library_clear);			
		}
		else {
			strBtn = ctx.getResources().getString(R.string.library_choose);
		}
		
		if( charactor < 0 || petData.getMaxLevel(charactor) < ConstantValue.MAX_LEVEL ||
			petData.status == ConstantValue.STATUS_DEAD) {
			return String.format(strBtn, 0);
		}
		
		return String.format(strBtn, Math.min(lastLevel, ConstantValue.MAX_LEVEL-1));		
	}
}
