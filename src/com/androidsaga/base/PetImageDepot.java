package com.androidsaga.base;

import com.androidsaga.action.*;
import android.content.Context;
import android.graphics.Bitmap;

public class PetImageDepot {

	public static class PetImageList {		
		public int[][] imageResID = new int[5][8];	
		public int[]   subSpeciesID = new int[8];
		public int	   subspeciesCount = 1;
		
		public PetImageList() {
			// TODO Auto-generated constructor stub
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < imageResID[i].length; j++) {
					imageResID[i][j] = -1;
				}
			}
		}
		
		public void setImages(Integer[][] resID) {
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < imageResID[i].length; j++) {
					imageResID[i][j] = resID[i][j];
				}
			}		
		}	
		
		public void setSubspecies(Integer[] resID) {
			for(int i = 0; i < resID.length; i++) {
				subSpeciesID[i] = resID[i];
			}
			subspeciesCount = resID.length;
		}
	}
	
	public static final Integer IDLE 			= 0;
	public static final Integer SLEEP  			= 1;
	public static final Integer HAPPY		 	= 2;
	public static final Integer ANGRY 			= 3;
	public static final Integer SAD				= 4;
	public static final Integer TEMP_ANGRY		= 5;
	public static final Integer DEAD			= 7;
	
	protected static PetImageList[] imageDepot = new PetImageList[32];
		
	public static void initPetImageDepot() {		
		for(int i = 0; i < imageDepot.length; i++) {
			imageDepot[i] = new PetImageList();
		}			
		
		//imageDepot[ConstantValue.CAESAR].setImages(Caesar.IMG_ID);
		//imageDepot[ConstantValue.CAESAR].setSubspecies(Caesar.SUBSPECIES_ID);
	}

	
	public static Bitmap getSubSpeciesImage(Context ctx, int charactor, int subSpecies, int width, int height) {
		return ConstantValue.scaleButtonBitmap(ctx, imageDepot[charactor].subSpeciesID[subSpecies], width, height);
	}
	
	public static Bitmap getSubSpeciesClipImage(Context ctx, int charactor, int subSpecies, int width, int height) {
		return ConstantValue.clipBitmap(ctx, imageDepot[charactor].subSpeciesID[subSpecies], width, height);
	}
	
	public static Bitmap getCharactorDefaultImage(Context ctx, int charactor, int level, int width, int height) {
		return ConstantValue.scaleButtonBitmap(ctx, imageDepot[charactor].imageResID[level][IDLE], width, height);
	}
	
	public static int getSubspeciesCount(int charactor) {
		return imageDepot[charactor].subspeciesCount;
	}
	
	public static Bitmap[] initImageLevel(Context ctx, int charactor, int level, int width, int height) {
		Bitmap[] bmps = new Bitmap[8];
		
		for(int i = 0; i < 8; i++) {
			//if it's a valid resource id
			if(imageDepot[charactor].imageResID[level][i] > 0) {
				bmps[i] = ConstantValue.scaleButtonBitmap(ctx, imageDepot[charactor].imageResID[level][i], width, height);
			}
		}
				
		return bmps;
	}
}
