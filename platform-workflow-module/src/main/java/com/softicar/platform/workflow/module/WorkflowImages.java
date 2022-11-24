package com.softicar.platform.workflow.module;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface WorkflowImages extends CoreImages {

	DomResourceSupplierProxyFactory FACTORY = new DomResourceSupplierProxyFactory(WorkflowImages.class);

	IResourceSupplier COPY = FACTORY.create("copy.svg");
	IResourceSupplier RIGHT = FACTORY.create("right.svg");
	IResourceSupplier USER = FACTORY.create("user.svg");
	IResourceSupplier USERS = FACTORY.create("users.svg");
	IResourceSupplier WORKFLOW = FACTORY.create("workflow-versions.svg");
	IResourceSupplier WORKFLOW_GRAPH = FACTORY.create("workflow-graph.svg");
	IResourceSupplier WORKFLOW_IMAGES = FACTORY.create("workflow-image.svg");
	IResourceSupplier WORKFLOW_ITEM_MESSAGE = FACTORY.create("workflow-item-message.svg");
	IResourceSupplier WORKFLOW_NODE = FACTORY.create("workflow-node.svg");
	IResourceSupplier WORKFLOW_TRANSITION = FACTORY.create("workflow-transition.svg");
}
