package Chess.Game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;


//import static org.hamcrest.Matchers.containsInAnyOrder;
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

		for (int i = 0; i < 8; i++)
		{
			// White Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, i, 1), gm.get(i, 1));

			for (int j = 2; j < 5; j++)
			{
				assertEquals(new Piece (0,i,j), gm.get(i, j));
			}

			// Black Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, i, 6), gm.get(i, 6));
		}

		// White Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(0, 0)), gm.get(0, 0));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(7, 0)), gm.get(7, 0));

		// Black Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(0, 7)), gm.get(0,  7));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(7, 7)), gm.get(7,  7));

		// White Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(1, 0)), gm.get(1, 0));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(6, 0)), gm.get(6, 0));

		// Black Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(1, 7)), gm.get(1, 7));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(6, 7)), gm.get(6, 7));

		// White Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(2, 0)), gm.get(2, 0));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(5, 0)), gm.get(5, 0));

		// Black Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(2, 7)), gm.get(2, 7));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(5, 7)), gm.get(5, 7));

		// White Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(4, 0)), gm.get(4, 0));

		// Black Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(4, 7)), gm.get(4, 7));

		// White King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(3, 0)), gm.get(3, 0));

		// Black King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(3, 7)), gm.get(3, 7));
	}

	@Test
	public void getAllPiecesWhite() throws Exception
	{
		ArrayList<Piece> expectedPieces = new ArrayList<Piece>();

		// Pawns
		for (int i = 0; i < 8; i++)
		{
			expectedPieces.add(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(i,1)));
		}

		// Rooks
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(0, 0)));
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(7, 0)));

		// Knights
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(1, 0)));
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(6, 0)));

		// Bishops
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(2, 0)));
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(5, 0)));

		// Queen
		expectedPieces.add(new Piece (PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(4, 0)));

		// King
		expectedPieces.add(new Piece (PieceData.KING_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(3, 0)));

		GameManager gm = new GameManager();
		gm.init();

//		assertThat("comparing piecelists", gm.getAllPieces(PieceData.WHITE_BYTE), containsInAnyOrder(expectedPieces.toArray()));
	}

	@Test
	public void getAllPiecesBlack() throws Exception
	{
		ArrayList<Piece> expectedPieces = new ArrayList<Piece>();

		// Pawns
		for (int i = 0; i < 8; i++)
		{
			expectedPieces.add(new Piece (PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(i,6)));
		}

		// Rooks
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(0, 7)));
		expectedPieces.add(new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(7, 7)));

		// Knights
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(1, 7)));
		expectedPieces.add(new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(6, 7)));

		// Bishops
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(2, 7)));
		expectedPieces.add(new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(5, 7)));

		// Queen
		expectedPieces.add(new Piece (PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(4, 7)));

		// King
		expectedPieces.add(new Piece (PieceData.KING_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(3, 7)));

		GameManager gm = new GameManager();
		gm.init();

//		assertThat("comparing piecelists", gm.getAllPieces(PieceData.BLACK_BYTE), containsInAnyOrder(expectedPieces.toArray()));
	}

	@Test
	public void getAllValidPawnMoves() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();
		Piece pawn = gm.get(3,1);
		ArrayList <Move> expectedMoves = new ArrayList<Move>();

		// Moves from initial position
		expectedMoves.add(new Move(3, 1, 3, 2, 0x0));
		expectedMoves.add(new Move(3, 1, 3, 3, Move.DOUBLE_PAWN_PUSH_MASK));

		assertThat("comparing movelists for white pawn", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));

		pawn = gm.get(4,6);
		expectedMoves = new ArrayList<Move>();

		// Moves from initial position
		expectedMoves.add(new Move(4, 6, 4, 5, 0x0));
		expectedMoves.add(new Move(4, 6, 4, 4, Move.DOUBLE_PAWN_PUSH_MASK));

		assertThat("comparing movelists for black pawn", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));

		// Moved pawns in opposing, capturable positions
		gm.makeMove(new Move (4, 6, 4, 4, Move.DOUBLE_PAWN_PUSH_MASK));
		gm.makeMove(new Move (3, 1, 3, 3, Move.DOUBLE_PAWN_PUSH_MASK));

		System.out.println("Piece at (4,4): " + gm.get(4,4).toString());
		System.out.println("Piece at (3,3): " + gm.get(3,3).toString());

		pawn = gm.get(3,3);
		expectedMoves = new ArrayList<Move>();

		System.out.println("Checking white pawn: " + pawn.toString());

		// Moves from initial position
		expectedMoves.add(new Move(3, 3, 3, 4, 0x0));
		expectedMoves.add(new Move(3, 3, 4, 4, Move.CAPTURE_MASK));

		assertThat("comparing movelists for white pawn, capture possible", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));

		pawn = gm.get(4,4);
		expectedMoves = new ArrayList<Move>();

		System.out.println("Checking black pawn: " + pawn.toString());

		// Moves from initial position
		expectedMoves.add(new Move(4, 4, 4, 3, 0x0));
		expectedMoves.add(new Move(4, 4, 3, 3, Move.CAPTURE_MASK));

		assertThat("comparing movelists for black pawn, capture possible", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));
//		assertThat("comparing movelists", gm.getAllValidMoves(pawn), containsInAnyOrder(expectedMoves.toArray()));
	}

	@Test
	public void makeMove() throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();
		Move m = new Move (0,1,0,3,Move.DOUBLE_PAWN_PUSH_MASK);
		gm.makeMove(m);

		assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, 0,3), gm.get(0,3));
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

		for (int i = 0; i < 8; i++)
		{
			// White Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, i, 1), gm.get(i, 1));

			for (int j = 2; j < 6; j++)
			{
				assertEquals(new Piece (0, i, j), gm.get(i, j));
			}

			// Black Pawns
			assertEquals(new Piece (PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, i, 6), gm.get(i, 6));
		}

		// White Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(0, 0)), gm.get(0, 0));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(7, 0)), gm.get(7, 0));

		// Black Rooks
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(0, 7)), gm.get( 0, 7));
		assertEquals (new Piece (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(7, 7)), gm.get( 7, 7));

		// White Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(1, 0)), gm.get(1, 0));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(6, 0)), gm.get(6, 0));

		// Black Knights
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(1, 7)), gm.get(1, 7));
		assertEquals (new Piece (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(6, 7)), gm.get(6, 7));

		// White Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(2, 0)), gm.get(2, 0));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(5, 0)), gm.get(5, 0));

		// Black Bishops
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(2, 7)), gm.get(2, 7));
		assertEquals (new Piece (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(5, 7)), gm.get(5, 7));

		// White Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(4, 0)), gm.get(4, 0));

		// Black Queen
		assertEquals (new Piece (PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(4, 7)), gm.get(4, 7));

		// White King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(3, 0)), gm.get(3, 0));

		// Black King
		assertEquals (new Piece (PieceData.KING_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(3, 7)), gm.get(3, 7));
	}
}