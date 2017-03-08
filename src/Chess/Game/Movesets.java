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
	//public static final int[][] PAWN_MOVE_WHITE =      {{0,1},		{0,2}};
	public static final int[] PAWN_MOVE_WHITE = 	{0x10,	0x20};
	//public static final int[][] PAWN_MOVE_BLACK =      {{0,-1},	{0,-2}};
	public static final int[] PAWN_MOVE_BLACK =	{-0x10, -0x20};

	/*
	 * Rook

	public static final int[][] ROOK_MOVE = {  {0,1},  {0,2},  {0,3},  {0,4},  {0,5},  {0,6},  {0,7},  {0,8},          // Moving away from the white player
												{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7}, {0,-8},         // Moving towards the white player
												{1,0},  {2,0},  {3,0},  {4,0},  {5,0},  {6,0},  {7,0},  {8,0},          // Moving to the white player's right
												{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}, {-8,0}  };		// Moving to the white player's left
	*/

	public static final int[] ROOK_MOVE =	{	0x10,	0x20,	0x30,	0x40,	0x50,	0x60,	0x70,	 0x80,
												-0x10,	-0x20,	-0x30,	-0x40,	-0x50,	-0x60,	-0x70,	-0x80,
												0x01,	0x02,	0x03,	0x04,	0x05,	0x06,	0x07,	0x08,
												-0x01,	-0x02,	-0x03,	-0x04,	-0x05,	-0x06,	-0x07,	-0x08};

	/*
	 * Knight
	 * Knight moves are first listed in an "up" (away from the white player) fashion (first 4 moves)
	 * 								Followed by 4 moves in a down fashion (away from the black player)

	public static final int[][] KNIGHT_MOVE =	{	{-1,2},		// 		_|
													{1,2},		//  		|_
													{-2,1},		// __|
													{2,1},		//		|__
													{-1,-2},	// Same as first
													{1,-2},		// Same as second
													{-2,-1},	// Same as third
													{2,-1}};		// Same as fourth
	*/
	public static final int[] KNIGHT_MOVE =	{	0x21,	0x1F,		// |_ _|	Up
													0x0E,	0x12,		// |__ __|	Up
													-0x21,	-0x1F,		// |_ _|	Down
													-0x0E,	-0x12};		// |__ __|	Down
	/*
	 * Bishop

	public static final int [][] BISHOP_MOVE = {	{1,1},	{2,2},	{3,3},	{4,4},	{5,5},	{6,6},	{7,7},	{8,8},		// Bottom left to top right (from white's perspective)
													{-1,1},	{-2,2},	{-3,3},	{-4,4},	{-5,5},	{-6,6},	{-7,7},	{-8,8},		// Bottom right to top left (from white's perspective)
													{1,-1},	{2,-2},	{3,-3},	{4,-4},	{5,-5},	{6,-6},	{7,-7},	{8,-8},		// Top Left to bottom right (from white's perspective)
													{-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7},{-8,-8}};	// Top right to bottom left (from white's perspective)
	*/
	public static final int[] BISHOP_MOVE =	{	0x11,	0x22,	0x33,	0x44,	0x55,	0x66,	0x77,	0x88,	// Bottom Left to top right
												0x0F,	0x1E,	0x2D,	0x3C,	0x4B,	0x5A,	0x69,	0x78,	// Bottom right to top left
												-0x11,	-0x22,	-0x33,	-0x44,	-0x55,	-0x66,	-0x77,	-0x88,	// Top right to bottom left
												-0x0F,	-0x1E,	-0x2D,	-0x3C,	-0x4B,	-0x5A,	-0x69,	-0x78};	// Top left to bottom right

	/*
	 * Queen = Rook + Bishop

	public static final int [][] QUEEN_MOVE =	{	{0,1},  {0,2},  {0,3},  {0,4},  {0,5},  {0,6},  {0,7},  {0,8},			// Moving away from the white player
													{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7}, {0,-8},         // Moving towards the white player
													{1,0},  {2,0},  {3,0},  {4,0},  {5,0},  {6,0},  {7,0},  {8,0},          // Moving to the white player's right
													{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}, {-8,0},		    // Moving to the white player's left
													{1,1},	{2,2},	{3,3},	{4,4},	{5,5},	{6,6},	{7,7},	{8,8},			// Bottom left to top right (from white's perspective)
													{-1,1},	{-2,2},	{-3,3},	{-4,4},	{-5,5},	{-6,6},	{-7,7},	{-8,8},			// Bottom right to top left (from white's perspective)
													{1,-1},	{2,-2},	{3,-3},	{4,-4},	{5,-5},	{6,-6},	{7,-7},	{8,-8},			// Top Left to bottom right (from white's perspective)
													{-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7},{-8,-8}};		// Top right to bottom left (from white's perspective)
	*/
	public static final int[] QUEEN_MOVE = 	{	0x10,	0x20,	0x30,	0x40,	0x50,	0x60,	0x70,	 0x80,
													-0x10,	-0x20,	-0x30,	-0x40,	-0x50,	-0x60,	-0x70,	-0x80,
													0x01,	0x02,	0x03,	0x04,	0x05,	0x06,	0x07,	0x08,
													-0x01,	-0x02,	-0x03,	-0x04,	-0x05,	-0x06,	-0x07,	-0x08,
													0x11,	0x22,	0x33,	0x44,	0x55,	0x66,	0x77,	 0x88,	// Bottom Left to top right
													0x0F,	0x1E,	0x2D,	0x3C,	0x4B,	0x5A,	0x69,	 0x78,	// Bottom right to top left
													-0x11,	-0x22,	-0x33,	-0x44,	-0x55,	-0x66,	-0x77,	 -0x88,	// Top right to bottom left
													-0x0F,	-0x1E,	-0x2D,	-0x3C,	-0x4B,	-0x5A,	-0x69,	 -0x78};	// Top left to bottom right

	/*
	public static final int [][] KING_MOVE =		{	{0,1},		// Away from white
												{0,-1},		// Closer to white
												{1,0},		// To the right from white's perspective
												{-1,0},		// To the left from white's perspective
												{1,1},		// Away and to the right from white's view
												{-1,1},		// Away and to the left from white's view
												{1,-1},		// Closer and to the right from white's view
												{-1,-1}};	// Closer and to the left from white's view
	*/
	public static final int[] KING_MOVE =	{	0x10,	-0x10,	0x01,	-0x01,
												0x11,	0x0F,	-0x11,	-0x0F};
}
