package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class WorkflowAutoTransitionsResult {

	private final Set<AGWorkflowItem> transitioned = new HashSet<>();
	private final Set<AGWorkflowItem> failed = new HashSet<>();
	private final Set<AGWorkflowItem> omitted = new HashSet<>();

	public void addTransitioned(AGWorkflowItem item) {

		Objects.requireNonNull(item);
		transitioned.add(item);
	}

	public void addFailed(AGWorkflowItem item) {

		Objects.requireNonNull(item);
		failed.add(item);
	}

	public void addOmitted(AGWorkflowItem item) {

		Objects.requireNonNull(item);
		omitted.add(item);
	}

	public void addAll(WorkflowAutoTransitionsResult other) {

		other.getTransitioned().forEach(this::addTransitioned);
		other.getFailed().forEach(this::addFailed);
		other.getOmitted().forEach(this::addOmitted);
	}

	public Set<AGWorkflowItem> getTransitioned() {

		return transitioned;
	}

	public Set<AGWorkflowItem> getFailed() {

		return failed;
	}

	public Set<AGWorkflowItem> getOmitted() {

		return omitted;
	}
}
