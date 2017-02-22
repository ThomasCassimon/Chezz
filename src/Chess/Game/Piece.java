package Chess.Game;

import Chess.Exceptions.Unchecked.IllegalPieceException;
import Chess.Exceptions.Unchecked.IllegalSideException;
import Chess.Exceptions.Unchecked.IllegalSquareException;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */

public class Piece
{
	private byte pieceByte;
	private byte positionByte;

	/**
	 * Constructor, takes a pieceByte and a 0x88 positionByte
	 * @param pieceByte a piece byte
	 * @param positionByte a (0x88) position byte
	 */
	public Piece (byte pieceByte, byte positionByte)
	{
		this.pieceByte = pieceByte;
		this.positionByte = positionByte;
	}

	/**
	 * Constructor, takes a pieceByte and a file and rank byte
	 * @param pieceByte a pieceByte
	 * @param file a file byte
	 * @param rank a rank byte
	 */
	public Piece (byte pieceByte, byte file, byte rank)
	{
		this.pieceByte = pieceByte;
		this.positionByte = ChessBoard.get0x88Index(file, rank);
	}

	/**
	 * Constructor, takes a pieceString, localeByte and (0x88) positionByte
	 * The sting can be either in long, human-readable format or in short
	 * 1-character format
	 * @param pieceString can be either in long, human-readable format or in short 1-character format
	 * @param positionByte a 0x88 position byte
	 * @param localeByte a locale that goes with pieceString
	 */
	public Piece (String pieceString, byte positionByte, byte localeByte)
	{
		switch (pieceString.length())
		{
			case 1:
				this.pieceByte = PieceData.toByteFromShort(pieceString, localeByte);
				break;
			default:
				this.pieceByte = PieceData.toByteFromString(pieceString, localeByte);
				break;
		}

		this.positionByte = positionByte;
	}

	/**
	 * Constructor, takes a pieceString, localeByte and a file and rank byte.
	 * The sting can be either in long, human-readable format or in short
	 * 1-character format
	 * @param pieceString can be either in long, human-readable format or in short 1-character format
	 * @param file  piece file
	 * @param rank  piece rank
	 * @param localeByte locale that goes with pieceString
	 */
	public Piece (String pieceString, byte file, byte rank, byte localeByte)
	{
		switch (pieceString.length())
		{
			case 1:
				this.pieceByte = PieceData.toByteFromShort(pieceString, localeByte);
				break;
			default:
				this.pieceByte = PieceData.toByteFromString(pieceString, localeByte);
				break;
		}

		this.positionByte = ChessBoard.get0x88Index(file,rank);
	}

	public byte getPieceByte ()
	{
		return this.pieceByte;
	}

	public byte getPositionByte ()
	{
		return this.positionByte;
	}

