package Chess.UI;

import Chess.Game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConfigurationPanel extends JFrame implements ActionListener
{
	private JLabel logo;
	private JLabel title;
	private JButton load;
	private JButton start;
	private JCheckBox undo;

	private GameManager gameManager;


	public ConfigurationPanel(GameManager gameManager)
	{

		super("Settings");

		this.gameManager = gameManager;

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		this.logo = new JLabel("");
		this.logo.setIcon(UIData.BK);
		this.title = new JLabel("Chezz!");
		this.load = new JButton("Load game");
		this.start = new JButton("Start the game");

		this.setBackground(UIData.BACKGROUND);
		this.setSize(UIData.CONFIGURATIONPANEL_DIMENSION);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		this.load.addActionListener(this);

		panel.add(title);
		title.setAlignmentX(Component.RIGHT_ALIGNMENT);
		title.setFont(title.getFont().deriveFont(UIData.TITLE_FONT_SIZE));
		panel.add(UIData.SPACING);
		panel.add(logo);
		logo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(load);
		load.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(UIData.SPACING);
		panel.add(start);
		start.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.setContentPane(panel);


		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == load)
		{
			//Parser.readFromFile(gameManager,this);

		}

	}

	public JButton getStart()
	{
		return this.start;
	}
}
