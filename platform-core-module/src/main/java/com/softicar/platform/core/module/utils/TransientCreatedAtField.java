package com.softicar.platform.core.module.utils;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;
import java.util.Set;

public class TransientCreatedAtField<R extends IEmfTableRow<R, ?>, L extends IDbRecord<L, Tuple2<R, AGTransaction>>>
		extends AbstractTransientObjectField<R, DayTime> {

	private final IDbRecordTable<L, Tuple2<R, AGTransaction>> logTable;
	private final IDbForeignRowField<L, R, ?> fkField;
	private final IDbForeignRowField<L, AGTransaction, ?> transactionField;

	public TransientCreatedAtField(IDbRecordTable<L, Tuple2<R, AGTransaction>> logTable, IDbForeignRowField<L, R, ?> fkField,
			IDbForeignRowField<L, AGTransaction, ?> transactionField) {

		super(DayTime.class);

		this.logTable = Objects.requireNonNull(logTable);
		this.fkField = Objects.requireNonNull(fkField);
		this.transactionField = Objects.requireNonNull(transactionField);
	}

	@Override
	protected void loadValues(Set<R> rows, IValueSetter<R, DayTime> setter) {

		for (Tuple2<R, DayTime> row: Sql//
			.from(logTable)
			.where(fkField.isIn(rows))
			.select(fkField)
			.groupBy(fkField)
			.join(transactionField)
			.select(AGTransaction.AT.min())) {
			setter.set(row.get0(), row.get1());
		}
	}

	@Override
	protected DayTime getDefaultValue() {

		return null;
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CREATED_AT;
	}
}
