package Chess.UI;

import Chess.Main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Astrid on 27/02/2017.
 */
public class UIData
{
	//CHESS PIECES

	//Pawns
	public static final ImageIcon WP = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/WP"));
	public static final ImageIcon BP = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/BP"));
	//Kings
	public static final ImageIcon WK = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces.WK"));
	public static final ImageIcon BK = new ImageIcon(MainWindow.class.getResource("/Ches_Pieces/BK"));
	//Queens
	public static final ImageIcon WQ = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/WQ"));
	public static final ImageIcon BQ = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/BQ"));
	//Knights
	public static final ImageIcon WN = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/WN"));
	public static final ImageIcon BN = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/BN"));
	//Bischops
	public static final ImageIcon WB = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/WB"));
	public static final ImageIcon BB = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/BB"));
	//Rooks
	public static final ImageIcon WR = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/WR"));
	public static final ImageIcon BR = new ImageIcon(MainWindow.class.getResource("/Chess_Pieces/BR"));

	//COLORS
	public static final Color BROWN = new Color(140,70,20); 	//Saddlebrown (RGB 139,69,19)
	public static final Color LIGHT_BROWN = new Color(210,180,140); 	//Tan (RGB 210,180,140)
	public static final Color FRAME = Color.LIGHT_GRAY;
	public static final Color BACKGROUND = Color.BLACK;
	public static final Color TEXT_BACKGROUND = Color.WHITE;

	//FONTS
	public static final float FONT_SIZE = 25.0f;

}
