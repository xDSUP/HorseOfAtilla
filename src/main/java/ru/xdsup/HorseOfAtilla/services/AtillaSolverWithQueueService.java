package ru.xdsup.HorseOfAtilla.services;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import lombok.extern.log4j.Log4j2;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

import java.util.Deque;
import java.util.LinkedList;

@Log4j2
public class AtillaSolverWithQueueService extends AtillaBaseSolverService
{
	private final Deque<Board> potentialStates = new LinkedList<>();
	private final Set<Coord> checkedCoords = new HashSet<>();
	private boolean kingDefeated;

	@Override
	protected int getCountPotentialStates()
	{
		return potentialStates.size();
	}

	@Override
	protected Board getPotentialState(){
		return potentialStates.removeLast();
	}

	@Override
	protected void addPotentialState(Board board){
		potentialStates.addFirst(board);
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
