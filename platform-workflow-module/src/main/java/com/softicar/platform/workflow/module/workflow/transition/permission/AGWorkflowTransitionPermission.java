package com.softicar.platform.workflow.module.workflow.transition.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Optional;

public class AGWorkflowTransitionPermission extends AGWorkflowTransitionPermissionGenerated implements IEmfObject<AGWorkflowTransitionPermission> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getStaticPermission()//
			.map(IEmfStaticPermission::getTitle)
			.orElse(IDisplayString.EMPTY);
	}

	public boolean testUserAssignmentForItem(AGUser user, AGWorkflowItem item) {

		// TODO fix ugly cast
		return getStaticPermission()//
			.map(it -> it.test(CastUtils.cast(item.getEntityOrThrow()), user))
			.orElse(false);
	}

	public Optional<IEmfStaticPermission<?>> getStaticPermission() {

		return CurrentEmfPermissionRegistry.get().getStaticPermission(getPermission().getUuid());
	}
}
