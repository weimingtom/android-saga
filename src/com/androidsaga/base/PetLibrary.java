package com.androidsaga.base;

import com.androidsaga.R;

import android.R.integer;
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
	public int lastIdx = 0;
	
	public int curLevel = -1;
	
	protected int border = 4;
	
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
		
		levelDescription[ConstantValue.SAGA]		= ctx.getResources().getStringArray(R.array.saga_level_description);
		subspeciesDescription[ConstantValue.SAGA]	= ctx.getResources().getStringArray(R.array.saga_subspecies_description);
	}
	
	public Bitmap getThumbnailImage(Data data, int idx, boolean isChecked) {
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
		boolean availability = false;
		if( (idx <= data.getMaxLevel(data.curCharactor) && idx < ConstantValue.MAX_LEVEL)) {
			availability = true;			
		}
		else if(data.getMaxLevel(data.curCharactor) == ConstantValue.MAX_LEVEL &&
				idx >= ConstantValue.MAX_LEVEL && idx < ConstantValue.MAX_LEVEL + subspeciesDescription.length) {
			int subspecies = idx - ConstantValue.MAX_LEVEL;
			availability = data.getSubspeciesFeed(data.curCharactor, subspecies);
		}
			
		if(!availability) {
			canvas.drawBitmap(lockedThumbnail, 0, 0, paint);
		}				
		else {
			Bitmap charactorThumbnailBitmap;
			if(idx < ConstantValue.MAX_LEVEL) {
				charactorThumbnailBitmap = PetImageDepot.getCharactorDefaultImage(
					ctx, data.curCharactor, idx, thumbnailSize, thumbnailSize);
			}
			else {
				charactorThumbnailBitmap = PetImageDepot.getSubSpeciesImage(
					ctx, data.curCharactor, idx-ConstantValue.MAX_LEVEL, thumbnailSize, thumbnailSize);
			}
			
			int x = 0;			
			if(charactorThumbnailBitmap.getHeight() > thumbnailSize) {
				charactorThumbnailBitmap = Bitmap.createScaledBitmap(
						charactorThumbnailBitmap, thumbnailSize*thumbnailSize/charactorThumbnailBitmap.getHeight(), thumbnailSize, true);
				x = (thumbnailSize - charactorThumbnailBitmap.getWidth())/2;
			}
			int y = (thumbnailSize - charactorThumbnailBitmap.getHeight())/2;					
			
			canvas.drawBitmap(charactorThumbnailBitmap, x, y, paint);					
		}
			
		if(isChecked) {
			paint.setAlpha(255);
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.argb(255, 201, 233, 250));
			paint.setStrokeWidth(4);
			canvas.drawRect(0, 0, thumbnailSize, thumbnailSize, paint);				
		}
				
		return petThumbnailListImage;
	}	
	
	
	public String getCharactorDescription(int charactor, int idx, Data data) {
		if(idx >= ConstantValue.MAX_LEVEL + subspeciesDescription[charactor].length) {
			return "";
		}	
		
		if(!data.isLevelMax() && idx > data.getMaxLevel(charactor) &&
		    idx < ConstantValue.MAX_LEVEL ) {
			return charactor_unavailable;
		}		
		
		if(idx < ConstantValue.MAX_LEVEL) {
			return levelDescription[charactor][idx];
		}
		else {
			int subspecies = idx - ConstantValue.MAX_LEVEL;
			if( data.getMaxLevel(data.curCharactor) == ConstantValue.MAX_LEVEL &&
				data.getSubspeciesFeed(charactor, subspecies))
				return subspeciesDescription[charactor][subspecies];
			else {
				String subspeciesStr = String.format(subspecies_type, subspecies);
				subspeciesStr += subspecies_unavailable;
				return subspeciesStr;
			}		
		}		
	}
	
	public void initDetailView(int _width, int _height) {
		width  = _width;
		height = _height;
		lockedImage = ConstantValue.scaleButtonBitmap(ctx, R.drawable.locked, width, height);		
		
		lockedX = (width - lockedImage.getWidth()) / 2;
		lockedY = (height - lockedImage.getHeight()) / 2;	
	}
	
	public Bitmap getCharactorSelection(int charactor, Data data, int idx) {
		Bitmap charactorDetail = Bitmap.createBitmap(width, height, Config.ARGB_4444);	
		Canvas canvas = new Canvas(charactorDetail);
		Paint paint  = new Paint();		
		paint.setAntiAlias(true);
		
		boolean availability = false;
		if(idx <= data.getMaxLevel(data.curCharactor) && idx != ConstantValue.MAX_LEVEL) {
			availability = true;			
		}
		else if(data.getMaxLevel(data.curCharactor) == ConstantValue.MAX_LEVEL &&
				idx >= ConstantValue.MAX_LEVEL && idx < ConstantValue.MAX_LEVEL + subspeciesDescription.length) {
			int subspecies = idx - ConstantValue.MAX_LEVEL;
			availability = data.getSubspeciesFeed(data.curCharactor, subspecies);
		}
		
		if(!availability) {
			canvas.drawBitmap(lockedImage, lockedX, lockedY, paint);				
		}			
		else {
			Bitmap charactorImg;
			if(idx < ConstantValue.MAX_LEVEL) {
				charactorImg = PetImageDepot.getCharactorDefaultImage(ctx, charactor, idx, width-2*border, height-2*border);
			}
			else {
				charactorImg = PetImageDepot.getSubSpeciesImage(ctx, charactor, idx-ConstantValue.MAX_LEVEL, width-2*border, height-2*border);
			}		
			
			int charactorX = (width  - charactorImg.getWidth() ) / 2;
			int charactorY = (height - charactorImg.getHeight()) / 2;			
			
			canvas.drawBitmap(charactorImg, charactorX, charactorY, paint);					
		}	
		
		paint.setAlpha(255);
		paint.setStrokeWidth(8);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, width, height, paint);
		
		return charactorDetail;
	}
	
	public String getSelectBtnText(int charactor, Data petData, int idx) {
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
		
		return String.format(strBtn, Math.min(idx, ConstantValue.MAX_LEVEL-1));		
	}
}
