package com.softicar.platform.core.module.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.module.AbstractEmfModule;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Base implementation of {@link IModule}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractModule<I extends IModuleInstance<I>> extends AbstractEmfModule<I> implements IModule<I> {

	@Override
	public Optional<I> getModuleInstance(Integer moduleInstanceId) {

		IModuleInstanceTable<I> table = getModuleInstanceTable();
		return table//
			.createSelect()
			.join(table.getPrimaryKeyField())
			.where(AGModuleInstanceBase.ID.isEqual(moduleInstanceId))
			.where(AGModuleInstanceBase.ACTIVE)
			.where(AGModuleInstanceBase.MODULE_UUID.isEqual(AGUuid.getOrCreate(getAnnotatedUuid())))
			.getOneAsOptional();
	}

	@Override
	public final Collection<I> getActiveModuleInstances() {

		return getModuleInstanceTable()//
			.createSelect()
			.join(getModuleInstanceTable().getPrimaryKeyField())
			.where(AGModuleInstanceBase.ACTIVE)
			.list();
	}

	@Override
	public Collection<IDbTable<?, ?>> getTransactionalDataTables() {

		return Collections.emptyList();
	}
}
