package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.transients.AbstractTransientBooleanField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.Set;

public class WorkflowVersionCurrentVersionField extends AbstractTransientBooleanField<AGWorkflowVersion> {

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.CURRENT_VERSION;
	}

	@Override
	protected void loadValues(Set<AGWorkflowVersion> versions, IValueSetter<AGWorkflowVersion, Boolean> setter) {

		var currentVersions = AGWorkflowVersion.getCurrentVersions();
		for (var version: versions) {
			setter.set(version, currentVersions.contains(version));
		}
	}

	@Override
	protected Boolean getDefaultValue() {

		return false;
	}
}
