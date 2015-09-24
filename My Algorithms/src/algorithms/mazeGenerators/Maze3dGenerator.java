package algorithms.mazeGenerators;

public interface Maze3dGenerator {
	
	public Maze3d generate(int height, int width, int length);
	public String measureAlgorithmTime(int height, int width, int length);

}
