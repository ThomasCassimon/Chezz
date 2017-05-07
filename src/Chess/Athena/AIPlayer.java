package Chess.Athena;

import Chess.Game.*;
import Chess.Utils.Telemetry;

import java.util.ArrayList;

import static Chess.Game.PieceData.*;
import static java.lang.Integer.max;
import static java.lang.Integer.min;

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
	private static final int NullMoveReduction = 2;
	private int maxSearchDepth;
	private int maxNullSearchDepth;

	public AIPlayer(int colorByte, int maxSearchDepth)
	{
		super(colorByte);

		this.maxSearchDepth = maxSearchDepth;
		this.maxNullSearchDepth = maxSearchDepth;
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
	public Move playTurn(GameManager gm)
	{
		Telemetry.start();

		int score = 0;
		int maxScore = -inf;
		int maxIndex = -1;

		ArrayList<Move> moves = gm.getAllPseudoLegalMoves(this.colorByte);

		for (int i = 0; i < moves.size(); i++)
		{
			Telemetry.searchedNode(0);
			Move m = moves.get(i);

			GameManager gm_alt = new GameManager(gm);

			gm_alt.makeMove(m);

			score = alphaBeta(gm_alt, maxSearchDepth, -inf, inf, true, true);

			if (gm.isValidMove(gm.get(m.getSrc()).getPieceWithoutColorByte(), gm.getActiveColorByte(), m))
			{
				if (score > maxScore)
				{
					maxScore = score;
					maxIndex = i;
				}
			}
		}

		if (maxIndex >= 0)
		{
			Telemetry.stop();
			return moves.get(maxIndex);
		}
		else
		{
			Telemetry.stop();
			return new Move();
		}
	}

	private int alphaBeta (GameManager gm, int depth, int alpha, int beta, Boolean maximizing, Boolean allowNull)
	{
		int color = gm.getActiveColorByte();
		Telemetry.searchedNode(depth);
		System.out.println("Alpha: " + alpha + " beta: " + beta + " maximizing: " + maximizing);

		if ((depth == 0) || gm.isCheckMate(color))
		{
			return AIPlayer.scoreGame(gm, color);
		}

		if (maximizing)
		{
			int v = -inf;

			/* NULL MOVE PRUNING
			if (allowNull)
			{
				if (!gm.isCheckMate(color))
				{
					gm.toggleActivePlayer();
					GameManager.chronometer.toggle();

					v = max(v, this.alphaBeta(gm, depth - 1 - AIPlayer.NullMoveReduction, alpha, beta, false, false));
					alpha =

					gm.toggleActivePlayer();
					GameManager.chronometer.toggle();

					if (beta <= v)
					{
						return v;
					}
				}
			}
			// END NULL MOVE PRUNING
			*/

			ArrayList <Move> moves = gm.getAllPseudoLegalMoves(color);

			for (Move m : moves)
			{
				GameManager alt = new GameManager(gm);

				alt.makeMove(m);

				v = max(v, this.alphaBeta(alt, depth - 1, alpha, beta, false, true));
				alpha = max(v, alpha);

				if (beta <= alpha)
				{
					break;
				}
			}

			return v;

		}
		else
		{
			int v = inf;

			/* NULL MOVE PRUNING
			if (allowNull)
			{
				if (!gm.isCheckMate(color))
				{
					gm.toggleActivePlayer();
					GameManager.chronometer.toggle();

					v = this.alphaBeta(gm, depth - 1 - AIPlayer.NullMoveReduction, alpha, beta, true, false);

					gm.toggleActivePlayer();
					GameManager.chronometer.toggle();

					if (beta <= v)
					{
						return v;
					}
				}
			}
			// END NULL MOVE PRUNING
			*/

			ArrayList <Move> moves = gm.getAllPseudoLegalMoves(color);

			for (Move m : moves)
			{
				GameManager alt = new GameManager(gm);

				alt.makeMove(m);

				v = min(v, this.alphaBeta(alt, depth - 1, alpha, beta, true, true));
				beta = min(v, beta);

				if (beta <= alpha)
				{
					break;
				}
			}

			return v;
		}
	}
	/*
	private static int negaScout(GameManager gm, int depth, int alpha, int beta, int color)
	{
		if (depth == 0 || gm.isCheckMate(color))
		{
			// Do Quiescence search here
			return AIPlayer.scoreGame(gm, color);
		}

		System.out.println("Entered NegaScout: depth: " + depth + " alpha: " + alpha + " beta: " + beta);

		int d = depth - 1;

		ArrayList <Move> moves = gm.getAllPseudoLegalMoves(color);

		int opponent_color =PieceData.getOpponentColor(color);

		int b = beta;

		for (int i = 0; i < moves.size(); i++)
		{
			GameManager alt = gm.makeMove(moves.get(i));

			int score = -negaScout(alt, d, -b, -alpha, opponent_color);

			if ((alpha < score) && (score < beta) && (1 < i))
			{
				// Re-search
				score = -negaScout(alt, d, -beta,-alpha, opponent_color);
			}

			alpha = max(alpha, score);

			if (alpha >= beta)
			{
				return alpha;
			}

			b = alpha + 1;
		}

		return alpha;
	}
	*/
}
