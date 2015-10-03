package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerThreadPool.
 */
public class ControllerThreadPool {

	/** The executor. */
	static ExecutorService executor;

	/** The instance. */
	private static ControllerThreadPool instance;
	private static int NumThreadPool = 5;

	/**
	 * Instantiates a new controller thread pool.
	 */
	private ControllerThreadPool() {
		executor = Executors.newFixedThreadPool(NumThreadPool);
	}

	/**
	 * Gets the executor.
	 *
	 * @return the executor
	 */
	public static ExecutorService getExecutor() {
		if (instance == null) {
			instance = new ControllerThreadPool();
		}

		return executor;
	}

	public static void setNumThreadPools(int num) {
		if (instance == null) {
			NumThreadPool = num;
		}
	}
}
