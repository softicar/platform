package com.softicar.platform.core.module.module.instance.actions;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.ModuleClassDoesNotImplementModuleInterfaceException;
import com.softicar.platform.core.module.module.instance.ModuleInstanceBasePredicates;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ModuleInstanceDetailsPopupAction implements IEmfManagementAction<AGModuleInstanceBase> {

	@Override
	public IEmfPredicate<AGModuleInstanceBase> getPrecondition() {

		return ModuleInstanceBasePredicates.INITIALIZED;
	}

	@Override
	public IEmfPermission<AGModuleInstanceBase> getRequiredPermission() {

		return CoreModule.getModuleAdministation();
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
	public void handleClick(AGModuleInstanceBase moduleInstanceBase) {

		IEmfModule<?> module = moduleInstanceBase.getModuleOrThrow();
		if (module instanceof IModule) {
			showPopup((IModule<?>) module, moduleInstanceBase);
		} else {
			throw new ModuleClassDoesNotImplementModuleInterfaceException(module);
		}
	}

	private <I extends IModuleInstance<I>> void showPopup(IEmfModule<I> module, AGModuleInstanceBase moduleInstanceBase) {

		I actualModuleInstance = module//
			.getModuleInstance(moduleInstanceBase.getId())
			.orElseThrow(() -> new SofticarUserException(CoreI18n.MODULE_INSTANCE_WAS_NOT_INITIALIZED));
		new EmfFormPopup<>(actualModuleInstance).open();
	}
}
