package SnakeGame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GetNameFrame extends JFrame {
	private Game game;
	private JTextField input;
	
	public GetNameFrame (Game game) {
		this.game = game;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		setVisible(true);
		setTitle("Possible New Record");
		setResizable(false);
		setLocationRelativeTo(null);
		input = new JTextField(20);
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ConfirmButtonActionListener());
		add(input);
		add(confirm);
		pack();
	}
	
	private class ConfirmButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			game.addPlayerToBoard(input.getText());
			GetNameFrame.this.dispose();
		}
	}
	
}
