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

	public int getHorseRange(Coord to){
		Coord subVector = sub(to);
		Coord modSubVector = new Coord(Math.abs(subVector.getX()), Math.abs(subVector.getY()));
		Coord fixedSubVector = new Coord(
				Math.max(modSubVector.getX(), modSubVector.getY()),
				Math.min(modSubVector.getX(), modSubVector.getY()));

		int vector = fixedSubVector.getX() * 10 + fixedSubVector.getY();
		switch (vector){
			case 0:
				return 0;
			case 12:
			case 21:
				return 1;
			case 2:
			case 11:
			case 20:
			case 40:
			case 31:
			case 13:
			case 4:
			case 42:
			case 33:
			case 24:
				return 2;
			case 1:
			case 10:
			case 3:
			case 30:
			case 41:
			case 32:
			case 23:
			case 14:
			case 43:
			case 34:
				return 3;
			case 22:
			case 44:
				return 4;
			default: return 5;
		}
	}


}
