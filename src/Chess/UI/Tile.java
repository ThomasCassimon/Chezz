package Chess.UI;

import javax.swing.*;

@Deprecated
public class Tile extends JButton
{
	private int file;
	private int rank;

	public Tile(int file, int rank)
	{
		this.file = file;
		this.rank = rank;
	}

	public int getFile()
	{
		return file;
	}

	public int getRank()
	{
		return rank;
	}
}
