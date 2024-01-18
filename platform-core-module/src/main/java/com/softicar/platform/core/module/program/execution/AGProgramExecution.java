package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.program.execution.status.ProgramExecutionStatus;
import com.softicar.platform.core.module.program.execution.status.ProgramExecutionStatusField;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.object.IEmfObject;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class AGProgramExecution extends AGProgramExecutionGenerated implements IEmfObject<AGProgramExecution> {

	public static final ProgramExecutionRuntimeField RUNTIME_FIELD = new ProgramExecutionRuntimeField();
	public static final ProgramExecutionStatusField STATUS_FIELD = new ProgramExecutionStatusField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString
			.format(//
				"%s (%s -> %s)",
				Programs.getProgramName(getProgramUuid().getUuid()),
				getDayTimeStringOrPlaceholder(this::getStartedAt),
				getDayTimeStringOrPlaceholder(this::getTerminatedAt));
	}

	public AGProgramExecution setProgramUuid(UUID programUuid) {

		return setProgramUuid(AGUuid.getOrCreate(programUuid));
	}

	public boolean isTerminated() {

		return getTerminatedAt() != null;
	}

	public boolean isRuntimeExceeded(Duration duration) {

		return getDuration().compareTo(duration) > 0;
	}

	public Duration getDuration() {

		return Optional//
			.ofNullable(getStartedAt())
			.map(DayTime::toInstant)
			.map(this::determineDurationTillEnd)
			.orElse(Duration.ZERO);
	}

	public AGProgram getProgram() {

		return AGProgram.loadByProgramUuid(getProgramUuid());
	}

	/**
	 * Returns all executions of the given {@link AGProgram} that have started
	 * since the given {@link DayTime}.
	 * <p>
	 * This includes running and finished executions.
	 *
	 * @param program
	 *            the {@link AGProgram} (never <i>null</i>)
	 * @param startedAt
	 *            the minimum started-at (never <i>null</i>)
	 * @return all matching executions
	 */
	public static Collection<AGProgramExecution> getRecentExecutions(AGProgram program, DayTime startedAt) {

		return AGProgramExecution.TABLE//
			.createSelect()
			.where(AGProgramExecution.PROGRAM_UUID.isEqual(program.getProgramUuid()))
			.where(AGProgramExecution.STARTED_AT.isGreaterEqual(startedAt))
			.orderBy(AGProgramExecution.STARTED_AT)
			.orderBy(AGProgramExecution.ID)
			.list();
	}

	private String getDayTimeStringOrPlaceholder(Supplier<DayTime> supplier) {

		return Optional//
			.ofNullable(supplier.get())
			.map(DayTime::toString)
			.orElse("?");
	}

	private Duration determineDurationTillEnd(Instant startInstant) {

		return Duration.between(startInstant, getEndInstantOrNow());
	}

	private Instant getEndInstantOrNow() {

		return Optional//
			.ofNullable(getTerminatedAt())
			.orElse(DayTime.now())
			.toInstant();
	}

	public ProgramExecutionStatus getStatus() {

		return STATUS_FIELD.getValue(this);
	}
}
