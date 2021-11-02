package com.softicar.platform.core.module.access.module.instance.actions;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.module.instance.ModuleInstancePredicates;
import com.softicar.platform.core.module.access.module.instance.ModuleNotStandardModuleException;
import com.softicar.platform.core.module.module.IStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.role.EmfDefaultModuleRoles;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ModuleInstanceDetailsPopupAction implements IEmfManagementAction<AGModuleInstance> {

	@Override
	public IEmfPredicate<AGModuleInstance> getPrecondition() {

		return ModuleInstancePredicates.INITIALIZED;
	}

	@Override
	public IEmfRole<AGModuleInstance> getAuthorizedRole() {

		return EmfDefaultModuleRoles.getModuleAdministator();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.MODULE_INSTANCE_DETAILS.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.MODULE_INSTANCE_DETAILS;
	}

	@Override
	public void handleClick(AGModuleInstance moduleInstance) {

		IEmfModule<?> module = moduleInstance.getModuleOrThrow();
		if (module instanceof IStandardModule) {
			showPopup((IStandardModule<?>) module, moduleInstance);
		} else {
			throw new ModuleNotStandardModuleException(module);
		}
	}

	private <I extends IStandardModuleInstance<I>> void showPopup(IStandardModule<I> standadModule, AGModuleInstance moduleInstance) {

		I standardModuleInstance = standadModule//
			.getModuleInstance(moduleInstance)
			.orElseThrow(() -> new SofticarUserException(CoreI18n.MODULE_INSTANCE_WAS_NOT_INITIALIZED));
		new EmfFormPopup<>(standardModuleInstance).show();
	}
}
