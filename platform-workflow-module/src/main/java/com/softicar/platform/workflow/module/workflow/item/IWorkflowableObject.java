package com.softicar.platform.workflow.module.workflow.item;

import com.softicar.platform.emf.object.IEmfObject;
import java.util.Optional;

/**
 * Common interface of {@link IEmfObject} based workflow item classes.
 *
 * @author Marco Pilipovic
 */
public interface IWorkflowableObject<R extends IEmfObject<R>> extends IEmfObject<R> {

	/**
	 * Returns the {@link AGWorkflowItem} referenced by this
	 * {@link IWorkflowableObject}
	 *
	 * @return the {@link AGWorkflowItem} (may be <i>null</i>)
	 */
	AGWorkflowItem getWorkflowItem();

	/**
	 * Returns the {@link AGWorkflowItem} referenced by this
	 * {@link IWorkflowableObject}
	 *
	 * @return the {@link AGWorkflowItem} as {@link Optional}
	 */
	default Optional<AGWorkflowItem> getWorkflowItemAsOptional() {

		return Optional.ofNullable(getWorkflowItem());
	}

	default boolean isWorkflowStarted() {

		return getWorkflowItem() != null;
	}

	/**
	 * Executes the longest possible cascade of auto transitions for the
	 * {@link AGWorkflowItem}.
	 * <p>
	 * If this {@link IWorkflowableObject} is not associated with a
	 * {@link AGWorkflowItem}, nothing will happen.
	 *
	 * @see #getWorkflowItem()
	 */
	default void executeAllAutoTransitions() {

		Optional//
			.ofNullable(getWorkflowItem())
			.ifPresent(AGWorkflowItem::executeAllAutoTransitions);
	}

	Boolean isActive();

	R setWorkflowItem(AGWorkflowItem item);
}
