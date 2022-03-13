package ru.xdsup.HorseOfAtilla.services;

import java.util.function.Predicate;
import lombok.extern.log4j.Log4j2;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

@Log4j2
public abstract class AtillaBaseSolverService implements AtillaSolverService
{
	@Override
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

			//2.2 проверяем на соответст целевому состоянию
			if(isEndState(potentialState)){
					log.info("Решение найдено! Обработано " + counter + " вершин");
					return potentialState.toString();
			}
			//2.3 помещаем в список закрытых вершин (в нашем случае просто удаляем из списка открытых)
			//2.4 процедура раскрытия вершины
			generatePotentialStates(potentialState);
		} while (getCountPotentialStates() > 0);
		//3 решения нет
		log.info("решения нет");
		return null;
	}

	protected abstract Board getPotentialState();

	protected boolean isEndState(Board state){
		return state.isEndState();
	}

	protected abstract void addPotentialState(Board board);

	protected abstract int getCountPotentialStates();

	protected void generatePotentialStates(Board board)
	{
		Knight.getAvailablePoints(board.getKnight().getCoords()).stream()
				.filter(getCoordPredicate(board))
				.map(board::moveKhignt)
				.forEach(this::addPotentialState);
	}

	protected Predicate<Coord> getCoordPredicate(Board board)
	{
		return p->  board.isAvailableMove(p);
	}
}
