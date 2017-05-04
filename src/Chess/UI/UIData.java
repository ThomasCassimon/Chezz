package Chess.UI;

import Chess.Game.ChessBoard;
import Chess.Game.Move;
import Chess.Game.PieceData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public final class UIData
{
	//CONSTANTS
	public static final int NUMBER_TILES = 8;
	public static final int TOTAL_TILES = UIData.NUMBER_TILES * UIData.NUMBER_TILES;
	public static final int MAX_HISTORY_ELEMENTS = 43;

	//STRINGS
	public static final String[] timeOptions = {"No time limit", "1 min" , "2.5 min", "5 min", "30 min", "1 hour"};


	//DIMENSIONS
	public static final Dimension BUTTONS_DIMENSION = new Dimension(300, 25);
	public static final Dimension BOARD_DIMENSION = new Dimension(800, 800);
	public static final Dimension GAMEPANEL_DIMENSION = new Dimension(1400, 850);
	public static final Dimension CONFIGURATIONPANEL_DIMENSION = new Dimension(450, 650);
	public static final Dimension HISTORY_DIMENSION = new Dimension(200, 750);
	public static final Dimension CONFIGURATIONPANEL_TEXTBOX = new Dimension(300, 1);

	//SPACING
	public static final Dimension SPACING = new Dimension(20, 20);


	//CHESS PIECES
	//Pawns
	public static final ImageIcon WP = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/PW.png"));
	public static final ImageIcon BP = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/PB.png"));
	//Kings
	public static final ImageIcon WK = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/KW.png"));
	public static final ImageIcon BK = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/KB.png"));
	//Queens
	public static final ImageIcon WQ = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/QW.png"));
	public static final ImageIcon BQ = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/QB.png"));
	//Knights
	public static final ImageIcon WN = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/NW.png"));
	public static final ImageIcon BN = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/NB.png"));
	//Bishops
	public static final ImageIcon WB = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/BW.png"));
	public static final ImageIcon BB = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/BB.png"));
	//Rooks
	public static final ImageIcon WR = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/RW.png"));
	public static final ImageIcon BR = new ImageIcon(GamePanel.class.getResource("Chess_Pieces/Default/RB.png"));

	//COLORS
	public static final Color BROWN = new Color(88, 39, 7);    //Saddlebrown (RGB 139,69,19)
	public static final Color LIGHT_BROWN = new Color(220, 204, 163);    //Tan (RGB 210,180,140)
	public static final Color BACKGROUND_COLOR = new Color(247, 249, 249);    //Tan (RGB 210,180,140)
	public static final Color FRAME_COLOR = new Color(98, 49, 17);

	public static final Color TEXT_BACKGROUND_COLOR = BACKGROUND_COLOR;
	public static final Color BORDER_COLOR = Color.BLACK;
	public static final Color HIGHLIGHT = new Color(93, 176, 93);
	public static final Color ACTIVE_PIECE = new Color(255, 237, 83);
	public static final Color CAPTURE = new Color(189, 60, 56);
	public static final Color PROMOTION = new Color(36, 123, 160); //lapis lazuli
	public static final Color CAPTURE_AND_PROMOTION = new Color(138, 25, 130); //russian violet


	//FONTS
	public static final float FONT_SIZE = 25.0f;
	public static final float TITLE_FONT_SIZE = 80.0f;

	//BORDERS
	public static final Border BORDER_BLACK = BorderFactory.createLineBorder(Color.BLACK);

	//MOVES
	public static final Move ROOK_QUEENSIDE_CASTLING_W = new Move(0, 7, 3, 7, 0);
	public static final Move ROOK_KINGSIDE_CASTLING_W = new Move(7, 7, 5, 7, 0);
	public static final Move ROOK_QUEENSIDE_CASTLING_B = new Move(0, 0, 3, 0, 0);
	public static final Move ROOK_KINGSIDE_CASTLING_B = new Move(7, 0, 5, 0, 0);

	public static final Move KING_KINGSIDE_CASTLING_W = new Move(4, 0, 6, 0, Move.KING_SIDE_CASTLE_MASK);
	public static final Move KING_KINGSIDE_CASTLING_B = new Move(4, 7, 6, 7, Move.KING_SIDE_CASTLE_MASK);
	public static final Move KING_QUEENSIDE_CASTLING_W = new Move(4, 0, 2, 0, Move.QUEEN_SIDE_CASTLE_MASK);
	public static final Move KING_QUEENSIDE_CASTLING_B = new Move(4, 7, 2, 7, Move.QUEEN_SIDE_CASTLE_MASK);
}
