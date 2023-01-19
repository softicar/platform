package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreationTransactionField<R extends IDbObject<R>, L extends IDbRecord<L, Tuple2<R, AGTransaction>>>
		extends AbstractTransientObjectField<R, AGTransaction> {

	private final IDbForeignField<L, R> logEntityField;
	private final IDbForeignField<L, AGTransaction> logTransactionField;

	public CreationTransactionField(IDbForeignField<L, R> logEntityField, IDbForeignField<L, AGTransaction> logTransactionField) {

		super(AGTransaction.class);
		this.logEntityField = logEntityField;
		this.logTransactionField = logTransactionField;
	}

	@Override
	protected void loadValues(Set<R> rows, IValueSetter<R, AGTransaction> setter) {

		logEntityField.getTargetTable().refreshAll(rows);
		List<L> logRows = logEntityField//
			.getTable()
			.createSelect()
			.where(logEntityField.isIn(rows))
			.list();
		logTransactionField.prefetch(logRows);

		logRows//
			.stream()
			.collect(Collectors.toMap(logEntityField::getValue, logTransactionField::getValue, AGTransaction::min))
			.entrySet()
			.stream()
			.forEach(entry -> setter.set(entry.getKey(), entry.getValue()));
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.CREATION_TRANSACTION;
	}

	@Override
	protected AGTransaction getDefaultValue() {

		return null;
	}
}
