package ru.xdsup.HorseOfAtilla.services;

import com.google.common.collect.MinMaxPriorityQueue;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;

@Log4j2
public class AtillaSolverWithHeuristicsService extends AtillaBaseSolverService
{
	private final MinMaxPriorityQueue<Board> potentialStates;
	private final Set<Coord> checkedCoords = new HashSet<>();
	private boolean kingDefeated;
	private Function<Board, Integer> heuristic;

	public AtillaSolverWithHeuristicsService(int maxSizeQueue, Function<Board, Integer> heuristic)
	{
		this.heuristic = heuristic;
		this.potentialStates = MinMaxPriorityQueue.<Board>orderedBy(Comparator.comparingInt(heuristic::apply))
				.maximumSize(maxSizeQueue)
				.create();
	}

	@Override
	protected int getCountPotentialStates()
	{
		return potentialStates.size();
	}

	@Override
	protected Board getPotentialState(){
		Board board = potentialStates.removeFirst();
		log.info("Беру " + board + " оценка = " + heuristic.apply(board));
		return board;
	}

	@Override
	protected void addPotentialState(Board board){
		potentialStates.add(board);
	}

	@Override
	protected void generatePotentialStates(Board board)
	{
		super.generatePotentialStates(board);
	}

	@Override
	protected Predicate<Coord> getCoordPredicate(Board board)
	{
		return p->  board.isAvailableMove(p);
	}
}
