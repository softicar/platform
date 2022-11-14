package com.softicar.platform.workflow.module.workflow.export;

import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import org.junit.Test;

public class WorkflowDtoV1ExporterTest extends AbstractWorkflowImportAndExporterTest {

	private final AGWorkflowVersion workflowVersion;

	public WorkflowDtoV1ExporterTest() {

		this.workflowVersion = new WorkflowDtoV1Importer(workflow, ExampleWorkflow.WORKFLOW_DTO).importWorkflow();
	}

	@Test
	public void testExportWorkflowWorkflow() {

		var workflowDto = new WorkflowDtoV1Exporter(workflowVersion).exportWorkflow();

		assertEquals(workflowDto.toString(), ExampleWorkflow.WORKFLOW_DTO.toString());
	}
}
