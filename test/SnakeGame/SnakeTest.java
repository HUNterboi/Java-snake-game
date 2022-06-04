package SnakeGame;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Assert;
import org.junit.Test;

public class SnakeTest {

	@Test
	public void gameTest() {
		SnakeFrame frame = new SnakeFrame();
		frame.removeAll();
		Game game = new Game(frame);
		Assert.assertNotNull(game);
	}
	@Test
	public void headTest () {
		Map map = new Map(3);
		map.spawnHead();
		Assert.assertEquals(map.getTile(2+3, 2).getContent().getClass(), SnakeHead.class);
	}
	@Test
	public void bodyTest () {
		Map map = new Map(3);
		map.spawnHead();
		for(int i = 0; i < 2; i++) {
			map.spawnSnakeBodyPart();
		}
		Assert.assertEquals(map.getTile(2+1, 2).getContent().getClass(), SnakeBodyPart.class);
	}
	@Test
	public void appleSpawnTest() {
		Apple apple = new Apple(null);
		Map map = new Map (0);
		map.spawnApple(apple);
		int i_cord = 0, j_cord = 0;
		for(int i = 0; i < SnakePanel.SCREEN_WIDTH/SnakePanel.TILE_SIZE; i++) {
			for (int j = 0; j < SnakePanel.SCREEN_HEIGHT/SnakePanel.TILE_SIZE; j++) {
				if (map.getTile(i, j).getContent() != null) {
					i_cord = i;
					j_cord = j;
					break;
				}
			}
		}
		Assert.assertEquals(map.getTile(i_cord, j_cord).getContent().getClass(), Apple.class);
	}
	@Test
	public void scoreTest() {
		SnakeFrame frame = new SnakeFrame();
		frame.removeAll();
		Game game = new Game(frame);
		game.appleEaten((SnakePanel.SCREEN_WIDTH/SnakePanel.TILE_SIZE)*(SnakePanel.SCREEN_HEIGHT/SnakePanel.TILE_SIZE));
		game.addPlayerToBoard("Test");
		Leaderboard board = null;
		try {
			FileInputStream fis = new FileInputStream("leaderboard.ser");
			ObjectInputStream in = new ObjectInputStream(fis);
			board = (Leaderboard) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(board.getName(0), "Test");
	}
}
