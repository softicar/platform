package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.daemon.DaemonProperties;
import com.softicar.platform.core.module.daemon.IDaemon;
import com.softicar.platform.core.module.daemon.IDaemonDefinition;
import java.time.Duration;

public class ScheduledProgramEnqueuerDaemonDefinition implements IDaemonDefinition {

	@Override
	public IDisplayString toDisplay() {

		return IDisplayString.create("Scheduled Program Enqueuer Daemon");
	}

	@Override
	public IDaemon create() {

		return new ScheduledProgramEnqueuerDaemon();
	}

	@Override
	public Duration getKillTimeout() {

		return Duration.ofSeconds(DaemonProperties.SCHEDULED_PROGRAM_ENQUEUER_DAEMON_HEARTBEAT_TIMEOUT.getValue());
	}
}
