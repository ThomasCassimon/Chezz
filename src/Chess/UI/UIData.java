package Chess.UI;

import Chess.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Astrid on 27/02/2017.
 */
public final class UIData
{
	//CHESS PIECES
	//Pawns
	public static final ImageIcon WP = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/PW.png"));
	public static final ImageIcon BP = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/PB.png"));
	//Kings
	public static final ImageIcon WK = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/KW.png"));
	public static final ImageIcon BK = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/KB.png"));
	//Queens
	public static final ImageIcon WQ = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/QW.png"));
	public static final ImageIcon BQ = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/QB.png"));
	//Knights
	public static final ImageIcon WN = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/NW.png"));
	public static final ImageIcon BN = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/NB.png"));
	//Bishops
	public static final ImageIcon WB = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/BW.png"));
	public static final ImageIcon BB = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/BB.png"));
	//Rooks
	public static final ImageIcon WR = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/RW.png"));
	public static final ImageIcon BR = new ImageIcon(MainWindow.class.getResource("res/pieceSets/default/RB.png"));

	//COLORS
	public static final Color BROWN = new Color(140,70,20); 	//Saddlebrown (RGB 139,69,19)
	public static final Color LIGHT_BROWN = new Color(210,180,140); 	//Tan (RGB 210,180,140)
	public static final Color FRAMES = new Color (145,75,25);
	public static final Color BACKGROUND = Color.WHITE;
	public static final Color TEXT_BACKGROUND = Color.WHITE;

	//FONTS
	public static final float FONT_SIZE = 25.0f;

}
