package Chess.Athena;

import java.util.ArrayList;

/**
 * @author Thomas
 * @date 29/04/2017
 * <p>
 * Project: Chezz
 * Package: Chess.Athena
 */
public class Node
{
	private Node parent;
	private ArrayList<Node> children;
	private long key;
	private GameState game;

	public Node (long key, GameState game)
	{
		this.parent = null;
		this.children = new ArrayList<Node>();
		this.key = key;
		this.game = game;
	}
}
