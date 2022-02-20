package ru.xdsup.HorseOfAtilla.core;


import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Move
{
	private Coord pos;
	private Move prevMove;

	public Move(Coord pos)
	{
		this(pos, null);
	}

	@Override
	public String toString()
	{
		return "Move{" +
				"pos=" + pos +
				", prevMove=" + prevMove +
				'}';
	}

	public static Move append(Move prevMove, Coord coord){
		return new Move(coord, prevMove);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Move move = (Move) o;
		return Objects.equals(pos, move.pos) &&
				Objects.equals(prevMove, move.prevMove);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(pos, prevMove);
	}

	static String alphabet = "ABCDEFGH";

	public String toChessNotation(){

		char ch = alphabet.charAt(pos.getX());
		String coord = ch + "" +(8-pos.getY());
		String otherMoves =  prevMove != null ? "-" + prevMove.toChessNotation() : "";
		return coord + otherMoves;
	}

	public String[] toArray(){
		String chessNotation = toChessNotation();
		return chessNotation.split("-");
	}

	public String toStrArray(){
		String chessNotation = toChessNotation();
		return "\"" + chessNotation.replace("-", "\", \"")+ "\"";
	}
}
