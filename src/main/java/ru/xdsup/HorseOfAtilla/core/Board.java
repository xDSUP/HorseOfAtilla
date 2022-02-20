package ru.xdsup.HorseOfAtilla.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board
{
	private int width;
	private int height;
	private Figure[][] figures;

	public boolean isAvailableCoord(Coord coord)
	{
		return coord.getX() >= 0 && coord.getX() < width && coord.getY() >= 0 && coord.getY() < height;
	}

	static public Board from(int height, int width, String str)
	{
		Figure[][] figures = new Figure[height][];
		try (BufferedReader reader = new BufferedReader(new StringReader(str)))
		{
			for (int i = 0; i < height; i++)
			{
				figures[i] = new Figure[width];
				String line = reader.readLine();
				char[] chars = line.toCharArray();
				for (int j = 0; j < width; j++)
				{
					Figure figure;
					switch (chars[j])
					{
						case 'W':
						{
							figure = new King(new Coord(j, i));
							break;
						}
						case 'F':
						{
							figure = new Figure(new Coord(j, i));
							break;
						}
						case 'K':
						{
							figure = new Knight(new Coord(j, i));
							break;
						}
						default:
							figure = null;
					}
					figures[i][j] = figure;
				}
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return new Board(width, height, figures);
	}
}
