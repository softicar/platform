package com.softicar.platform.core.module.module.instance.system;

import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.module.AbstractSystemModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.UUID;

/**
 * The singleton instance of any system module.
 *
 * @author Alexander Schmidt
 * @see AbstractSystemModule
 */
public class SystemModuleInstance implements IEmfModuleInstance<SystemModuleInstance> {

	private final UUID moduleUuid;

	public SystemModuleInstance(Class<? extends AbstractSystemModule> moduleClass) {

		this.moduleUuid = EmfSourceCodeReferencePoints.getUuidOrThrow(moduleClass);
	}

	@Override
	public ItemId getItemId() {

		return new ItemId(0);
	}

	@Override
	public IUuid getModuleUuid() {

		return () -> moduleUuid;
	}

	@Override
	public boolean hasRole(IEmfModuleRole<SystemModuleInstance> role, IBasicUser user) {

		throw new UnsupportedOperationException("System modules do not support module roles.");
	}
}
