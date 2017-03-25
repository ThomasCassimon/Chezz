package Chess.Utils;

import Chess.Game.*;

/**
 * Created by Astrid on 22/03/2017.
 */
public class Parser
{
	public Parser()
	{

	}

	/**
	 * generates move from string with algebraic notation of move
	 * @param string with move
	 * @param gm gameManager that manages current game
	 * @return move generated move
	 */
	public static Move moveFromString(String string, GameManager gm)
	{
		string = string.toLowerCase();
		char[] chars = string.toCharArray();
		int file, rank;
		int source, destination;
		Piece piece;
		Move move;


		System.out.println("Parsing move: " + string);
		if(string == "o-o-o")
		{
			//QUEEN SIDE CASTLING
		}
		else if(string == "o-o")
		{
			//KING SIDE CASTLING
		}
		else if(string.length() != 5)
		{
			System.out.println("invalid move (too long/short)");
		}
		else
		{

			file = chars[0]-'a';
			rank = chars[1] - '1';
			source = ChessBoard.get0x88Index(file,rank);
			System.out.println("Source: File: " + file + " Rank: " + rank);


			file = chars[3] - 'a';
			rank = chars[4] - '1';
			destination = ChessBoard.get0x88Index(file, rank);
			System.out.println("Destination: File: " + file + " Rank: " + rank);

			move = new Move(source, destination,0);
			move = gm.setFlags(gm.getActiveColorByte(), move);

			piece = gm.get(source);
			System.out.println("Checking move PIECE: " +piece.toString() + "MOVE: " + move.toString());
			if(gm.isLegalMove(piece,move) & piece.getAllPossibleMoves().contains(move))
			{
				return move;
			}
			else
			{
				System.out.println("invalid move (no piece can move there)");
			}
		}

		return null;
	}
}
