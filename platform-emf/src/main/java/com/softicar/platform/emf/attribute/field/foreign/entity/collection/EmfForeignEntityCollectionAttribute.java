package com.softicar.platform.emf.attribute.field.foreign.entity.collection;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.data.table.EmfAttributeDataTableStrategy;
import com.softicar.platform.emf.attribute.field.foreign.entity.EmfForeignEntityAttributeValidator;
import com.softicar.platform.emf.attribute.field.foreign.entity.scope.IEmfForeignEntityScope;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.collection.IEmfEntityCollection;
import com.softicar.platform.emf.collection.IEmfEntityCollectionTable;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.stream.Collectors;

public class EmfForeignEntityCollectionAttribute<R extends IEmfTableRow<R, ?>, S extends IEmfEntityCollection<S, F, FC>, F extends IEmfEntity<F, ?>, FC extends Collection<F>>
		extends EmfForeignRowAttribute<R, S> {

	private final IEmfEntityCollectionTable<S, F, FC, ?> collectionTable;
	private final EmfForeignEntityAttributeValidator<R, F> validator;

	public EmfForeignEntityCollectionAttribute(IDbForeignRowField<R, S, ?> field, IEmfEntityCollectionTable<S, F, FC, ?> collectionTable) {

		super(field);

		this.collectionTable = collectionTable;
		this.validator = new EmfForeignEntityAttributeValidator<>();

		setDataTableStrategyFactory(() -> new DataTableStrategy(this));
	}

	public IEmfEntityCollectionTable<S, F, FC, ?> getCollectionTable() {

		return collectionTable;
	}

	public EmfForeignEntityAttributeValidator<R, F> getValidator() {

		return validator;
	}

	public EmfForeignEntityCollectionAttribute<R, S, F, FC> setScope(IEmfForeignEntityScope<R, F> scope) {

		validator.setScope(scope);
		return this;
	}

	private class DataTableStrategy extends EmfAttributeDataTableStrategy<R, S> {

		public DataTableStrategy(IEmfAttribute<R, S> attribute) {

			super(attribute);
		}

		@Override
		public void setColumnHandlers(EmfDataTableDivBuilder<R> tableDivBuilder) {

			tableDivBuilder.setColumnHandler(dataColumn, new ColumnHandler());
		}
	}

	private class ColumnHandler extends EmfDataTableRowBasedColumnHandler<R, S> {

		@Override
		public void prefetchData(IEmfDataTableColumn<R, S> column, Collection<R> entities) {

			collectionTable
				.prefetchElements(
					field//
						.getValues(entities)
						.stream()
						.filter(it -> it != null)
						.collect(Collectors.toList()));
		}

		@Override
		public void buildCell(IEmfDataTableCell<R, S> cell, R tableRow) {

			createTabularDisplay(tableRow).ifPresent(cell::appendChild);
		}
	}
}
