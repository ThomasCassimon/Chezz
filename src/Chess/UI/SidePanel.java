package Chess.UI;

import javax.swing.*;
import java.awt.*;


public class SidePanel extends JPanel
{
	private JPanel leftPanel;
	private JButton pause;
	private JButton undo;
	private JButton save;
	private JButton exit;

	private TimePanel timePanel;
	private JPanel buttonPanel;

	private JPanel rightPanel;
	private JTextField moveInput;
	private JTextArea history;



	public SidePanel(GamePanel gamePanel)
	{
		super();

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setBackground(UIData.TEXT_BACKGROUND_COLOR);


		pause = new JButton("Pause");
		//pause.setPreferredSize(UIData.BUTTONS_DIMENSION);
		undo = new JButton(("Undo"));
		//undo.setPreferredSize(UIData.BUTTONS_DIMENSION);
		save = new JButton("Save");
		//save.setPreferredSize(UIData.BUTTONS_DIMENSION);
		exit = new JButton("Exit");
		//exit.setPreferredSize(UIData.BUTTONS_DIMENSION);

		timePanel = new TimePanel();
		timePanel.setBackground(UIData.BACKGROUND);


		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(UIData.BACKGROUND);
		leftPanel.add(timePanel);
		leftPanel.add(pause);
		leftPanel.add(undo);
		leftPanel.add(save);
		leftPanel.add(exit);



		history = new JTextArea("History:");
		history.setEditable(false);
		history.setPreferredSize(UIData.HISTORY_DIMENSION);
		history.setBackground(Color.WHITE);

		moveInput = new JTextField("<Insert your move here>");
		moveInput.setBackground(Color.WHITE);

		rightPanel = new JPanel();
		rightPanel.setPreferredSize(UIData.HISTORY_DIMENSION);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(UIData.BACKGROUND);
		rightPanel.setBorder(UIData.BORDER_BLACK);
		rightPanel.add(history);
		rightPanel.add(moveInput);

		pause.addActionListener(gamePanel);
		undo.addActionListener(gamePanel);
		exit.addActionListener(gamePanel);
		save.addActionListener(gamePanel);
		moveInput.addActionListener(gamePanel);
		moveInput.addMouseListener(gamePanel);


		this.add(leftPanel);
		this.add(rightPanel);


		this.setBackground(UIData.BACKGROUND_COLOR);
	}

	/**
	 *  returns JButton pause
	 * @return pause
	 */
	public JButton getPause()
	{
		return pause;
	}

	/**
	 * returns JButton undo
	 * @return unod
	 */
	public JButton getUndo()
	{
		return undo;
	}

	public JButton getSave()
	{
		return save;
	}

	public JButton getExit()
	{
		return exit;
	}

	public JTextField getMoveInput()
	{
		return moveInput;
	}

	public void setMoveInput(String text)
	{
		moveInput.setText(text);
	}

}
