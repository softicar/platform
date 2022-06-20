package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.sub.object.table.IEmfSubObjectTable;

/**
 * Implemented by tables which contain {@link IModuleInstance} records.
 * <p>
 * An {@link IModuleInstanceTable} provides a place to store and access
 * instance-specific configuration parameters of an {@link IEmfModule}.
 *
 * @author Alexander Schmidt
 */
public interface IModuleInstanceTable<I extends IModuleInstance<I>>
		extends IEmfSubObjectTable<I, AGModuleInstance, Integer, AGCoreModuleInstance> {

	/**
	 * Returns the {@link IEmfModule} class associated with this table.
	 *
	 * @return the {@link IEmfModule} class (never <i>null</i>)
	 */
	Class<? extends IEmfModule<I>> getModuleClass();
}
