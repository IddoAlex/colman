package search;

import static org.junit.Assert.*;

import org.junit.Test;

import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import demo.Maze3dSearchable;

public class AstarSearcherTest {

	@Test
	public void testSearch() {
		Maze3dSearchable searchable = new Maze3dSearchable(new MyMazeGenerator().generate(10, 10, 10));
		AstarSearcher<Position> searcher = new AstarSearcher<>(null);
		assertNull(searcher.search(searchable));
		
		searcher.setHeuristic(new MazeAirHeuristic());
		assertNotNull(searcher.search(searchable));
	}

}
