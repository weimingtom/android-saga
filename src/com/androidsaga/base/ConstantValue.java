package com.androidsaga.base;

import com.androidsaga.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

public class ConstantValue {
	
	// default dp is 160
	public static final Float DEFAULT_DENSITY = 1.5f;
	
	public static final Integer NONE	 	= 0xffffffff;
	public static final Integer IMG_PER_ROW		= 8;
	
	public static final Integer SAGA = 0;
	
	public static final Integer TOTAL_LIBITEMS = 32;
	
	//保存数据	
	public static final String DEFAULT_SHARED_PREF 	= "com.androidsaga_shared_pref";
	public static final String KEY_VERSION			= "version";
	
	public static final String KEY_SUBSPECISES 		= "subSpecises";	
	public static final String KEY_CHARACTORINFO 	= "charactorInfo";	
	public static final String KEY_STATUS			= "status";	
	public static final String KEY_LEVEL 			= "level";
	public static final String KEY_SATISFY 			= "satisfy";
	public static final String KEY_CLEAN			= "clean";
	public static final String KEY_CLEANFACTOR		= "cleanfactor";
	public static final String KEY_FAT				= "fat";
	public static final String KEY_HUNGRY 			= "hungry";
	public static final String KEY_HP	 			= "HP";	
	public static final String[] KEY_EXTRA			= {"extra0", "extra1", "extra2", "extra3", "extra4", "extra5", "extra6", "extra7"};		
	public static final String[] KEY_FOOD			= {"food0", "food1", "food2", "food3", "food4", "food5", "food6", "food7", "food8",
													   "food9", "food10", "food11", "food12", "food13", "food14", "food15"};
	
	public static final String KEY_MONEY			= "money";
	public static final String KEY_MONEY_FROZEN		= "money_frozen";
	public static final String KEY_LAST_CLOSE		= "last_close";
	public static final String KEY_QUIET			= "quiet";
	
	public static final Integer MAX_LEVEL_MASK 		= 0xf;
	public static final Integer SUBSPECIES_MASK		= 0xff00;
	public static final Integer SUBSPECIES_SHFBIT	= 8;
	public static final Integer PRE_MAX_LV_MASK  	= 0xf0;
	public static final Integer PRE_MAX_LV_SHFBIT	= 4;
	
	public static final Integer NOT_FEED			= 0x0;
	public static final Integer IS_FEED				= 0x1;
	
	// 状态
	public static final Integer STATUS_NORMAL	= 0;
	public static final Integer STATUS_SLEEP  	= 1;
	public static final Integer STATUS_DEAD		= -1;
	
	public static final Integer[] HP_LEVEL  = { 5, 10, 15, 20, 30, 40, 50, 60, 80, 100, Integer.MAX_VALUE };
	public static final Integer[] EXP_LEVEL = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, Integer.MAX_VALUE };
	public static final Integer[] INIT_EXP_LEVEL = { 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 0 };
	public static final Integer MAX_CLEAN = 100;
	public static final Integer LOW_CLEAN = 15;
	
	public static final Integer[] FOOD_COST    = {0, 0, 0, 5, 10, 15, 20, 30, 40, 60, 100, 999};
	
	public static final Integer EVENT_NONE = 0;
	public static final Integer EVENT_LVUP = 1;
	public static final Integer EVENT_DEAD = 2;
	public static final Integer EVENT_LVMAX = 3;
	
	public static final Integer MONEY_MANY = 2000;
	
	// 最高等级，到这个等级产生亚种
	public static final Integer MAX_LEVEL 		= 10;
	
		
	public static Bitmap scaleButtonBitmap(Context ctx, int resourceID, int scaleWidth, int scaleHeight){
		if(resourceID < 0) return null;
		
		Bitmap origBitmap = BitmapFactory.decodeResource(
							ctx.getResources(), resourceID);		
		
		int width  = origBitmap.getWidth();
		int height = origBitmap.getHeight();	
		int newHeight = height*scaleWidth/width;
		if(newHeight > scaleHeight) {
			scaleWidth = scaleHeight*width/height;
		}
		else {
			scaleHeight = newHeight;
		}
		
		return Bitmap.createScaledBitmap(origBitmap, scaleWidth, scaleHeight, true).copy(Config.ARGB_4444, true);		
	}
	
	public static Bitmap clipBitmap(Context ctx, int resourceID, int scaleWidth, int scaleHeight){
		Bitmap origBitmap = BitmapFactory.decodeResource(
							ctx.getResources(), resourceID);
		
		int width  = origBitmap.getWidth();
		int height = origBitmap.getHeight();		
		
		int newWidth, newHeight;
		if(width*scaleHeight > height*scaleWidth) {
			newHeight = scaleHeight;
			newWidth  = newHeight*width/height;			
		}
		else {
			newWidth  = scaleWidth;
			newHeight = height*newWidth/width;
		}
		int clipX = (newWidth-scaleWidth) / 2;
		int clipY = (newHeight-scaleHeight) / 2;		
		
		return Bitmap.createBitmap(Bitmap.createScaledBitmap(origBitmap, newWidth, newHeight, true).copy(Config.ARGB_4444, false),
				clipX, clipY, scaleWidth, scaleHeight);		
	}
	
	public static String getVersion(Context ctx)
    {  
        try {  
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);  
            return pi.versionName;  
        } 
        catch (NameNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return ctx.getString(R.string.version_unknown);  
        }  
    }  
	
    public static int getVersionCode(Context ctx)
    {  
        try {  
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);  
            return pi.versionCode;  
        }
        catch (NameNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return 0;  
        }  
    }  
    
    public static int scalePix(Context ctx, int pix) {
    	 final float scale = ctx.getResources().getDisplayMetrics().density;
         return (int)(pix*scale/ConstantValue.DEFAULT_DENSITY + 0.5f);     	
    }
}

