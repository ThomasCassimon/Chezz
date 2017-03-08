package Chess.Time;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Astrid on 08/03/2017.
 */
public class Chronometer
{
	private Timer timer;
	private TimerTick timerTick;
	public final long  NANOSECOND =1 ;

	public Chronometer(int initialTime)
	{
		timer = new Timer();
		timerTick = new TimerTick(initialTime);
		timer.scheduleAtFixedRate(timerTick,0,  NANOSECOND);
	}

	public int getTime()
	{
		return timerTick.getTime();
	}

	public JLabel getDisplayTime()
	{
		return timerTick.getTimeDisplay();
	}




}
