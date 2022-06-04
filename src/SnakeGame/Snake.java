package SnakeGame;

import java.util.LinkedList;

public class Snake {
	private SnakeHead head;
	private int score;
	private LinkedList<SnakeBodyPart> parts;
	private boolean alive;
	private boolean move_last;
	
	public Snake () {
		parts = new LinkedList<SnakeBodyPart>();
		alive = true;
		score = 0;
		SnakePanel.score = 0;
		move_last = true;
	}
	
	public void addBodyPart (SnakeBodyPart bodypart) {
		parts.addLast(bodypart);
		bodypart.setSnake(this);
	}
	
	public void setHead (SnakeHead head) {
		this.head = head;
		addBodyPart(head);
	}
	
	public int getScore () { return score; }
	
	public void move () {
		if (move_last) {
			for (int i = 0; i < parts.size(); i++) {
				SnakeBodyPart current = parts.get(i);
				current.move(current.getTile().getNeighbour(current.getDirection()));
			}
		}
		else {
			for (int i = 0; i < parts.size()-1; i++) {
				SnakeBodyPart current = parts.get(i);
				current.move(current.getTile().getNeighbour(current.getDirection()));
			}
			move_last = true;
		}
		for (int i = parts.size()-1; i > 0; i--) {
			parts.get(i).setDirections(parts.get(i-1).getDirection());
		}
	}
	
	public void expandSnake () {
		parts.get(parts.size()-1).markForExpansion();
		move_last = false;
	}
	
	public void addScore (int a) { 
		score += a;
		SnakePanel.score = score;
	}
	
	public Directions getDirection () { return head.getDirection(); }
	
	public void setDirection (Directions d) { head.setDirections(d); }
	
	public void die () { alive = false; }
	
	public boolean isAlive () { return alive; }
}
