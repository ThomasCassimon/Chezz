package Chess.Game;

import Chess.Athena.AIPlayer;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * @author Thomas
 * @since 21/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */
public class GameManager
{
	/**
	 * Stores the players, index 0 = White, index 1 = Black
	 */
	private Player[] players;
	private byte activeColor;
	private ChessBoard cb;

	public GameManager ()
	{
		this.cb = new ChessBoard();
		this.activeColor = PieceData.WHITE_BYTE;
		this.players = new Player [2];
	}

	/**
	 * Sets the white player's type
	 * WARNING: This resets the player, an AI player will lose it's stored data like hashtables and search trees
	 * @param human true sets the player to be human, false sets the player to be AI-controlled
	 */
	public void setWhitePlayerType (boolean human)
	{
		if (human)
		{
			this.players[0] = new HumanPlayer (PieceData.WHITE_BYTE);
		}
		else
		{
			this.players[0] = new AIPlayer (PieceData.WHITE_BYTE);
		}
	}

	/**
	 * Sets the black player's type
	 * WARNING: This resets the player, an AI player will lose it's stored data like hashtables and search trees
	 * @param human true sets the player to be human, false sets the player to be AI-controlled
	 */
	public void setBlackPlayerType (boolean human)
	{
		if (human)
		{
			this.players[1] = new HumanPlayer (PieceData.BLACK_BYTE);
		}
		else
		{
			this.players[1] = new AIPlayer (PieceData.BLACK_BYTE);
		}
	}

	/**
	 * Toggles the active player
	 */
	public void toggleActivePlayer ()
	{
		if (this.activeColor == PieceData.WHITE_BYTE)
		{
			this.activeColor = (byte) (this.activeColor << 1);
		}
		else
		{
			this.activeColor = (byte) (this.activeColor >> 1);
		}
	}

	/**
	 * Returns the color byte of the currently active player
	 * @return the color byte of the player who's turn it is
	 */
	public byte getActiveColorByte ()
	{
		return this.activeColor;
	}

	/**
	 * Initializes the game to it's starting position/situation
	 */
	public void init ()
	{
		this.cb.init();
		this.activeColor = PieceData.WHITE_BYTE;
		this.players[0] = new HumanPlayer(PieceData.WHITE_BYTE);
		this.players[1] = new HumanPlayer(PieceData.BLACK_BYTE);
	}

	/**
	 * Returns the piece at the specified file and rank
	 * @param file	The file of the piece
	 * @param rank	The rank of the piece
	 * @return	The piece at coordinate file-rank
	 */
	public Piece get (byte file, byte rank)
	{
		return new Piece(this.cb.get(file, rank), ChessBoard.get0x88Index(file, rank));
	}

	/**
	 * Returns an array of all pieces belonging to the white player
	 * @return	All white pieces
	 */
	public ArrayList<Piece> getAllWhitePieces ()
	{
		ArrayList<Piece> pieces = new ArrayList <Piece> (16);

		for (byte i = 1; i <= 8; i++)
		{
			for (byte j = 1; j <= 8; j++)
			{
				if ((this.cb.get(i,j) & PieceData.WHITE_BYTE) != 0)
				{
					pieces.add(new Piece (this.cb.get(i,j), i, j));
				}
			}
		}

		return pieces;
	}

	/**
	 * Returns an array of all pieces belonging to the Black player
	 * @return	All black pieces
	 */
	public ArrayList<Piece> getAllBlackPieces ()
	{
		ArrayList<Piece> pieces = new ArrayList <Piece> (16);

		for (byte i = 1; i <= 8; i++)
		{
			for (byte j = 1; j <= 8; j++)
			{
				if ((this.cb.get(i,j) & PieceData.BLACK_BYTE) != 0)
				{
					pieces.add(new Piece (this.cb.get(i,j), i, j));
				}
			}
		}

		return pieces;
	}

	public ArrayList<Move> getAllValidMoves (Piece p)
	{
		byte piece = p.getPieceWithoutColorByte();
		byte color = p.getColorByte();
		ArrayList <Move> possibleMoves = p.getAllPossibleMoves();

		for (byte i = 0; i < possibleMoves.size(); i++)
		{
			Move m = possibleMoves.get(i);
			byte src = m.getSrc();
			byte dst = m.getDst();
			byte deltaRank = (byte) abs((src >> 4) - (dst >> 4));
			byte deltaFile = (byte) abs((src & 7) - (dst & 7));

			if ((this.cb.get(dst) & (PieceData.BLACK_BYTE | PieceData.WHITE_BYTE)) == (color))
			{
				possibleMoves.remove(i);
				i--;
				continue;
			}
			else if ((this.cb.get(dst) & (PieceData.BLACK_BYTE | PieceData.WHITE_BYTE)) == (PieceData.getOpponentColorByte(color)))
			{
				possibleMoves.set(i, m.setSpecial((byte) (m.getSpecial() | Move.CAPTURE_MASK)));
			}

			switch (piece)
			{
				case PieceData.PAWN_BYTE:
					// Checking for pieces along move path
					if (deltaRank != 1)
					{

					}
					break;
				case PieceData.ROOK_BYTE:
					// Checking for pieces along the move path

					if (deltaRank != 0)
					{
						for (byte j = 1; j <= deltaRank; j++)
						{
							if (this.cb.get((byte) (src + (j * 0x10))) != 0)
							{
								possibleMoves.remove(i);
								i--;
								break;
							}
						}
					}
					else if (deltaFile != 0)
					{
						for (byte j = 1; j <= deltaFile; j++)
						{
							if (this.cb.get((byte) (src + j)) != 0)
							{
								possibleMoves.remove(i);
								i--;
								break;
							}
						}
					}
					else
					{
						possibleMoves.remove(i);
						i--;
					}
					break;
			}
		}

		return possibleMoves;
	}

	/**
	 * Makes the specified move
	 * @param m The move to be made
	 */
	public void makeMove (Move m)
	{
		this.cb.set(m.getDst(), this.cb.get(m.getSrc()));
		this.cb.set(m.getSrc(), (byte) 0x0);	// Empty the source square
	}
}
