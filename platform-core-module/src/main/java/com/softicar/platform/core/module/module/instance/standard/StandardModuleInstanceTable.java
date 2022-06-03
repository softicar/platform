package com.softicar.platform.core.module.module.instance.standard;

import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.sub.object.table.EmfSubObjectTable;

public class StandardModuleInstanceTable<I extends IStandardModuleInstance<I>> extends EmfSubObjectTable<I, AGModuleInstance, Integer, SystemModuleInstance>
		implements IStandardModuleInstanceTable<I> {

	private final Class<? extends IEmfModule<I>> moduleClass;

	public StandardModuleInstanceTable(IDbSubObjectTableBuilder<I, AGModuleInstance, Integer> builder, Class<? extends IEmfModule<I>> moduleClass) {

		super(builder);
		this.moduleClass = moduleClass;
	}

	@Override
	public Class<? extends IEmfModule<I>> getModuleClass() {

		return moduleClass;
	}

	@Override
	public final void customizeAuthorizer(EmfAuthorizer<I, SystemModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(EmfDefaultModulePermissions.getModuleAdministator())
			.setEditPermission(EmfPermissions.any(CorePermissions.ACCESS_MANAGER.<I> toOtherEntityPermission(), EmfDefaultModulePermissions.getModuleAdministator()))
			.setViewPermission(EmfPermissions.any(CorePermissions.ACCESS_MANAGER.<I> toOtherEntityPermission(), EmfDefaultModulePermissions.getModuleAdministator()));
	}
}
