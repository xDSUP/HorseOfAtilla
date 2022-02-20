package ru.xdsup.HorseOfAtilla.core;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class BoardTest
{
	@Test
	void testFromWithNormalFigures()
	{
		Board board = Board.from(8, 8, "" +
				"12345678\n" +
				"12345678\n" +
				"123K5678\n" +
				"12345678\n" +
				"1234W678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678");
		assertEquals(Utils.toChessNotation(board.getFigures()[2][3].getCoords()), "D6");
		assertEquals(Utils.toChessNotation(board.getFigures()[4][4].getCoords()), "E4");
	}
}