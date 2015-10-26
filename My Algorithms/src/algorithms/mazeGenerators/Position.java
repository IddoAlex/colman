package algorithms.mazeGenerators;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3017273643559091051L;
	
	private int m_height = 0;
	private int m_width = 0;
	private int m_length = 0;
	
	public boolean visited;

	public Position() {}

	public Position(int height, int width, int length) {
		m_height = height;
		m_width = width;
		m_length = length;
	}
	
	public Position(byte[] arr)	{
		buildPosition(arr);
	}

	private void buildPosition(byte[] arr) {
		ByteBuffer buffer = ByteBuffer.wrap(arr);
		m_height = buffer.getInt();
		m_width = buffer.getInt();
		m_length = buffer.getInt();
	}

	public int getHeight() {
		return m_height;
	}

	public void setHeight(int height) {
		this.m_height = height;
	}

	public int getWidth() {
		return m_width;
	}

	public void setWidth(int width) {
		this.m_width = width;
	}

	public int getLength() {
		return m_length;
	}

	public void setLength(int length) {
		this.m_length = length;
	}

	public void moveForwards() {
		m_length++;
	}

	public void moveBackwards() {
		m_length--;
	}

	public void moveRight() {
		m_width++;
	}

	public void moveLeft() {
		m_width--;
	}

	public void moveUp() {
		m_height++;
	}

	public void moveDown() {
		m_height--;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (m_height != other.m_height)
			return false;
		if (m_length != other.m_length)
			return false;
		if (m_width != other.m_width)
			return false;
		return true;
	}

	public void moveInRandom() {
		int direction = (int) Math.random()*6;
		switch (direction)
		{
			case 0:
				moveForwards();
				break;
			case 1: 
				moveBackwards();
				break;
			case 2:
				moveRight();
				break;
			case 3:
				moveLeft();
				break;
			case 4:
				moveUp();
				break;
			case 5:
				moveDown();
				break;
			default:
					break;
		}
	}
	
	public Position clone()
	{
		return new Position(m_height, m_width, m_length);
	}

	@Override
	public String toString()
	{
		return "{" + m_length + ", " + m_height + ", " + m_width+ "}";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + m_height;
		result = prime * result + m_length;
		result = prime * result + m_width;
		return result;
	}
	
	public byte[] toByteArray() {
		byte[] heightArr = utils.Utilities.intToByteArray(m_height);
		byte[] widthArr = utils.Utilities.intToByteArray(m_width);
		byte[] lengthArr = utils.Utilities.intToByteArray(m_length);
		
		ByteBuffer buff = ByteBuffer.wrap(new byte[12]);
		buff.put(heightArr);
		buff.put(widthArr);
		buff.put(lengthArr);
		
		return buff.array();
	}
}
