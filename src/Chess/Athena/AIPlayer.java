package Chess.Athena;

import Chess.Game.*;

import java.util.ArrayList;

import static Chess.Game.PieceData.BOTH_BISHOPS_BONUS;
import static Chess.Game.PieceData.BOTH_KNIGHTS_PENALTY;
import static Chess.Game.PieceData.BOTH_ROOKS_PENALTY;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Athena
 */
public class AIPlayer extends Player
{
	public AIPlayer(int colorByte)
	{
		super(colorByte);
	}

	/**
	 * Generates an integer score, taking into account the following factors:
	 *      Own pieces alive
	 *      Number of bishops alive (having both bishops results in +50)
	 *      Number of Rooks alive (having both rooks results in -30)
	 *      Number of Knights alive (having both knights results in -15)
	 * @param cb The board to be scored
	 * @return an int, the score
	 */
	public int scoreGame (ChessBoard cb)
	{
		int score = 0;
		ArrayList<Piece> pieces = new ArrayList<Piece> (8);

		for (byte i = 1; i <= 8; i++)
		{
			for (byte j = 1; j <= 8; j++)
			{
				if ((cb.get(i,j) & 0x30) == colorByte)  // True if the piece's colorByte is identical to the player's
				{
					score += PieceData.getPieceScore((byte) (cb.get(i,j) & 0x07));
					pieces.add(new Piece(cb.get(i,j), ChessBoard.get0x88Index(i,j)));
				}
			}
		}

		int bishopCount = 0;
		int rookCount = 0;
		int knightCount = 0;

		for (Piece p : pieces)
		{
			if (p.getPieceByte() == (PieceData.BISHOP_BYTE | this.colorByte))
			{
				bishopCount++;
			}

			if (p.getPieceByte() == (PieceData.ROOK_BYTE | this.colorByte))
			{
				rookCount++;
			}

			if (p.getPieceByte() == (PieceData.KNIGHT_BYTE | this.colorByte))
			{
				knightCount++;
			}
		}

		if (bishopCount == 2)
		{
			score += BOTH_BISHOPS_BONUS;
		}

		if (rookCount == 2)
		{
			score += BOTH_ROOKS_PENALTY;
		}

		if (knightCount == 2)
		{
			score += BOTH_KNIGHTS_PENALTY;
		}

		return score;
	}

	/**
	 * Makes the AI play it's turn
	 * @return The chosen move
	 */
	@Override
	public Move playTurn()
	{
		return new Move();
	}
}
