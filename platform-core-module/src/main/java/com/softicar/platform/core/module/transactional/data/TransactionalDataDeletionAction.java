package com.softicar.platform.core.module.transactional.data;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModuleInstancePredicates;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class TransactionalDataDeletionAction implements IEmfScopeAction<AGCoreModuleInstance> {

	@Override
	public IEmfPredicate<AGCoreModuleInstance> getPrecondition() {

		return CoreModuleInstancePredicates.TEST_SYSTEM;
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return EmfDefaultModulePermissions.getModuleAdministration();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.RESET.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.DELETE_ALL_TRANSACTIONAL_DATA;
	}

	@Override
	public void handleClick(AGCoreModuleInstance scope) {

		new TransactionalDataDeletionPopup().open();
	}
}
