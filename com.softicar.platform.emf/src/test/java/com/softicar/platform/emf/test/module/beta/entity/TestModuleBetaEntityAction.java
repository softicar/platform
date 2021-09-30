package com.softicar.platform.emf.test.module.beta.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.test.module.beta.TestModuleBetaRoles;

public class TestModuleBetaEntityAction implements IEmfPrimaryAction<TestModuleBetaEntity> {

	@Override
	public IEmfRole<TestModuleBetaEntity> getAuthorizedRole() {

		return new EmfAnyRole<>(//
			TestModuleBetaRoles.ROLE_ONE.of(TestModuleBetaEntity.MODULE_INSTANCE),
			TestModuleBetaRoles.ROLE_TWO.of(TestModuleBetaEntity.MODULE_INSTANCE));
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
