package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class Apple implements Eatable {
	private static Color color = Color.RED;
	private Game game;
	
	public Apple (Game game) { this.game = game; }
	
	@Override
	public void eat() {
		game.appleEaten(1);
	}
	
	@Override
	public void draw(Graphics g, Coordinates coordinates) {
		Color temp = g.getColor();
		g.setColor(color);
		g.fillOval(coordinates.getx0(), coordinates.gety0(), SnakePanel.TILE_SIZE, SnakePanel.TILE_SIZE);
		g.setColor(temp);
	}
}
