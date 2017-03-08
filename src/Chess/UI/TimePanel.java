package Chess.UI;

import Chess.Time.Chronometer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Astrid on 08/03/2017.
 */
public class TimePanel extends JPanel
{
	private Chronometer chronometerWhite;
	private Chronometer chronometerBlack;

	private JLabel labelWhite;
	private JLabel labelBlack;


	private JLabel timeWhite;
	private JLabel timeBlack;

	private JPanel panelWhite;
	private JPanel panelBlack;


	public TimePanel(Chronometer chronometerBlack, Chronometer chronometerWhite)
	{

		this.chronometerWhite = chronometerWhite;
		this.chronometerBlack = chronometerBlack;

		this.labelWhite = new JLabel("");
		this.labelBlack = new JLabel("");

		this.labelWhite.setIcon(UIData.WK);
		this.labelBlack.setIcon(UIData.BK);

		this.timeWhite = this.chronometerWhite.getDisplayTime();
		this.timeBlack = this.chronometerWhite.getDisplayTime();

		this.add(labelWhite);
		this.add(timeWhite);

		this.add(labelBlack);
		this.add(timeBlack);

	}
}
