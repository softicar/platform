package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.export.WorkflowDtoV1Exporter;
import java.util.Set;

public class WorkflowVersionHashField extends AbstractTransientObjectField<AGWorkflowVersion, String> {

	public WorkflowVersionHashField() {

		super(String.class);
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.HASH;
	}

	@Override
	protected void loadValues(Set<AGWorkflowVersion> versions, IValueSetter<AGWorkflowVersion, String> setter) {

		versions.forEach(version -> {
			try {
				var dto = new WorkflowDtoV1Exporter(version).setSkipNodePositions(true).exportWorkflow();
				var hash = Hash.SHA1.getHashStringLC(dto.toString()).substring(0, 7);
				setter.set(version, hash);
			} catch (Exception exception) {
				exception.printStackTrace();
				setter.set(version, WorkflowI18n.UNKNOWN.toString());
			}
		});
	}

	@Override
	protected String getDefaultValue() {

		return "";
	}
}
