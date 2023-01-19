package com.softicar.platform.workflow.module.workflow.export;

import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.elements.upload.DomUploadForm;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class WorkflowUploadForm extends DomUploadForm {

	private final AGWorkflowModuleInstance moduleInstance;

	public WorkflowUploadForm(AGWorkflowModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;

		setUploadHandler(this::importWorkflows);
	}

	private void importWorkflows(Iterable<IDomFileUpload> uploads) {

		uploads.forEach(this::importWorkflow);

		executeAlert(WorkflowI18n.IMPORT_SUCCESSFUL);
	}

	private void importWorkflow(IDomFileUpload upload) {

		String json = upload.getContentAsString(Charsets.UTF8);
		new WorkflowDtoV1Importer(moduleInstance, WorkflowDtoV1.fromJson(json)).importWorkflow();
	}
}
