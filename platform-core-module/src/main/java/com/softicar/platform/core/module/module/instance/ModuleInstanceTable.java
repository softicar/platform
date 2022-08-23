package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.sub.object.table.EmfSubObjectTable;

public class ModuleInstanceTable<I extends IModuleInstance<I>> extends EmfSubObjectTable<I, AGModuleInstanceBase, Integer, AGCoreModuleInstance>
		implements IModuleInstanceTable<I> {

	private final Class<? extends IEmfModule<I>> moduleClass;

	public ModuleInstanceTable(IDbSubObjectTableBuilder<I, AGModuleInstanceBase, Integer> builder, Class<? extends IEmfModule<I>> moduleClass) {

		super(builder);
		this.moduleClass = moduleClass;
	}

	@Override
	public Class<? extends IEmfModule<I>> getModuleClass() {

		return moduleClass;
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<I, AGCoreModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(CorePermissions.ADMINISTRATION)
			.setEditPermission(CoreModule.getModuleAdministation())
			.setViewPermission(CoreModule.getModuleAdministation());
	}
}
