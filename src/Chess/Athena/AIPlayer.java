package Chess.Athena;

import Chess.Game.*;

import java.util.ArrayList;

import static Chess.Game.PieceData.BOTH_BISHOPS_BONUS;
import static Chess.Game.PieceData.BOTH_KNIGHTS_PENALTY;
import static Chess.Game.PieceData.BOTH_ROOKS_PENALTY;
import static java.lang.Integer.max;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Athena
 */
public class AIPlayer extends Player
{
	private static final int inf = Integer.MAX_VALUE;

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
	 * @param gm The GameManager that contains the game that needs to be scored
	 * * @return an int, the score
	 */
	public int scoreGame (GameManager gm)
	{
		int score = 0;
		ArrayList<Piece> pieces = new ArrayList<Piece> (8);

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (gm.get(i,j).getColor() == colorByte)  // True if the piece's colorByte is identical to the player's
				{
					score += PieceData.getPieceScore(gm.get(i,j).getPieceWithoutColorByte());
					pieces.add(gm.get(i,j));
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
	public Move playTurn(GameManager gm, int searchDepth)
	{
		int score = 0;

		score = this.negaScout(gm, searchDepth, -inf, +inf, this.colorByte);

		return new Move();
	}

	private int negaScout(GameManager gm, int depth, int alpha, int beta, int colorOnTurn)
	{
		if ((gm.isCheckMate(PieceData.getOpponentColor(colorOnTurn))) || (depth == 0))
		{
			return this.scoreGame(gm);
		}
		else
		{
			int score = 0;
			ArrayList <Move> moves = gm.getAllMoves(colorOnTurn);

			for (int i = 0; i < moves.size(); i++)
			{
				if (i != 0)
				{
					GameManager possibility = gm;
					possibility.makeMove(moves.get(i));

					score = -this.negaScout(possibility, depth - 1, -alpha-1, -alpha, PieceData.getOpponentColor(colorOnTurn));

					if ((alpha < score) && (score < beta))
					{
						score = -this.negaScout(possibility, depth -1, -beta, -score, PieceData.getOpponentColor(colorOnTurn));
					}
					else
					{
						score = -this.negaScout(possibility, depth - 1, -beta, -alpha, PieceData.getOpponentColor(colorOnTurn));
					}

					alpha = max(alpha, score);

					if (alpha >= beta)
					{
						break;
					}

				}
			}

			return alpha;
		}
	}
}
