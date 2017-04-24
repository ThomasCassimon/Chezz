package Chess.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SidePanel extends JPanel
{

	private JPanel leftPanel;
	private JButton pause;
	private JButton undo;
	private JButton save;
	private JButton exit;

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
		this.undo = new JButton(("Undo"));
		this.save = new JButton("Save");
		this.exit = new JButton("Exit");



		this.timePanel = new TimePanel(gamePanel.getGameManager());
		this.timePanel.setBackground(UIData.BACKGROUND_COLOR);


		this.leftPanel = new JPanel();
		this.leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		this.leftPanel.setBackground(UIData.BACKGROUND_COLOR);
		this.leftPanel.add(timePanel);
		this.leftPanel.add(pause);
		this.leftPanel.add(undo);
		this.leftPanel.add(save);
		this.leftPanel.add(exit);

		this.pause.setAlignmentX(CENTER_ALIGNMENT);
		this.undo.setAlignmentX(CENTER_ALIGNMENT);
		this.save.setAlignmentX(CENTER_ALIGNMENT);
		this.exit.setAlignmentX(CENTER_ALIGNMENT);


		this.history = new JTextArea("History:");
		this.history.setEditable(false);
		this.history.setPreferredSize(UIData.HISTORY_DIMENSION);
		this.history.setBackground(UIData.TEXT_BACKGROUND_COLOR);

		this.moveInput = new JTextField("<Insert your move here>");
		this.moveInput.setBackground(UIData.TEXT_BACKGROUND_COLOR);

		this.rightPanel = new JPanel();
		this.rightPanel.setPreferredSize(UIData.HISTORY_DIMENSION);
		this.rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		this.rightPanel.setBackground(UIData.BACKGROUND_COLOR);
		this.rightPanel.setBorder(UIData.BORDER_BLACK);
		this.rightPanel.add(history);
		this.rightPanel.add(moveInput);

		this.pause.addActionListener(gamePanel);
		this.undo.addActionListener(gamePanel);
		this.save.addActionListener(gamePanel);
		this.moveInput.addActionListener(gamePanel);
		this.moveInput.addMouseListener(gamePanel);
		this.exit.addActionListener(gamePanel);


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
