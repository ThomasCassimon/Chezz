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


	//CHESS PIECES
	//Pawns
	public static final ImageIcon WP = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/PW.png"));
	public static final ImageIcon BP = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/PB.png"));
	//Kings
	public static final ImageIcon WK = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/KW.png"));
	public static final ImageIcon BK = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/KB.png"));
	//Queens
	public static final ImageIcon WQ = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/QW.png"));
	public static final ImageIcon BQ = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/QB.png"));
	//Knights
	public static final ImageIcon WN = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/NW.png"));
	public static final ImageIcon BN = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/NB.png"));
	//Bishops
	public static final ImageIcon WB = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/BW.png"));
	public static final ImageIcon BB = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/BB.png"));
	//Rooks
	public static final ImageIcon WR = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/RW.png"));
	public static final ImageIcon BR = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/Default/RB.png"));

	//COLORS
	public static final Color BROWN = new Color(140,70,20); 	//Saddlebrown (RGB 139,69,19)
	public static final Color LIGHT_BROWN = new Color(210,180,140); 	//Tan (RGB 210,180,140)
	public static final Color FRAMES = new Color (140,70,20);
	public static final Color BACKGROUND = Color.WHITE;
	public static final Color TEXT_BACKGROUND = Color.WHITE;
	public static final Color FRAME_COLOR = new Color (145,75,25);
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	public static final Color TEXT_BACKGROUND_COLOR = Color.WHITE;
	public static final Color BORDER_COLOR = Color.BLACK;
	public static final Color HIGHLIGHT = Color.GREEN;

	//FONTS
	public static final float FONT_SIZE = 25.0f;

	//BORDERS
	public static final Border TOP_BORDER = BorderFactory.createMatteBorder(1,0,0,0, BORDER_COLOR);
	public static final Border BOTTOM_BORDER = BorderFactory.createMatteBorder(0,0,1,0, BORDER_COLOR);
	public static final Border LEFT_BORDER = BorderFactory.createMatteBorder(0,1,0,0,BORDER_COLOR);
	public static final Border RIGHT_BORDER = BorderFactory.createMatteBorder(0,0,0,0,BORDER_COLOR);
}
