package com.androidsaga.base;

import android.content.Context;

public class PetBath {
	protected Context ctx;
	
	protected int headerHeight;
	protected int xl;
	protected int xr;
	protected int yt;
	protected int yb;
	
	public PetBath(Context _ctx, int _headerHeight) {
		ctx 	= _ctx;			
        headerHeight = _headerHeight;                  
 	}
	
	public void SetTubPos(int _xl, int _xr, int _yt, int _yb)
	{	
		xl = _xl;
		xr = _xr;
		yt = _yt;
		yb = _yb;
	}
	
	public boolean isTubClicked(int _x, int _y) {
		return (_x > xl && _y > yt+headerHeight && 
				_x < xr && _y < yb+headerHeight);
	}
	
	public void onSoapTouch(PetBase pet) {
		pet.petData.clean += 0.05f;
		if(pet.petData.clean > ConstantValue.MAX_CLEAN) {
			pet.petData.clean = ConstantValue.MAX_CLEAN;
			pet.petData.HP -= 0.002f;
		}
		else {
			pet.petData.HP += 0.001f;
			pet.petData.satisfy += 0.002f * (pet.petData.level + 1);
			if(pet.petData.satisfy > ConstantValue.EXP_LEVEL[pet.petData.level]){
				pet.petData.satisfy = ConstantValue.EXP_LEVEL[pet.petData.level];
			}
		}
		if(pet.petData.clean > ConstantValue.LOW_CLEAN)
			pet.petData.cleanfactor = 1;
	}
	
}
