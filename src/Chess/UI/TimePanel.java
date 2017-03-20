package Chess.UI;

import Chess.Game.GameManager;
import Chess.Time.Chronometer;
import Chess.Time.TimerTick;

import javax.swing.*;

/**
 * Created by Astrid on 08/03/2017.
 */
public class TimePanel extends JPanel
{
	private Chronometer chronometer;

	private JLabel labelWhite;
	private JLabel labelBlack;
	private JLabel text;


	private JLabel timeWhite;
	private JLabel timeBlack;

	private JPanel panelWhite;
	private JPanel panelBlack;


	public TimePanel()
	{

		this.chronometer = GameManager.chronometer;

		this.labelWhite = new JLabel("");
		this.labelBlack = new JLabel("");
		this.text = new JLabel("VS");

		this.labelWhite.setIcon(UIData.WK);
		this.labelBlack.setIcon(UIData.BK);
		this.text.setBackground(UIData.BACKGROUND);

		this.timeWhite = this.chronometer.getDisplayWhite();
		this.timeBlack = this.chronometer.getDisplayBlack();

		this.add(timeWhite);
		this.add(labelWhite);

		this.add(text);

		this.add(labelBlack);
		this.add(timeBlack);

		this.setBorder(UIData.BORDER_BLACK);

	}
}
