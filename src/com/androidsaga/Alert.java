package com.androidsaga;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alert{
	public static void showAlert(String title, String msg, Context ctx)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub					
			}			
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
			
		});
		AlertDialog ad = builder.create();
		ad.show();
	}	
}