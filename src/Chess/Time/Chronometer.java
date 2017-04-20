package Chess.Time;

import javax.swing.*;
import java.util.Timer;


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

	public Chronometer()
	{
		timer= new Timer();

		timeWhite = 300000;
		timeBlack = 300000;

		timerTickWhite = new TimerTick(timeWhite);
		timerTickBlack = new TimerTick(timeBlack);
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
		timeWhite = timerTickWhite.getTime();
		timeBlack = timerTickBlack.getTime();

		labelWhite = timerTickWhite.getTimeDisplay();
		labelBlack = timerTickBlack.getTimeDisplay();

		timer.cancel();
		timer = new Timer();

		timerTickWhite = new TimerTick(timeWhite,labelWhite);
		timerTickBlack = new TimerTick(timeBlack,labelBlack);



		if(activeWhite)
		{
			timer.scheduleAtFixedRate(timerTickBlack,0, NANOSECOND);
		}
		else
		{
			timer.scheduleAtFixedRate(timerTickWhite,0, NANOSECOND);
		}

		activeWhite = !activeWhite;
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

	public boolean isRunning ()
	{
		return this.running;
	}

}
