package SnakeGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.Timer;

public class Game implements ActionListener{
	private Map map;
	private Snake snake;
	private int SNAKE_STARTING_PARTS = 3;
	private int DELAY = 100;
	private SnakePanel panel;
	private SnakeFrame frame;
	private Timer timer;
	private Apple apple;
	private boolean inputisvalid;
	
	/*
	 *	Játék inicializálása
	 */
	public Game (SnakeFrame snakeframe) {
		inputisvalid = true;						// Beállítja, hogy mostantól fogad inputot a játékostól
		frame = snakeframe;							// Eltárolja a keretet késõbbi használatra
		map = new Map(SNAKE_STARTING_PARTS);		// Létrehozza az új pályát
		panel = new SnakePanel(map);				// Létrehozza a grafikus panelt a játék kirajzolásához
		snakeframe.add(panel);						// A kerethez hozzáadja a panelt
		snake = new Snake();						// Létrehozza a kígyót
		snakeSpawn();								// "Feltölti" a kígyót és elhelyezi a pályán
		frame.addKeyListener(new Controls());		// Beállítja nyilak irányítását
		apple = new Apple(this);					// Létrehoz egy új almát (a játék során nem csinál több almát, egy alma kerül mindig más pozícióba)
		map.spawnApple(apple);						// Elhelyezi a pályán az almát
		timer = new Timer(DELAY, this);				// Létrehozza az ütemezõt
		timer.start();								// Elindítja az ütemezõt
	}
	/*
	 *	Kígyó testének létrehozása és pályára elhelyezése
	 */
	private void snakeSpawn () {
		snake.setHead(map.spawnHead());
		for (int i = 0; i < SNAKE_STARTING_PARTS-1; i++) {
			snake.addBodyPart(map.spawnSnakeBodyPart());
		}
	}
	/*
	 *	Egy alma megételének lereagálása: alma újrahelyezése, pont és kígyõ növelése
	 */
	public void appleEaten (int score_earned) {
		snake.expandSnake();
		snake.addScore(score_earned);
		map.spawnApple(apple);
	}
	/*
	 *	Játék végeztével játékos elhelyezése dicsõséglistára
	 */
	public void addPlayerToBoard (String name) {
		Leaderboard board;
		//Normál eset: már létezik dicsõséglista, azt beolvassa és hozzáadja az új elemet
		try {
			FileInputStream fis = new FileInputStream("leaderboard.ser");
			ObjectInputStream in = new ObjectInputStream(fis);
			board = (Leaderboard) in.readObject();
			in.close();
			board.add(name, snake.getScore());
			FileOutputStream fos = new FileOutputStream("leaderboard.ser");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(board);
			out.close();
		}
		//Különleges eset: még nincs dicsõséglista, ilyenkor létre kell hozni és aztán hozzáadni az új elemet
		catch (FileNotFoundException e) {
			try {
				board = new Leaderboard();
				board.add(name, snake.getScore());
				FileOutputStream fos = new FileOutputStream("leaderboard.ser");
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(board);
				out.close();
			}catch (FileNotFoundException exc) {
				exc.printStackTrace();
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		} 
		//Kötelezõ kivételkezelés
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *	Idõközönkénti lépéskényszer, az idõzítõ segítségével
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(snake.isAlive()) {
			snake.move();
			inputisvalid = true;
		}
		else {
			timer.stop();
			new GetNameFrame(this);
			panel.setVisible(false);
			frame.remove(panel);
			frame.add(new MenuPanel());
		}
		panel.repaint();
	}
	
	/*
	 * Belsõ osztály az irányítás kezelésére
	 */
	private class Controls extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (inputisvalid) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_UP:
						if (snake.getDirection() != Directions.DOWN)
							snake.setDirection(Directions.UP);
						break;
					case KeyEvent.VK_LEFT:
						if (snake.getDirection() != Directions.RIGHT)
							snake.setDirection(Directions.LEFT);
						break;
					case KeyEvent.VK_DOWN:
						if (snake.getDirection() != Directions.UP)
							snake.setDirection(Directions.DOWN);
						break;
					case KeyEvent.VK_RIGHT:
						if (snake.getDirection() != Directions.LEFT)
							snake.setDirection(Directions.RIGHT);
				}
			}
			inputisvalid = false;
		}
		
	}
}
