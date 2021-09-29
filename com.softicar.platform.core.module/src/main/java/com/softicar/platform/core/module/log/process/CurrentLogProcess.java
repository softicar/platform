package com.softicar.platform.core.module.log.process;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.core.module.log.entry.point.CurrentLogDbEntryPoint;
import com.softicar.platform.core.module.log.level.AGLogLevelEnum;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import java.util.Optional;

/**
 * Helper class to create an entry into {@link AGLogProcess} when the first log
 * message entry is to be written.
 *
 * @author Marco Pilipovic
 * @author Oliver Richers
 */
public class CurrentLogProcess {

	private final static Singleton<AGLogProcess> INSTANCE = new Singleton<>();

	public static Optional<AGLogProcess> get() {

		return Optional.ofNullable(INSTANCE.get());
	}

	public static void set(AGLogProcess process) {

		INSTANCE.set(process);
	}

	public static AGLogProcess getOrCreateProcess() {

		AGLogProcess process = INSTANCE.get();
		if (process == null) {
			process = new AGLogProcess();
			process.setServerIp(LogDbIpFetcher.getHostIpAddresses());
			process.setClassName(getEntryPointClassName());
			process.setWorstLevel(AGLogLevelEnum.VERBOSE.getRecord());
			process.save();

			INSTANCE.set(process);
		}
		return process;
	}

	private static String getEntryPointClassName() {

		Optional<Class<?>> entryPoint = CurrentLogDbEntryPoint.get();
		return entryPoint.isPresent()? entryPoint.get().getCanonicalName() : findClassName();
	}

	private static String findClassName() {

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String classname = stackTrace[stackTrace.length - 1].getClassName();

		if (classname.equals("java.lang.Thread")) {
			classname = Optional//
				.ofNullable(CurrentDomDocument.get())
				.map(IDomDocument::getBody)
				.map(IAjaxDocument.class::cast)
				.map(page -> page.getClass().getName())
				.orElse(classname);
		}

		return classname;
	}
}
