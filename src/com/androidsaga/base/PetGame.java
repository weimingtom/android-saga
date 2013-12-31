package com.androidsaga.base;

import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;

import com.androidsaga.R;

public class PetGame {
	protected Context ctx;	
	public int principal = 1;
	
	protected Bitmap[][] opponentImg 	= new Bitmap[3][8];	
	protected Bitmap[] opponentHandImg 	= new Bitmap[3];
	protected Bitmap[] myHandImg		= new Bitmap[3];	
	
	protected int[] opponentImgCount = new int[3];
		
	protected Random rnd = new Random(System.currentTimeMillis());
	
	public static int STONE	  = 0;
	public static int SCISSORS = 1;	
	public static int CLOTH	  = 2;
	
	public static int DRAW = 1;
	public static int WIN  = 0;
	public static int LOSE = 2;
	
	public int opponentHand;
	public int myHand;
	public int result;
	
	protected int opponentImgIdx = 0;
	
	public PetGame(Context _ctx, int width, int height) {
		ctx = _ctx;		
		
		opponentImg[DRAW][0] = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_start, width, height);
		opponentImgCount[DRAW] = 1;
		
		opponentImg[WIN][0]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_win01, width, height);
		opponentImg[WIN][1]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_win02, width, height);
		opponentImg[WIN][2]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_win03, width, height);
		opponentImg[WIN][3]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_win04, width, height);
		opponentImg[WIN][4]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_win05, width, height);
		opponentImgCount[WIN] = 5;
		
		opponentImg[LOSE][0]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_lose01, width, height);
		opponentImg[LOSE][1]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_lose02, width, height);
		opponentImg[LOSE][2]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_lose03, width, height);
		opponentImg[LOSE][3]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_lose04, width, height);
		opponentImg[LOSE][4]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_lose05, width, height);
		opponentImg[LOSE][5]  = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_lose06, width, height);
		opponentImgCount[LOSE] = 6;
		
		opponentHandImg[CLOTH] = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_opponent_cloth, width, height);
		opponentHandImg[STONE] = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_opponent_stone, width, height);
		opponentHandImg[SCISSORS] = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_opponent_scissors, width, height);
		
		myHandImg[CLOTH] = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_my_cloth, width, height);
		myHandImg[STONE] = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_my_stone, width, height);
		myHandImg[SCISSORS] = ConstantValue.scaleButtonBitmap(ctx, R.drawable.game_my_scissors, width, height);
		
		result = DRAW;
	}
	
	public void resetGame() {
		result = DRAW;
		opponentImgIdx = 0;
		myHand = opponentHand = STONE;
	}
	
	protected Bitmap getMyDraw(PetBase pet) {
		return pet.getPetStatusBitmap(PetImageDepot.IDLE);		
	}
	
	protected Bitmap getMyWin(PetBase pet) {
		return pet.getPetStatusBitmap(PetImageDepot.HAPPY);		
	}
	
	protected Bitmap getMyLose(PetBase pet) {
		return pet.getPetStatusBitmap(PetImageDepot.SAD);		
	}	
	
	public Bitmap getOpponentFace() {
		return opponentImg[2-result][opponentImgIdx];
	}
	
	public Bitmap getMyFace(PetBase pet) {
		if(result == WIN) 
			return getMyWin(pet);
		else if(result == LOSE) 
			return getMyLose(pet);
		else
			return getMyDraw(pet);
	}
	
	public Bitmap getOpponentHand() {
		return opponentHandImg[opponentHand];
	}	
	
	public Bitmap getMyHand() {
		return myHandImg[myHand];
	}
	
	
	public int getGameMoney() {
		return principal;
	}
	
	public int onMyHandStone() {	
		opponentHand = rnd.nextInt(3);
		myHand = STONE;
		
		if(opponentHand == STONE) {			
			result = DRAW;
		}
		else if(opponentHand == SCISSORS) {			
			result = WIN;
		}
		else {			
			result = LOSE;
		}	
		opponentImgIdx = rnd.nextInt(opponentImgCount[2-result]);
		
		return result;
	}
	
	public int onMyHandScissors() {	
		opponentHand = rnd.nextInt(3);
		myHand = SCISSORS;
		
		if(opponentHand == STONE) {			
			result = LOSE;
		}
		else if(opponentHand == SCISSORS) {			
			result = DRAW;
		}
		else {			
			result = WIN;
		}			
		opponentImgIdx = rnd.nextInt(opponentImgCount[2-result]);
		
		return result;
	}
	
	public int onMyHandCloth() {	
		opponentHand = rnd.nextInt(3);	
		myHand = CLOTH;
		
		if(opponentHand == STONE) {			
			result = WIN;
		}
		else if(opponentHand == SCISSORS) {			
			result = LOSE;
		}
		else {			
			result = DRAW;
		}	
		opponentImgIdx = rnd.nextInt(opponentImgCount[2-result]);
		
		return result;
	}
}
