package com.softicar.platform.core.module.module;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.Optional;

/**
 * Interface of modules that use instances of type
 * {@link IStandardModuleInstance}.
 *
 * @author Alexander Schmidt
 */
public interface IStandardModule<I extends IStandardModuleInstance<I>> extends IEmfModule<I> {

	/**
	 * Returns the specific module instance for the given basic module instance.
	 *
	 * @param moduleInstance
	 *            the basic module instance
	 * @return the optional specific module instance
	 */
	Optional<I> getModuleInstance(AGModuleInstance moduleInstance);

	/**
	 * Returns the table that represents the module instances.
	 *
	 * @return the module instance table (never null)
	 */
	IStandardModuleInstanceTable<I> getModuleInstanceTable();

	/**
	 * Safely casts the given {@link IEmfModule} reference to an
	 * {@link IStandardModule} reference.
	 *
	 * @param module
	 *            the {@link IEmfModule} reference to cast
	 * @return the given {@link IEmfModule} as an {@link IStandardModule}; null
	 *         if the given {@link IEmfModule} is not an
	 *         {@link IStandardModule}; null if the given {@link IEmfModule} is
	 *         null
	 */
	static IStandardModule<?> cast(IEmfModule<?> module) {

		if (module instanceof IStandardModule) {
			return (IStandardModule<?>) module;
		} else {
			return null;
		}
	}
}
