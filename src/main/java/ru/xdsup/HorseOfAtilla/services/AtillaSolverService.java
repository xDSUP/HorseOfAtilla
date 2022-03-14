package ru.xdsup.HorseOfAtilla.services;

import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.Statistic;

public interface AtillaSolverService
{
	String analyze(Board initialState);

	Statistic getStatistic();
}
