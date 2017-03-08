package Chess.UI;

import Chess.Exceptions.Unchecked.IllegalPieceException;
import Chess.Game.Move;
import Chess.Game.Piece;
import Chess.Game.PieceData;

import javax.swing.*;
import java.awt.*;


public class Board extends JPanel
{
	private Tile tiles[] = new Tile[64];





	public Board(MainWindow mainWindow)
	{
		super();

		this.setPreferredSize(new Dimension(800,800));
		this.setLayout(new GridLayout(UIData.NUMBER_TILES +2, UIData.NUMBER_TILES +2));
		this.setBorder(BorderFactory.createLineBorder(UIData.BORDER_COLOR));
		initBoard(mainWindow);

	}

	/**
	 * Sets the layout of the board
	 */
	private void initBoard(MainWindow mainWindow)
	{
		//Forming the chess board
		char letter='A';
		int rank, file, arrayIndex;
		int indexArr[] = new int[2];


		//TOP of frame

		JLabel label = new JLabel("");
		label.setBackground(UIData.FRAME_COLOR);
		label.setOpaque(true);
		this.add(label);


		for (file=0;file<8;file++)
		{
			label = new JLabel (Character.toString(letter));
			label.setBackground(UIData.FRAME_COLOR);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);
			letter++;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAME_COLOR);
		label.setOpaque(true);
		this.add(label);


		for (rank = UIData.NUMBER_TILES-1; rank >=0; rank--)
		{
			//LEFT of frame
			label = new JLabel(Integer.toString(rank+1)+" ");
			label.setBackground(UIData.FRAME_COLOR);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

			//TILES
			for (file = 0; file < UIData.NUMBER_TILES; file++)
			{
				arrayIndex = (rank * 8) + file;


				tiles[arrayIndex] = new Tile(file,rank);
				indexArr[0] = file +1;
				indexArr[1] = rank +1;

				System.out.println("1st index " + arrayIndex);
				setNormalTileColor(indexArr);

				tiles[arrayIndex].setOpaque(true);
				tiles[arrayIndex].setBorderPainted(false);
				tiles[arrayIndex].addActionListener(mainWindow);

				this.add(tiles[arrayIndex]);
			}

			//RIGHT of frame
			label = new JLabel(" " + Integer.toString(rank+1));
			label.setBackground(UIData.FRAME_COLOR);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

		}


		//BOTTOM of frame
		label = new JLabel("");
		label.setBackground(UIData.FRAME_COLOR);
		label.setOpaque(true);
		this.add(label);

		letter = 'A';
		for (file=0;file<8;file++)
		{
			label = new JLabel (Character.toString(letter));
			label.setBackground(UIData.FRAME_COLOR);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.TOP);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);
			letter++;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAME_COLOR);
		label.setOpaque(true);
		this.add(label);
	}

	/**
	 * Sets icon on the right place on the board
	 * @param piece the piece with the desired position
	 */
	public void setPiece(Piece piece)
	{
		int index;
		int pieceByte;
		int colorByte;

		index = getIndex(piece);
		pieceByte = piece.getPieceWithoutColorByte();
		colorByte = piece.getColor();

		if (colorByte == PieceData.WHITE_BYTE)
		{
			switch (pieceByte)
			{
				case PieceData.PAWN_BYTE:
					tiles[index].setIcon(UIData.WP);
					break;
				case PieceData.BISHOP_BYTE:
					tiles[index].setIcon(UIData.WB);
					break;
				case PieceData.KNIGHT_BYTE:
					tiles[index].setIcon(UIData.WN);
					break;
				case PieceData.ROOK_BYTE:
					tiles[index].setIcon(UIData.WR);
					break;
				case PieceData.QUEEN_BYTE:
					tiles[index].setIcon(UIData.WQ);
					break;
				case PieceData.KING_BYTE:
					tiles[index].setIcon(UIData.WK);
					break;
				default:
					throw new IllegalPieceException(Integer.toString(pieceByte) + "not a valid piece");
			}
		}
		else if (colorByte == PieceData.BLACK_BYTE)
		{
			switch(pieceByte)
			{
				case PieceData.PAWN_BYTE:
					tiles[index].setIcon(UIData.BP);
					break;
				case PieceData.BISHOP_BYTE:
					tiles[index].setIcon(UIData.BB);
					break;
				case PieceData.KNIGHT_BYTE:
					tiles[index].setIcon(UIData.BN);
					break;
				case PieceData.ROOK_BYTE:
					tiles[index].setIcon(UIData.BR);
					break;
				case PieceData.QUEEN_BYTE:
					tiles[index].setIcon(UIData.BQ);
					break;
				case PieceData.KING_BYTE:
					tiles[index].setIcon(UIData.BK);
					break;
				default:
					throw new IllegalPieceException(Integer.toString(pieceByte) + "not a valid piece");
			}

		}

	}


	/**
	 * Returns the index of a given piece
	 * @param piece piece that holds the location
	 * @return index of the piece
	 */
	public int getIndex(Piece piece)
	{
		int indexArr[];
		int index;
		indexArr = piece.get2DCoord();
		//System.out.println("File: " + indexArr[0] + "	Rank: " + indexArr[1]);
		index = ((indexArr[1]-1) * 8) + (indexArr[0]-1);
		//index = ((rank-1)*8 + file+1);
		//System.out.println("Index: " + index);
		return index;
	}

	public int getIndex(int[] indexArr)
	{
		int index;

		index = ((indexArr[1]-1)*8 + indexArr[0]-1);

		return index;

	}

	/**
	 * Returns 2D coordinates for a given index.
	 * @param i index of a tile
	 * @return array with file and rank (file first)
	 */
	public int[] get2DCoord(int i)
	{
		int index[] = new int[2];

		index[0] =i%8 + 1;
		//System.out.println("File: " + index[0]);
		index[1] = (int) (i/8 + 1);
		//System.out.println("Rank: " + index[1]);

		return index;

	}

	public Tile getTile(int index)
	{
		return tiles[index];
	}

	public void highlightPiece(Move move)
	{
		int i = this.getIndex(move.get2DDst());
		if (move.isCapture())
		{
			tiles[i].setBackground(UIData.CAPTURE);
		}
		else
		{
			tiles[i].setBackground(UIData.HIGHLIGHT);
		}

	}

	public void setActive(int index)
	{
		tiles[index].setBackground(UIData.ACTIVE_PIECE);
	}

	public void setNormalTileColor(int[] indexArr)
	{
		int index = getIndex(indexArr);

		if ((indexArr[0] + indexArr[1]) % 2 != 0)
		{
			tiles[index].setBackground(UIData.BROWN);
		}
		else
		{
			tiles[index].setBackground(UIData.LIGHT_BROWN);
		}
	}

	public void makeMove(Move move)
	{
		tiles[getIndex(move.get2DDst())].setIcon(tiles[getIndex(move.get2DSrc())].getIcon());
		tiles[getIndex(move.get2DSrc())].setIcon(null);
	}
}
