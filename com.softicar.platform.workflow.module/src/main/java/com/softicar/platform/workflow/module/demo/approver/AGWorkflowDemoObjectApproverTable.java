package com.softicar.platform.workflow.module.demo.approver;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;

public class AGWorkflowDemoObjectApproverTable extends EmfObjectTable<AGWorkflowDemoObjectApprover, AGWorkflowDemoObject> {

	public AGWorkflowDemoObjectApproverTable(IDbObjectTableBuilder<AGWorkflowDemoObjectApprover> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowDemoObjectApprover, Integer, AGWorkflowDemoObject> configuration) {

		configuration.setScopeField(AGWorkflowDemoObjectApprover.OBJECT);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowDemoObjectApprover> attributes) {

		attributes//
			.editAttribute(AGWorkflowDemoObjectApprover.APPROVED)
			.setEditable(false);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowDemoObjectApprover> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowDemoObjectApproverLog.APPROVER, AGWorkflowDemoObjectApproverLog.TRANSACTION)//
			.addMapping(AGWorkflowDemoObjectApprover.APPROVAL_TIER, AGWorkflowDemoObjectApproverLog.APPROVAL_TIER)
			.addMapping(AGWorkflowDemoObjectApprover.USER, AGWorkflowDemoObjectApproverLog.USER)
			.addMapping(AGWorkflowDemoObjectApprover.ACTIVE, AGWorkflowDemoObjectApproverLog.ACTIVE)
			.addMapping(AGWorkflowDemoObjectApprover.APPROVED, AGWorkflowDemoObjectApproverLog.APPROVED);
	}
}
