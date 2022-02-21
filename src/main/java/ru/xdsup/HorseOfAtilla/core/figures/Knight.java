package ru.xdsup.HorseOfAtilla.core.figures;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Figure
{
	public Knight(Coord coords)
	{
		super(coords);
	}

	static public List<Coord> getAvailablePoints(Coord coords){
		List<Coord> pairs = new ArrayList<>();
		pairs.add(new Coord(coords.getX() + 1, coords.getY() + 2));
		pairs.add(new Coord(coords.getX() + 1, coords.getY() - 2));
		pairs.add(new Coord(coords.getX() - 1, coords.getY() + 2));
		pairs.add(new Coord(coords.getX() - 1, coords.getY() - 2));
		pairs.add(new Coord(coords.getX() + 2, coords.getY() + 1));
		pairs.add(new Coord(coords.getX() + 2, coords.getY() - 1));
		pairs.add(new Coord(coords.getX() - 2, coords.getY() + 1));
		pairs.add(new Coord(coords.getX() - 2, coords.getY() - 1));
		return pairs;
	}

	public void move(Coord coord){
		this.coords.move(coord);
	}
}
