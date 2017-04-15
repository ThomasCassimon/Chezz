package Chess.UI;

import Chess.Game.GameManager;

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
		gamePanel = new GamePanel(gameManager);

		gamePanel.getExit().addActionListener(this);
		configurationPanel.getStart().addActionListener(this);

		configurationPanel.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == configurationPanel.getStart())
		{
			configurationPanel.setVisible(false);
			gamePanel = new GamePanel(gameManager);
			gamePanel.setVisible(true);
		}
		else if(e.getSource() == gamePanel.getExit())
		{
			System.out.println("Exit");
			int choice = JOptionPane.showConfirmDialog(gamePanel, "Are you sure you want to exit the game?", "Exit", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,UIData.BK);

			if(choice == JOptionPane.YES_OPTION)
			{
				configurationPanel.setVisible(true);
				gamePanel.setVisible(false);
				gameManager = new GameManager();
			}
		}
		System.out.println("not start or exit");
	}
}
