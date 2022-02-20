package ru.xdsup.HorseOfAtilla.core;

import java.nio.charset.CoderResult;

public class Utils
{
	static String alphabet = "ABCDEFGH";
	public static String toChessNotation(int x, int y){

		char ch = alphabet.charAt(x);
		String coord = ch + "" +(8-y);
		return coord;
	}

	public static String toChessNotation(Coord coord){
		return toChessNotation(coord.getX(), coord.getY());
	}
}
