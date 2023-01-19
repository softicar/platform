package com.softicar.platform.workflow.module.workflow.export;

import com.softicar.platform.workflow.module.AbstractWorkflowTest;
import com.softicar.platform.workflow.module.demo.WorkflowDemoObjectTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;

public class AbstractWorkflowImportAndExporterTest extends AbstractWorkflowTest {

	protected final AGWorkflow workflow;

	public AbstractWorkflowImportAndExporterTest() {

		this.workflow = insertWorkflow(moduleInstance, "test", WorkflowDemoObjectTableReferencePoint.class);
	}
}
