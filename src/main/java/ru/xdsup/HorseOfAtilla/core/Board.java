package ru.xdsup.HorseOfAtilla.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xdsup.HorseOfAtilla.Utils;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;
import ru.xdsup.HorseOfAtilla.core.figures.Figure;
import ru.xdsup.HorseOfAtilla.core.figures.King;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class Board
{
	private static int WIDTH = 8;
	private static int HEIGHT = 8;

	private Coord startPosition;
	private Knight knight;
	private King king;
	private HashMap<Coord, Figure> fires = new HashMap<>();
	private Board prevState;
	private boolean kingDefeated;

	public void addFire(Figure figure){
		fires.put(figure.getCoords(), figure);
	}

	public boolean isAvailableCoord(Coord coord)
	{
		return coord.getX() >= 0 && coord.getX() < WIDTH && coord.getY() >= 0 && coord.getY() < HEIGHT;
	}

	public boolean isAvailableMove(Coord coord){
		return isAvailableCoord(coord) && !fires.containsKey(coord) || coord.equals(startPosition) && kingDefeated;
	}

	public Board moveKhignt(Coord newCoord){
		Board board = new Board();
		board.setStartPosition(startPosition);
		board.setFires(new HashMap<>(fires));
		board.addFire( new Figure(knight.getCoords()));
		board.setKnight(new Knight(newCoord));
		board.setKing(this.getKing());
		board.setKingDefeated(this.isKingDefeated());
		if(!board.kingDefeated){
			if(board.checkKingDefeat())
				board.kingDefeated = true;
		}
		board.setPrevState(this);
		return board;
	}

	public boolean checkKingDefeat(){
		return knight.getCoords().equals(king.getCoords());
	}

	public boolean isEndState(){
		return kingDefeated && knight.getCoords().equals(startPosition);
	}

	static public Board from(String str)
	{
		Board board = new Board();
		try (BufferedReader reader = new BufferedReader(new StringReader(str)))
		{
			for (int i = 0; i < HEIGHT; i++)
			{
				String line = reader.readLine();
				char[] chars = line.toCharArray();
				for (int j = 0; j < WIDTH; j++)
				{
					switch (chars[j])
					{
						case 'W':
						{
							board.setKing(new King(new Coord(j, i)));
							break;
						}
						case 'F':
						{
							board.addFire(new Figure(new Coord(j, i)));
							break;
						}
						case 'K':
						{
							board.setStartPosition(new Coord(j, i));
							board.setKnight(new Knight(new Coord(j, i)));
							break;
						}
						default:
							continue;
					}
				}
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return board;
	}

	@Override
	public String toString(){
		String coord = Utils.toChessNotation(knight.getCoords());
		String otherMoves =  prevState != null ? "-" + prevState.toString() : "";
		return coord + otherMoves;
	}

	public String toStrArray(){
		String chessNotation = toString();
		return "\"" + chessNotation.replace("-", "\", \"")+ "\"";
	}
}
