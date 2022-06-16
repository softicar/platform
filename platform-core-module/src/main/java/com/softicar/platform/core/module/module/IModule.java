package com.softicar.platform.core.module.module;

import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.emf.module.IEmfModule;

/**
 * Interface of modules that use instances of type {@link IModuleInstance}.
 *
 * @author Alexander Schmidt
 */
public interface IModule<I extends IModuleInstance<I>> extends IEmfModule<I> {

	/**
	 * Returns the table that represents the module instances.
	 *
	 * @return the module instance table (never <i>null</i>)
	 */
	IModuleInstanceTable<I> getModuleInstanceTable();
}
