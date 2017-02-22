package Chess.Game;

import Chess.Athena.AIPlayer;

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
		this.activeColor = PieceData.WHITE_MASK;
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
			this.players[0] = new HumanPlayer (PieceData.WHITE_MASK);
		}
		else
		{
			this.players[0] = new AIPlayer (PieceData.WHITE_MASK);
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
			this.players[1] = new HumanPlayer (PieceData.BLACK_MASK);
		}
		else
		{
			this.players[1] = new AIPlayer (PieceData.BLACK_MASK);
		}
	}

	public void toggleActivePlayer ()
	{
		if (this.activeColor == PieceData.WHITE_MASK)
		{
			this.activeColor = PieceData.BLACK_MASK;
		}
		else
		{
			this.activeColor = PieceData.WHITE_MASK;
		}
	}

	public byte getActiveColorByte ()
	{
		return this.activeColor;
	}

	// todo set active color to white
	// todo reset players
	public void init ()
	{
		this.cb.init();
	}

	public Piece get (byte file, byte rank)
	{
		return new Piece(this.cb.get(file, rank), ChessBoard.get0x88Index(file, rank));
	}
}
