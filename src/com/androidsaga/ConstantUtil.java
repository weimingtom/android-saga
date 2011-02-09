package com.androidsaga;
public class ConstantUtil{	
	public static final Integer NORMAL_SAGA  = 0;
	public static final Integer GOODMAN_SAGA = 1;
	public static final Integer SCIENCE_SAGA = 2;
	public static final Integer OTAKU_SAGA   = 3;
	public static final Integer UNCLE_SAGA   = 4;
	public static final Integer GOKATERA_SAGA = 5;
	
	public static final Integer STATUS_DEAD   = -1;
	public static final Integer STATUS_NORMAL = 0;
	public static final Integer STATUS_SLEEP = 1;
	public static final Integer STATUS_BATH = 2;
	public static final Integer STATUS_HUNGRY = 3;
	public static final Integer STATUS_SICK = 4;
	
	public static final Integer BLUE_THRSHOLD = 60;
	public static final Integer BLACK_THRESHOLD = 40;	
	
	public static final Float SATISFACTION_DEFAULT = 80.f;
	public static final String  ENCODING = "UTF-8";	
	
	public static final Float OTAKU_TIME = 7200.f; //2 hours	
	
	public static final Float DIRTY_AFFECT_SICK = 50.f;
	public static final Float HUNGRY_AFFECT_SICK = 50.f;
	public static final Float DIRTY_AFFECT_SATISFY = 40.f;
	public static final Float HUNGRY_AFFECT_SATISFY = 30.f;
	public static final Float SICK_AFFECT_SATISFY = 40.f;
	
	public static final String DATA_PATH = "saga.dat";
	public static final String DEFAULT_SHARED_PREF = "com.androidsaga_preferences";
	
	public static final String KEY_CHARACTOR = "charactor";
	public static final String KEY_STATUS = "status";
	public static final String KEY_TOTAL_TIME = "total_time";
	public static final String KEY_LEVEL = "level";
	public static final String KEY_SATISFY = "satisfy";
	public static final String KEY_HUNGRY = "hungry";
	public static final String KEY_DIRTY = "dirty";
	public static final String KEY_SICK = "sick";
	public static final String KEY_OTAKU = "otaku";
	public static final String KEY_INELEGANT = "inelegant";
	public static final String KEY_SCIENTIST = "scientist";
	public static final String KEY_STRANGE = "strange";	
	public static final String KEY_IS_RUNNING = "is_running";	
	public static final String KEY_LAST_CLOSE = "last_close";
}