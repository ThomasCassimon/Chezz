package Chess.UI;

import Chess.Main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Astrid on 27/02/2017.
 */
public final class UIData
{
	//CHESS PIECES
	//Pawns
	public static final  ImageIcon WP = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/PW.png"));
	public static final ImageIcon BP = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/PB.png"));
	//Kings
	public static final ImageIcon WK = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/KW.png"));
	public static final ImageIcon BK = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/KB.png"));
	//Queens
	public static final ImageIcon WQ = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/QW.png"));
	public static final ImageIcon BQ = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/QB.png"));
	//Knights
	public static final ImageIcon WN = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/NW.png"));
	public static final ImageIcon BN = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/NB.png"));
	//Bishops
	public static final ImageIcon WB = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/BW.png"));
	public static final ImageIcon BB = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/BB.png"));
	//Rooks
	public static final ImageIcon WR = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/RW.png"));
	public static final ImageIcon BR = new ImageIcon(MainWindow.class.getResource("Chess_Pieces/RB.png"));

	//COLORS
	public static final Color BROWN = new Color(140,70,20); 	//Saddlebrown (RGB 139,69,19)
	public static final Color LIGHT_BROWN = new Color(210,180,140); 	//Tan (RGB 210,180,140)
	public static final Color FRAMES = Color.LIGHT_GRAY;
	public static final Color BACKGROUND = Color.BLACK;
	public static final Color TEXT_BACKGROUND = Color.WHITE;

	//FONTS
	public static final float FONT_SIZE = 25.0f;

}
