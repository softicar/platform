package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.List;
import java.util.Optional;

public class AGModuleInstance extends AGModuleInstanceGenerated implements IEmfObject<AGModuleInstance>, IEmfModuleInstance<AGModuleInstance> {

	public static final ModuleInstanceTitleField TITLE_FIELD = new ModuleInstanceTitleField();

	@Override
	public ItemId getItemId() {

		return IEmfObject.super.getItemId();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getActualModuleInstance()//
			.map(IEmfModuleInstance::toDisplayWithoutId)
			.orElseGet(() -> IEmfModuleInstance.super.toDisplayWithoutId());
	}

	@Override
	public IModule<?> getModuleOrThrow() {

		IEmfModule<?> module = IEmfModuleInstance.super.getModuleOrThrow();
		return CastUtils//
			.tryCast(module, IModule.class)
			.orElseThrow(() -> new ModuleClassNotImplementingModuleInterfaceException(module));
	}

	@Override
	public boolean hasPermission(IEmfModulePermission<AGModuleInstance> permission, IBasicUser user) {

		return AGUser.get(user).hasModulePermission(permission, this);
	}

	/**
	 * Returns the actual {@link IEmfModuleInstance} that derives from this
	 * {@link AGModuleInstance}.
	 *
	 * @return the optional {@link IEmfModuleInstance}.
	 */
	public Optional<IEmfModuleInstance<?>> getActualModuleInstance() {

		return getModule().flatMap(module -> module.getModuleInstance(getId()));
	}

	/**
	 * Determines whether this module instance is initialized.
	 * <p>
	 * A module instance can only be considered initialized if the referenced
	 * module is an {@link IModule}.
	 *
	 * @return true if this module instance is initialized; false otherwise
	 */
	public boolean isInitialized() {

		return new ModuleInstanceStateChecker(this).isInitialized();
	}

	public static List<AGModuleInstance> loadAllActive() {

		return AGModuleInstance//
			.createSelect()
			.where(AGModuleInstance.ACTIVE)
			.list();
	}
}
