package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowModule;
import com.softicar.platform.workflow.module.workflow.export.WorkflowUploadForm;

@SourceCodeReferencePointUuid("eb0b7a20-e06e-472d-b8f3-73336ac201d1")
public class WorkflowManagementPage extends AbstractEmfManagementPage<AGWorkflowModuleInstance> {

	@Override
	public IDomNode createContentNode(AGWorkflowModuleInstance moduleInstance) {

		if (AGWorkflow.TABLE.getAuthorizer().getCreationPermission().test(moduleInstance, CurrentUser.get())) {
			var div = new DomDiv();
			div.appendChild(new WorkflowUploadForm(moduleInstance));
			div.appendChild(super.createContentNode(moduleInstance));
			return div;
		} else {
			return super.createContentNode(moduleInstance);
		}
	}

	@Override
	public Class<WorkflowModule> getModuleClass() {

		return WorkflowModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGWorkflowModuleInstance> getTable() {

		return AGWorkflow.TABLE;
	}
}
