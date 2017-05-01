package Chess.UI;

import Chess.Game.GameManager;
import Chess.Utils.SettingsObject;

import javax.swing.*;
import java.awt.*;


public class ConfigurationPanel extends JFrame
{
	private JLabel title;
	private JButton load;
	private JButton start;
	private JButton exit;

	private JLabel undoText;
	private JCheckBox undo;

	private JLabel timeLimitText;
	private JComboBox<String> timeLimit;

	private GameManager gameManager;


	public ConfigurationPanel(GameManager gameManager, MainFrame mainFrame)
	{

		super("Settings");

		this.gameManager = gameManager;

		JPanel panel = new JPanel();


		//

		timeLimit = new JComboBox<>(UIData.timeOptions);

		this.title = new JLabel("Chezz!");
		this.load = new JButton("Load game");
		this.start = new JButton("Start new game");
		this.exit = new JButton("Exit game");

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
		this.timeLimit.setBackground(UIData.BACKGROUND_COLOR);


		this.undoText.setAlignmentX(Component.CENTER_ALIGNMENT );
		this.undoText.setText("Do you want to enable undo's?");
		//undoText.setBorder(UIData.BORDER_BLACK);

		this.undo.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.exit.setAlignmentX(Component.CENTER_ALIGNMENT);



//		this.load.setAlignmentX(Component.CENTER_ALIGNMENT);

//		this.start.setAlignmentX(Component.CENTER_ALIGNMENT);

		//BUTTON FUNCTIONALITY

		this.undo.setSelected(true);

		this.start.addActionListener(mainFrame);
		this.load.addActionListener(mainFrame);
		this.exit.addActionListener(mainFrame);


		//ADDING COMPONENTS
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.X_AXIS));
		startPanel.setBackground(UIData.BACKGROUND_COLOR);

		startPanel.add(load);
		startPanel.add(Box.createRigidArea(UIData.SPACING));
		startPanel.add(start);

		//panel.add(Box.createRigidArea(UIData.SPACING));
		//panel.add(Box.createRigidArea(UIData.SPACING));
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

		panel.add(exit);
		panel.add(Box.createRigidArea(UIData.SPACING));



		this.setContentPane(panel);

		this.setResizable(false);
	}



	public SettingsObject getSettings()
	{

		SettingsObject settings = new SettingsObject();

		settings.setUndo(undo.isSelected());

		switch( (String) timeLimit.getSelectedItem())
		{
			case "No time limit":
				//System.out.println("no time limit");
				gameManager.disableChronometer();
				//gameManager.getChronometer().getDisplayBlack().setText("/");
				//gameManager.getChronometer().getDisplayWhite().setText("/");
				break;
			case "1 min":
				//System.out.println("time limit 1 min");
				settings.setTime_ms_W(60000); //1 min
				settings.setTime_ms_B(60000);
				break;
			case "2.5 min":
				//System.out.println("time limit 2.5 min");
				settings.setTime_ms_W(150000); //2.5 min
				settings.setTime_ms_B(150000);
				break;
			case "5 min":
				//System.out.println("time limit 5 min");
				settings.setTime_ms_W(300000); //5 min
				settings.setTime_ms_B(300000);
				break;
			case "30 min":
				//System.out.println("time limit 30 min");
				settings.setTime_ms_W(1800000); //30 min
				settings.setTime_ms_B(1800000);
				break;
			case "1 hour":
				//System.out.println("time limit 1 hour");
				settings.setTime_ms_W(3600000); //60 min
				settings.setTime_ms_B(3600000);

		}

		return settings;
	}

	public JButton getStart()
	{
		return this.start;
	}

	public JButton getLoad()
	{
		return this.load;
	}

	public JButton getExit()
	{
		return this.exit;
	}
}
