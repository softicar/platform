package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class TestStandardModuleBetaEntityAction implements IEmfPrimaryAction<TestStandardModuleBetaEntity> {

	@Override
	public IEmfRole<TestStandardModuleBetaEntity> getAuthorizedRole() {

		return new EmfAnyRole<>(TestStandardModuleBetaRoles.ROLE_ONE.of(TestStandardModuleBetaEntity.MODULE_INSTANCE));
	}

	@Override
	public IEmfPredicate<TestStandardModuleBetaEntity> getPrecondition() {

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
	public void integrate(TestStandardModuleBetaEntity entity, IEmfFormSectionDiv<TestStandardModuleBetaEntity> actionContainer) {

		// nothing to do
	}
}
