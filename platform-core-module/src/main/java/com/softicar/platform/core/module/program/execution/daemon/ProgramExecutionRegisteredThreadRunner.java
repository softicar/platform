package com.softicar.platform.core.module.program.execution.daemon;

import com.softicar.platform.common.core.thread.IRunnableThread;
import com.softicar.platform.common.core.thread.runner.ILimitedThreadRunner;
import com.softicar.platform.core.module.program.execution.ProgramExecutionRunnable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

class ProgramExecutionRegisteredThreadRunner implements ILimitedThreadRunner<ProgramExecutionRunnable> {

	private final ILimitedThreadRunner<ProgramExecutionRunnable> wrappedThreadRunner;
	private final Map<UUID, Collection<IRunnableThread<ProgramExecutionRunnable>>> registeredThreadMap;

	public ProgramExecutionRegisteredThreadRunner(ILimitedThreadRunner<ProgramExecutionRunnable> wrappedThreadRunner) {

		this.wrappedThreadRunner = Objects.requireNonNull(wrappedThreadRunner);
		this.registeredThreadMap = new TreeMap<>();
	}

	@Override
	public void addRunnable(ProgramExecutionRunnable runnable) {

		wrappedThreadRunner.addRunnable(runnable);
	}

	@Override
	public boolean hasAvailableSlots() {

		return wrappedThreadRunner.hasAvailableSlots();
	}

	@Override
	public Collection<IRunnableThread<ProgramExecutionRunnable>> startThreads() {

		var runnableThreads = wrappedThreadRunner.startThreads();
		addAll(runnableThreads);
		return runnableThreads;
	}

	public Collection<IRunnableThread<ProgramExecutionRunnable>> getAllRunning() {

		removeTerminated();
		return registeredThreadMap//
			.values()
			.stream()
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
	}

	public Collection<IRunnableThread<ProgramExecutionRunnable>> getAllRunning(UUID programUuid) {

		Objects.requireNonNull(programUuid);
		removeTerminated();
		return Optional//
			.ofNullable(registeredThreadMap.get(programUuid))
			.orElse(Collections.emptyList());
	}

	@Override
	public boolean isFinished() {

		return wrappedThreadRunner.isFinished();
	}

	private void addAll(Collection<IRunnableThread<ProgramExecutionRunnable>> runnableThreads) {

		Objects.requireNonNull(runnableThreads);
		removeTerminated();
		runnableThreads.forEach(this::addToMap);
	}

	private boolean addToMap(IRunnableThread<ProgramExecutionRunnable> runnableThread) {

		return registeredThreadMap//
			.computeIfAbsent(runnableThread.getRunnable().getProgramUuid(), dummy -> new ArrayList<>())
			.add(runnableThread);
	}

	private void removeTerminated() {

		registeredThreadMap.values().forEach(this::removeTerminatedFrom);
	}

	private void removeTerminatedFrom(Collection<IRunnableThread<ProgramExecutionRunnable>> runnableThreads) {

		Iterator<IRunnableThread<ProgramExecutionRunnable>> iterator = runnableThreads.iterator();
		while (iterator.hasNext()) {
			var runnableThread = iterator.next();
			if (runnableThread.isTerminated()) {
				iterator.remove();
			}
		}
	}
}