	/**
	 * The only factor taken into account is wheter or not the moves would land the piece off the board
	 * @return All moves that won't land the piece outside the board
	 */
	public Move[] getAllPossibleMoves ()
	{
		int size = 0;
		int j;
		Move[] tmp;
		Move[] moveArray;

		switch (this.pieceByte & (0x07))
		{
			case PieceData.PAWN_BYTE:
				if ((this.pieceByte & (PieceData.WHITE_MASK | PieceData.BLACK_MASK)) == PieceData.WHITE_MASK)
				{
					tmp = new Move[Movesets.PAWN_MOVE_WHITE.length];

					for (int i = 0; i < tmp.length; i++)
					{
						byte[] oriPos = ChessBoard.get2DCoord(this.getPositionByte());
						oriPos[0] += Movesets.PAWN_MOVE_WHITE[i][0];
						oriPos[1] += Movesets.PAWN_MOVE_WHITE[i][1];

						try
						{
							tmp[i] = new Move (this.positionByte, ChessBoard.get0x88Index(oriPos[0], oriPos[1]), (byte) 0x0);
						}
						catch (IllegalSquareException ise)
						{
							tmp[i] = null;
						}
					}
				}
				else if ((this.pieceByte & (PieceData.WHITE_MASK | PieceData.BLACK_MASK)) == PieceData.BLACK_MASK)
				{
					tmp = new Move[Movesets.PAWN_MOVE_BLACK.length];

					for (int i = 0; i < tmp.length; i++)
					{
						byte[] oriPos = ChessBoard.get2DCoord(this.getPositionByte());
						oriPos[0] += Movesets.PAWN_MOVE_BLACK[i][0];
						oriPos[1] += Movesets.PAWN_MOVE_BLACK[i][1];

						try
						{
							tmp[i] = new Move (this.positionByte, ChessBoard.get0x88Index(oriPos[0], oriPos[1]), (byte) 0x0);
						}
						catch (IllegalSquareException ise)
						{
							tmp[i] = null;
						}
					}
				}
				else
				{
					throw new IllegalSideException(Byte.toString((byte) (this.pieceByte & (PieceData.WHITE_MASK | PieceData.BLACK_MASK))) + " is not a valid side-byte.");
				}

				for (Move m : tmp)
				{
					if (m != null)
					{
						size++;
					}
				}

				moveArray = new Move [size];

				j = 0;

				for (int i = 0; i < tmp.length; i++)
				{
					if (tmp[i] != null)
					{
						moveArray[j] = tmp[i];
						moveArray[j].checkValid();
						j++;
					}
				}

				return moveArray;

			case PieceData.ROOK_BYTE:
				tmp = new Move [Movesets.ROOK_MOVE.length];

				for (int i = 0; i < tmp.length; i++)
				{
					byte[] oriPos = ChessBoard.get2DCoord(this.getPositionByte());
					oriPos[0] += Movesets.ROOK_MOVE[i][0];
					oriPos[1] += Movesets.ROOK_MOVE[i][1];

					try
					{
						tmp[i] = new Move (this.positionByte, ChessBoard.get0x88Index(oriPos[0], oriPos[1]), (byte) 0x0);
					}
					catch (IllegalSquareException ise)
					{
						tmp[i] = null;
					}
				}

				for (Move m : tmp)
				{
					if (m != null)
					{
						size++;
					}
				}

				moveArray = new Move [size];

				j = 0;

				for (int i = 0; i < tmp.length; i++)
				{
					if (tmp[i] != null)
					{
						moveArray[j] = tmp[i];
						moveArray[j].checkValid();
						j++;
					}
				}

				return moveArray;

			case PieceData.KNIGHT_BYTE:
				tmp = new Move [Movesets.KNIGHT_MOVE.length];

				for (int i = 0; i < tmp.length; i++)
				{
					byte[] oriPos = ChessBoard.get2DCoord(this.getPositionByte());
					oriPos[0] += Movesets.KNIGHT_MOVE[i][0];
					oriPos[1] += Movesets.KNIGHT_MOVE[i][1];

					try
					{
						tmp[i] = new Move (this.positionByte, ChessBoard.get0x88Index(oriPos[0], oriPos[1]), (byte) 0x0);
					}
					catch (IllegalSquareException ise)
					{
						tmp[i] = null;
					}
				}
				for (Move m : tmp)
				{
					if (m != null)
					{
						size++;
					}
				}

				moveArray = new Move [size];

				j = 0;

				for (int i = 0; i < tmp.length; i++)
				{
					if (tmp[i] != null)
					{
						moveArray[j] = tmp[i];
						moveArray[j].checkValid();
						j++;
					}
				}

				return moveArray;

			case PieceData.BISHOP_BYTE:
				tmp = new Move [Movesets.BISHOP_MOVE.length];

				for (int i = 0; i < tmp.length; i++)
				{
					byte[] oriPos = ChessBoard.get2DCoord(this.getPositionByte());
					oriPos[0] += Movesets.BISHOP_MOVE[i][0];
					oriPos[1] += Movesets.BISHOP_MOVE[i][1];

					try
					{
						tmp[i] = new Move (this.positionByte, ChessBoard.get0x88Index(oriPos[0], oriPos[1]), (byte) 0x0);
					}
					catch (IllegalSquareException ise)
					{
						tmp[i] = null;
					}
				}

				for (Move m : tmp)
				{
					if (m != null)
					{
						size++;
					}
				}

				moveArray = new Move [size];

				j = 0;

				for (int i = 0; i < tmp.length; i++)
				{
					if (tmp[i] != null)
					{
						moveArray[j] = tmp[i];
						moveArray[j].checkValid();
						j++;
					}
				}

				return moveArray;

			case PieceData.QUEEN_BYTE:
				tmp = new Move [Movesets.QUEEN_MOVE.length];

				for (int i = 0; i < tmp.length; i++)
				{
					byte[] oriPos = ChessBoard.get2DCoord(this.getPositionByte());
					oriPos[0] += Movesets.QUEEN_MOVE[i][0];
					oriPos[1] += Movesets.QUEEN_MOVE[i][1];

					try
					{
						tmp[i] = new Move (this.positionByte, ChessBoard.get0x88Index(oriPos[0], oriPos[1]), (byte) 0x0);
					}
					catch (IllegalSquareException ise)
					{
						tmp[i] = null;
					}
				}

				for (Move m : tmp)
				{
					if (m != null)
					{
						size++;
					}
				}

				moveArray = new Move [size];

				j = 0;

				for (int i = 0; i < tmp.length; i++)
				{
					if (tmp[i] != null)
					{
						moveArray[j] = tmp[i];
						moveArray[j].checkValid();
						j++;
					}
				}

				return moveArray;

			case PieceData.KING_BYTE:
				tmp = new Move [Movesets.KING_MOVE.length];

				for (int i = 0; i < tmp.length; i++)
				{
					byte[] oriPos = ChessBoard.get2DCoord(this.getPositionByte());
					oriPos[0] += Movesets.KING_MOVE[i][0];
					oriPos[1] += Movesets.KING_MOVE[i][1];

					try
					{
						tmp[i] = new Move (this.positionByte, ChessBoard.get0x88Index(oriPos[0], oriPos[1]), (byte) 0x0);
					}
					catch (IllegalSquareException ise)
					{
						tmp[i] = null;
					}
				}

				for (Move m : tmp)
				{
					if (m != null)
					{
						size++;
					}
				}

				moveArray = new Move [size];

				j = 0;

				for (int i = 0; i < tmp.length; i++)
				{
					if (tmp[i] != null)
					{
						moveArray[j] = tmp[i];
						moveArray[j].checkValid();
						j++;
					}
				}

				return moveArray;

			default:
				throw new IllegalPieceException(Byte.toString(this.pieceByte) + " is not a valid piece-byte.");
		}
	}
}
