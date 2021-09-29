package com.softicar.platform.workflow.module.workflow.transients.field.display;

import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Optional;

public class WorkflowNodeFieldDisplay extends DomBar {

	public WorkflowNodeFieldDisplay(Optional<AGWorkflowNode> node) {

		appendChild(
			node//
				.map(AGWorkflowNode::getName)
				.orElse(""));
	}
}
