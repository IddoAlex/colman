package algorithms.mazeGenerators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public abstract class CommonMaze3dGenerator implements Maze3dGenerator {
	Random rnd = new Random();

	@Override
	abstract public Maze3d generate(int height, int width, int length);

	@Override
	public String measureAlgorithmTime(int height, int width, int length) {
		long begin, diff;
		begin = System.currentTimeMillis();
		generate(height, width, length);
		diff = System.currentTimeMillis() - begin;
		return getSimpleTime(diff);
	}

	private String getSimpleTime(long ms) {
		return (new SimpleDateFormat("HH:mm:ss:SSS").format(new Date(ms)));
	}

	// An edge position - entrance / exit
	protected Position generateEdgePosition(Maze3d maze) {
		int maxHeight = maze.getHeight();
		int maxWidth = maze.getWidth();
		int maxLength = maze.getLength();
		int entryHeight, entryWidth, entryLength;

		do {
			entryHeight = rnd.nextInt(maxHeight);
			entryWidth = rnd.nextInt(maxWidth);
			entryLength = rnd.nextInt(maxLength);
		} while ((entryHeight != 0) && (entryWidth != 0) && (entryLength != 0));

		return new Position(entryHeight, entryWidth, entryLength);
	}
	
}
