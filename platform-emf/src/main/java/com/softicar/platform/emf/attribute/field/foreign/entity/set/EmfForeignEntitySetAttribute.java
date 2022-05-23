package com.softicar.platform.emf.attribute.field.foreign.entity.set;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.field.foreign.entity.EmfForeignEntityAttributeValidator;
import com.softicar.platform.emf.attribute.field.foreign.entity.scope.IEmfForeignEntityScope;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.set.IEmfEntitySet;
import com.softicar.platform.emf.entity.set.IEmfEntitySetTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.stream.Collectors;

public class EmfForeignEntitySetAttribute<R extends IEmfTableRow<R, ?>, S extends IEmfEntitySet<S, F>, F extends IEmfEntity<F, ?>>
		extends EmfForeignRowAttribute<R, S> {

	private final IEmfEntitySetTable<S, F, ?> setTable;
	private final EmfForeignEntityAttributeValidator<R, F> validator;

	public EmfForeignEntitySetAttribute(IDbForeignRowField<R, S, ?> field, IEmfEntitySetTable<S, F, ?> setTable) {

		super(field);

		this.setTable = setTable;
		this.validator = new EmfForeignEntityAttributeValidator<>();

		setColumnHandlerFactory(ColumnHandler::new);
	}

	public IEmfEntitySetTable<S, F, ?> getSetTable() {

		return setTable;
	}

	public EmfForeignEntityAttributeValidator<R, F> getValidator() {

		return validator;
	}

	public EmfForeignEntitySetAttribute<R, S, F> setScope(IEmfForeignEntityScope<R, F> scope) {

		validator.setScope(scope);
		return this;
	}

	private class ColumnHandler extends EmfDataTableRowBasedColumnHandler<R, S> {

		@Override
		public void prefetchData(IEmfDataTableColumn<R, S> column, Collection<R> entities) {

			setTable
				.prefetchElements(
					field//
						.getValues(entities)
						.stream()
						.filter(it -> it != null)
						.collect(Collectors.toList()));
		}

		@Override
		public void buildCell(IEmfDataTableCell<R, S> cell, R tableRow) {

			cell.appendChild(createTabularDisplay(tableRow));
		}
	}
}
