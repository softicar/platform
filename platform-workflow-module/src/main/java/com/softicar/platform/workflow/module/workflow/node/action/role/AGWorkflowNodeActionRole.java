package com.softicar.platform.workflow.module.workflow.node.action.role;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Optional;

public class AGWorkflowNodeActionRole extends AGWorkflowNodeActionRoleGenerated implements IEmfObject<AGWorkflowNodeActionRole> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		Optional<IEmfStaticRole<?>> staticRole = CurrentEmfRoleRegistry.get().getStaticRole(getRoleUuid().getUuid());

		if (staticRole == null) {
			return IDisplayString.create(getRoleUuid().getUuidString());
		} else {
			return staticRole.get().getTitle();
		}
	}

	public <R> IEmfStaticRole<R> getStaticRoleOrThrow() {

		Optional<IEmfStaticRole<?>> staticRole = CurrentEmfRoleRegistry.get().getStaticRole(getRoleUuid().getUuid());

		if (staticRole.isEmpty()) {
			throw new SofticarDeveloperException("Error, could not load %s for UUID %s", IEmfStaticRole.class.getSimpleName(), getRoleUuid().getUuid());
		}

		return CastUtils.cast(staticRole.get());
	}
}
