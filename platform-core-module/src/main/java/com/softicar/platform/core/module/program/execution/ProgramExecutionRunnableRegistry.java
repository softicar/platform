package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.container.map.weak.WeakIntHashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * A static registry for currently-running {@link ProgramExecutionRunnable}
 * instances.
 *
 * @author Alexander Schmidt
 */
public class ProgramExecutionRunnableRegistry {

	private static final ProgramExecutionRunnableRegistry INSTANCE = new ProgramExecutionRunnableRegistry();
	private final WeakIntHashMap<ProgramExecutionRunnable> runnableMap;

	private ProgramExecutionRunnableRegistry() {

		this.runnableMap = new WeakIntHashMap<>();
	}

	/**
	 * Returns the static singleton {@link ProgramExecutionRunnableRegistry}
	 * instance.
	 *
	 * @return the {@link ProgramExecutionRunnableRegistry} instance (never
	 *         <i>null</i>)
	 */
	public static ProgramExecutionRunnableRegistry getInstance() {

		return INSTANCE;
	}

	/**
	 * Adds the given {@link ProgramExecutionRunnable} for the given
	 * {@link AGProgramExecution} to this registry.
	 *
	 * @param programExecution
	 *            the {@link AGProgramExecution} for which the
	 *            {@link ProgramExecutionRunnable} was created (never
	 *            <i>null</i>)
	 * @param programExecutionRunnable
	 *            the {@link ProgramExecutionRunnable} that was created for the
	 *            given {@link AGProgramExecution} (never <i>null</i>)
	 */
	public synchronized void register(AGProgramExecution programExecution, ProgramExecutionRunnable programExecutionRunnable) {

		Objects.requireNonNull(programExecution);
		Objects.requireNonNull(programExecutionRunnable);
		runnableMap.put(programExecution.getId(), programExecutionRunnable);
	}

	/**
	 * Removes the {@link ProgramExecutionRunnable} for the given
	 * {@link AGProgramExecution} from this registry.
	 * <p>
	 * If this registry does not contain an entry for the given
	 * {@link AGProgramExecution}, nothing will happen.
	 *
	 * @param programExecution
	 *            the {@link AGProgramExecution} for which the
	 *            {@link ProgramExecutionRunnable} shall be removed (never
	 *            <i>null</i>)
	 */
	public synchronized void unregister(AGProgramExecution programExecution) {

		Objects.requireNonNull(programExecution);
		runnableMap.remove(programExecution.getId());
	}

	/**
	 * Returns the log output that is currently buffered in the
	 * {@link ProgramExecutionRunnable} of the given {@link AGProgramExecution}.
	 * <p>
	 * If this registry does not contain an entry for the given
	 * {@link AGProgramExecution}, {@link Optional#empty()} will be returned.
	 *
	 * @param programExecution
	 *            the {@link AGProgramExecution} for which the log output shall
	 *            be retrieved (never <i>null</i>)
	 * @return the log output
	 */
	public synchronized Optional<String> getOutput(AGProgramExecution programExecution) {

		Objects.requireNonNull(programExecution);
		return Optional//
			.ofNullable(runnableMap.get(programExecution.getId()))
			.map(ProgramExecutionRunnable::getLogs);
	}
}
