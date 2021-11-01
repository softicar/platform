package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public class AGWorkflowAutoTransitionExecutionTable extends EmfObjectTable<AGWorkflowAutoTransitionExecution, AGWorkflowItem> {

	public AGWorkflowAutoTransitionExecutionTable(IDbObjectTableBuilder<AGWorkflowAutoTransitionExecution> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowAutoTransitionExecution, Integer, AGWorkflowItem> configuration) {

		configuration.setScopeField(AGWorkflowAutoTransitionExecution.WORKFLOW_ITEM);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowAutoTransitionExecution> attributes) {

		attributes//
			.editAttribute(AGWorkflowAutoTransitionExecution.WORKFLOW_TRANSITION)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowAutoTransitionExecution.WORKFLOW_ITEM)
			.setPredicateMandatory(EmfPredicates.always());
	}
}
