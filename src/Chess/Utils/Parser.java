package Chess.Utils;

import Chess.Game.*;
import Chess.Time.Chronometer;
import Chess.UI.GamePanel;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


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
			//System.out.println("Source: File: " + file + " Rank: " + rank);


			file = chars[3] - 'a';
			rank = chars[4] - '1';
			destination = ChessBoard.get0x88Index(file, rank);
			//System.out.println("Destination: File: " + file + " Rank: " + rank);

			move = new Move(source, destination,0);
			move = gm.setFlags(gm.getActiveColorByte(), move);

			piece = gm.get(source);
			//System.out.println("Checking move PIECE: " +piece.toString() + "MOVE: " + move.toString());
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

	/**
	 * Saves the history of the game to a file.
	 * @param history History of the Moves in the game.
	 */
	public static void saveToFile(ArrayList<HistoryMove> history, GamePanel gp)
	{
		JFileChooser fc = new JFileChooser();
		File file;
		PrintWriter writer;
		String text ="";


		int returnvalue = fc.showDialog(gp, "Save to...");

		if(returnvalue == JFileChooser.APPROVE_OPTION)
		{
			file = new File(fc.getSelectedFile().getParent(), fc.getSelectedFile().getName()+ ".pgn");

			try
			{
				writer = new PrintWriter(file);

				

				for(Move move: history)
				{
					text += Parser.moveToString(move) + "\n";
				}
				writer.print(text);
				writer.close();
			}
			catch (Exception e)
			{
				System.out.println("file not found");
			}

		}
	}


	/**
	 * Reads moves from a file and the moves are made immediately.
	 */
	public static void readFromFile(GameManager gm, JFrame gp)
	{
		JFileChooser fc = new JFileChooser();

		int returnValue = fc.showDialog(gp, "Read from...");

		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fc.getSelectedFile())));
				String str;
				while((str =reader.readLine()) != null & str.length() != 0)
				{
					Move move = Parser.stringToMove(str, gm);
					gm.makeMove(move);
				}
			}
			catch(Exception e)
			{

			}
		}
	}
}
