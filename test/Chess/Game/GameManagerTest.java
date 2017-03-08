package Chess.Game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Created by thomas on 04/03/17.
 */

//todo: Add unit tests for following methods: setCachedMoves(), getCachedMoves(), setWhitePlayerType(), setBlackPlayerType(), getMoveHistory(), undo(), isCheckMate(), isCheck(), getKing()

public class GameManagerTest
{
	@Test
	public void toggleActivePlayer() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();

		assertEquals(PieceData.WHITE_BYTE, gm.getActiveColorByte());
		gm.toggleActivePlayer();
		assertEquals(PieceData.BLACK_BYTE, gm.getActiveColorByte());
	}

	@Test
	public void getActiveColorByte() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();

		assertEquals(PieceData.WHITE_BYTE, gm.getActiveColorByte());
	}

	@Test
	public void get() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();

		for (int i = 1; i <= 8; i++)
		{
			// White Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, i, 2), gm.get(i, 2));

			for (int j = 3; j < 7; j++)
			{
				assertEquals(new Piece (0,i,j), gm.get(i, j));
			}

			// Black Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, i, 7), gm.get(i, 7));
		}

		// White Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(1,1)), gm.get( 1,  1));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(8,1)), gm.get( 8,  1));

		// Black Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(1,8)), gm.get( 1,  8));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(8,8)), gm.get( 8,  8));

		// White Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(2,1)), gm.get( 2,  1));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(7,1)), gm.get( 7,  1));

		// Black Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(2,8)), gm.get( 2,  8));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(7,8)), gm.get( 7,  8));

		// White Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(3,1)), gm.get( 3,  1));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(6,1)), gm.get( 6,  1));

		// Black Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(3,8)), gm.get( 3,  8));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(6,8)), gm.get( 6,  8));

		// White Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(5,1)), gm.get( 5,  1));

		// Black Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(5,8)), gm.get( 5,  8));

		// White King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(4,1)), gm.get( 4,  1));

		// Black King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(4,8)), gm.get( 4,  8));
	}

	@Test
	public void getAllPiecesWhite() throws Exception
	{
		ArrayList<Piece> expectedPieces = new ArrayList<Piece>();

		// Pawns
		for (int i = 1; i <= 8; i++)
		{
			expectedPieces.add(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(i,2)));
		}

		// Rooks
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(1,1)));
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(8,1)));

		// Knights
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(2,1)));
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(7,1)));

		// Bishops
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(3,1)));
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(6,1)));

		// Queen
		expectedPieces.add(new Piece (PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(5,1)));

		// King
		expectedPieces.add(new Piece (PieceData.KING_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(4,1)));

		GameManager gm = new GameManager();
		gm.init();

		assertThat("comparing piecelists", gm.getAllPieces(PieceData.WHITE_BYTE), containsInAnyOrder(expectedPieces.toArray()));
	}

	@Test
	public void getAllPiecesBlack() throws Exception
	{
		ArrayList<Piece> expectedPieces = new ArrayList<Piece>();

		// Pawns
		for (int i = 1; i <= 8; i++)
		{
			expectedPieces.add(new Piece (PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(i,7)));
		}

		// Rooks
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(1,8)));
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(8,8)));

		// Knights
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(2,8)));
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(7,8)));

		// Bishops
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(3,8)));
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(6,8)));

		// Queen
		expectedPieces.add(new Piece (PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(5,8)));

		// King
		expectedPieces.add(new Piece (PieceData.KING_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(4,8)));

		GameManager gm = new GameManager();
		gm.init();

		assertThat("comparing piecelists", gm.getAllPieces(PieceData.BLACK_BYTE), containsInAnyOrder(expectedPieces.toArray()));
	}

	@Test
	public void getAllValidPawnMoves() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();
		Piece pawn = gm.get(4,2);
		ArrayList <Move> expectedMoves = new ArrayList<Move>();

		// Moves from initial position
		expectedMoves.add(new Move(4,2,4,3,0x0));
		expectedMoves.add(new Move(4,2,4,4, Move.DOUBLE_PAWN_PUSH_MASK));

		assertThat("comparing movelists for white pawn", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));

		pawn = gm.get(5,7);
		expectedMoves = new ArrayList<Move>();

		// Moves from initial position
		expectedMoves.add(new Move(5,7,5,6, 0x0));
		expectedMoves.add(new Move(5,7,5,5, Move.DOUBLE_PAWN_PUSH_MASK));

		assertThat("comparing movelists for black pawn", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));

		// Moved pawns in opposing, capturable positions
		gm.makeMove(new Move (5, 7, 5, 5, Move.DOUBLE_PAWN_PUSH_MASK));
		gm.makeMove(new Move (4, 2, 4, 4, Move.DOUBLE_PAWN_PUSH_MASK));

		System.out.println("Piece at (5,5): " + gm.get(5,5).toString());
		System.out.println("Piece at (4,4): " + gm.get(4,4).toString());

		pawn = gm.get(4,4);
		expectedMoves = new ArrayList<Move>();

		System.out.println("Checking white pawn: " + pawn.toString());

		// Moves from initial position
		expectedMoves.add(new Move(4, 4, 4, 5, 0x0));
		expectedMoves.add(new Move(4, 4, 5, 5, Move.CAPTURE_MASK));

		assertThat("comparing movelists for white pawn, capture possible", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));

		pawn = gm.get(5,5);
		expectedMoves = new ArrayList<Move>();

		System.out.println("Checking black pawn: " + pawn.toString());

		// Moves from initial position
		expectedMoves.add(new Move(5,5,5,4, 0x0));
		expectedMoves.add(new Move(5,5,4,4, Move.CAPTURE_MASK));

		assertThat("comparing movelists for black pawn, capture possible", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));
	}

	@Test
	public void makeMove() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();
		Move m = new Move (1,2,1,4,Move.DOUBLE_PAWN_PUSH_MASK);
		gm.makeMove(m);

		assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, 1,4), gm.get(1,4));
	}

	@Test
	public void getLastMove() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();
		Move m = new Move (1,2,1,4,Move.DOUBLE_PAWN_PUSH_MASK);
		gm.makeMove(m);

		assertEquals(m, gm.getLastMove());

		m = new Move (8, 2,8,4,Move.DOUBLE_PAWN_PUSH_MASK);
		gm.makeMove(m);

		assertEquals(m, gm.getLastMove());
	}

	/*
	@Test
	public void getAllMoves() throws Exception
	{
		ArrayList <Move> moves = new ArrayList<Move> ();
	}
	*/

	@Test
	public void init() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();

		for (int i = 1; i <= 8; i++)
		{
			// White Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, i, 2), gm.get(i, 2));

			for (int j = 3; j < 7; j++)
			{
				assertEquals(new Piece (0,i,j), gm.get(i, j));
			}

			// Black Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, i, 7), gm.get(i, 7));
		}

		// White Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(1,1)), gm.get( 1,  1));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(8,1)), gm.get( 8,  1));

		// Black Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(1,8)), gm.get( 1,  8));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(8,8)), gm.get( 8,  8));

		// White Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(2,1)), gm.get( 2,  1));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(7,1)), gm.get( 7,  1));

		// Black Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(2,8)), gm.get( 2,  8));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(7,8)), gm.get( 7,  8));

		// White Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(3,1)), gm.get( 3,  1));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(6,1)), gm.get( 6,  1));

		// Black Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(3,8)), gm.get( 3,  8));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(6,8)), gm.get( 6,  8));

		// White Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(5,1)), gm.get( 5,  1));

		// Black Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(5,8)), gm.get( 5,  8));

		// White King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(4,1)), gm.get( 4,  1));

		// Black King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(4,8)), gm.get( 4,  8));
	}
}