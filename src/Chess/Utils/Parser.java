package Chess.Utils;

import Chess.Game.*;
import Chess.UI.GamePanel;
import Chess.UI.MainFrame;
import Chess.UI.UIData;

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
		if(string.equals("o-o-o"))
		{
			//System.out.println("Q-castle");
			//QUEEN SIDE CASTLING
			if(gm.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				return UIData.KING_QUEENSIDE_CASTLING_W;
			}
			else
			{
				return UIData.KING_QUEENSIDE_CASTLING_B;
			}
		}
		else if(string.equals("o-o"))
		{
			//System.out.println("K-castle");
			//KING SIDE CASTLING
			if(gm.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				return UIData.KING_KINGSIDE_CASTLING_W;
			}
			else
			{
				return UIData.KING_KINGSIDE_CASTLING_B;
			}
		}
		else if(string.length() != 5)
		{
			System.out.println(string);
			//System.out.println("invalid move (too long/short)");
		}
		else
		{
			//System.out.println("Entered parsing branch");
			file = chars[0] - 'a';
			rank = chars[1] - '1';
			source = ChessBoard.get0x88Index(file,rank);
			//System.out.println("[PARSER] Source: File: " + file + " Rank: " + rank);


			file = chars[3] - 'a';
			rank = chars[4] - '1';
			destination = ChessBoard.get0x88Index(file, rank);
			//System.out.println("[PARSER] Destination: File: " + file + " Rank: " + rank);

			move = new Move(source, destination,0);

			piece = gm.get(source);

			if (piece.getAllPossibleMoves().contains(move))
			{
				move = gm.setFlags(gm.getActiveColorByte(), move);
				//System.out.println("[PARSER] Checking move PIECE: " +piece.toString() + "MOVE: " + move.toString());

				if(gm.isLegalMove(piece, move))
				{
					//System.out.println("[Parser]: " + move.toString());
					return move;
				}
				else
				{
					System.out.println(string);
					//System.out.println("invalid move (no piece can move there)");
				}
			}
		}



		return null;
	}

	/**
	 * Generates algebraic notation of given Move in a String.
	 * @param move Move to be translated.
	 * @return String with algebraic notation of the Move.
	 */
	public static String moveToString (Move move)
	{
		String string = "";

		if(move.isKingCastle())
		{
			string = "o-o";
		}
		else if(move.isQueenCastle())
		{
			string = "o-o-o";
		}
		else
		{
			string += (char) (move.get2DSrc()[0] + 'a');
			string += Integer.toString(move.get2DSrc()[1] + 1);
			string += "-";
			string += (char) (move.get2DDst()[0] + 'a');
			string += Integer.toString(move.get2DDst()[1] + 1);
		}
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


		if(gp.getGameManager().getSettings().undoEnabled())
		{
			text += "1\n";
		}
		else
		{
			text += "0\n";
		}
		if(gp.getGameManager().getChronometer().isEnabled())
		{
			text += Long.toString( gp.getGameManager().getChronometer().getTimeWhite()) + "\n";
			text += Long.toString( gp.getGameManager().getChronometer().getTimeBlack()) + "\n";
		}
		else
		{
			text += "/\n";
		}

		for(Move move: history)
		{
			text += Parser.moveToString(move) + "\n";
		}

		int returnvalue = fc.showDialog(gp, "Save to...");

		if(returnvalue == JFileChooser.APPROVE_OPTION)
		{
			if(!text.endsWith(".pgn"))
			{
				file = new File(fc.getSelectedFile().getParent(), fc.getSelectedFile().getName()+ ".pgn");
			}
			else
			{
				file = new File(fc.getSelectedFile().getParent(), fc.getSelectedFile().getName());
			}

			try
			{
				writer = new PrintWriter(file);

				


				writer.print(text);
				writer.close();
			}
			catch (Exception e)
			{
				//System.out.println("file not found");
			}

		}
	}


	/**
	 * Reads moves from a file and the moves are made immediately.
	 */
	public static boolean readFromFile(GameManager gm, JFrame gp, MainFrame mf)
	{
		JFileChooser fc = new JFileChooser();

		int returnValue = fc.showDialog(gp, "Read from...");

		boolean succes = false;

		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fc.getSelectedFile())));
				String str;
				SettingsObject so = new SettingsObject();

				if( reader.readLine().equals("1"))
				{
					//System.out.println("Undo enabled via file");
					so.setUndo(true);
				}
				else
				{
					//System.out.println("Undo disabled via file");
					so.setUndo(false);
				}

				if((str = reader.readLine()).equals("/"))
				{
					//System.out.println("Chronometer disabled via file");
					gm.getChronometer().disable();
				}
				else
				{
					//System.out.println("Chronometer values set via file");
					so.setTimeMsW(Long.parseLong(str));
					so.setTimeMsB(Long.parseLong(reader.readLine()));
				}
				succes = true;
				gm.setSettings(so, mf.getGamePanel());
				while((str = reader.readLine()) != null && str.length() != 0)
				{
					//System.out.println("Read " + str);
					//System.out.println("[Parser] " + str);
					Move move = Parser.stringToMove(str, gm);
					//System.out.println("[Parser] " + move.toString());

					gm.makeMove(move);
				}

				//System.out.println("Done reading");

				reader.close();

			}
			catch(IOException ioe)
			{
				//ioe.printStackTrace();
			}
		}
		return succes;

	}
}
