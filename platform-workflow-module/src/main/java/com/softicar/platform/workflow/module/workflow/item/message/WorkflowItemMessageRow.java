package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;

public class WorkflowItemMessageRow {

	private final Boolean isTransition;
	private final AGUser createdBy;
	private final String text;
	private final DayTime createdAt;

	public WorkflowItemMessageRow(Boolean isTransition, AGTransaction transaction, String text) {

		this(isTransition, transaction.getBy(), text, transaction.getAt());
	}

	public WorkflowItemMessageRow(Boolean isTransition, AGUser createdBy, String text, DayTime createdAt) {

		this.isTransition = isTransition;
		this.createdBy = createdBy;
		this.text = text;
		this.createdAt = createdAt;
	}

	public Boolean isTransition() {

		return isTransition;
	}

	public DayTime getCreatedAt() {

		return createdAt;
	}

	public AGUser getCreatedBy() {

		return createdBy;
	}

	public String getText() {

		return text;
	}
}
