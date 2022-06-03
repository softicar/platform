package com.softicar.platform.workflow.module.workflow.node.action.permission;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import java.util.Optional;

public class AGWorkflowNodeActionPermission extends AGWorkflowNodeActionPermissionGenerated implements IEmfObject<AGWorkflowNodeActionPermission> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		Optional<IEmfStaticPermission<?>> staticPermission = CurrentEmfPermissionRegistry.get().getStaticPermission(getPermissionUuid().getUuid());

		if (staticPermission == null) {
			return IDisplayString.create(getPermissionUuid().getUuidString());
		} else {
			return staticPermission.get().getTitle();
		}
	}

	public <R> IEmfStaticPermission<R> getStaticPermissionOrThrow() {

		Optional<IEmfStaticPermission<?>> staticPermission = CurrentEmfPermissionRegistry.get().getStaticPermission(getPermissionUuid().getUuid());

		if (staticPermission.isEmpty()) {
			throw new SofticarDeveloperException(//
				"Error, could not load %s for UUID %s",
				IEmfStaticPermission.class.getSimpleName(),
				getPermissionUuid().getUuid());
		}

		return CastUtils.cast(staticPermission.get());
	}
}
