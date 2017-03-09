package Chess.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Astrid on 23/02/2017.
 */
public class SidePanel extends JPanel
{
	private JPanel leftPanel;
	private JButton pause;
	private JButton undo;
	private TimePanel timePanel;

	private JPanel rightPanel;
	private JTextField textField;
	private JTextArea history;



	public SidePanel()
	{
		super();

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setBackground(UIData.TEXT_BACKGROUND_COLOR);


		pause = new JButton("Pause");
		undo = new JButton(("Undo"));

		timePanel = new TimePanel();
		timePanel.setBackground(UIData.BACKGROUND);

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(UIData.BACKGROUND);
		leftPanel.add(pause);
		leftPanel.add(undo);
		leftPanel.add(timePanel);

		history = new JTextArea("History:");
		history.setEditable(false);
		history.setPreferredSize(new Dimension(100,750));
		history.setBackground(Color.WHITE);

		textField = new JTextField("<Insert your move here>");
		textField.setBackground(Color.WHITE);

		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(UIData.BACKGROUND);
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightPanel.add(history);
		rightPanel.add(textField);




		this.add(pause);
		this.add(undo);
		this.add(timePanel);
		this.add(rightPanel);

		this.setBackground(UIData.BACKGROUND_COLOR);
		//this.add(history);
		//this.add(textField);
	}
}
