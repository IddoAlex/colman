package algorithms.mazeGenerators;

import java.util.ArrayList;

public class Maze3d {
	/*
	 * X - Length Y - Height Z - Width int[Y][Z][X] maze;
	 */
	public static final int WALL = 1;
	public static final int PASS = 0;

	private int[][][] m_maze;
	private static int DEFAULTSIZE = 5;

	private int m_height;
	private int m_width;
	private int m_length;

	private Position entryPosition = null;
	private Position exitPosition = null;

	public Maze3d() {
		this(DEFAULTSIZE);
	}

	public Maze3d(int arrSize) {
		this(arrSize, arrSize, arrSize);
	}

	public Maze3d(int height, int width, int length) {
		m_maze = new int[height][width][length];
		m_height = height;
		m_width = width;
		m_length = length;
	}

	public Maze3d(int[][][] anArray) {
		m_maze = anArray.clone();
		m_height = m_maze.length;
		m_width = m_maze[0].length;
		m_length = m_maze[0][0].length;
	}

	public void printMaze() {
		for (int i = 0; i < m_maze.length; i++) {
			System.out.println("{");
			for (int j = 0; j < m_maze[0].length; j++) {
				System.out.print("\t{ ");
				for (int k = 0; k < m_maze[0][0].length; k++) {
					System.out.print(m_maze[i][j][k] + " ");
				}
				System.out.println("},");
			}
			System.out.println("},");
		}
	}

	public int getHeight() {
		return m_height;
	}

	public int getWidth() {
		return m_width;
	}

	public int getLength() {
		return m_length;
	}

	public int[][][] getArray() {
		return m_maze;
	}

	public Position getStartPosition() {
		return entryPosition;
	}

	public void setEntryPosition(Position position) {
		this.entryPosition = position;
	}

	public Position getGoalPosition() {
		return exitPosition;
	}

	public void setExitPosition(Position position) {
		this.exitPosition = position;
	}

	public void setWall(Position point) {
		m_maze[point.getHeight()][point.getWidth()][point.getLength()] = WALL;
	}

	public void setPass(Position point) {
		m_maze[point.getHeight()][point.getWidth()][point.getLength()] = PASS;
	}
	
	public boolean isWall(Position p){
		return m_maze[p.getHeight()][p.getWidth()][p.getLength()] == WALL;
	}
	
	public boolean isPass(Position p){
		return m_maze[p.getHeight()][p.getWidth()][p.getLength()] == PASS;
	}

	public boolean isLegalPosition(Position point) {
		return isLegalHeight(point) && isLegalWidth(point) && isLegalLength(point);
	}

	private boolean isLegalHeight(Position point) {
		return point.getHeight() < getHeight() && point.getHeight() >= 0;
	}

	private boolean isLegalWidth(Position point) {
		return point.getWidth() < getWidth() && point.getWidth() >= 0;
	}

	private boolean isLegalLength(Position point) {
		return point.getLength() < getLength() && point.getLength() >= 0;
	}

	public int[][] getCrossSectionByX(int num) {
		if (num < 0 || num >= getLength()) {
			throw new IndexOutOfBoundsException();
		}

		int[][] maze2d = new int[getHeight()][getWidth()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				maze2d[i][j] = m_maze[i][j][num];
			}
		}
		return maze2d;
	}

	public int[][] getCrossSectionByY(int num) {
		if (num < 0 || num >= getHeight()) {
			throw new IndexOutOfBoundsException();
		}

		int[][] maze2d = new int[getWidth()][getLength()];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getLength(); j++) {
				maze2d[i][j] = m_maze[num][i][j];
			}
		}
		return maze2d;
	}

	public int[][] getCrossSectionByZ(int num) {
		if (num < 0 || num >= getWidth()) {
			throw new IndexOutOfBoundsException();
		}

		int[][] maze2d = new int[getHeight()][getLength()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getLength(); j++) {
				maze2d[i][j] = m_maze[i][num][j];
			}
		}
		return maze2d;
	}

	public void initWalls() {
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				for (int k = 0; k < getLength(); k++) {
					m_maze[i][j][k] = WALL;
				}
			}
		}
	}
	
	public int getPositionValue(Position pos)
	{
		if(isLegalPosition(pos))
		{
			return m_maze[pos.getHeight()][pos.getWidth()][pos.getLength()];
		}
		else
			return -1;
	}
	
	public ArrayList<Position> getPossibleMoves(Position p)
	{
		ArrayList<Position> list = new ArrayList<>();
		Position tmp = p.clone();
		
		tmp.moveBackwards();
		if(isLegalPosition(tmp))
		{
			list.add(tmp);
		}
		tmp = p.clone();
		
		tmp.moveDown();
		if(isLegalPosition(tmp))
		{
			list.add(tmp);
		}
		tmp = p.clone();
		
		tmp.moveForwards();
		if(isLegalPosition(tmp))
		{
			list.add(tmp);
		}
		tmp = p.clone();
		
		tmp.moveLeft();
		if(isLegalPosition(tmp))
		{
			list.add(tmp);
		}
		tmp = p.clone();
		
		tmp.moveRight();
		if(isLegalPosition(tmp))
		{
			list.add(tmp);
		}
		tmp = p.clone();
		
		tmp.moveUp();
		if(isLegalPosition(tmp))
		{
			list.add(tmp);
		}
		tmp = p.clone();
		
		return list;
	}
}
