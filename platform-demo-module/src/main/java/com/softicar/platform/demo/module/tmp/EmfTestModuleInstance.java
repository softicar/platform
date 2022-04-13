package com.softicar.platform.demo.module.tmp;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.access.module.instance.ModuleNotStandardModuleException;
import com.softicar.platform.core.module.module.IStandardModule;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.List;
import java.util.Optional;

public class EmfTestModuleInstance extends EmfTestModuleInstanceGenerated implements IEmfObject<EmfTestModuleInstance>, IEmfModuleInstance<EmfTestModuleInstance> {

//	public static final ModuleInstanceTitleField TITLE_FIELD = new ModuleInstanceTitleField();

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
	public IStandardModule<?> getModuleOrThrow() {

		IEmfModule<?> module = IEmfModuleInstance.super.getModuleOrThrow();
		return Optional//
			.ofNullable(module)
			.map(IStandardModule::cast)
			.orElseThrow(() -> new ModuleNotStandardModuleException(module));
	}

	@Override
	public boolean hasRole(IEmfModuleRole<EmfTestModuleInstance> role, IBasicUser user) {

		return true;
	}

	/**
	 * Returns the actual {@link IEmfModuleInstance} that derives from this
	 * {@link EmfTestModuleInstance}.
	 *
	 * @return the optional {@link IEmfModuleInstance}.
	 */
	public Optional<IEmfModuleInstance<?>> getActualModuleInstance() {

		return getModule().flatMap(module -> module.getModuleInstance(getId()));
	}

//	/**
//	 * Determines whether this module instance is initialized.
//	 * <p>
//	 * A module instance can only be considered initialized if the referenced
//	 * module is an {@link IStandardModule}.
//	 *
//	 * @return true if this module instance is initialized; false otherwise
//	 */
//	public boolean isInitialized() {
//
//		return new ModuleInstanceStateChecker(this).isInitialized();
//	}

	public static List<EmfTestModuleInstance> loadAllActive() {

		return EmfTestModuleInstance//
			.createSelect()
			.where(EmfTestModuleInstance.ACTIVE)
			.list();
	}
}
