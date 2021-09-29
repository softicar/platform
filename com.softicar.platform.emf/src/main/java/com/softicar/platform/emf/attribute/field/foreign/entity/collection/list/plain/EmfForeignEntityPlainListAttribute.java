package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.plain;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.data.table.EmfDataTableStrategy;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.foreign.entity.EmfForeignEntityAttributeValidator;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

public class EmfForeignEntityPlainListAttribute<R extends IEmfTableRow<R, ?>, F extends IEmfObject<F>> extends EmfFieldAttribute<R, String> {

	private final IDbObjectTable<F> foreignTable;
	private BiPredicate<R, F> validator;

	public EmfForeignEntityPlainListAttribute(IDbField<R, String> field, IEmfObjectTable<F, ?> foreignTable) {

		super(field);

		this.foreignTable = foreignTable;
		this.validator = new EmfForeignEntityAttributeValidator<>();

		setDisplayFactory((String value) -> new EmfForeignEntityPlainListDisplay<>(foreignTable, value));
		setInputFactoryByEntity(entity -> new EmfForeignEntityPlainListInput<>(foreignTable, new EmfEntityInputEngine<>(entity, foreignTable, validator)));
		setDataTableStrategyFactory(() -> new DataTableStrategy(this));
	}

	public EmfForeignEntityPlainListAttribute<R, F> setValidator(BiPredicate<R, F> validator) {

		this.validator = validator;
		return this;
	}

	public List<F> getList(R tableRow) {

		String idList = Optional.ofNullable(field.getValue(tableRow)).orElse("");
		return new EmfForeignEntityIdPlainListParser(idList).parseToEntityList(foreignTable);
	}

	private class DataTableStrategy extends EmfDataTableStrategy<R, String> {

		public DataTableStrategy(IEmfAttribute<R, String> attribute) {

			super(attribute);
		}

		@Override
		public void setColumnHandlers(EmfDataTableDivBuilder<R> tableDivBuilder) {

			tableDivBuilder.setColumnHandler(dataColumn, new ColumnHandler());
		}
	}

	private class ColumnHandler extends EmfDataTableValueBasedColumnHandler<String> {

		private Map<Integer, F> entities;

		@Override
		public void prefetchData(IEmfDataTableColumn<?, String> column, Collection<String> values) {

			this.entities = new EmfForeignEntityPlainListsParser(values).parseToEntityMap(foreignTable);
		}

		@Override
		public void buildCell(IEmfDataTableCell<?, String> cell, String value) {

			for (String idString: value.split(",")) {
				IntegerParser//
					.parse(idString)
					.map(entities::get)
					.ifPresent(entity -> appendItem(cell, entity));
			}
		}

		private void appendItem(IEmfDataTableCell<?, String> cell, F entity) {

			cell.appendText(entity.toDisplayWithoutId());
			cell.appendNewChild(DomElementTag.BR);
		}
	}
}
