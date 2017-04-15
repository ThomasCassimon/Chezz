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
	//private JButton load;

	private TimePanel timePanel;

	private JPanel rightPanel;
	private JTextField moveInput;
	private JTextArea history;



	public SidePanel(GamePanel gamePanel)
	{
		super();

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setBackground(UIData.TEXT_BACKGROUND_COLOR);


		this.pause = new JButton("Pause");
		//pause.setPreferredSize(UIData.BUTTONS_DIMENSION);
		this.undo = new JButton(("Undo"));
		//undo.setPreferredSize(UIData.BUTTONS_DIMENSION);
		this.save = new JButton("Save");
		//save.setPreferredSize(UIData.BUTTONS_DIMENSION);
		this.exit = new JButton("Exit");
		//exit.setPreferredSize(UIData.BUTTONS_DIMENSION);
		//this.load = new JButton("Load");


		this.timePanel = new TimePanel();
		this.timePanel.setBackground(UIData.BACKGROUND);


		this.leftPanel = new JPanel();
		this.leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		this.leftPanel.setBackground(UIData.BACKGROUND);
		this.leftPanel.add(timePanel);
		this.leftPanel.add(pause);
		this.leftPanel.add(undo);
		this.leftPanel.add(save);
		this.leftPanel.add(exit);
		//this.leftPanel.add(load);



		this.history = new JTextArea("History:");
		this.history.setEditable(false);
		this.history.setPreferredSize(UIData.HISTORY_DIMENSION);
		this.history.setBackground(Color.WHITE);

		this.moveInput = new JTextField("<Insert your move here>");
		this.moveInput.setBackground(Color.WHITE);

		this.rightPanel = new JPanel();
		this.rightPanel.setPreferredSize(UIData.HISTORY_DIMENSION);
		this.rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		this.rightPanel.setBackground(UIData.BACKGROUND);
		this.rightPanel.setBorder(UIData.BORDER_BLACK);
		this.rightPanel.add(history);
		this.rightPanel.add(moveInput);

		this.pause.addActionListener(gamePanel);
		this.undo.addActionListener(gamePanel);
		this.save.addActionListener(gamePanel);
		//this.load.addActionListener(gamePanel);
		this.moveInput.addActionListener(gamePanel);
		this.moveInput.addMouseListener(gamePanel);


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
	 * @return undo
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

	//public JButton getLoad()
	{
		//return load;
	}

	public JTextField getMoveInput()
	{
		return moveInput;
	}

	public void setMoveInput(String text)
	{
		moveInput.setText(text);
	}

	public void setHistory(String history)
	{
		this.history.setText("History: \n" + history);
	}

}
