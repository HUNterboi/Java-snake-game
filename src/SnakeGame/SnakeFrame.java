package SnakeGame;

import javax.swing.JFrame;

public class SnakeFrame extends JFrame {
	
	public SnakeFrame () {
		add(new MenuPanel());
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setFocusable(true);
	}
	
}
