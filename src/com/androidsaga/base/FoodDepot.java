package com.androidsaga.base;

import com.androidsaga.action.*;
import com.androidsaga.base.FoodList.Food;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FoodDepot {
	protected static FoodList foodList;
	
	
	public static void initFoodDepot(Context ctx) {
		for(int i = 0; i < 1; i++) {
			foodList = new FoodList();
		}
		
		// TODO Auto-generated constructor stub		
		for(int i = 0; i < 12; i++) {			
			foodList.setFood(i, 
					Saga.FOOD_RESID[i],
					ConstantValue.FOOD_COST[i],
					Saga.FOOD_SATISFY[i],
					Saga.FOOD_HP[i],
					ctx.getResources().getString(Saga.FOOD_DESCRIPTION[i]));
		}		
	}
	
	public static Bitmap getFoodImage(Context ctx, int foodIdx, int width, int height) {
		return BitmapFactory.decodeResource(
				ctx.getResources(), foodList.foods[foodIdx].resID);		
	}
	
	public static String getFoodDescription(int foodIdx) {
		return foodList.foods[foodIdx].description;
	}
	
	public static int getFoodCost(int foodIdx) {
		return foodList.foods[foodIdx].cost;
	}
	
	public static int getFoodSatisfy(int foodIdx) {
		return foodList.foods[foodIdx].satisfy;
	}
	
	public static int getFoodHP(int foodIdx) {
		return (int)(foodList.foods[foodIdx].HP*100);
	}
	
	public static Food getFood(int foodIdx) {
		return foodList.foods[foodIdx];
	}
}
