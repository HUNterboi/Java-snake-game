package SnakeGame;

public class Coordinates {
	private int x0;
	private int y0;
	private int x1;
	private int y1;
	
	public Coordinates (int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	public int getx0 () { return x0; }
	public int gety0 () { return y0; }
	public int getx1 () { return x1; }
	public int gety1 () { return y1; }
	
}
