package com.softicar.platform.workflow.module.workflow.entity.table;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

/**
 * This exception will be thrown if more that one matching {@link IEmfEntity} is
 * found for a given {@link AGWorkflowItem}.
 * <p>
 * This error should usually never happen, but it cannot be ruled out
 * completely.
 *
 * @author Oliver Richers
 */
public class MultipleEntitiesForWorkflowItemException extends SofticarDeveloperException {

	public MultipleEntitiesForWorkflowItemException(AGWorkflowItem workflowItem) {

		super("Multiple entities for workflow item %s.", workflowItem.getId());
	}
}
