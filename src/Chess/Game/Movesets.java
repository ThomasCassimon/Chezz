package Chess.Game;

/**
 * @author Thomas
 * @since 22/02/2017
 * <p>
 * Project: ChessGame
 * Package: Chess.Game
 *
 * All moves are generated in a file-first fashion
 */
public final class Movesets
{
	/**
	 * Move-offsets for a Pawn
	 */
	public static final byte[][] PAWN_MOVE_WHITE =      {{0,1},		{0,2}};
	public static final byte[][] PAWN_MOVE_BLACK =      {{0,-1},	{0,-2}};
	public static final byte[][] PAWN_CAPTURE_WHITE = 	{{-1,1},	{1,1}};
	public static final byte[][] PAWN_CAPTURE_BLACK = 	{{-1,-1},	{1,-1}};

	/*
	 * Rook
	 */
	public static final byte[][] ROOK_MOVE = {  {0,1},  {0,2},  {0,3},  {0,4},  {0,5},  {0,6},  {0,7},  {0,8},          // Moving away from the white player
												{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7}, {0,-8},         // Moving towards the whute player
												{1,0},  {2,0},  {3,0},  {4,0},  {5,0},  {6,0},  {7,0},  {8,0},          // Moving to the white player's right
												{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}, {-8,0}  };      // Moving to the white player's left

	/*
	 * Knight
	 * Knight moves are first listed in an "up" (away from the white player) fashion (first 4 moves)
	 * 								Followed by 4 moves in a down fashion (away from the black player)
	 */

	public static final byte[][] KNIGHT_MOVE =	{	{-1, 2},	// 		_|
													{1,2},		//  		|_
													{-2,1},		// __|
													{2,1},		//		|__
													{-1,-2},	// Same as first
													{1,-2},		// Same as second
													{-2,-1},	// Same as third
													{2-1}};		// Same as fourth

	public static final byte [][] BISHOP_MOVE = {	{1,1},	{2,2},	{3,3},	{4,4},	{5,5},	{6,6},	{7,7},	{8,8},		// Bottom left to top right (from white's perspective)
													{-1,1},	{-2,2},	{-3,3},	{-4,4},	{-5,5},	{-6,6},	{-7,7},	{-8,8},		// Bottom right to top left (from white's perspective)
													{1,-1},	{2,-2},	{3,-3},	{4,-4},	{5,-5},	{6,-6},	{7,-7},	{8,-8},		// Top Left to bottom right (from white's perspective)
													{-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7},{-8,-8}};	// Top right to bottom left (from white's perspective)
}
