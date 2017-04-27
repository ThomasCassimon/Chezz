package Chess.Time;

import javax.swing.*;
import java.util.Timer;


public class Chronometer
{
	private static final int MILLISECOND = 1;
	private Timer timer;

	private TimerTick timerTickWhite;
	private TimerTick timerTickBlack;

	private boolean activeWhite;      //true = white, false = black
	private boolean running;
	private boolean enabled;

	private long timeWhite;
	private long timeBlack;

	private JLabel labelWhite;
	private JLabel labelBlack;



	public Chronometer(long initialWhite, long initialBlack)
	{
		timer= new Timer();

		timeWhite = initialWhite;
		timeBlack = initialBlack;

		timerTickWhite = new TimerTick(timeWhite);
		timerTickBlack = new TimerTick(timeBlack);




		activeWhite = true;
		enabled = true;
	}

	public Chronometer(long initialWhite, long initialBlack, JLabel labelWhite, JLabel labelBlack)
	{
		this.timer= new Timer();

		this.timeWhite = initialWhite;
		this.timeBlack = initialBlack;

		this.timerTickWhite = new TimerTick(timeWhite);
		this.timerTickBlack = new TimerTick(timeBlack);

		this.labelWhite = labelWhite;
		this.labelBlack = labelBlack;

		this.timerTickWhite.setTimeDisplay(this.labelWhite);
		this.timerTickBlack.setTimeDisplay(this.labelBlack);

		this.activeWhite = true;
		this.enabled = true;
	}

	public Chronometer()
	{
		this.timer= new Timer();

		this.timeWhite = 300000;
		this.timeBlack = 300000;

		this.timerTickWhite = new TimerTick(timeWhite);
		this.timerTickBlack = new TimerTick(timeBlack);

		this.labelBlack = timerTickBlack.getTimeDisplay();
		this.labelWhite = timerTickWhite.getTimeDisplay();

		this.activeWhite = true;
		this.enabled = true;
	}

	public Chronometer(Chronometer chronometer)
	{
		this.timer= new Timer();

		this.timeWhite = chronometer.getTimeWhite();
		this.timeBlack = chronometer.getTimeWhite();

		this.activeWhite = true;

		this.timerTickWhite = new TimerTick(timeWhite);
		this.timerTickBlack = new TimerTick(timeBlack);

		this.timerTickWhite.setTimeDisplay(labelWhite);
		this.timerTickBlack.setTimeDisplay(labelBlack);

		this.activeWhite = true;
		this.enabled = true;
	}

	/**
	 * start chronometer (white starts)
	 */
	public void start()
	{
		if(enabled)
		{
			activeWhite = true;
			running = true;
			timer.scheduleAtFixedRate(timerTickWhite,0, MILLISECOND);
		}
	}

	/**
	 * switch activeWhite timer
	 */
	public void toggle()
	{
		if(enabled)
		{
			//System.out.println("Chronometer toggled");
			timeWhite = timerTickWhite.getTime();
			timeBlack = timerTickBlack.getTime();

			labelWhite = timerTickWhite.getTimeDisplay();
			labelBlack = timerTickBlack.getTimeDisplay();

			timer.cancel();
			timer = new Timer();

			timerTickWhite = new TimerTick(timeWhite, labelWhite);
			timerTickBlack = new TimerTick(timeBlack, labelBlack);


			if (activeWhite)
			{
				timer.scheduleAtFixedRate(timerTickBlack, 0, MILLISECOND);
			}
			else
			{
				timer.scheduleAtFixedRate(timerTickWhite, 0, MILLISECOND);
			}

			activeWhite = !activeWhite;
		}
	}

	/**
	 * pause chronometer if running, restart if not running
	 */
	public void pause()
	{
		if(enabled)
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
				timerTickWhite = new TimerTick(timeWhite, labelWhite);
				timerTickBlack = new TimerTick(timeBlack, labelBlack);

				if (activeWhite)
				{
					timer.scheduleAtFixedRate(timerTickWhite, 0, MILLISECOND);
				}
				else
				{
					timer.scheduleAtFixedRate(timerTickBlack, 0, MILLISECOND);
				}

				running = true;
			}
		}

	}

	/**
	 * get JLabel for time White
	 * @return JLabel for time White
	 */
	public JLabel getDisplayWhite()
	{
		if (enabled)
		{
			return timerTickWhite.getTimeDisplay();
		}
		else
		{
			return labelWhite;
		}

	}

	/**
	 * get Jlabel for time Black
	 * @return JLabel for time Black
	 */
	public JLabel getDisplayBlack()
	{
		if(enabled)
		{
			return timerTickBlack.getTimeDisplay();
		}
		else
		{
			return labelBlack;
		}

	}

	/**
	 * get time White
	 * @return time White
	 */
	public long getTimeWhite()
	{
		return timerTickWhite.getTime();
	}

	/**
	 * get time Black
	 * @return time Black
	 */
	public long getTimeBlack()
	{
		return timerTickBlack.getTime();
	}

	public boolean isRunning ()
	{
		return this.running;
	}

	public void disable()
	{
		System.out.println("Timer disabled");

		if(enabled)
		{
			labelBlack = timerTickBlack.getTimeDisplay();
			labelWhite = timerTickWhite.getTimeDisplay();
			timerTickBlack = null;
			timerTickWhite = null;
			timer.cancel();
			labelWhite.setText("/");
			labelBlack.setText("/");
		}


		enabled = false;

	}


}
