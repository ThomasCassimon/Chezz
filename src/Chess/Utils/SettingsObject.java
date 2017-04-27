package Chess.Utils;

/**
 * Created by thomas on 20/04/17.
 */
public class SettingsObject
{
	private boolean undo;
	private boolean chronometer;
	private long time_ms;

	public SettingsObject ()
	{
		this.undo = true;
	}

	public SettingsObject (boolean undoEnabled, long time_seconds, boolean chronometer)
	{
		this.undo = undoEnabled;
		this.time_ms = time_seconds;
		this.chronometer = chronometer;
	}

	public SettingsObject (SettingsObject so)
	{
		this.undo = so.undo;
		this.time_ms = so.time_ms;
	}

	public boolean undoEnabled ()
	{
		return this.undo;
	}

	public long getTime_ms()
	{
		return this.time_ms;
	}

	public void setUndo(boolean undo)
	{
		this.undo = undo;
	}

	public void setTime_ms(long ms)
	{
		this.time_ms = ms;
	}

	public void setChronometer(boolean chronometer)
	{
		this.chronometer = chronometer;
	}
}
