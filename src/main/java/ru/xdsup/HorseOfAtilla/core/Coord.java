package ru.xdsup.HorseOfAtilla.core;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Coord
{
	private int x;
	private int y;

	public void move(int dx, int dy){
		x += dx;
		y += dy;
	}

	public void move(Coord coord){
		move(coord.getX(), coord.getY());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Coord coord = (Coord) o;
		return x == coord.x &&
				y == coord.y;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}

	@Override
	public String toString()
	{
		return Utils.toChessNotation(x, y);
	}
}
