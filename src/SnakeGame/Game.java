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
	 *	J�t�k inicializ�l�sa
	 */
	public Game (SnakeFrame snakeframe) {
		inputisvalid = true;						// Be�ll�tja, hogy mostant�l fogad inputot a j�t�kost�l
		frame = snakeframe;							// Elt�rolja a keretet k�s�bbi haszn�latra
		map = new Map(SNAKE_STARTING_PARTS);		// L�trehozza az �j p�ly�t
		panel = new SnakePanel(map);				// L�trehozza a grafikus panelt a j�t�k kirajzol�s�hoz
		snakeframe.add(panel);						// A kerethez hozz�adja a panelt
		snake = new Snake();						// L�trehozza a k�gy�t
		snakeSpawn();								// "Felt�lti" a k�gy�t �s elhelyezi a p�ly�n
		frame.addKeyListener(new Controls());		// Be�ll�tja nyilak ir�ny�t�s�t
		apple = new Apple(this);					// L�trehoz egy �j alm�t (a j�t�k sor�n nem csin�l t�bb alm�t, egy alma ker�l mindig m�s poz�ci�ba)
		map.spawnApple(apple);						// Elhelyezi a p�ly�n az alm�t
		timer = new Timer(DELAY, this);				// L�trehozza az �temez�t
		timer.start();								// Elind�tja az �temez�t
	}
	/*
	 *	K�gy� test�nek l�trehoz�sa �s p�ly�ra elhelyez�se
	 */
	private void snakeSpawn () {
		snake.setHead(map.spawnHead());
		for (int i = 0; i < SNAKE_STARTING_PARTS-1; i++) {
			snake.addBodyPart(map.spawnSnakeBodyPart());
		}
	}
	/*
	 *	Egy alma meg�tel�nek lereag�l�sa: alma �jrahelyez�se, pont �s k�gy� n�vel�se
	 */
	public void appleEaten (int score_earned) {
		snake.expandSnake();
		snake.addScore(score_earned);
		map.spawnApple(apple);
	}
	/*
	 *	J�t�k v�gezt�vel j�t�kos elhelyez�se dics�s�glist�ra
	 */
	public void addPlayerToBoard (String name) {
		Leaderboard board;
		//Norm�l eset: m�r l�tezik dics�s�glista, azt beolvassa �s hozz�adja az �j elemet
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
		//K�l�nleges eset: m�g nincs dics�s�glista, ilyenkor l�tre kell hozni �s azt�n hozz�adni az �j elemet
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
		//K�telez� kiv�telkezel�s
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *	Id�k�z�nk�nti l�p�sk�nyszer, az id�z�t� seg�ts�g�vel
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
	 * Bels� oszt�ly az ir�ny�t�s kezel�s�re
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
