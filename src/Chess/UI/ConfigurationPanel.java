package Chess.UI;

import Chess.Game.GameManager;
import Chess.Utils.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConfigurationPanel extends JFrame implements ActionListener
{
	private JLabel title;
	private JButton load;
	private JButton start;
	private JCheckBox undo;
	private JComboBox<Integer> timeLimit;

	private GameManager gameManager;


	public ConfigurationPanel(GameManager gameManager)
	{

		super("Settings");

		this.gameManager = gameManager;

		JPanel panel = new JPanel();


		//
		this.title = new JLabel("Chezz!");
		this.load = new JButton("Load game");
		this.start = new JButton("Start the game");
		this.undo = new JCheckBox("Undo enabled");
		timeLimit = new JComboBox<Integer>();

		this.setBackground(UIData.BACKGROUND);
		this.setSize(UIData.CONFIGURATIONPANEL_DIMENSION);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//LAYOUT
		panel.setBackground(UIData.BACKGROUND);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


		this.load.addActionListener(this);




		title.setFont(title.getFont().deriveFont(UIData.TITLE_FONT_SIZE));
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));


		panel.add(timeLimit);
		timeLimit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(undo);
		undo.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(load);
		load.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(Box.createRigidArea(UIData.SPACING));

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
			Parser.readFromFile(gameManager,this);
		}

	}

	public JButton getStart()
	{
		return this.start;
	}
}
