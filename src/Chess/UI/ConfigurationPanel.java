package Chess.UI;

import Chess.Game.GameManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConfigurationPanel extends JFrame implements ActionListener
{
	private JButton load;
	private JButton start;
	private JCheckBox undo;

	private GameManager gameManager;


	public ConfigurationPanel(GameManager gameManager)
	{

		super("Settings");

		this.gameManager = gameManager;

		JPanel panel = new JPanel();


		this.load = new JButton("Load game");
		this.start = new JButton("Start the game");

		this.setBackground(UIData.BACKGROUND);
		this.setSize(UIData.CONFIGURATIONPANEL_DIMENSION);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		this.load.addActionListener(this);
		//this.start.addActionListener(mainFrame);


		panel.add(load);
		panel.add(start);

		this.setContentPane(panel);


		this.setResizable(false);
		//this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}

	public JButton getStart()
	{
		return this.start;
	}
}
