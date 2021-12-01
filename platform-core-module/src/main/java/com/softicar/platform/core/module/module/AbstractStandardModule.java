package com.softicar.platform.core.module.module;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.AbstractEmfModule;
import java.util.Collection;
import java.util.Optional;

/**
 * Base implementation of {@link IStandardModule}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractStandardModule<I extends IStandardModuleInstance<I>> extends AbstractEmfModule<I> implements IStandardModule<I> {

	@Override
	public final Optional<I> getModuleInstance(AGModuleInstance moduleInstance) {

		IStandardModuleInstanceTable<I> table = getModuleInstanceTable();
		return table//
			.createSelect()
			.join(table.getPrimaryKeyField())
			.where(AGModuleInstance.ID.isEqual(moduleInstance))
			.where(AGModuleInstance.ACTIVE)
			.where(AGModuleInstance.MODULE_UUID.equal(AGUuid.getOrCreate(getAnnotatedUuid())))
			.getOneAsOptional();
	}

	@Override
	public final I getModuleInstanceById(Integer moduleInstanceId) {

		return getModuleInstance(moduleInstanceId)
			.orElseThrow(() -> new SofticarUserException(CoreI18n.MODULE_INSTANCE_WITH_ID_ARG1_DOES_NOT_EXIST.toDisplay(moduleInstanceId)));
	}

	@Override
	public Optional<I> getModuleInstance(Integer moduleInstanceId) {

		IStandardModuleInstanceTable<I> table = getModuleInstanceTable();
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
