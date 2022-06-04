package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class SnakeHead extends SnakeBodyPart {
	private static Color color = Color.YELLOW;
	
	public SnakeHead(Tile pos, Directions d) {
		super(pos, d);
	}
	
	public void move (Tile tile) {
		if (tile == null) { 
			snake.die();
		}
		else {
			Eatable content = tile.getContent();
			if (content != null) {
				content.eat();
				super.move(tile);
			}
			else {
				super.move(tile);
			}
		}
	}
	
	public void draw (Graphics g, Coordinates coordinates) {
		Color temp = g.getColor();
		g.setColor(color);
		g.fillRect(coordinates.getx0(), coordinates.gety0(), coordinates.getx1()-coordinates.getx0(), coordinates.gety1()-coordinates.gety0());
		g.setColor(temp);
	}
	
}
