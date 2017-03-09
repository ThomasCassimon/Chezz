package Chess.Time;

import javax.swing.*;
import java.util.TimerTask;

/**
 * Created by Astrid on 08/03/2017.
 */
public class TimerTick extends TimerTask
{
	private int time;
	private JLabel timeDisplay;

	public TimerTick(int initialCounter)
	{
		time = initialCounter;
		timeDisplay = new JLabel();
	}


	@Override
	public void run()
	{
		time--;
		timeDisplay.setText(getPrettyTime());
		//System.out.println("Timer: " + getPrettyTime());

	}

	public JLabel getTimeDisplay()
	{
		return timeDisplay;
	}

	/**
	 * returns time in milliseconds
	 * @return time
	 */
	public int getTime()
	{
		return time;
	}


	/**
	 * returns pretty String format of time
	 * @return String with time in minutes and seconds
	 */
	public String getPrettyTime()
	{
		int seconds;
		int minutes;
		String prettyTime;

		seconds = time/1000;
		minutes = seconds/60;
		seconds = seconds%60;

		prettyTime = Integer.toString(minutes) + ":" + Integer.toString(seconds);

		return prettyTime;
	}
}

