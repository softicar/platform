package com.softicar.platform.workflow.module.workflow.export;

import org.junit.Test;

public class WorkflowDtoV1ImporterTest extends AbstractWorkflowImportAndExporterTest {

	@Test
	public void testImportWorkflow() {

		var workflowVersion = new WorkflowDtoV1Importer(workflow, ExampleWorkflow.WORKFLOW_DTO).importWorkflow();

		assertEquals(//
			new WorkflowDtoV1Exporter(workflowVersion).exportWorkflow().toString(),
			ExampleWorkflow.WORKFLOW_DTO.toString());
	}
}
