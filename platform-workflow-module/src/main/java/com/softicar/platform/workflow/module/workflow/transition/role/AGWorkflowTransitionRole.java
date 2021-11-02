package com.softicar.platform.workflow.module.workflow.transition.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Optional;

public class AGWorkflowTransitionRole extends AGWorkflowTransitionRoleGenerated implements IEmfObject<AGWorkflowTransitionRole> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getStaticRole()//
			.map(IEmfStaticRole::getTitle)
			.orElse(IDisplayString.EMPTY);
	}

	public boolean testUserAssignmentForItem(AGUser user, AGWorkflowItem item) {

		// TODO fix ugly cast
		return getStaticRole()//
			.map(it -> it.test(CastUtils.cast(item.getEntityOrThrow()), user))
			.orElse(false);
	}

	public Optional<IEmfStaticRole<?>> getStaticRole() {

		return CurrentEmfRoleRegistry.get().getStaticRole(getRole().getUuid());
	}
}
