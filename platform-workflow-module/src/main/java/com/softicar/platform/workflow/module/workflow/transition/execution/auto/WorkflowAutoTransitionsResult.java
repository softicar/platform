package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WorkflowAutoTransitionsResult {

	private final Set<AGWorkflowItem> transitioned = new HashSet<>();
	private final Set<AGWorkflowItem> failed = new HashSet<>();
	private final Set<AGWorkflowItem> omitted = new HashSet<>();

	public void addTransitioned(Collection<AGWorkflowItem> items) {

		transitioned.addAll(items);
	}

	public void addTransitioned(AGWorkflowItem item) {

		Objects.requireNonNull(item);
		addTransitioned(Set.of(item));
	}

	public void addFailed(Collection<AGWorkflowItem> items) {

		failed.addAll(items);
	}

	public void addFailed(AGWorkflowItem item) {

		Objects.requireNonNull(item);
		addFailed(Set.of(item));
	}

	public void addOmitted(Collection<AGWorkflowItem> items) {

		omitted.addAll(items);
	}

	public void addOmitted(AGWorkflowItem item) {

		Objects.requireNonNull(item);
		addOmitted(Set.of(item));
	}

	public void addAll(WorkflowAutoTransitionsResult other) {

		addTransitioned(other.getTransitioned());
		addFailed(other.getFailed());
		addOmitted(other.getOmitted());
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
