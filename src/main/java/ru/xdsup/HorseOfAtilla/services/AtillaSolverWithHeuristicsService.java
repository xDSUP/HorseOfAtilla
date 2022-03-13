package ru.xdsup.HorseOfAtilla.services;

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
	private final PriorityQueue<Board> potentialStates;
	private final Set<Coord> checkedCoords = new HashSet<>();
	private boolean kingDefeated;

	public AtillaSolverWithHeuristicsService(int initialCapacity, Function<Board, Integer> heuristic)
	{
		this.potentialStates = new PriorityQueue<>(initialCapacity, Comparator.comparingInt(heuristic::apply));
	}

	@Override
	protected int getCountPotentialStates()
	{
		return potentialStates.size();
	}

	@Override
	protected Board getPotentialState(){
		return potentialStates.remove();
	}

	@Override
	protected void addPotentialState(Board board){
		potentialStates.add(board);
	}

	@Override
	protected void generatePotentialStates(Board board)
	{
		if(checkedCoords.contains(board.getKnight().getCoords()))
			return;
		else
			checkedCoords.add(board.getKnight().getCoords());

		// когда дошли до короля, делаем отметочку и чистим все потец пути сохр до этого и идем теперь от короля
		// найденный путь до короля считаем огнями для всех послед генер путей
		if(!kingDefeated && board.isKingDefeated()){
			kingDefeated = true;
			checkedCoords.clear();
			checkedCoords.addAll(board.getFires());
			checkedCoords.remove(board.getStartPosition());
			potentialStates.clear();
		}

		super.generatePotentialStates(board);
	}

	@Override
	protected Predicate<Coord> getCoordPredicate(Board board)
	{
		return p->  board.isAvailableMove(p) && !checkedCoords.contains(p);
	}
}
