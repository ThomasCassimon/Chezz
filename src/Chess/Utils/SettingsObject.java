package Chess.Utils;

/**
 * Created by thomas on 20/04/17.
 */
public class SettingsObject
{
	private boolean undo;
	private long time_s;

	public SettingsObject ()
	{
		this.undo = false;
	}

	public SettingsObject (boolean undoEnabled, long time_seconds)
	{
		this.undo = undoEnabled;
		this.time_s = time_seconds;
	}

	public SettingsObject (SettingsObject so)
	{
		this.undo = so.undo;
		this.time_s = so.time_s;
	}

	public boolean undoEnabled ()
	{
		return this.undo;
	}

	public long getTimeSeconds ()
	{
		return this.time_s;
	}
}
