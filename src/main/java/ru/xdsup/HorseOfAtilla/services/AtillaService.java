package ru.xdsup.HorseOfAtilla.services;

import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

import java.util.Deque;
import java.util.LinkedList;

@Log4j2
public class AtillaService
{
	public enum Mode {QUEUE, STACK}
	private final Mode mode;

	@Setter
	private int maxMoveCount = 100000;

	private final Deque<Board> potentialStates = new LinkedList<>();
	private final Set<Coord> chechedCoords = new HashSet<>();
	private boolean kingDefeated;

	public AtillaService() {
		this(Mode.QUEUE);
	}

	public AtillaService(Mode mode) {
		this.mode = mode;
	}

	public String analyze(Board initialState)
	{
		long counter = 0;
		//1 - помещаем в список открытых вершин нач сост
		addPotentialState(initialState);

		Board potentialState;
		//2 цикл поиска
		do
		{
			//2.1 извлекаем вершину из поиска и удаляем ее (2.3)
			potentialState = getPotentialState();
			counter++;

			if(counter % 1000 == 0)
				log.info("Обработано " + counter + " вершин");

			if(mode == Mode.QUEUE){
				if(chechedCoords.contains(potentialState.getKnight().getCoords()))
					continue;
				else
					chechedCoords.add(potentialState.getKnight().getCoords());
			}

			// когда дошли до короля, делаем отметочку и чистим все потец пути сохр до этого и идем теперь от короля
			// найденный путь до короля считаем огнями для всех послед генер путей
			if(!kingDefeated && potentialState.isKingDefeated()){
				kingDefeated = true;
				chechedCoords.clear();
				chechedCoords.addAll(potentialState.getFires());
				chechedCoords.remove(potentialState.getStartPosition());
				potentialStates.clear();
			}
			//2.2 проверяем на соответст целевому состоянию
			if(isEndState(potentialState)){
					log.info("Решение найдено! Обработано " + counter + " вершин");
					return potentialState.toString();
			}
			//2.3 помещаем в список закрытых вершин (в нашем случае просто удаляем из списка открытых)
			//2.4 процедура раскрытия вершины
			generatePotentialStates(potentialState);
		} while (potentialStates.size() > 0);
		//3 решения нет
		log.info("решения нет");
		return null;
	}

	private Board getPotentialState(){
		if(mode == Mode.QUEUE)
			return potentialStates.removeLast();
		else
			return potentialStates.removeFirst();
	}

	private boolean isEndState(Board state){
		return state.isEndState();
	}

	private void addPotentialState(Board board){
		potentialStates.addFirst(board);
	}

	private void generatePotentialStates(Board board)
	{
		if (board.getMoveCount() > maxMoveCount && mode == Mode.STACK)
			return;
		Knight.getAvailablePoints(board.getKnight().getCoords()).stream()
				.filter(p-> isAvailableMove(board, p))
				.map(board::moveKhignt)
				.forEach(this::addPotentialState);
	}

	private boolean isAvailableMove(Board board, Coord p)
	{
		if(mode == Mode.QUEUE)
			return board.isAvailableMove(p) && !chechedCoords.contains(p);
		else
			return board.isAvailableMove(p);
	}
}
