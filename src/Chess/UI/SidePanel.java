package Chess.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Astrid on 23/02/2017.
 */
public class SidePanel extends JPanel
{

	private JButton pause;
	private JButton undo;

	private JPanel textPanel;

	private JTextField textField;
	private JTextArea history;

	public SidePanel()
	{
		super();

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setBackground(UIData.TEXT_BACKGROUND_COLOR);

		textPanel = new JPanel();
		pause = new JButton("Pause");
		undo = new JButton(("Undo"));
		textField = new JTextField("<Insert your move here>");
		history = new JTextArea("History:");
		history.setEditable(false);

		history.setPreferredSize(new Dimension(100,750));

		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		textPanel.setBackground(Color.WHITE);
		history.setBackground(Color.WHITE);
		textField.setBackground(Color.WHITE);
		textPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textPanel.add(history);
		textPanel.add(textField);


		this.add(pause);
		this.add(undo);
		this.add(textPanel);

		this.setBackground(UIData.BACKGROUND_COLOR);
		//this.add(history);
		//this.add(textField);
	}
}
