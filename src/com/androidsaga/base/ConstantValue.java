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
	public static final Integer TOTAL_CHARACTOR = 32;
	public static final Integer IMG_PER_ROW		= 8;
	
	public static final Integer SAGA = 0;
	
	/*public static final Integer JONATHAN 	= 0;
	public static final Integer JOSEPH	 	= 1;
	public static final Integer JOTARO	 	= 2;
	public static final Integer JOSUKE	 	= 3;
	public static final Integer GIORNO	 	= 4;
	public static final Integer JOLYNE	 	= 5;
	public static final Integer JOHNNY		= 6;
	public static final Integer JO2UKE	 	= 7;	
	public static final Integer GYRO	 	= 8;
	public static final Integer ISKAMEN	 	= 9;
	public static final Integer DIO		 	= 10;
	public static final Integer DIEGO	 	= 11;
	public static final Integer VALENTINE 	= 12;
	public static final Integer CAESAR		= 13;
	public static final Integer KAKYOIN		= 14;
	public static final Integer OKUYASU		= 15;
	public static final Integer ROHEN		= 16;
	public static final Integer MISTA		= 17;
	public static final Integer ANNASUI		= 18;
	public static final Integer WEATHER		= 19;
	public static final Integer BUCCIARATI	= 20;
	public static final Integer DIABOLO		= 21;
	public static final Integer KAZZ		= 22;
	public static final Integer YOSHIKAGE	= 23;
	public static final Integer PUCCI		= 24;	
	public static final Integer POLNAREFF	= 25;
	public static final Integer CHARACTOR_COUNT = 26;
	
	public static final Integer TOTAL_CHARACTOR = 32;
	public static final Integer IMG_PER_ROW		= 8;
	
	//亚种
	public static final Integer NORMAL 		= 0;
	public static final Integer ABNORMAL1 	= 1;
	public static final Integer ABNORMAL2 	= 2;
	public static final Integer ABNORMAL3 	= 3;
	public static final Integer TOTAL_SUBSPECIES = 4;
	*/
	
	//保存数据	
	public static final String DEFAULT_SHARED_PREF 	= "com.androidsaga_shared_pref";
	public static final String KEY_VERSION			= "version";
	
	public static final String KEY_CHARACTOR 		= "charactor";	
	public static final String KEY_STATUS			= "status";	
	public static final String KEY_LEVEL 			= "level";
	public static final String KEY_SATISFY 			= "satisfy";
	public static final String KEY_FAT				= "fat";
	public static final String KEY_HUNGRY 			= "hungry";
	public static final String KEY_HP	 			= "HP";	
	public static final String[] KEY_EXTRA			= {"extra0", "extra1", "extra2", "extra3", "extra4", "extra5", "extra6", "extra7"};		
	
	public static final String KEY_MONEY			= "money";
	public static final String KEY_MONEY_FROZEN		= "money_frozen";
	public static final String KEY_LAST_CLOSE		= "last_close";
	public static final String KEY_QUIET			= "quiet";
	
	public static final String KEY_EGG_EXISTS		= "egg_exists";
	public static final String KEY_EGG_TIME			= "egg_time";
	public static final String KEY_EGG_CHANGE		= "egg_change";
	public static final String KEY_EGG_GOLD			= "egg_gold";
	public static final String KEY_EGG_DARK			= "egg_dark";
	public static final String KEY_EGG_TEMPERATUE	= "egg_temperature";	
	public static final String KEY_EGG_CUR_TEMP		= "egg_cur_temp";
	public static final String KEY_EGG_HATCH_TIME	= "egg_hatch_time";
	
	public static final String[] KEY_CHARACTOR_AVAILABLE 	= {
		"char00", "char01", "char02", "char03", "char04", "char05", "char06", "char07", "char08", "char09",
		"char10", "char11", "char12", "char13", "char14", "char15", "char16", "char17", "char18", "char19",
		"char20", "char21", "char22", "char23", "char24", "char25", "char26", "char27", "char28", "char29",
		"char30", "char31"
	};	
	
	//public static final Integer[] DEFAULT_CHARACTOR_SETTING = new Integer[ConstantValue.TOTAL_CHARACTOR];
	
	public static final Integer MAX_LEVEL_MASK 		= 0xf;
	public static final Integer AVAILABLITIY_MASK 	= 0xf0;
	public static final Integer AVAILABLITIY_SHFBIT	= 4;
	public static final Integer SUBSPECIES_MASK		= 0xff00;
	public static final Integer SUBSPECIES_SHFBIT	= 8;
	public static final Integer PRE_MAX_LV_MASK  	= 0xff0000;
	public static final Integer PRE_MAX_LV_SHFBIT	= 16;
	
	public static final Integer NOT_AVAILABLE		= 0xf;
	public static final Integer NOT_FEED			= 0x0;
	public static final Integer IS_FEED				= 0x1;
	
	public static final Integer DEFAULT_CHARACTOR = 0x240; //0xff=1001000000，currently only johnny/ishikamen is available
	// 状态
	public static final Integer STATUS_NORMAL	= 0;
	public static final Integer STATUS_SLEEP  	= 1;
	public static final Integer STATUS_DEAD		= -1;
	
	public static final Integer[] HP_LEVEL  = { 5, 10, 15, 20, 30, 40, 50, 60, 80, 100, Integer.MAX_VALUE };
	public static final Integer[] EXP_LEVEL = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, Integer.MAX_VALUE };
	public static final Integer[] INIT_EXP_LEVEL = { 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 0 };
	
	//public static final Integer[] FOOD_COST    = {0, 0, 5, 15, 50, 500};
	public static final Integer[] FOOD_COST    = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	public static final Integer EVENT_NONE = 0;
	public static final Integer EVENT_LVUP = 1;
	public static final Integer EVENT_DEAD = 2;
	public static final Integer EVENT_LVMAX = 3;
	
	public static final Integer MONEY_MANY = 2000;
	
	// 最高等级，到这个等级产生亚种
	public static final Integer MAX_LEVEL 		= 10;
	
	/*static {
		for(int i = 0; i < DEFAULT_CHARACTOR_SETTING.length; i++) {
			DEFAULT_CHARACTOR_SETTING[i] = 0xf0;			
		}
		DEFAULT_CHARACTOR_SETTING[ISKAMEN] 		= 0x0;
		DEFAULT_CHARACTOR_SETTING[JOHNNY]  		= 0x0;
		DEFAULT_CHARACTOR_SETTING[GYRO]	   		= 0x0;
		DEFAULT_CHARACTOR_SETTING[DIEGO]   		= 0x0;
		DEFAULT_CHARACTOR_SETTING[VALENTINE] 	= 0x0;
		DEFAULT_CHARACTOR_SETTING[JOTARO]		= 0x0;
		DEFAULT_CHARACTOR_SETTING[JOSUKE]		= 0x0;
		DEFAULT_CHARACTOR_SETTING[CAESAR]		= 0x0;
	}*/
	
	public static Bitmap scaleButtonBitmap(Context ctx, int resourceID, int scaleWidth, int scaleHeight){
		if(resourceID < 0) return null;
		
		Bitmap origBitmap = BitmapFactory.decodeResource(
							ctx.getResources(), resourceID);
		
		//Log.i("jojo", Integer.toString(scaleWidth)+","+ Integer.toString(origBitmap.getWidth()));		
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
		
		//Log.i("jojo", Integer.toString(scaleWidth)+","+ Integer.toString(origBitmap.getWidth()));
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

