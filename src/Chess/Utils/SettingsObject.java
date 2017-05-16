package Chess.Utils;


public class SettingsObject
{
	private boolean undo;
	private long timeMsW;
	private long timeMsB;

	public SettingsObject ()
	{
		this.undo = true;
	}

	public SettingsObject (boolean undoEnabled, long time_seconds, boolean chronometer)
	{
		this.undo = undoEnabled;
		this.timeMsW = time_seconds;
		this.timeMsB = time_seconds;
	}

	public SettingsObject (SettingsObject so)
	{
		this.undo = so.undo;
		this.timeMsW = so.timeMsW;
	}

	public boolean undoEnabled ()
	{
		return this.undo;
	}

	public long getTimeMsW()
	{
		return this.timeMsW;
	}

	public void setUndo(boolean undo)
	{
		this.undo = undo;
	}

	public void setTimeMsW(long ms)
	{
		this.timeMsW = ms;
	}

	public void setTimeMsB(long ms)
	{
		this.timeMsB = ms;
	}

	public long getTimeMsB()
	{
		return this.timeMsB;
	}
}
