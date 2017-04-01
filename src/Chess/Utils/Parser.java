package Chess.Utils;

import Chess.Game.*;


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
	public static Move stringToMove(String string, GameManager gm)
	{
		string = string.toLowerCase();
		char[] chars = string.toCharArray();
		int file, rank;
		int source, destination;
		Piece piece;
		Move move;


		//System.out.println("Parsing move: " + string);
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
			if(gm.getLegalMoves(piece).contains(move))
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

	/**
	 * Saves the history of the game to a file.
	 * @param history History of the Moves in the game.
	 */
	public void saveToFile(Move[] history)
	{

	}

	/**
	 * Generates algebraic notation of given Move in a String.
	 * @param move Move to be translated.
	 * @return String with algebraic notation of the Move.
	 */
	public static String moveToString(Move move)
	{
		String string = "";

		string += (char) (move.get2DSrc()[0] + 'a');
		string += Integer.toString(move.get2DSrc()[1] + 1);
		string += "-";
		string += (char) (move.get2DDst()[0] + 'a');
		string += Integer.toString(move.get2DDst()[1] + 1);

		return string;
	}


}
