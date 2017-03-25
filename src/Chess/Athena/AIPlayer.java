package Chess.Athena;

import Chess.Game.*;

import java.util.ArrayList;

import static Chess.Game.PieceData.*;
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
	public static int scoreGame (GameManager gm, int color)
	{
		int score = 0;
		ArrayList<Piece> pieces = new ArrayList<> (16);

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (gm.get(i,j).getColor() == color)  // True if the piece's colorByte is identical to the player's
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
			if (p.getPieceWithoutColorByte() == (PieceData.BISHOP_BYTE | color))
			{
				bishopCount++;
			}

			if (p.getPieceWithoutColorByte() == (PieceData.ROOK_BYTE | color))
			{
				rookCount++;
			}

			if (p.getPieceWithoutColorByte() == (PieceData.KNIGHT_BYTE | color))
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
		int maxScore = -inf;
		int maxIndex = -1;

		ArrayList<Move> moves = new ArrayList<>();

		for (Piece p : gm.getAllPieces(this.colorByte))
		{
			moves.addAll(gm.getLegalMoves(p));
		}

		//System.out.println("Found " + Integer.toString(moves.size()) + " pieces");

		for (int i = 0; i < moves.size(); i++)
		{
			Move m = moves.get(i);

			GameManager gm_alt = new GameManager(gm);
			gm_alt.makeMove(m);

			score = negaScout(gm_alt, searchDepth, -inf, inf, this.colorByte);

			if (score > maxScore)
			{
				maxScore = score;
				maxIndex = i;
			}
		}

		if (maxIndex >= 0)
		{
			return moves.get(maxIndex);
		}
		else
		{
			return new Move();
		}
	}

	private static int negaScout(GameManager gm, int depth, int alpha, int beta, int color)
	{
		if (gm.isCheckMate(color) || depth == 0)
		{
			//System.out.println("checkmate: " + gm.isCheckMate(color));
			/*
			TableRecord tr = null;
			if ((tr = TranspositionTable.getInstance().get(gm.getHash())) != null)
			{
				System.out.println("Table hit");
				return tr.getScore();
			}
			else
			{*/
				int score = AIPlayer.scoreGame(gm, color);
				//tr = new TableRecord(score, depth, gm.getAllLegalMoves(color));
				//TranspositionTable.getInstance().put(gm.getHash(), tr);
				return score;
			//}
		}
		else
		{
			int score = 0;
			ArrayList <Move> opponentMoves = gm.getAllLegalMoves(PieceData.getOpponentColor(color));

			for (int i = 0; i < opponentMoves.size(); i++)
			{
				Move m = opponentMoves.get(i);

				GameManager gm_alt = new GameManager(gm);
				gm_alt.makeMove(m);

				if (i != 0)
				{
					score = -negaScout(gm_alt, depth - 1, -alpha-1, -alpha, PieceData.getOpponentColor(color));

					if ((alpha < score) && (score < beta))
					{
						score = -negaScout(gm_alt, depth - 1, -beta, -score, PieceData.getOpponentColor(color));
					}
				}
				else
				{
					score = -negaScout(gm_alt, depth-1, -beta, -alpha, PieceData.getOpponentColor(color));
				}

				alpha = max(alpha, score);

				if (alpha >= beta)
				{
					break;
				}
			}

			return alpha;
		}
	}
}
