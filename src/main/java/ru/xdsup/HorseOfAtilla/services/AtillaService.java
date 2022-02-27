package ru.xdsup.HorseOfAtilla.services;

import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

import java.util.ArrayDeque;
import java.util.Deque;

public class AtillaService
{
	public enum Mode {QUEUE, STACK}
	private final Mode mode;

	private final Deque<Board> potentialStates = new ArrayDeque<>();

	public AtillaService() {
		this(Mode.QUEUE);
	}

	public AtillaService(Mode mode) {
		this.mode = mode;
	}

	public String analyze(Board board)
	{
		//1 - помещаем в список открытых вершин нач сост
		potentialStates.addFirst(board);
		Board potentialState;
		//2 цикл поиска
		do
		{
			//2.1 извлекаем вершину из поиска и удаляем ее (2.3)
			potentialState = getPotentialState();
			//2.2 проверяем на соответст целевому состоянию
			if(potentialState.isEndState())
				return potentialState.toString();
			//2.3 помещаем в список закрытых вершин (в нашем случае просто удаляем из списка открытых)
			//2.4 процедура раскрытия вершины
			generatePotentialStates(potentialState);
		} while (potentialStates.size() > 0);
		//3 решения нет
		return null;
	}

	private Board getPotentialState(){
		if(mode == Mode.QUEUE)
			return potentialStates.getLast();
		else
			return potentialStates.getFirst();
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
