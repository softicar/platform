package com.softicar.platform.workflow.module;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;

@ResourceSupplierContainer
public interface WorkflowCssFiles {

	ResourceSupplierFactory FACTORY = new ResourceSupplierFactory(WorkflowCssFiles.class, Charsets.UTF8);

	IResourceSupplier WORKFLOW_DISPLAY_ELEMENTS_STYLE = FACTORY.create("workflow-display-elements-style.css");
}
