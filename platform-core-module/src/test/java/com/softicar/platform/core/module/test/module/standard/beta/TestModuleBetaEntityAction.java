package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.permission.EmfAnyPermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class TestModuleBetaEntityAction implements IEmfPrimaryAction<TestModuleBetaEntity> {

	@Override
	public IEmfPermission<TestModuleBetaEntity> getRequiredPermission() {

		return new EmfAnyPermission<>(TestModuleBetaPermissions.PERMISSION_ONE.of(TestModuleBetaEntity.MODULE_INSTANCE));
	}

	@Override
	public IEmfPredicate<TestModuleBetaEntity> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IResource getIcon() {

		return DomElementsImages.DIALOG_OKAY.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("someAction");
	}

	@Override
	public void integrate(TestModuleBetaEntity entity, IEmfFormSectionDiv<TestModuleBetaEntity> actionContainer) {

		// nothing to do
	}
}
