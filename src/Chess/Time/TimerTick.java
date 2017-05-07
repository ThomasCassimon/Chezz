package Chess.Time;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;


public class TimerTick extends TimerTask
{
	private long time;
	private JLabel timeDisplay;

	public TimerTick(long initialCounter)
	{
		time = initialCounter;
		timeDisplay = new JLabel(getPrettyTime());
		//System.out.println("New timertick created with initial time");
	}

	public TimerTick(long initialCounter, JLabel label)
	{
		time = initialCounter;
		timeDisplay = label;
		//System.out.println("New timertick created with initial time and label");
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
	public long getTime()
	{
		return time;
	}


	/**
	 * returns pretty String format of time
	 * @return String with time in minutes and seconds
	 */
	public String getPrettyTime()
	{
		long seconds;
		long minutes;
		String prettyTime;

		seconds = time/1000;
		minutes = seconds/60;
		seconds = seconds%60;

		if(seconds<10)
		{
			if (minutes<10)
			{
				prettyTime = "0" + Long.toString(minutes) + ":0" + Long.toString(seconds);
			}
			else
			{
				prettyTime = Long.toString(minutes) + ":0" + Long.toString(seconds);
			}
		}
		else
		{
			if (minutes<10)
			{
				prettyTime = "0" + Long.toString(minutes) + ":" + Long.toString(seconds);
			}
			else
			{
				prettyTime = Long.toString(minutes) + ":" + Long.toString(seconds);
			}
		}



		return prettyTime;
	}

	public void setTimeDisplay(JLabel timeDisplay)
	{
		this.timeDisplay = timeDisplay;
		timeDisplay.setText(getPrettyTime());
	}
}

