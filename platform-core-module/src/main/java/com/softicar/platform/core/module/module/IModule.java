package com.softicar.platform.core.module.module;

import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.Collection;

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

	/**
	 * Returns all {@link IDbTable} objects of this module that contain
	 * transaction data.
	 * <p>
	 * Transaction data is data that records transactions, e.g. orders,
	 * invoices, deliveries, storage records, etc. In general, transaction data
	 * is all data that is neither reference data nor master data.
	 *
	 * @return all {@link IDbTable} objects containing transaction data (never
	 *         <i>null</i>)
	 */
	Collection<IDbTable<?, ?>> getTransactionDataTables();
}
