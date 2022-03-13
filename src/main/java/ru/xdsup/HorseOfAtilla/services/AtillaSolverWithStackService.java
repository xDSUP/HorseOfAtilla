package ru.xdsup.HorseOfAtilla.services;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Predicate;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

@Log4j2
public class AtillaSolverWithStackService extends AtillaBaseSolverService
{
	@Setter
	private int maxMoveCount = 100000;

	private final Deque<Board> potentialStates = new LinkedList<>();

	public AtillaSolverWithStackService(int maxMoveCount) {
		this.maxMoveCount = maxMoveCount;
	}

	@Override
	protected void addPotentialState(Board board){
		potentialStates.addFirst(board);
	}

	@Override
	protected int getCountPotentialStates()
	{
		return potentialStates.size();
	}

	@Override
	protected Board getPotentialState(){
		return potentialStates.removeFirst();
	}

	@Override
	protected void generatePotentialStates(Board board)
	{
		if (board.getMoveCount() > maxMoveCount)
			return;
		super.generatePotentialStates(board);
	}
}
