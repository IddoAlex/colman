package serverSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import demo.Maze3dSearchable;
import exceptions.ModelException;
import search.AstarSearcher;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;
import search.Searcher;
import search.Solution;

public class MyClientHandler implements ClientHandler {

	HashMap<String, Searcher<Position>> algorithmMap;
	ExecutorService threadPool;

	public MyClientHandler() {
		threadPool = Executors.newFixedThreadPool(1);
		algorithmMap = new HashMap<>();
		initAlgorithmMap();
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		BufferedReader readerFromClient = new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter writerToClient = new PrintWriter(outToClient);
		ObjectInputStream mazeFromClient;
		ObjectOutputStream solutionToClient;
		Solution<Position> solution;
		String algorithmName = null;

		try {
			algorithmName = readerFromClient.readLine();

			Searcher<Position> searcher = getAlgorithm(algorithmName);
			
			if (searcher != null) {
				writerToClient.println("alg");
				writerToClient.flush();

				mazeFromClient = new ObjectInputStream(inFromClient);
				Maze3d maze = (Maze3d) mazeFromClient.readObject();
				Future<Solution<Position>> futureSolution = threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						return searcher.search(new Maze3dSearchable(maze));
					}
				});
				solution = futureSolution.get();

				solutionToClient = new ObjectOutputStream(outToClient);
				solutionToClient.writeObject(solution);
				solutionToClient.flush();
			}
		} catch (IOException | ClassNotFoundException | InterruptedException | ExecutionException | ModelException e) {
		}
	}

	private Searcher<Position> getAlgorithm(String algorithmName) throws ModelException {
		Searcher<Position> algorithm;

		algorithm = validateName(algorithmName) ? algorithmMap.get(algorithmName) : null;

		return algorithm;
	}

	private boolean validateName(String name) {
		return name != null && !name.isEmpty();
	}

	private void initAlgorithmMap() {
		algorithmMap.put("bfs", new BFSSearcher<Position>());
		algorithmMap.put("manhattan", new AstarSearcher<Position>(new MazeManhattanHeuristic()));
		algorithmMap.put("air", new AstarSearcher<Position>(new MazeAirHeuristic()));
	}
}
