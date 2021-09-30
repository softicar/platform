package com.softicar.platform.core.module.access.module.instance.actions;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.module.instance.ModuleInstancePredicates;
import com.softicar.platform.core.module.module.IStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.emf.action.AbstractEmfButtonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ModuleInstanceInitializationAction extends AbstractEmfButtonAction<AGModuleInstance> implements IEmfManagementAction<AGModuleInstance> {

	@Override
	public IEmfPredicate<AGModuleInstance> getPrecondition() {

		return ModuleInstancePredicates.NOT_INITIALIZED;
	}

	@Override
	public IEmfRole<AGModuleInstance> getAuthorizedRole() {

		return CoreRoles.ACCESS_MANAGER.toOtherEntityRole();
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
	public void handleClick(IEmfFormBody<AGModuleInstance> formBody) {

		createInstanceAndShowPopup(formBody.getTableRow());
	}

	@Override
	public void handleClick(AGModuleInstance moduleInstance) {

		createInstanceAndShowPopup(moduleInstance);
	}

	private <I extends IStandardModuleInstance<I>> void createInstanceAndShowPopup(AGModuleInstance moduleInstance) {

		createInstanceAndShowPopup(moduleInstance.getModuleOrThrow(), moduleInstance);
	}

	private <I extends IStandardModuleInstance<I>> void createInstanceAndShowPopup(IStandardModule<I> standadModule, AGModuleInstance moduleInstance) {

		I newModuleInstance = standadModule.getModuleInstanceTable().createObject(moduleInstance);
		new EmfFormPopup<>(newModuleInstance).show();
	}
}
