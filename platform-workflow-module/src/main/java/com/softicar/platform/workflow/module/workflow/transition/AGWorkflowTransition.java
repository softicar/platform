package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.image.AGWorkflowIcon;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import com.softicar.platform.workflow.module.workflow.transition.side.effect.IWorkflowTransitionSideEffect;
import java.util.List;
import java.util.Optional;

public class AGWorkflowTransition extends AGWorkflowTransitionGenerated implements IEmfObject<AGWorkflowTransition> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public List<AGWorkflowTransitionPermission> getAllActiveWorkflowTransitionPermissions() {

		return AGWorkflowTransitionPermission.TABLE//
			.createSelect()
			.where(AGWorkflowTransitionPermission.ACTIVE)
			.where(AGWorkflowTransitionPermission.TRANSITION.equal(this))
			.list();
	}

	public boolean isUserAllowedToSeeTransition(AGUser user, AGWorkflowItem item) {

		return getAllActiveWorkflowTransitionPermissions()//
			.stream()
			.anyMatch(permission -> permission.testUserAssignmentForItem(user, item));
	}

	public IResource getIcon() {

		return Optional//
			.ofNullable(getTransitionIcon())
			.map(AGWorkflowIcon::getIcon)
			.map(StoredFileResource::new)
			.map(it -> (IResource) it)
			.orElse(EmfImages.INPUT_PREVIEW.getResource());
	}

	public void executeSideEffect(AGWorkflowItem workflowItem) {

		if (getSideEffect() != null) {
			IWorkflowTransitionSideEffect<?> sideEffect =
					SourceCodeReferencePoints.getReferencePointOrThrow(getSideEffect().getUuid(), IWorkflowTransitionSideEffect.class);
			executeSideEffect(sideEffect, workflowItem.getEntityOrThrow());
		}
	}

	private <T extends IWorkflowableObject<T>> void executeSideEffect(IWorkflowTransitionSideEffect<T> sideEffect, Object object) {

		sideEffect.execute(sideEffect.getValueClass().cast(object), this);
	}
}
