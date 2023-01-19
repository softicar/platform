package com.softicar.platform.workflow.module.workflow.version.export;

import com.google.gson.GsonBuilder;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.writer.ManagedWriter;
import com.softicar.platform.common.io.writer.OutputStreamWriterFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.workflow.module.workflow.export.WorkflowDtoV1Exporter;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.io.IOException;

public class WorkflowVersionExporter {

	private final AGWorkflowVersion workflowVersion;

	public WorkflowVersionExporter(AGWorkflowVersion workflowVersion) {

		this.workflowVersion = workflowVersion;
	}

	public void export(IDomDocument document) {

		var workflowDto = new WorkflowDtoV1Exporter(workflowVersion).exportWorkflow();
		var workflowJson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(workflowDto);

		var export = document//
			.getEngine()
			.createExport()
			.setFilename(workflowVersion.getWorkflow().getName() + createTimestampSuffix() + ".json")
			.setMimeType(MimeType.APPLICATION_JSON)
			.setCharset(Charsets.UTF8);
		try (var stream = export.openOutputStream(); var writer = OutputStreamWriterFactory.writeUtf8(stream)) {
			new ManagedWriter(writer).write(workflowJson);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private String createTimestampSuffix() {

		return "_" + DayTime.now().getTimeAsStringYYYYMMDD_HHMM();
	}
}
