package Chess.Utils;

/**
 * Created by thomas on 20/04/17.
 */
public class SettingsObject
{
	private boolean undo;
	private long time_ms_W;
	private long time_ms_B;

	public SettingsObject ()
	{
		this.undo = true;
	}

	public SettingsObject (boolean undoEnabled, long time_seconds, boolean chronometer)
	{
		this.undo = undoEnabled;
		this.time_ms_W = time_seconds;
		this.time_ms_B = time_seconds;
	}

	public SettingsObject (SettingsObject so)
	{
		this.undo = so.undo;
		this.time_ms_W = so.time_ms_W;
	}

	public boolean undoEnabled ()
	{
		return this.undo;
	}

	public long getTime_ms_W()
	{
		return this.time_ms_W;
	}

	public void setUndo(boolean undo)
	{
		this.undo = undo;
	}

	public void setTime_ms_W(long ms)
	{
		this.time_ms_W = ms;
	}

	public void setTime_ms_B(long ms)
	{
		this.time_ms_B = ms;
	}

	public long getTime_ms_B()
	{
		return this.time_ms_B;
	}
}
