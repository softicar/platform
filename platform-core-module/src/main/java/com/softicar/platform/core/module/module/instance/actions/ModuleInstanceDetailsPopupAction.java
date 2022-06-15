package com.softicar.platform.core.module.module.instance.actions;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.ModuleInstancePredicates;
import com.softicar.platform.core.module.module.instance.ModuleClassNotImplementingModuleInterfaceException;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ModuleInstanceDetailsPopupAction implements IEmfManagementAction<AGModuleInstance> {

	@Override
	public IEmfPredicate<AGModuleInstance> getPrecondition() {

		return ModuleInstancePredicates.INITIALIZED;
	}

	@Override
	public IEmfPermission<AGModuleInstance> getRequiredPermission() {

		return EmfDefaultModulePermissions.getModuleAdministation();
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
		if (module instanceof IModule) {
			showPopup((IModule<?>) module, moduleInstance);
		} else {
			throw new ModuleClassNotImplementingModuleInterfaceException(module);
		}
	}

	private <I extends IModuleInstance<I>> void showPopup(IEmfModule<I> module, AGModuleInstance moduleInstance) {

		I actualModuleInstance = module//
			.getModuleInstance(moduleInstance.getId())
			.orElseThrow(() -> new SofticarUserException(CoreI18n.MODULE_INSTANCE_WAS_NOT_INITIALIZED));
		new EmfFormPopup<>(actualModuleInstance).open();
	}
}
