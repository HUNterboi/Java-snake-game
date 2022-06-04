package SnakeGame;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;

public class Leaderboard implements Serializable {
	private LinkedList<PlayerScore> top_players;
	
	public Leaderboard () {
		top_players = new LinkedList<PlayerScore>();
		for (int i = 0; i < 10; i++) {
			top_players.add(new PlayerScore("", 0));
		}
	}
	
	public void add (String name, int score) {
		top_players.add(new PlayerScore(name, score));
		top_players.sort(new ScoreComparator());
		while(top_players.size() > 10) {
			top_players.removeLast();
		}
	}
	
	public String getName (int index) { return top_players.get(index).getName(); }
	
	public int getScore (int index) { return top_players.get(index).getScore(); }
	
	private class PlayerScore implements Serializable {
		private String name;
		private int score;
		
		public String getName () { return name; }
		
		public int getScore () { return score; }
		
		public PlayerScore (String name, int score) {
			this.name = name;
			this.score = score;
		}
	}
	
	private class ScoreComparator implements Comparator<PlayerScore> {
		@Override
		public int compare(PlayerScore o1, PlayerScore o2) {
			if (o2.score > o1.score)
				return 1;
			else if (o1.score > o2.score)
				return -1;
			else
				return 0;
		}
	}
}
