package com.softicar.platform.core.module.daemon;

import com.softicar.platform.common.core.i18n.IDisplayable;
import java.time.Duration;

/**
 * Supplies the implementation of an {@link IDaemon}, and related properties.
 *
 * @author Alexander Schmidt
 */
public interface IDaemonDefinition extends IDisplayable {

	/**
	 * Creates a new {@link IDaemon} instance for which a daemon thread can be
	 * spawned.
	 *
	 * @return a new {@link IDaemon} instance (never <i>null</i>)
	 */
	IDaemon create();

	/**
	 * Returns the timeout duration after which a thread created for the given
	 * {@link IDaemon} should be terminated forcibly.
	 * <p>
	 * FIXME this method should be renamed to getHeartbeatTimeout
	 *
	 * @return the kill timeout (never <i>null</i>)
	 */
	Duration getKillTimeout();
}
