package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverity;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverityEnum;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Comparator;
import java.util.Optional;

public class WorkflowItemMessageRow implements Comparable<WorkflowItemMessageRow> {

	private final String text;
	private final AGWorkflowMessageSeverity severity;
	private final AGTransaction transaction;
	private AGWorkflowNode workflowNode;
	private boolean transition;
	private Integer index;

	public WorkflowItemMessageRow(IDisplayString text, AGTransaction transaction) {

		this(text, AGWorkflowMessageSeverityEnum.VERBOSE.getRecord(), transaction);
	}

	public WorkflowItemMessageRow(IDisplayString text, AGWorkflowMessageSeverity severity, AGTransaction transaction) {

		this.text = text.toString();
		this.severity = severity;
		this.transaction = transaction;
	}

	public String getText() {

		return text;
	}

	public AGWorkflowMessageSeverity getSeverity() {

		return severity;
	}

	public DayTime getCreatedAt() {

		return transaction.getAt();
	}

	public AGUser getCreatedBy() {

		return transaction.getBy();
	}

	public AGTransaction getTransaction() {

		return transaction;
	}

	public WorkflowItemMessageRow setWorkflowNode(AGWorkflowNode workflowNode, boolean transition) {

		this.workflowNode = workflowNode;
		this.transition = transition;
		return this;
	}

	public AGWorkflowNode getWorkflowNode() {

		return workflowNode;
	}

	public String getNodeName() {

		return Optional.ofNullable(workflowNode).map(AGWorkflowNode::getName).orElse("");
	}

	public WorkflowItemMessageRow setIndex(Integer index) {

		this.index = index;
		return this;
	}

	public boolean isTransition() {

		return transition;
	}

	public Integer getIndex() {

		return index;
	}

	@Override
	public int compareTo(WorkflowItemMessageRow other) {

		return Comparator//
			.comparing(WorkflowItemMessageRow::getTransaction)
			.thenComparing(WorkflowItemMessageRow::getText)
			.compare(this, other);
	}
}
