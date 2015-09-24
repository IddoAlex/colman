package algorithms.mazeGenerators;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Maze3d {
	/*
	 * X - Length Y - Height Z - Width int[Y][Z][X] maze;
	 */
	public static final int WALL = 1;
	public static final int PASS = 0;

	private byte[][][] m_maze;
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
		m_maze = new byte[height][width][length];
		m_height = height;
		m_width = width;
		m_length = length;
	}

	public Maze3d(byte[][][] anArray) {
		m_maze = anArray.clone();
		m_height = m_maze.length;
		m_width = m_maze[0].length;
		m_length = m_maze[0][0].length;
	}
	
	public Maze3d(byte[] arr) {
		buildMaze(arr);
	}

	public void printMaze() {
		System.out.println(m_maze.toString());
	}

	public String toString() {
		String mazeString = "";
		for (int i = 0; i < m_maze.length; i++) {
			mazeString += "{\n";
			for (int j = 0; j < m_maze[0].length; j++) {
				mazeString += "\t{ ";
				for (int k = 0; k < m_maze[0][0].length; k++) {
					mazeString += m_maze[i][j][k] + " ";
				}
				mazeString += "},\n";
			}
			mazeString += "},\n";
		}
		return mazeString;
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

	public byte[][][] getArray() {
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
	
	public byte[] toByteArray() {
		/* Calculate how many bytes we need: 4 for each maze size (height, width, length)
		 * 12 for entryPos, 12 for exitPos, then 1 for each cell: height*width*length
		 * total: 36 + numOfCells
		 */
		int totalCells = m_height*m_width*m_length;
		int totalSize = totalCells + 36;
		byte[] byteArray = new byte[totalSize];
		ByteBuffer buffer = ByteBuffer.wrap(byteArray);
		buffer.put(utils.Utilities.intToByteArray(m_height));
		buffer.put(utils.Utilities.intToByteArray(m_width));
		buffer.put(utils.Utilities.intToByteArray(m_length));
		
		buffer.put(entryPosition.toByteArray());
		buffer.put(exitPosition.toByteArray());
		
		for(int i=0;i<totalSize-36;i++)
		{
			int floor;
			int cellsInFloor = m_width*m_length;
			int cellNumInFloor;
			int row;
			int cellInRow;
			floor = i / (cellsInFloor);
			cellNumInFloor = i - (floor*cellsInFloor);
			row = cellNumInFloor / m_length;
			cellInRow = cellNumInFloor - (m_length*row);
			
			byteArray[i+36]=m_maze[floor][row][cellInRow];
		}
		
		return byteArray;
	}
	
	private void buildMaze(byte[] arr) {
		ByteBuffer buff = ByteBuffer.wrap(arr);
		m_height = buff.getInt();
		m_width = buff.getInt();
		m_length = buff.getInt();
		
		byte[] entryPosArray = new byte[12];
		buff.get(entryPosArray, 0, 12);
		entryPosition = new Position(entryPosArray);
		
		byte[] exitPosArray = new byte[12];
		buff.get(exitPosArray, 0, 12);
		exitPosition = new Position(exitPosArray);
		
		m_maze = new byte[m_height][m_width][m_length];
		
		for(int i=0;i<arr.length-36;i++) {
			int floor;
			int cellsInFloor = m_width*m_length;
			int cellNumInFloor;
			int row;
			int cellInRow;
			floor = i / (cellsInFloor);
			cellNumInFloor = i - (floor*cellsInFloor);
			row = cellNumInFloor / m_length;
			cellInRow = cellNumInFloor - (m_length*row);
			
			m_maze[floor][row][cellInRow] = arr[i+36];
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entryPosition == null) ? 0 : entryPosition.hashCode());
		result = prime * result + ((exitPosition == null) ? 0 : exitPosition.hashCode());
		result = prime * result + m_height;
		result = prime * result + m_length;
		result = prime * result + Arrays.deepHashCode(m_maze);
		result = prime * result + m_width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Maze3d other = (Maze3d) obj;
		if (entryPosition == null) {
			if (other.entryPosition != null)
				return false;
		} else if (!entryPosition.equals(other.entryPosition))
			return false;
		if (exitPosition == null) {
			if (other.exitPosition != null)
				return false;
		} else if (!exitPosition.equals(other.exitPosition))
			return false;
		if (m_height != other.m_height)
			return false;
		if (m_length != other.m_length)
			return false;
		if (!Arrays.deepEquals(m_maze, other.m_maze))
			return false;
		if (m_width != other.m_width)
			return false;
		return true;
	}
}
