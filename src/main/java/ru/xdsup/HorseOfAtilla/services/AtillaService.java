package ru.xdsup.HorseOfAtilla.services;

import lombok.extern.log4j.Log4j2;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

import java.util.Deque;
import java.util.LinkedList;

@Log4j2
public class AtillaService
{
	public enum Mode {QUEUE, STACK}
	private final Mode mode;
	private long counter;

	private final Deque<Board> potentialStates = new LinkedList<>();

	public AtillaService() {
		this(Mode.QUEUE);
	}

	public AtillaService(Mode mode) {
		this.mode = mode;
	}

	public String analyze(Board board)
	{
		counter = 0;
		//1 - помещаем в список открытых вершин нач сост
		potentialStates.addFirst(board);
		Board potentialState;
		//2 цикл поиска
		do
		{
			//2.1 извлекаем вершину из поиска и удаляем ее (2.3)
			potentialState = getPotentialState();
			counter++;

			if(counter % 500 == 0)
				log.info("Обработано " + counter + " вершин");

			//2.2 проверяем на соответст целевому состоянию
			if(potentialState.isEndState()){
				log.info("Решение найдено! Обработано " + counter + " вершин");
				return potentialState.toString();
			}
			//2.3 помещаем в список закрытых вершин (в нашем случае просто удаляем из списка открытых)
			//2.4 процедура раскрытия вершины
			generatePotentialStates(potentialState);
		} while (potentialStates.size() > 0);
		//3 решения нет
		return null;
	}

	private Board getPotentialState(){
		if(mode == Mode.QUEUE)
			return potentialStates.removeLast();
		else
			return potentialStates.removeFirst();
	}

	private void addPotentialState(Board board){
		potentialStates.addFirst(board);
	}

	private void generatePotentialStates(Board board)
	{
		Knight.getAvailablePoints(board.getKnight().getCoords()).stream()
				.filter(board::isAvailableMove)
				.map(board::moveKhignt)
				.forEach(this::addPotentialState);
	}
}
