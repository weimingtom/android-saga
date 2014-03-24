package com.androidsaga.base;

public class FoodList {	
	public class Food {
		public int resID;
		public int cost;
		public int satisfy;
		public float HP;  //percentage of HP change
		public String description;
	}
	
	public Food[] foods = new Food[16];
	
	FoodList() {
		for(int i = 0; i < foods.length; i++) {
			foods[i] = new Food();
		}
	}
	
	public void setFood(int idx, int resID, int cost, int satisfy, float HP, String description) {
		foods[idx].resID 	= resID;
		foods[idx].cost  	= cost;
		foods[idx].satisfy 	= satisfy;
		foods[idx].HP		= HP;
		foods[idx].description = description;
	}
}
