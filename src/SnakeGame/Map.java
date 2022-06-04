package SnakeGame;

import java.util.Random;

public class Map {
	private Tile [][] tiles;
	private int spart_spawn_counter;
	private Random random;
	int max_rows;
	int max_columns;
	
	/*
	 *	Pálya inicializálása
	 */
	public Map (int snake_max) {
		spart_spawn_counter = snake_max;
		random = new Random();
		max_rows = SnakePanel.SCREEN_HEIGHT/SnakePanel.TILE_SIZE;
		max_columns = SnakePanel.SCREEN_WIDTH/SnakePanel.TILE_SIZE;
		tiles = new Tile[max_columns][max_rows];
		for (int i = 0; i < max_columns; i++) {
			for (int j = 0; j < max_rows; j++) {
				tiles[i][j] = new Tile(i * SnakePanel.TILE_SIZE, j * SnakePanel.TILE_SIZE, (i+1) * SnakePanel.TILE_SIZE, (j+1) * SnakePanel.TILE_SIZE);
			}
		}
		setNeighbours();
	}
	
	/*
	 * Szomszédságok beállítása
	 */
	private void setNeighbours () {
		/*
		 *	Különleges esetek kezelése:
		 *		Sarkok kezelése:
		 */
		tiles[0][0].setNeighbour(Directions.RIGHT, tiles[1][0]);
		tiles[0][0].setNeighbour(Directions.DOWN, tiles[0][1]);
		
		tiles[max_rows-1][0].setNeighbour(Directions.LEFT, tiles[max_rows-2][0]);
		tiles[max_rows-1][0].setNeighbour(Directions.DOWN, tiles[max_rows-1][1]);
		
		tiles[max_rows-1][max_columns-1].setNeighbour(Directions.UP, tiles[max_rows-1][max_columns-2]);
		tiles[max_rows-1][max_columns-1].setNeighbour(Directions.LEFT, tiles[max_rows-2][max_columns-1]);
		
		tiles[0][max_columns-1].setNeighbour(Directions.UP, tiles[0][max_columns-2]);
		tiles[0][max_columns-1].setNeighbour(Directions.RIGHT, tiles[1][max_columns-1]);
		/*
		 * 		Oldalak (sarkok nélkül) kezelése
		 */
		for (int i = 1; i < max_rows-1; i++) {
			tiles[i][0].setNeighbour(Directions.UP, null);
			tiles[i][0].setNeighbour(Directions.LEFT, tiles[i-1][0]);
			tiles[i][0].setNeighbour(Directions.DOWN, tiles[i][1]);
			tiles[i][0].setNeighbour(Directions.RIGHT, tiles[i+1][0]);
			
			tiles[i][max_columns-1].setNeighbour(Directions.UP, tiles[i][max_columns-2]);
			tiles[i][max_columns-1].setNeighbour(Directions.LEFT, tiles[i-1][max_columns-1]);
			tiles[i][max_columns-1].setNeighbour(Directions.DOWN, null);
			tiles[i][max_columns-1].setNeighbour(Directions.RIGHT, tiles[i+1][max_columns-1]);
		}
		
		for (int i = 1; i < max_columns-1; i++) {
			tiles[0][i].setNeighbour(Directions.UP, tiles[0][i-1]);
			tiles[0][i].setNeighbour(Directions.LEFT, null);
			tiles[0][i].setNeighbour(Directions.DOWN, tiles[0][i+1]);
			tiles[0][i].setNeighbour(Directions.RIGHT, tiles[1][i]);
			
			tiles[max_rows-1][i].setNeighbour(Directions.UP, tiles[max_rows-1][i-1]);
			tiles[max_rows-1][i].setNeighbour(Directions.LEFT, tiles[max_rows-2][i]);
			tiles[max_rows-1][i].setNeighbour(Directions.DOWN, tiles[max_rows-1][i+1]);
			tiles[max_rows-1][i].setNeighbour(Directions.RIGHT, null);
		}
		/*
		 *	"Átlagos" esetek, a pálya belsejének kezelése
		 */
		for (int i = 1; i < max_rows-1; i++) {
			for (int j = 1; j < max_columns-1; j++) {
				tiles[i][j].setNeighbour(Directions.UP, tiles[i][j-1]);
				tiles[i][j].setNeighbour(Directions.LEFT, tiles[i-1][j]);
				tiles[i][j].setNeighbour(Directions.DOWN, tiles[i][j+1]);
				tiles[i][j].setNeighbour(Directions.RIGHT, tiles[i+1][j]);
			}
		}
	}
	
	public SnakeBodyPart spawnSnakeBodyPart () {
		SnakeBodyPart bodypart = new SnakeBodyPart(tiles[2+spart_spawn_counter][2], Directions.RIGHT);
		tiles[2+spart_spawn_counter][2].setContent(bodypart);
		spart_spawn_counter--;
		return bodypart;
	}
	
	public SnakeHead spawnHead () {
		SnakeHead head = new SnakeHead(tiles[2+spart_spawn_counter][2], Directions.RIGHT);
		tiles[2+spart_spawn_counter][2].setContent(head);
		spart_spawn_counter--;
		return head;
	}
	
	public Tile getTile (int i, int j) {
		return tiles[i][j];
	}
	
	public void spawnApple (Apple apple) {
		int x = random.nextInt(max_columns-1);
		int y = random.nextInt(max_rows-1);
		while (tiles[x][y].getContent() != null) {
			x = random.nextInt(max_columns-1);
			y = random.nextInt(max_rows-1);
		}
		tiles[x][y].setContent(apple);
	}
	
	
}
