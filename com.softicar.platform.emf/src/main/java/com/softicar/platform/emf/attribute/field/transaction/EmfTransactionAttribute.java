package com.softicar.platform.emf.attribute.field.transaction;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Collection;

public class EmfTransactionAttribute<R extends IEmfTableRow<R, ?>, T extends IEmfTransactionObject<T>> extends EmfFieldAttribute<R, T> {

	private final IDbForeignField<R, T> transactionField;
	private final IDbTable<T, Integer> transactionTable;

	public EmfTransactionAttribute(IDbForeignField<R, T> transactionField) {

		super(transactionField);
		this.transactionField = transactionField;
		this.transactionTable = transactionField.getTargetTable();
	}

	@Override
	public final boolean isConcealed() {

		return true;
	}

	@Override
	public final boolean isEditable() {

		return false;
	}

	public void updateTransactionFields(Collection<R> entities) {

		T transactionRecord = new EmfTransactionRecordManager<>(transactionTable).getOrInsertRecord();
		entities.forEach(entity -> transactionField.setValue(entity, transactionRecord));
	}
}
