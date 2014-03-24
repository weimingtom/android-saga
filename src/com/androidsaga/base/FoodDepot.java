package com.androidsaga.base;

import com.androidsaga.action.*;
import com.androidsaga.base.FoodList.Food;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FoodDepot {
	protected static FoodList[] foodLists = new FoodList[1];
	
	
	public static void initFoodDepot(Context ctx) {
		for(int i = 0; i < 1; i++) {
			foodLists[i] = new FoodList();
		}
		
		// TODO Auto-generated constructor stub		
		for(int i = 0; i < 12; i++) {			
			foodLists[ConstantValue.SAGA].setFood(i, 
					Saga.FOOD_RESID[i],
					ConstantValue.FOOD_COST[i],
					Saga.FOOD_SATISFY[i],
					Saga.FOOD_HP[i],
					ctx.getResources().getString(Saga.FOOD_DESCRIPTION[i]));
		}		
	}
	
	public static Bitmap getFoodImage(Context ctx, int charactor, int foodIdx, int width, int height) {
		return BitmapFactory.decodeResource(
				ctx.getResources(), foodLists[charactor].foods[foodIdx].resID);		
	}
	
	public static String getFoodDescription(int charactor, int foodIdx) {
		return foodLists[charactor].foods[foodIdx].description;
	}
	
	public static int getFoodCost(int charactor, int foodIdx) {
		return foodLists[charactor].foods[foodIdx].cost;
	}
	
	public static int getFoodSatisfy(int charactor, int foodIdx) {
		return foodLists[charactor].foods[foodIdx].satisfy;
	}
	
	public static int getFoodHP(int charactor, int foodIdx) {
		return (int)(foodLists[charactor].foods[foodIdx].HP*100);
	}
	
	public static Food getFood(int charactor, int foodIdx) {
		return foodLists[charactor].foods[foodIdx];
	}
}
