package ru.xdsup.HorseOfAtilla.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class AtillaService
{
	public enum Mode {QUEUE, STACK}
	private Mode mode;
	private Queue<Board> potentialStatesQueue = new ArrayDeque<>();
	private Stack<Board> potentialStatesStack = new Stack<>();

	public AtillaService() {
		this(Mode.QUEUE);
	}

	public AtillaService(Mode mode) {
		this.mode = mode;
	}

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
