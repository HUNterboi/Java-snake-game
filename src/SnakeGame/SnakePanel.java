package SnakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SnakePanel extends JPanel {
	static final int SCREEN_WIDTH = 700;
	static final int SCREEN_HEIGHT= 700;
	static final int TILE_SIZE = 25;
	static int score = 0;
	private Map map;
	
	public SnakePanel (Map map) {
		this.map = map;
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		super.paintComponent(g);
//		for (int i = 0; i < SCREEN_WIDTH/TILE_SIZE; i++) {
//			g.drawLine(i*TILE_SIZE, 0, i*TILE_SIZE, SCREEN_HEIGHT);
//		}
//		for (int i = 0; i < SCREEN_HEIGHT/TILE_SIZE; i++) {
//			g.drawLine(0, i*TILE_SIZE, SCREEN_WIDTH, i*TILE_SIZE);
//		}
		for (int i = 0; i < SCREEN_WIDTH/TILE_SIZE; i++) {
			for (int j = 0; j < SCREEN_HEIGHT/TILE_SIZE; j++) {
				map.getTile(i, j).draw(g);
			}
		}
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score))/2, g.getFont().getSize());
	}	
}
