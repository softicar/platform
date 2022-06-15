package com.softicar.platform.core.module.module;

import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.module.IEmfModule;

/**
 * Interface of modules that use instances of type
 * {@link IStandardModuleInstance}.
 *
 * @author Alexander Schmidt
 */
public interface IStandardModule<I extends IStandardModuleInstance<I>> extends IEmfModule<I> {

	/**
	 * Returns the table that represents the module instances.
	 *
	 * @return the module instance table (never null)
	 */
	IStandardModuleInstanceTable<I> getModuleInstanceTable();
}
