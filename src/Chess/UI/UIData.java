package Chess.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Astrid on 27/02/2017.
 */
public final class UIData
{
	//CONSTANTS
	public static final int NUMBER_TILES = 8;
	public static final int TOTAL_TILES = UIData.NUMBER_TILES * UIData.NUMBER_TILES;



	//DIMENSIONS
	public static final Dimension BUTTONS_DIMENSION = new Dimension(250,20);
	public static final Dimension BOARD_DIMENSION = new Dimension(800,800);
	public static final Dimension WINDOW_DIMENSION = new Dimension(1400,850);


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
	public static final Color BROWN = new Color(140,70,20); 	//Saddlebrown (RGB 139,69,19)
	public static final Color LIGHT_BROWN = new Color(210,180,140); 	//Tan (RGB 210,180,140)
	public static final Color BACKGROUND = Color.WHITE;
	public static final Color FRAME_COLOR = new Color (145,75,25);
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	public static final Color TEXT_BACKGROUND_COLOR = Color.WHITE;
	public static final Color BORDER_COLOR = Color.BLACK;
	public static final Color HIGHLIGHT = Color.GREEN;
	//public static final Color HIGHLIGHT = new Color (0,255,0,127);
	public static final Color ACTIVE_PIECE = Color.YELLOW;
	//public static final Color ACTIVE_PIECE = new Color(255,255,0,127);
	public static final Color CAPTURE = Color.RED;
	public static final Color PROMOTION = Color.BLUE;
	public static final Color CAPTURE_AND_PROMOTION = new Color(138,43,226);


	//FONTS
	public static final float FONT_SIZE = 25.0f;

	//BORDERS
	public static final Border BORDER_BLACK = BorderFactory.createLineBorder(Color.BLACK);
}
