package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class TestStandardModuleAlphaEntityAction implements IEmfPrimaryAction<TestStandardModuleAlphaEntity> {

	@Override
	public IEmfRole<TestStandardModuleAlphaEntity> getAuthorizedRole() {

		return new EmfAnyRole<>(//
			TestStandardModuleAlphaRoles.ROLE_ONE.of(TestStandardModuleAlphaEntity.MODULE_INSTANCE),
			TestStandardModuleAlphaRoles.ROLE_TWO.of(TestStandardModuleAlphaEntity.MODULE_INSTANCE));
	}

	@Override
	public IEmfPredicate<TestStandardModuleAlphaEntity> getPrecondition() {

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
	public void integrate(TestStandardModuleAlphaEntity entity, IEmfFormSectionDiv<TestStandardModuleAlphaEntity> actionContainer) {

		// nothing to do
	}
}
