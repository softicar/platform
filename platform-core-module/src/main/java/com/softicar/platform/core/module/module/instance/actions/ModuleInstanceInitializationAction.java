package com.softicar.platform.core.module.module.instance.actions;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.ModuleInstanceBasePredicates;
import com.softicar.platform.emf.action.AbstractEmfButtonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ModuleInstanceInitializationAction extends AbstractEmfButtonAction<AGModuleInstanceBase> implements IEmfManagementAction<AGModuleInstanceBase> {

	@Override
	public IEmfPredicate<AGModuleInstanceBase> getPrecondition() {

		return ModuleInstanceBasePredicates.NOT_INITIALIZED.and(ModuleInstanceBasePredicates.ACTIVE);
	}

	@Override
	public IEmfPermission<AGModuleInstanceBase> getRequiredPermission() {

		return CoreModule.getModuleAdministation();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.MODULE_INSTANCE_CREATE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.INITIALIZE_MODULE_INSTANCE;
	}

	@Override
	public void handleClick(IEmfFormBody<AGModuleInstanceBase> formBody) {

		createInstanceAndShowPopup(formBody.getTableRow());
	}

	@Override
	public void handleClick(AGModuleInstanceBase moduleInstanceBase) {

		createInstanceAndShowPopup(moduleInstanceBase);
	}

	private <I extends IModuleInstance<I>> void createInstanceAndShowPopup(AGModuleInstanceBase moduleInstanceBase) {

		createInstanceAndShowPopup(moduleInstanceBase.getModuleOrThrow(), moduleInstanceBase);
	}

	private <I extends IModuleInstance<I>> void createInstanceAndShowPopup(IModule<I> module, AGModuleInstanceBase moduleInstanceBase) {

		I moduleInstance = module.getModuleInstanceTable().createObject(moduleInstanceBase);
		new EmfFormPopup<>(moduleInstance).open();
	}
}
