package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.daemon.DaemonProperties;
import com.softicar.platform.core.module.daemon.IDaemon;
import com.softicar.platform.core.module.daemon.IDaemonDefinition;
import java.time.Duration;

public class QueuedProgramExecutionDaemonDefinition implements IDaemonDefinition {

	@Override
	public IDisplayString toDisplay() {

		return IDisplayString.create("Queued Program Execution Daemon");
	}

	@Override
	public IDaemon create() {

		return new QueuedProgramExecutionDaemon();
	}

	@Override
	public Duration getKillTimeout() {

		return Duration.ofSeconds(DaemonProperties.QUEUED_PROGRAM_EXECUTION_DAEMON_HEARTBEAT_TIMEOUT.getValue());
	}
}
