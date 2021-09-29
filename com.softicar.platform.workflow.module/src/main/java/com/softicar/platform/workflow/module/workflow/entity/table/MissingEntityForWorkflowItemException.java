package com.softicar.platform.workflow.module.workflow.entity.table;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

/**
 * This exception will be thrown if no matching {@link IEmfEntity} is found for
 * a given {@link AGWorkflowItem}.
 *
 * @author Oliver Richers
 * @see IWorkflowTableReferencePoint
 */
public class MissingEntityForWorkflowItemException extends SofticarDeveloperException {

	public MissingEntityForWorkflowItemException(AGWorkflowItem workflowItem) {

		super("Missing entity for workflow item %s.", workflowItem.getId());
	}
}
