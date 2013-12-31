package com.androidsaga.action;
import android.content.Context;

public class ActionFactory {
	public static ActionBase getAction(int curCharactor, Context ctx) {
		ActionBase action;
		
		action = new ActionBase(ctx);			
				
		return action;
	}
}
