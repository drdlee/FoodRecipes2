package com.example.foodrecipes2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

	private static AppExecutors instance;
	public static AppExecutors getInstance() {
		if (instance == null) {
			instance = new AppExecutors();
		}
		return instance;
	}

	private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);
	// Executor = to run task in the background
	// ScheduledExecutor = able to set timeout for task run in background

	public ScheduledExecutorService networkIO() {
		return mNetworkIO;
	}
}
