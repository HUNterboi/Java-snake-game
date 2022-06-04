package SnakeGame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MenuPanel extends JPanel {
	static final int SCREEN_WIDTH = 700;
	static final int SCREEN_HEIGHT= 700;
	
	public MenuPanel () {
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setLayout(new GridLayout(3, 1, 0, 75));
		int border = 150;
		setBorder(new EmptyBorder(border, border, border, border));
		
		JButton game_button = new JButton("New Game");
		JButton lead_button = new JButton("Leaderboard");
		JButton exit_button = new JButton("Exit");
		game_button.setFont(new Font("Calibri", Font.PLAIN, 40));
		lead_button.setFont(new Font("Calibri", Font.PLAIN, 40));
		exit_button.setFont(new Font("Calibri", Font.PLAIN, 40));
		game_button.addActionListener(new NewGameActionListener());
		lead_button.addActionListener(new LeadActionListener());
		exit_button.addActionListener(new ExitActionListener());
		add(game_button);
		add(lead_button);
		add(exit_button);
	}
	
	private class NewGameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MenuPanel.this.setVisible(false);
			new Game((SnakeFrame) SwingUtilities.getWindowAncestor(MenuPanel.this));
			((SnakeFrame) SwingUtilities.getWindowAncestor(MenuPanel.this)).remove(MenuPanel.this);
		}
	}
	private class LeadActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MenuPanel.this.setVisible(false);
			((SnakeFrame) SwingUtilities.getWindowAncestor(MenuPanel.this)).add(new LeaderboardPanel());
			((SnakeFrame) SwingUtilities.getWindowAncestor(MenuPanel.this)).remove(MenuPanel.this);
		}
	}
	private class ExitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((SnakeFrame) SwingUtilities.getWindowAncestor(MenuPanel.this)).dispose();
		}
	}
}
