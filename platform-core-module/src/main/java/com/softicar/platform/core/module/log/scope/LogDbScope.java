package com.softicar.platform.core.module.log.scope;

import com.softicar.platform.core.module.log.entry.point.CurrentLogDbEntryPoint;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.core.module.log.process.CurrentLogProcess;
import java.util.Optional;

public class LogDbScope implements AutoCloseable {

	private final Optional<Class<?>> originalEntryPoint;
	private final Optional<AGLogProcess> originalLogProcess;

	public LogDbScope(Class<?> entryPoint) {

		this.originalEntryPoint = CurrentLogDbEntryPoint.get();
		this.originalLogProcess = CurrentLogProcess.get();

		CurrentLogDbEntryPoint.set(entryPoint);
	}

	@Override
	public void close() {

		CurrentLogDbEntryPoint.set(originalEntryPoint.orElse(null));
		CurrentLogProcess.set(originalLogProcess.orElse(null));
	}
}
