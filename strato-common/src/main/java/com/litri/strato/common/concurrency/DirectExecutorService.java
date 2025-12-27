package com.litri.strato.common.concurrency;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DirectExecutorService extends AbstractExecutorService {
	
	private Lock shutdownLock = new ReentrantLock();
	private Condition shutdownCondition = this.shutdownLock.newCondition();
	private volatile boolean isShutdown = false;

	@Override
	public void shutdown() {
		this.shutdownLock.lock();
		try {
			this.isShutdown = true;
			this.shutdownCondition.signalAll();
		} finally {
			this.shutdownLock.unlock();
		}
	}

	@Override
	public List<Runnable> shutdownNow() {
		this.shutdown();
		return Collections.emptyList();
	}

	@Override
	public boolean isShutdown() {
		this.shutdownLock.lock();
		try {
			return this.isShutdown;
		} finally {
			this.shutdownLock.unlock();
		}
	}

	@Override
	public boolean isTerminated() {
		return this.isShutdown();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		this.shutdownLock.lock();
		try {
			if (!this.isShutdown) {
				this.shutdownCondition.await(timeout, unit);
			}
			return this.isShutdown;
		} finally {
			this.shutdownLock.unlock();
		}
	}

	@Override
	public void execute(Runnable command) {
		command.run();
	}
	
}
