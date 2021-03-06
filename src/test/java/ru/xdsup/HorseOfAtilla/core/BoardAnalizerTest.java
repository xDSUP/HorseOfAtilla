package ru.xdsup.HorseOfAtilla.core;

import org.junit.Test;
import ru.xdsup.HorseOfAtilla.Utils;
import ru.xdsup.HorseOfAtilla.services.AtillaSolverWithQueueService;

import static org.junit.Assert.*;

public class BoardAnalizerTest
{
	@Test
	public void testSimpleBoard()
	{
		Board board = Board.from("" +
				"12345678\n" +
				"12345678\n" +
				"123K5678\n" +
				"12345678\n" +
				"1234W678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678");
		AtillaSolverWithQueueService boardAnalizer = new AtillaSolverWithQueueService();
		assertEquals( boardAnalizer.analyze(board), "D6-E4-D6");
	}

	@Test
	public void testSimpleBoard2()
	{
		Board board = Board.from("" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12K45678\n" +
				"W2345678");
		AtillaSolverWithQueueService boardAnalizer = new AtillaSolverWithQueueService();
		assertEquals( boardAnalizer.analyze(board), "C2-A1-C2");
	}

	@Test
	public void testHardBoard()
	{
		Board board = Board.from("" +
				"K2345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"12345678\n" +
				"1234567W");
		AtillaSolverWithQueueService boardAnalizer = new AtillaSolverWithQueueService();
		String result = boardAnalizer.analyze(board);
		System.out.println(result);
		System.out.println(Utils.toStrArray(result));
		assertEquals(result, "G4-F6-E8-G7-E6-F8-G6-E7-C6-A5-B3-D2-B1-A3-B5-D6-F7-H6-G4");
	}

	@Test
	public void testHardBoard2()
	{
		Board board = Board.from("" +
				"1FFF56FF\n" +
				"FFFF567F\n" +
				"12345678\n" +
				"12FFFFFF\n" +
				"12FFFFKF\n" +
				"1WFF5F78\n" +
				"12F4567F\n" +
				"F2F45F78");
		AtillaSolverWithQueueService boardAnalizer = new AtillaSolverWithQueueService();
		String result = boardAnalizer.analyze(board);
		System.out.println(result);
		System.out.println(Utils.toStrArray(result));
		assertEquals(result, "G4-F6-E8-G7-E6-F8-G6-E7-C6-A5-B3-D2-B1-A3-B5-D6-F7-H6-G4");

	}
}