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
		configurationPanel = new ConfigurationPanel(gameManager, this);
		gamePanel = new GamePanel(gameManager, this);

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

			if(Parser.readFromFile(gameManager,configurationPanel,this))
			{
				//gameManager.startChronometer();
				configurationPanel.setVisible(false);
				gamePanel.initBoard();
				gamePanel.setVisible(true);
			}
		}
		else if(e.getSource() == gamePanel.getExit())
		{
			if(e.getActionCommand().equals("Exit"))
			{
				gameManager = new GameManager();
				gameManager.init();
				configurationPanel = new ConfigurationPanel(gameManager, this);
				configurationPanel.setVisible(true);
				gamePanel.setVisible(false);
				gamePanel = new GamePanel(gameManager, this);
			}
			else
			{
				System.out.println("Exit");
				int choice = JOptionPane.showConfirmDialog(gamePanel, "Are you sure you want to exit the game? All unsaved progress will be lost.", "Exit", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,UIData.BK);


				if(choice == JOptionPane.YES_OPTION)
				{
					gameManager = new GameManager();
					gameManager.init();
					configurationPanel = new ConfigurationPanel(gameManager, this);
					configurationPanel.setVisible(true);
					gamePanel.setVisible(false);
					gamePanel = new GamePanel(gameManager, this);
				}
			}
		}
		else if(e.getSource() == configurationPanel.getExit())
		{
			System.exit(0);
		}

	}

	public GamePanel getGamePanel()
	{
		return this.gamePanel;
	}
}
