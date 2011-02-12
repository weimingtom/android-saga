package com.androidsaga;

import android.graphics.Bitmap;

public class AutoPresent {
	//present id, each present has its own identical id
	//this id is used as key to search in the database
	public Integer presentID;
	
	//present pic and gray-level pic
	public Bitmap presentPic = null;	
	public Bitmap presentGrayPic = null;
	
	//current place: will be moved every frame
	public Integer x;
	public Integer y;
	public Float rotation;
	
	public Boolean hasPresentNow = false;	
}
