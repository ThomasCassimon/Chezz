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

	private JLabel undoText;
	private JCheckBox undo;

	private JLabel timeLimitText;
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

		this.undoText = new JLabel("");
		this.undo = new JCheckBox("Undo enabled");

		this.timeLimitText = new JLabel("");
		this.timeLimit = new JComboBox<Integer>();

		this.setBackground(UIData.BACKGROUND_COLOR);
		this.setSize(UIData.CONFIGURATIONPANEL_DIMENSION);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//LAYOUT
		panel.setBackground(UIData.BACKGROUND_COLOR);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		title.setFont(title.getFont().deriveFont(UIData.TITLE_FONT_SIZE));
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		timeLimitText.setAlignmentX(Component.CENTER_ALIGNMENT);
		//timeLimitText.setPreferredSize(UIData.CONFIGURATIONPANEL_TEXTBOX);
		timeLimitText.setText("<html>Please enter a timelimit for your game (in mins) <br> or select one from the options below. </html>");
		//timeLimitText.setBorder(UIData.BORDER_BLACK);

		timeLimit.setAlignmentX(Component.CENTER_ALIGNMENT);
		//timeLimit.setPreferredSize(UIData.CONFIGURATIONPANEL_TEXTBOX);


		undoText.setAlignmentX(Component.CENTER_ALIGNMENT );
		undoText.setText("Do you want to enable undo's?");
		//undoText.setBorder(UIData.BORDER_BLACK);

		undo.setAlignmentX(Component.CENTER_ALIGNMENT);

		load.setAlignmentX(Component.CENTER_ALIGNMENT);

		start.setAlignmentX(Component.CENTER_ALIGNMENT);

		//BUTTON FUNCTIONALITY
		load.addActionListener(this);




		//ADDING COMPONENTS
		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(title);

		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(timeLimitText);
		panel.add(timeLimit);

		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(undoText);
		panel.add(undo);


		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(load);


		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(start);

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
