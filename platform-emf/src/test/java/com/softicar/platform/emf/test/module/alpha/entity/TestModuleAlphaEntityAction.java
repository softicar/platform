package com.softicar.platform.emf.test.module.alpha.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlphaRoles;

public class TestModuleAlphaEntityAction implements IEmfPrimaryAction<TestModuleAlphaEntity> {

	@Override
	public IEmfRole<TestModuleAlphaEntity> getAuthorizedRole() {

		return new EmfAnyRole<>(//
			TestModuleAlphaRoles.ROLE_ONE.of(TestModuleAlphaEntity.MODULE_INSTANCE),
			TestModuleAlphaRoles.ROLE_TWO.of(TestModuleAlphaEntity.MODULE_INSTANCE));
	}

	@Override
	public IEmfPredicate<TestModuleAlphaEntity> getPrecondition() {

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
	public void integrate(TestModuleAlphaEntity entity, IEmfFormSectionDiv<TestModuleAlphaEntity> actionContainer) {

		// nothing to do
	}
}
