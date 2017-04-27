package Chess.UI;

import Chess.Game.GameManager;
import Chess.Utils.Parser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame implements ActionListener
{
	private GamePanel gamePanel;
	private ConfigurationPanel configurationPanel;
	private GameManager gameManager;


	public MainFrame()
	{
		gameManager = new GameManager();
		gameManager.init();
		configurationPanel = new ConfigurationPanel(gameManager);
		gamePanel = new GamePanel(gameManager, this);

		configurationPanel.getStart().addActionListener(this);
		configurationPanel.getLoad().addActionListener(this);
		configurationPanel.getExit().addActionListener(this);

		configurationPanel.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//System.out.println("Action performed");

		if(e.getSource() == configurationPanel.getStart())
		{
			configurationPanel.setVisible(false);
			gameManager.setSettings(configurationPanel.getSettings(),gamePanel);
			gamePanel.initBoard();
			gameManager.startChronometer();
			gamePanel.setVisible(true);
		}
		if(e.getSource() == configurationPanel.getLoad())
		{
			Parser.readFromFile(gameManager,configurationPanel);
		}
		else if(e.getSource() == gamePanel.getExit())
		{
			System.out.println("Exit");
			int choice = JOptionPane.showConfirmDialog(gamePanel, "Are you sure you want to exit the game? All unsaved progress will be lost.", "Exit", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,UIData.BK);

			if(choice == JOptionPane.YES_OPTION)
			{
				configurationPanel.setVisible(true);
				gamePanel.setVisible(false);
				gameManager = new GameManager();
				gameManager.init();
				gamePanel = new GamePanel(gameManager, this);
			}
		}
		else if(e.getSource() == configurationPanel.getExit())
		{
			System.exit(0);
		}

	}


	public static boolean isSpecialLayout()
	{
		String os = System.getProperty("os.name").toLowerCase();

		if (os.indexOf("mac") >= 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
