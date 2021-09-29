package com.softicar.platform.common.core.thread;

import java.util.concurrent.locks.Lock;

/**
 * An {@link AutoCloseable} to lock a {@link Lock}.
 * <p>
 * Use this in a try-with-resource.
 *
 * @author Oliver Richers
 */
public class Locker implements AutoCloseable {

	private final Lock lock;

	public Locker(Lock lock) {

		this.lock = lock;
		lock.lock();
	}

	@Override
	public void close() {

		lock.unlock();
	}
}
