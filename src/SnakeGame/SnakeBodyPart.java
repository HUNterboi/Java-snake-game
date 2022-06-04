package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class SnakeBodyPart implements Eatable {
	protected Tile pos;
	private Directions d;
	protected Snake snake;
	private boolean expand;
	private static Color color = Color.GREEN;
	
	public SnakeBodyPart (Tile pos, Directions d) {
		this.pos = pos;
		this.d = d;
		expand = false;
	}
	
	public Tile getTile () { return pos; }
	
	public Directions getDirection () { return d; }
	
	public void setDirections (Directions d) { this.d = d; }
	
	public void setSnake (Snake snake) { this.snake = snake; }
	
	public void markForExpansion () { expand = true; }
	
	public void move (Tile tile) {
		if (tile != null) {
			if (expand) {
				SnakeBodyPart new_part = new SnakeBodyPart(this.pos, this.d);
				this.pos.setContent(new_part);
				this.pos = tile;
				tile.setContent(this);
				snake.addBodyPart(new_part);
				expand = false;
			}
			else {
				tile.setContent(this);
				pos.setContent(null);
				pos = tile;
			}
		}
	}
	
	@Override
	public void eat () {
		snake.die();
	}
	
	@Override
	public void draw (Graphics g, Coordinates coordinates) {
		Color temp = g.getColor();
		g.setColor(color);
		g.fillRect(coordinates.getx0(), coordinates.gety0(), coordinates.getx1()-coordinates.getx0(), coordinates.gety1()-coordinates.gety0());
		g.setColor(temp);
	}
}
