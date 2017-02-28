package Chess.UI;

import Chess.Exceptions.Unchecked.IllegalPieceException;
import Chess.Game.Piece;
import Chess.Game.PieceData;

import javax.swing.*;
import java.awt.*;


public class Board extends JPanel
{
	private Tile tiles[] = new Tile[64];


	private static final int NUMBER_TILES = 8;



	//Images
	private ImageIcon king = new ImageIcon(MainWindow.class.getResource("/home/thomas/Dropbox/Universiteit/Semester_4/Software_Design/Chezz/res/pieceSets/default"));


	public Board()
	{
		super();

		this.setPreferredSize(new Dimension(800,800));
		this.setLayout(new GridLayout(NUMBER_TILES +2, NUMBER_TILES +2));

		initBoard();

	}

	/**
	 * Sets the layout of the board
	 */
	private void initBoard()
	{
		//Forming the chess board
		char letter='A';
		int row, col, arrayIndex;


		//TOP of frame

		JLabel label = new JLabel("");
		label.setBackground(UIData.FRAMES);
		label.setOpaque(true);
		this.add(label);


		for (col=0;col<8;col++)
		{
			label = new JLabel (Character.toString(letter));
			label.setBackground(UIData.FRAMES);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);
			letter++;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAMES);
		label.setOpaque(true);
		this.add(label);


		for (row = NUMBER_TILES-1; row >=0; row--)
		{
			//LEFT of frame
			label = new JLabel(Integer.toString(row+1)+" ");
			label.setBackground(UIData.FRAMES);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

			//TILES
			for (col = NUMBER_TILES-1; col >=0; col--)
			{
				arrayIndex = (row * 8) + col;


				tiles[arrayIndex] = new Tile();

				if ((row + col) % 2 != 0)
				{
					tiles[arrayIndex].setBackground(UIData.BROWN);
				}
				else
				{
				tiles[arrayIndex].setBackground(UIData.LIGHT_BROWN);
				}

				tiles[arrayIndex].setOpaque(true);
				tiles[arrayIndex].setBorderPainted(false);

				this.add(tiles[arrayIndex]);
			}

			//RIGHT of frame
			label = new JLabel(" " + Integer.toString(row+1));
			label.setBackground(UIData.FRAMES);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

		}


		//BOTTOM of frame
		label = new JLabel("");
		label.setBackground(UIData.FRAMES);
		label.setOpaque(true);
		this.add(label);

		letter = 'A';
		for (col=0;col<8;col++)
		{
			label = new JLabel (Character.toString(letter));
			label.setBackground(UIData.FRAMES);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.TOP);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);
			letter++;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAMES);
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
		colorByte = piece.getColorByte();

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
	private int getIndex(Piece piece)
	{
		int indexArr[];
		int index;
		int file,rank;
		indexArr = piece.get2DCoord();
		index = ((indexArr[1]-1) * 8) + (indexArr[0]-1);
		return index;

	}


}
