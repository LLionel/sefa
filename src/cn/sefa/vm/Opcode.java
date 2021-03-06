package cn.sefa.vm;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 *
 */

public class Opcode {
	
	public static final byte LOADI = 1; //load an integer
	public static final byte LOADB = 2; // load an integer of one byte
	public static final byte LOADS = 3; //load a string
	/*
	 * reg to reg
	 * reg to stack
	 * stack to stack
	 * stack to reg 
	 * 
	 */
	public static final byte MOVE = 4;
	/*
	 * GMOVE src dest
	 * reg to heap
	 * heap to reg 
	 * 
	 */
	public static final byte GMOVE = 5; //move a global value. 
	public static final byte IFZERO = 6;
	public static final byte IFNONZERO = 7;
	public static final byte GOTO = 8;
	public static final byte CALL = 9;
	public static final byte RETURN = 10;
	public static final byte SAVE = 11;	   //save all value of register to stack
	public static final byte RESTORE = 12; //restore the value of all registers from stack
	public static final byte ARRAYR = 13; //get the element of array
	public static final byte ARRAYW = 14;
	public static final byte ADD = 15;
	public static final byte SUB = 16;
	public static final byte MUL = 17;
	public static final byte DIV = 18;
	public static final byte REM = 19;	//remainder
	public static final byte NEG = 20;
	public static final byte EQUAL = 21 ;
	public static final byte MORE = 22 ;
	public static final byte LESS = 23 ;//
	public static final byte MEQ = 24 ;  //more or euqal
	public static final byte LEQ = 25 ; //less or equal
	
	public static byte encodeRegister(int reg){
		if(reg>SefaVM.NUM_OF_REG)
			throw new SefaException("the number of reg has exceeded. ");
		else
			return (byte)(-(reg+1));
	}
	
	public static int decodeRegister(byte operand){
		return -1-operand ;
	}
	
	public static byte encodeByteOffset(int offset){
		if(offset>Byte.MAX_VALUE)
			throw new SefaException("byte offset has exceeded the limits.");
		else
			return (byte)offset;
	}
	
	public static short encodeShortOffset(int offset){
		if(offset<Short.MIN_VALUE || Short.MAX_VALUE < offset)
			throw new SefaException("short offset has exceeded.");
		else
			return (short)offset ;
	}
	
	public static int decodeOffset(byte operand){
		return operand;
	}
	public static boolean isRegister(byte operand){
		return operand < 0;
	}
	public static boolean isOffset(byte operand){
		return operand >= 0;
	}

}
