package com.softicar.platform.common.core.thread.collection;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents a {@link Collection} of {@link Thread} objects.
 *
 * @author Oliver Richers
 */
public class ThreadCollection<T extends Thread> extends AbstractCollection<T> {

	private final Set<T> threads;

	public ThreadCollection() {

		this.threads = new HashSet<>();
	}

	public ThreadCollection(T thread) {

		this(Collections.singleton(thread));
	}

	public ThreadCollection(Collection<? extends T> threads) {

		this.threads = new HashSet<>(threads);
	}

	public ThreadCollection(ThreadCollection<? extends T> threads) {

		this.threads = new HashSet<>(threads.threads);
	}

	@Override
	public boolean add(T thread) {

		return threads.add(thread);
	}

	@Override
	public Iterator<T> iterator() {

		return threads.iterator();
	}

	@Override
	public int size() {

		return threads.size();
	}

	public void startAll() {

		threads.stream().forEach(Thread::start);
	}

	public void interruptAll() {

		threads.stream().forEach(Thread::interrupt);
	}

	public boolean joinAll(long timeoutMillis) {

		return new ThreadJoiner<>(this).joinAll(timeoutMillis);
	}

	public boolean killAll() {

		return new ThreadKiller<>(this).killAll();
	}

	public boolean runAll(long timeoutMillis) {

		return new ThreadRunner<>(this).runAll(timeoutMillis);
	}

	public void removeFinishedThreads() {

		for (Iterator<T> iterator = threads.iterator(); iterator.hasNext();) {
			Thread thread = iterator.next();
			if (!thread.isAlive()) {
				iterator.remove();
			}
		}
	}
}
