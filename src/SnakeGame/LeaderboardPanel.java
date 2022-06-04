package SnakeGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class LeaderboardPanel extends JPanel {
	static final int SCREEN_WIDTH = 700;
	static final int SCREEN_HEIGHT= 700;
	private Leaderboard board;
	private JPanel upper;
	private JPanel main;
	
	public LeaderboardPanel () {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		upper = new JPanel();
		upper.setLayout(new FlowLayout());
		JButton go_back_button = new JButton("Back");
		go_back_button.addActionListener(new BackButtonActionListener());
		go_back_button.setFont(new Font("Calibri", Font.PLAIN, 30));
		upper.add(go_back_button);
		this.add(upper, BorderLayout.PAGE_START);
		
		main = new JPanel();
		main.setLayout(new GridLayout(10, 1, 0, 30));
		int border = 10;
		main.setBorder(new EmptyBorder(border, border, border, border));
		this.add(main, BorderLayout.CENTER);
		try {
			FileInputStream fis = new FileInputStream("leaderboard.ser");
			ObjectInputStream in = new ObjectInputStream(fis);
			board = (Leaderboard) in.readObject();
			in.close();
			addLeaderboardElements();
		} catch (FileNotFoundException e) {
			noLeaderboard();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void addLeaderboardElements () {
		for (int i = 0; i < 10; i++) {
			JLabel element = new JLabel("Name: " + board.getName(i) + " Score: " + board.getScore(i));
			element.setFont(new Font("Calibri", Font.PLAIN, 20));
			main.add(element);
		}
	}
	
	private void noLeaderboard () {
		for (int i = 0; i < 10; i++) {
			JLabel element = new JLabel("Name: " + " Score: 0");
			element.setFont(new Font("Calibri", Font.PLAIN, 20));
			main.add(element);
		}
	}
	
	private class BackButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LeaderboardPanel.this.setVisible(false);
			((SnakeFrame) SwingUtilities.getWindowAncestor(LeaderboardPanel.this)).add(new MenuPanel());
			((SnakeFrame) SwingUtilities.getWindowAncestor(LeaderboardPanel.this)).remove(LeaderboardPanel.this);
		}
		
	}
	
}
