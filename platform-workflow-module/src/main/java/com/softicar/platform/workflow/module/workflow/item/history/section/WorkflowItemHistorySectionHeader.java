package com.softicar.platform.workflow.module.workflow.item.history.section;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.form.section.header.IEmfFormSectionHeader;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import java.util.Optional;

public class WorkflowItemHistorySectionHeader implements IEmfFormSectionHeader {

	@Override
	public Optional<IResource> getIcon() {

		return Optional.of(WorkflowImages.WORKFLOW_GRAPH.getResource());
	}

	@Override
	public IDisplayString getDisplayString() {

		return WorkflowI18n.WORKFLOW_ITEM_HISTORY;
	}
}
