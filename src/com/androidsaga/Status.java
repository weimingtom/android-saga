package com.androidsaga;

public class Status{	
	public static final Long NORMAL_BLUE  = 0x0100000000000000L;
	public static final Long NORMAL_BLACK = 0x0200000000000000L;
	public static final Long NORMAL_BLUE_BLACK = 0x0300000000000000L;
	public static final Long CHANGE_COLOR = 0x0400000000000000L;	
	
	public static final Long FALL_DOWN_1  = 0x0500000000000000L;
	public static final Long FALL_DOWN_2  = 0x0600000000000000L;
	public static final Long FALL_DOWN_3  = 0x0700000000000000L;
	public static final Long FALL_DOWN_SP = 0x0800000000000000L;
	
	public static final Long HEAD_NORMAL     = 0x0000000000000000L;
	public static final Long HEAD_DISORDER   = 0x0000010000000000L;
	public static final Long HEAD_UP         = 0x0001000000000000L;	
	public static final Long HEAD_UP_BACK    = 0x0002000000000000L;
	public static final Long HEAD_DOWN       = 0x0004000000000000L;	
	public static final Long HEAD_DOWN_BACK  = 0x0008000000000000L;
	public static final Long HEAD_LEFT       = 0x0010000000000000L;
	public static final Long HEAD_LEFT_BACK  = 0x0020000000000000L;
	public static final Long HEAD_RIGHT      = 0x0040000000000000L;
	public static final Long HEAD_RIGHT_BACK = 0x0080000000000000L;
	
	public static final Long FACE_NORMAL   = 0x0000000000000000L;
	public static final Long FACE_SHUT_EYE = 0x0000000100000000L;
	public static final Long FACE_OPEN_EYE = 0x0000000200000000L;	
	public static final Long FACE_LICK     = 0x0000004000000000L;
	public static final Long FACE_SNEEZE   = 0x0000008000000000L;
	public static final Long FACE_UNBREATH = 0x0000008000000000L;
	public static final Long FACE_LEFT_OUT_BACK      = 0x0000000400000000L;
	public static final Long FACE_LEFT_OUT_PINGPONG  = 0x0000000800000000L;
	public static final Long FACE_RIGHT_OUT_BACK     = 0x0000001000000000L;
	public static final Long FACE_RIGHT_OUT_PINGPONG = 0x0000002000000000L;	
	
	public static final Long BODY_NORMAL      = 0x0000000000000000L;
	public static final Long LEFTHAND_NORMAL  = 0x0000000000010000L;
	public static final Long LEFTHAND_UP      = 0x0000000000020000L;
	public static final Long LEFTHAND_DOWN    = 0x0000000000040000L;
	public static final Long LEFTHAND_RAND    = 0x0000000000080000L;
	public static final Long RIGHTHAND_NORMAL = 0x0000000000100000L;
	public static final Long RIGHTHAND_UP     = 0x0000000000200000L;
	public static final Long RIGHTHAND_DOWN   = 0x0000000000400000L;
	public static final Long RIGHTHAND_RAND   = 0x0000000000800000L;
}