package Chess.UI;

import Chess.Game.GameManager;
import Chess.Utils.Parser;
import Chess.Utils.SettingsObject;

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
	private JComboBox<String> timeLimit;

	private GameManager gameManager;


	public ConfigurationPanel(GameManager gameManager)
	{

		super("Settings");

		this.gameManager = gameManager;

		JPanel panel = new JPanel();


		//
		String[] timeOptions = { "No Time Limit","1 min", "2.5 min", "5 min", "30 min", "1 hour"};

		timeLimit = new JComboBox<>(timeOptions);

		this.title = new JLabel("Chezz!");
		this.load = new JButton("Load game");
		this.start = new JButton("Start new game");

		this.undoText = new JLabel("");
		this.undo = new JCheckBox("Undo enabled");

		this.timeLimitText = new JLabel("");


		this.setBackground(UIData.BACKGROUND_COLOR);
		this.setSize(UIData.CONFIGURATIONPANEL_DIMENSION);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//LAYOUT
		panel.setBackground(UIData.BACKGROUND_COLOR);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		this.title.setFont(title.getFont().deriveFont(UIData.TITLE_FONT_SIZE));
		this.title.setFont(title.getFont().deriveFont(Font.BOLD));
		this.title.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.timeLimitText.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.timeLimitText.setText("<html>Please enter a timelimit for your game (in mins) <br> or select one of the default options. </html>");

		this.timeLimit.setAlignmentX(Component.CENTER_ALIGNMENT);


		this.undoText.setAlignmentX(Component.CENTER_ALIGNMENT );
		this.undoText.setText("Do you want to enable undo's?");
		//undoText.setBorder(UIData.BORDER_BLACK);

		this.undo.setAlignmentX(Component.CENTER_ALIGNMENT);



//		this.load.setAlignmentX(Component.CENTER_ALIGNMENT);

//		this.start.setAlignmentX(Component.CENTER_ALIGNMENT);

		//BUTTON FUNCTIONALITY
		this.load.addActionListener(this);

		this.undo.setSelected(true);






		//ADDING COMPONENTS
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.X_AXIS));
		startPanel.setBackground(UIData.BACKGROUND_COLOR);

		startPanel.add(load);
		startPanel.add(start);



		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(title);



		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(timeLimitText);
		panel.add(timeLimit);

		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(undoText);
		panel.add(undo);

		panel.add(Box.createRigidArea(UIData.SPACING));
		panel.add(Box.createRigidArea(UIData.SPACING));

		panel.add(startPanel);
		panel.add(Box.createRigidArea(UIData.SPACING));



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

	public SettingsObject getSettings()
	{
		SettingsObject settings = new SettingsObject();

		settings.setUndo(undo.isSelected());

		//settings.setTime_s((long) timeLimit.getSelectedItem());

		return settings;
	}

	public JButton getStart()
	{
		return this.start;
	}
}
