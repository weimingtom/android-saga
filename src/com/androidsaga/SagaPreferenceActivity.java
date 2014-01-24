package com.androidsaga;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SagaPreferenceActivity extends PreferenceActivity {	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.layout.preference);
	}
}
