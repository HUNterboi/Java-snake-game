package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
	private Coordinates coordinates;
	private Eatable content;
	private Neighbours neighbours; 
	
	public Tile (int x0, int y0, int x1, int y1) {
		coordinates = new Coordinates(x0, y0, x1, y1);
		content = null;
		neighbours = new Neighbours();
	}
	
	public void setNeighbour (Directions d, Tile tile) {
		if (d == Directions.UP)
			neighbours.upper = tile;
		else if (d == Directions.LEFT)
			neighbours.left = tile;
		else if (d == Directions.DOWN)
			neighbours.down = tile;
		else if (d == Directions.RIGHT)
			neighbours.right = tile;
	}
	
	public Tile getNeighbour (Directions d) {
		if (d == Directions.UP)
			return neighbours.upper;
		else if (d == Directions.LEFT)
			return neighbours.left;
		else if (d == Directions.DOWN)
			return neighbours.down;
		else
			return neighbours.right;
	}
	
	public void setContent (Eatable stuff) {
		content = stuff;
	}
	
	public Eatable getContent () {
		return content;
	}
	
	public void draw (Graphics g) {
		if (content != null) {
			content.draw(g, coordinates);
		}
		
		else {
			Color temp = g.getColor();
			g.setColor(new Color(coordinates.getx0()*255/SnakePanel.SCREEN_WIDTH, 0, coordinates.gety0()*255/SnakePanel.SCREEN_WIDTH));
			g.fillRect(coordinates.getx0(), coordinates.gety0(), coordinates.getx1()-coordinates.getx0(), coordinates.gety1()-coordinates.gety0());
			g.setColor(temp);
		}
	}
	
	private class Neighbours {
		public Tile upper;
		public Tile left;
		public Tile down;
		public Tile right;
		
		public Neighbours () {
			upper = null;
			left = null;
			down = null;
			right = null;
		}
	}
}
