package Chess.Time;

import javax.swing.*;
import java.util.Timer;

/**
 * Created by Astrid on 17/03/2017.
 */
public class Chronometer
{
	private static final int NANOSECOND = 1;
	private Timer timer;

	private TimerTick timerTickWhite;
	private TimerTick timerTickBlack;

	private boolean activeWhite;      //true = white, false = black
	private boolean running;

	private int timeWhite;
	private int timeBlack;

	private JLabel labelWhite;
	private JLabel labelBlack;



	public Chronometer(int initialWhite, int initialBlack)
	{
		timer= new Timer();

		timeWhite = initialWhite;
		timeBlack = initialBlack;

		timerTickWhite = new TimerTick(timeWhite);
		timerTickBlack = new TimerTick(timeBlack);




		activeWhite = true;
	}

	/**
	 * start chronometer (white starts)
	 */
	public void start()
	{
		activeWhite = true;
		running = true;
		timer.scheduleAtFixedRate(timerTickWhite,0,NANOSECOND);
	}

	/**
	 * switch activeWhite timer
	 */
	public void toggle()
	{
		if(activeWhite)
		{
			timer.scheduleAtFixedRate(timerTickBlack,0, NANOSECOND);
		}
		else
		{
			timer.scheduleAtFixedRate(timerTickWhite,0, NANOSECOND);
		}
	}

	/**
	 * pause chronometer if running, restart if not running
	 */
	public void pause()
	{
		if (running)
		{
			timeWhite = timerTickWhite.getTime();
			timeBlack = timerTickBlack.getTime();
			labelWhite = timerTickWhite.getTimeDisplay();
			labelBlack = timerTickBlack.getTimeDisplay();
			timer.cancel();
			running = false;
		}
		else
		{

			timer = new Timer();
			timerTickWhite = new TimerTick(timeWhite,labelWhite);
			timerTickBlack = new TimerTick(timeBlack, labelBlack);

			if(activeWhite)
			{
				timer.scheduleAtFixedRate(timerTickWhite,0, NANOSECOND);
			}
			else
			{
				timer.scheduleAtFixedRate(timerTickBlack,0, NANOSECOND);
			}

			running = true;
		}

	}

	/**
	 * get JLabel for time White
	 * @return JLabel for time White
	 */
	public JLabel getDisplayWhite()
	{
		return timerTickWhite.getTimeDisplay();
	}

	/**
	 * get Jlabel for time Black
	 * @return JLabel for time Black
	 */
	public JLabel getDisplayBlack()
	{
		return timerTickBlack.getTimeDisplay();
	}

	/**
	 * get time White
	 * @return time White
	 */
	public int getTimeWhite()
	{
		return timerTickWhite.getTime();
	}

	/**
	 * get time Black
	 * @return time Black
	 */
	public int getTimeBlack()
	{
		return timerTickBlack.getTime();
	}



}
