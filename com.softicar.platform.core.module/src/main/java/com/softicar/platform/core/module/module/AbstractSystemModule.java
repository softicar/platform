package com.softicar.platform.core.module.module;

import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.module.AbstractEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import java.util.Collection;
import java.util.Collections;

/**
 * Common base class of all system modules that are bound to the singleton
 * {@link SystemModuleInstance}.
 * <p>
 * Registered system modules are always considered deployed and active.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractSystemModule extends AbstractEmfModule<SystemModuleInstance> {

	/**
	 * Returns the singleton {@link SystemModuleInstance}.
	 *
	 * @param moduleInstanceId
	 *            the module instance ID (will be ignored)
	 * @return the {@link SystemModuleInstance} (never null)
	 */
	@Override
	public final SystemModuleInstance getModuleInstanceById(Integer moduleInstanceId) {

		return getSystemModuleInstance();
	}

	/**
	 * @return a collection that contains the sole {@link IEmfModuleInstance} of
	 *         this singleton module (never null; never empty)
	 */
	@Override
	public final Collection<SystemModuleInstance> getActiveModuleInstances() {

		return Collections.singleton(getSystemModuleInstance());
	}

	protected abstract SystemModuleInstance getSystemModuleInstance();
}
