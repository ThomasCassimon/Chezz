package Chess.Time;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;


public class TimerTick extends TimerTask
{
	private int time;
	private JLabel timeDisplay;

	public TimerTick(int initialCounter)
	{
		time = initialCounter;
		timeDisplay = new JLabel(getPrettyTime());
	}

	public TimerTick(int initialCounter, JLabel label)
	{
		time = initialCounter;
		timeDisplay = label;
	}

	@Override
	/**
	 * increases time and updates JLabel for time
	 */
	public void run()
	{
		if (time != 0)
		{
			time--;
			timeDisplay.setText(getPrettyTime());
		}
		else
		{
			timeDisplay.setText("00:00");
			timeDisplay.setForeground(Color.RED);
		}

		//System.out.println("Timer: " + getPrettyTime());

	}

	/**
	 * get JLabel for time
	 * @return JLabel time
	 */
	public JLabel getTimeDisplay()
	{
		timeDisplay.setText((getPrettyTime()));
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

		if(seconds<10)
		{
			if (minutes<10)
			{
				prettyTime = "0" + Integer.toString(minutes) + ":0" + Integer.toString(seconds);
			}
			else
			{
				prettyTime = Integer.toString(minutes) + ":0" + Integer.toString(seconds);
			}
		}
		else
		{
			if (minutes<10)
			{
				prettyTime = "0" + Integer.toString(minutes) + ":" + Integer.toString(seconds);
			}
			else
			{
				prettyTime = Integer.toString(minutes) + ":" + Integer.toString(seconds);
			}
		}



		return prettyTime;
	}
}

