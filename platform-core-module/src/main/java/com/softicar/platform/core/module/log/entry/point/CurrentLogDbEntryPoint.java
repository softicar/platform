package com.softicar.platform.core.module.log.entry.point;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.core.module.log.process.CurrentLogProcess;
import java.util.Objects;
import java.util.Optional;

/**
 * Holds the entry point for the current thread.
 *
 * @author Oliver Richers
 */
public class CurrentLogDbEntryPoint {

	private final static Singleton<Class<?>> INSTANCE = new Singleton<Class<?>>()//
		.setInheritByIdentity();

	public static void set(Class<?> entryPointClass) {

		INSTANCE.set(entryPointClass);

		// reset current log process if necessary
		Optional<AGLogProcess> process = CurrentLogProcess.get();
		if (process.isPresent()) {
			if (entryPointClass == null || !Objects.equals(entryPointClass.getCanonicalName(), process.get().getClassName())) {
				CurrentLogProcess.set(null);
			}
		}
	}

	public static Optional<Class<?>> get() {

		return Optional.ofNullable(INSTANCE.get());
	}
}
