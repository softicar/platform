package com.softicar.platform.core.module.module.instance.standard;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.sub.object.table.IEmfSubObjectTable;

/**
 * Implemented by tables which contain {@link IStandardModuleInstance} records.
 * <p>
 * An {@link IStandardModuleInstanceTable} provides a place to store and access
 * instance-specific configuration parameters of an {@link IEmfModule}.
 *
 * @author Alexander Schmidt
 */
public interface IStandardModuleInstanceTable<I extends IStandardModuleInstance<I>>
		extends IEmfSubObjectTable<I, AGModuleInstance, Integer, SystemModuleInstance> {

	/**
	 * Returns the {@link IEmfModule} class associated with this table.
	 *
	 * @return the {@link IEmfModule} class (never <i>null</i>)
	 */
	Class<? extends IEmfModule<I>> getModuleClass();
}
