package Chess.UI;

import Chess.Game.GameManager;
import Chess.Utils.SettingsObject;

import javax.swing.*;
import java.awt.*;


public class ConfigurationPanel extends JFrame
{
	private JLabel title;
	private JLabel newGame;
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

		JPanel contentPanel = new JPanel();
		JPanel newGameSettingsPanel = new JPanel();


		//

		timeLimit = new JComboBox<>(UIData.timeOptions);

		this.title = new JLabel("Chezz!");
		this.load = new JButton("Load game");
		this.start = new JButton("Start new game");
		this.exit = new JButton("Exit game");

		this.undoText = new JLabel("");
		this.undo = new JCheckBox("Undo enabled");

		this.newGame = new JLabel("New Game Settings");

		this.timeLimitText = new JLabel("");


		this.setBackground(UIData.BACKGROUND_COLOR);
		this.setSize(UIData.CONFIGURATIONPANEL_DIMENSION);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//LAYOUT
		contentPanel.setBackground(UIData.BACKGROUND_COLOR);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		newGameSettingsPanel.setBorder(UIData.BORDER_BLACK);
		newGameSettingsPanel.setBackground(UIData.BACKGROUND_COLOR);
		newGameSettingsPanel.setLayout(new BoxLayout(newGameSettingsPanel, BoxLayout.Y_AXIS));

		this.title.setFont(title.getFont().deriveFont(UIData.TITLE_FONT_SIZE));
		this.title.setFont(title.getFont().deriveFont(Font.BOLD));
		this.title.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.timeLimitText.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.timeLimitText.setText("Select a time limit.");

		this.timeLimit.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.timeLimit.setBackground(UIData.BACKGROUND_COLOR);


		this.undoText.setAlignmentX(Component.CENTER_ALIGNMENT );
		this.undoText.setText("Do you want to enable undo's?");
		//undoText.setBorder(UIData.BORDER_BLACK);

		this.undo.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.exit.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.newGame.setFont(newGame.getFont().deriveFont(UIData.FONT_SIZE));
		this.newGame.setFont(newGame.getFont().deriveFont(Font.BOLD));
		this.newGame.setAlignmentX(Component.CENTER_ALIGNMENT);



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


		contentPanel.add(Box.createRigidArea(UIData.SPACING));
		contentPanel.add(Box.createRigidArea(UIData.SPACING));
		contentPanel.add(Box.createRigidArea(UIData.SPACING));
		contentPanel.add(Box.createRigidArea(UIData.SPACING));

		contentPanel.add(title);


		contentPanel.add(Box.createRigidArea(UIData.SPACING));
		contentPanel.add(Box.createRigidArea(UIData.SPACING));
		contentPanel.add(Box.createRigidArea(UIData.SPACING));
		contentPanel.add(Box.createRigidArea(UIData.SPACING));

		newGameSettingsPanel.add(Box.createRigidArea(UIData.SPACING));
		newGameSettingsPanel.add(newGame);

		newGameSettingsPanel.add(Box.createRigidArea(UIData.SPACING));
		newGameSettingsPanel.add(Box.createRigidArea(UIData.SPACING));

		newGameSettingsPanel.add(timeLimitText);
		newGameSettingsPanel.add(timeLimit);

		newGameSettingsPanel.add(Box.createRigidArea(UIData.SPACING));

		newGameSettingsPanel.add(undoText);
		newGameSettingsPanel.add(undo);

		newGameSettingsPanel.add(Box.createRigidArea(UIData.SPACING));

		contentPanel.add(newGameSettingsPanel);

		contentPanel.add(Box.createRigidArea(UIData.SPACING));
		contentPanel.add(Box.createRigidArea(UIData.SPACING));

		contentPanel.add(startPanel);
		contentPanel.add(Box.createRigidArea(UIData.SPACING));

		contentPanel.add(exit);
		contentPanel.add(Box.createRigidArea(UIData.SPACING));



		this.setContentPane(contentPanel);

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
				settings.setTimeMsW(60000); //1 min
				settings.setTimeMsB(60000);
				break;
			case "2.5 min":
				//System.out.println("time limit 2.5 min");
				settings.setTimeMsW(150000); //2.5 min
				settings.setTimeMsB(150000);
				break;
			case "5 min":
				//System.out.println("time limit 5 min");
				settings.setTimeMsW(300000); //5 min
				settings.setTimeMsB(300000);
				break;
			case "30 min":
				//System.out.println("time limit 30 min");
				settings.setTimeMsW(1800000); //30 min
				settings.setTimeMsB(1800000);
				break;
			case "1 hour":
				//System.out.println("time limit 1 hour");
				settings.setTimeMsW(3600000); //60 min
				settings.setTimeMsB(3600000);

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
