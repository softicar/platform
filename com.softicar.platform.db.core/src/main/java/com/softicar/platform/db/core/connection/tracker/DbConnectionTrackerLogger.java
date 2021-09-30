package com.softicar.platform.db.core.connection.tracker;

import com.softicar.platform.common.core.logger.ILogger;
import com.softicar.platform.common.core.logger.SystemErrLogger;
import com.softicar.platform.common.date.DayTime;
import java.util.Collection;
import java.util.Date;
import java.util.function.Consumer;

public class DbConnectionTrackerLogger implements Consumer<Collection<DbConnectionTrack>> {

	private final ILogger logger;

	public DbConnectionTrackerLogger() {

		this(new SystemErrLogger());
	}

	public DbConnectionTrackerLogger(ILogger logger) {

		this.logger = logger;
	}

	@Override
	public void accept(Collection<DbConnectionTrack> idleConnectionTracks) {

		logger.info("IDLE OPEN DATABASE CONNECTIONS:");
		for (DbConnectionTrack track: idleConnectionTracks) {
			logger
				.info(//
					"\tIdle connection #%s opened at %s by %s",
					track.getConnectionIndex(),
					getOpenedAt(track),
					getCallerFromStackTrace(track.getStackTrace()));
			for (StackTraceElement element: track.getStackTrace()) {
				logger.info("\t\t%s", element);
			}
		}
	}

	private static String getOpenedAt(DbConnectionTrack track) {

		return new DayTime(new Date(track.getOpenedAt())).toString();
	}

	private static String getCallerFromStackTrace(StackTraceElement[] stackTrace) {

		for (StackTraceElement element: stackTrace) {
			String className = element.getClassName();
			if (!className.startsWith("java.") && !className.startsWith("sun.") && !className.startsWith("com.softicar.platform.")) {
				return element.toString();
			}
		}
		return "<unkown caller>";
	}
}
