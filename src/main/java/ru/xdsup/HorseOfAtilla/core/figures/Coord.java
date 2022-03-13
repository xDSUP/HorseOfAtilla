package ru.xdsup.HorseOfAtilla.core.figures;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.xdsup.HorseOfAtilla.Utils;

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

	public Coord add(int d)
	{
		return new Coord(x + d, y + d);
	}

	public Coord sub(int d)
	{
		return new Coord(x - d, y - d);
	}

	public Coord add(Coord coord)
	{
		return new Coord(x + coord.getX(), y + coord.getY());
	}

	public Coord sub(Coord coord)
	{
		return new Coord(x - coord.getX(), y - coord.getY());
	}

	public Coord mul(int cl)
	{
		return new Coord(x * cl, y * cl);
	}

	public Coord div(int cl)
	{
		return new Coord(x / cl, y / cl);
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
