package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.permission.EmfAnyPermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class TestModuleAlphaEntityAction implements IEmfPrimaryAction<TestModuleAlphaEntity> {

	@Override
	public IEmfPermission<TestModuleAlphaEntity> getRequiredPermission() {

		return new EmfAnyPermission<>(//
			TestModuleAlphaPermissions.PERMISSION_ONE.of(TestModuleAlphaEntity.MODULE_INSTANCE),
			TestModuleAlphaPermissions.PERMISSION_TWO.of(TestModuleAlphaEntity.MODULE_INSTANCE));
	}

	@Override
	public IEmfPredicate<TestModuleAlphaEntity> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.EXECUTE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("someAction");
	}

	@Override
	public void integrate(TestModuleAlphaEntity entity, IEmfFormSectionDiv<TestModuleAlphaEntity> actionContainer) {

		// nothing to do
	}
}
