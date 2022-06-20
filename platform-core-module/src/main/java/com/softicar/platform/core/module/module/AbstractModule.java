package com.softicar.platform.core.module.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.AbstractEmfModule;
import java.util.Collection;
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
			.where(AGModuleInstance.ID.isEqual(moduleInstanceId))
			.where(AGModuleInstance.ACTIVE)
			.where(AGModuleInstance.MODULE_UUID.equal(AGUuid.getOrCreate(getAnnotatedUuid())))
			.getOneAsOptional();
	}

	@Override
	public final Collection<I> getActiveModuleInstances() {

		return getModuleInstanceTable()//
			.createSelect()
			.join(getModuleInstanceTable().getPrimaryKeyField())
			.where(AGModuleInstance.ACTIVE)
			.list();
	}
}
