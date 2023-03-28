package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.image.AGWorkflowIcon;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import com.softicar.platform.workflow.module.workflow.transition.side.effect.IWorkflowTransitionSideEffect;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class AGWorkflowTransition extends AGWorkflowTransitionGenerated implements IEmfObject<AGWorkflowTransition> {

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public List<AGWorkflowTransitionPermission> getAllActiveWorkflowTransitionPermissions() {

		return AGWorkflowTransitionPermission.TABLE//
			.createSelect()
			.where(AGWorkflowTransitionPermission.ACTIVE)
			.where(AGWorkflowTransitionPermission.TRANSITION.isEqual(this))
			.list();
	}

	/**
	 * Checks whether this {@link AGWorkflowTransition} is a voting transition
	 * or not.
	 * <p>
	 * A voting transition has a required voting count that is different from
	 * <i>1</i>.
	 *
	 * @return <i>true</i> if this is a voting transition; <i>false</i>
	 *         otherwise
	 */
	public boolean isVotingTransition() {

		return !new WorkflowTransitionRequiredVotesParser(this).isOne();
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

	public void assertInSourceNode(AGWorkflowItem item) {

		assertInSourceNode(item, () -> new SofticarUserException(WorkflowI18n.THE_ITEM_RESIDES_IN_AN_UNEXPECTED_WORKFLOW_NODE));
	}

	public void assertInSourceNode(AGWorkflowItem item, Supplier<? extends RuntimeException> exceptionSupplier) {

		if (item.getWorkflowNode() != getSourceNode()) {
			CurrentDomDocument.getAsOptional().ifPresent(document -> document.getRefreshBus().setAllChanged());
			throw exceptionSupplier.get();
		}
	}

	public AGWorkflowTransition setSideEffect(Class<? extends IWorkflowTransitionSideEffect<?>> sideEffectClass) {

		return super.setSideEffect(AGUuid.getOrCreate(SourceCodeReferencePoints.getUuidOrThrow(sideEffectClass)));
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
