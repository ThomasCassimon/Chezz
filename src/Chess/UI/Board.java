package Chess.UI;

import Chess.Exceptions.Unchecked.IllegalPieceException;
import Chess.Game.GameManager;
import Chess.Game.Move;
import Chess.Game.Piece;
import Chess.Game.PieceData;

import javax.swing.*;
import java.awt.*;


public class Board extends JPanel
{
	private JButton tiles[] = new JButton[64];

	public Board(GamePanel gamePanel)
	{
		super();

		this.setPreferredSize(UIData.BOARD_DIMENSION);
		this.setLayout(new GridLayout(UIData.NUMBER_TILES +2, UIData.NUMBER_TILES + 2));
		this.setBorder(BorderFactory.createLineBorder(UIData.BORDER_COLOR));
		initBoard(gamePanel);

	}

	/**
	 * Sets the layout of the board
	 */
	public void initBoard(GamePanel gamePanel)
	{
		for (int i = 0; i<UIData.TOTAL_TILES;i++)
		{
			tiles[i] = new JButton();

			setNormalTileColor(get2DCoord(i));

			tiles[i].setOpaque(true);
			tiles[i].setBorderPainted(false);
			tiles[i].addActionListener(gamePanel);
		}

		//this.setBoardWhite();
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
		pieceByte = piece.getPieceByte();
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
		else
		{
			tiles[index].setIcon(null);
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
		index = ((indexArr[1]) * 8) + (indexArr[0]);
		return index;
	}

	public int getIndex(int[] indexArr)
	{
		int index;

		index = ((indexArr[1])*8 + indexArr[0]);

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

		index[0] = i % 8;
		index[1] = i / 8;

		return index;

	}

	/**
	 * Returns tile from given index
	 * @param index index for array of JButtons
	 * @return corresponding JButton from index
	 */
	public JButton getTile(int index)
	{
		return tiles[index];
	}


	/**
	 * Highlights a move
	 * @param move move to be highlighted
	 */
	public void highlightMove(Move move)
	{
		int i = this.getIndex(move.get2DDst());

		if (move.isCapture())
		{
			if(move.isPromotion())
			{
				tiles[i].setBackground(UIData.CAPTURE_AND_PROMOTION);
			}
			else
			{
				tiles[i].setBackground(UIData.CAPTURE);
			}
		}
		else if (move.isPromotion())
		{
			tiles[i].setBackground(UIData.PROMOTION);
		}
		else
		{
			tiles[i].setBackground(UIData.HIGHLIGHT);
		}

	}


	/**
	 * Gives gives tile tile from given index the active color
	 * @param index
	 */
	public void setActive(int index)
	{
		tiles[index].setBackground(UIData.ACTIVE_PIECE);
	}

	/**
	 * sets tile with given file and rank the correct color (normal board color)
	 * @param indexArr array with file and rank (file first)
	 */
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

	/**
	 * makes a move. moves the icons from src to dst. Board gets switched according to given active color
	 * @param move move that needs to be made
	 * @param gameManager GameManager of the game
	 */
	public void makeMove(Move move, GameManager gameManager)
	{

		this.setPiece(gameManager.get(move.get2DDst()));
		this.setPiece(gameManager.get(move.get2DSrc()));

		if (gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
		{
			this.setBoardWhite();
		}
		else
		{
			this.setBoardBlack();
		}



	}


	/**
	 * sets up the board for when player white is active
	 */
	public void setBoardWhite()
	{
		this.removeAll();


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
				indexArr[0] = file;
				indexArr[1] = rank;

				arrayIndex = getIndex(indexArr);
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

		this.revalidate();
		this.repaint();
	}

	/**
	 * sets up the board for when player black is active
	 */
	public void setBoardBlack()
	{
		this.removeAll();




		//Forming the chess board
		char letter='H';
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
			letter--;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAME_COLOR);
		label.setOpaque(true);
		this.add(label);


		for (rank = 0; rank <UIData.NUMBER_TILES; rank++)
		{
			//LEFT of frame
			label = new JLabel(Integer.toString(rank + 1)+" ");
			label.setBackground(UIData.FRAME_COLOR);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

			//TILES
			for (file = UIData.NUMBER_TILES-1; file >=0; file--)
			{
				indexArr[0] = file;
				indexArr[1] = rank;

				arrayIndex = getIndex(indexArr);
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

		letter = 'H';
		for (file=0;file<8;file++)
		{
			label = new JLabel (Character.toString(letter));
			label.setBackground(UIData.FRAME_COLOR);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.TOP);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);
			letter--;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAME_COLOR);
		label.setOpaque(true);
		this.add(label);

		this.revalidate();
		this.repaint();


	}

	public void update(int color)
	{
		if(color == PieceData.WHITE_BYTE)
		{
			this.setBoardWhite();
		}
		else
		{
			this.setBoardBlack();
		}
	}

	public void setTileIcon(int i, Icon icon)
	{
		tiles[i].setIcon(icon);
	}
}
