package ru.xdsup.HorseOfAtilla.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class BoardAnalizer
{
	private Board board;
	private Knight knight;
	private King king;
	private HashMap<Coord, Figure> figures = new HashMap<>();
	//private Queue<Move> potentialPoints = new ArrayDeque<>();
	private Queue<Move> potentialPoints = new ArrayDeque<>();
	private List<Move> paths = new ArrayList<>();

	public Move analyze(Board board)
	{
		for (Figure[] figuresInRow : board.getFigures())
		{
			for (Figure figure : figuresInRow)
			{
				if (figure == null)
					continue;
				if (figure instanceof King)
					king = (King) figure;
				else if (figure instanceof Knight)
					knight = (Knight) figure;
				else
					figures.put(figure.getCoords(), figure);
			}
		}

		potentialPoints.add(new Move(knight.getCoords()));
		Move move = null;
		do
		{
			move = potentialPoints.poll();
			if(figures.containsKey(move.getPos()))
				continue;
			if (king.getCoords().equals(move.getPos()))
			{
				if (paths.size() == 1){
					if(move.getPrevMove() == null)
						return move;
					Move firstPass = paths.get(0);
					for (Move prev = move.getPrevMove(); prev != null ; prev = prev.getPrevMove())
					{
						firstPass = Move.append(firstPass, prev.getPos());
					}
					return firstPass;
				}
				paths.add(move);

				continue;
			}
			Coord pos = move.getPos();
			if(!knight.getCoords().equals(pos))
				figures.put(pos, new Figure(pos));
			updatePoints(board, move);
		} while (potentialPoints.size() > 0);
		return null;
	}

	private void updatePoints(Board board, Move lastMove)
	{
		Knight.getAvailablePoints(lastMove.getPos()).forEach((coord) ->
		{
			if ((board.isAvailableCoord(coord) && !figures.containsKey(coord)))
			{
				potentialPoints.add(Move.append(lastMove, coord));
			}
		});
	}
}
