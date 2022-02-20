package ru.xdsup.HorseOfAtilla.core;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class BoardAnalizer
{
	public enum Mode {QUEUE, STACK}
	private Mode mode = Mode.QUEUE;
	private Queue<Board> potentialStatesQueue = new ArrayDeque<>();
	private Stack<Board> potentialStatesStack = new Stack<>();

	public String analyze(Board board)
	{
		potentialStatesQueue.add(board);
		Board potentialState;
		do
		{
			potentialState = getPotentialState();
			if(potentialState.isEndState())
				return potentialState.toString();
			generatePotentialStates(potentialState);
		} while (potentialStatesQueue.size() > 0);
		return null;
	}

	private Board getPotentialState(){
		if(mode == Mode.QUEUE)
			return potentialStatesQueue.poll();
		else
			return potentialStatesStack.pop();
	}

	private void addPotentialState(Board board){
		if(mode == Mode.QUEUE)
			potentialStatesQueue.add(board);
		else
			potentialStatesStack.add(board);
	}

	private void generatePotentialStates(Board board)
	{
		Knight.getAvailablePoints(board.getKnight().getCoords()).stream()
				.filter(board::isAvailableMove)
				.map(board::moveKhignt)
				.forEach(this::addPotentialState);
	}
}
