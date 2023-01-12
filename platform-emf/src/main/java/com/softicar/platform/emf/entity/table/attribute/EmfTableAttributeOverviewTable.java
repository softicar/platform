package com.softicar.platform.emf.entity.table.attribute;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.predicate.EmfPredicateWrapper;
import com.softicar.platform.emf.table.IEmfTable;
import java.util.ArrayList;

public class EmfTableAttributeOverviewTable extends AbstractInMemoryDataTable<IEmfAttribute<?, ?>> {

	private final IEmfTable<?, ?, ?> table;
	private final IDataTableColumn<IEmfAttribute<?, ?>, IDisplayString> titleColumn;
	private final IDataTableColumn<IEmfAttribute<?, ?>, String> valueClassColumn;
	private final IDataTableColumn<IEmfAttribute<?, ?>, Boolean> isConcealedColumn;
	private final IDataTableColumn<IEmfAttribute<?, ?>, Boolean> isEditableColumn;
	private final IDataTableColumn<IEmfAttribute<?, ?>, Boolean> isTransientColumn;
	private final IDataTableColumn<IEmfAttribute<?, ?>, EmfPredicateWrapper> predicateVisibleColumn;
	private final IDataTableColumn<IEmfAttribute<?, ?>, EmfPredicateWrapper> predicateEditableColumn;
	private final IDataTableColumn<IEmfAttribute<?, ?>, EmfPredicateWrapper> predicateMandatoryColumn;

	public EmfTableAttributeOverviewTable(IEmfTable<?, ?, ?> table) {

		this.table = table;
		this.titleColumn = newColumn(IDisplayString.class)//
			.setGetter(IEmfAttribute::getTitle)
			.setTitle(EmfI18n.TITLE)
			.addColumn();
		this.valueClassColumn = newColumn(String.class)//
			.setGetter(attribute -> attribute.getValueClass().getSimpleName())
			.setTitle(EmfI18n.VALUE_CLASS)
			.addColumn();
		this.isConcealedColumn = newColumn(Boolean.class)//
			.setGetter(IEmfAttribute::isConcealed)
			.setTitle(EmfI18n.CONCEALED)
			.addColumn();
		this.isEditableColumn = newColumn(Boolean.class)//
			.setGetter(IEmfAttribute::isEditable)
			.setTitle(EmfI18n.EDITABLE)
			.addColumn();
		this.isTransientColumn = newColumn(Boolean.class)//
			.setGetter(IEmfAttribute::isTransient)
			.setTitle(EmfI18n.TRANSIENT)
			.addColumn();
		this.predicateVisibleColumn = newColumn(EmfPredicateWrapper.class)//
			.setGetter(attribute -> new EmfPredicateWrapper(attribute.getPredicateVisible()))
			.setTitle(EmfI18n.VISIBILITY_PREDICATE)
			.addColumn();
		this.predicateEditableColumn = newColumn(EmfPredicateWrapper.class)//
			.setGetter(attribute -> new EmfPredicateWrapper(attribute.getPredicateEditable()))
			.setTitle(EmfI18n.EDITABILITY_PREDICATE)
			.addColumn();
		this.predicateMandatoryColumn = newColumn(EmfPredicateWrapper.class)//
			.setGetter(attribute -> new EmfPredicateWrapper(attribute.getPredicateMandatory()))
			.setTitle(EmfI18n.MANDATORINESS_PREDICATE)
			.addColumn();
	}

	@Override
	public DataTableIdentifier getIdentifier() {

		return new DataTableIdentifier("fc54f4cb-ba83-44dc-9f3c-8ea2b727c0b9");
	}

	@Override
	protected Iterable<IEmfAttribute<?, ?>> getTableRows() {
	
		return new ArrayList<>(table.getAttributes());
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, IDisplayString> getTitleColumn() {

		return titleColumn;
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, String> getValueClassColumn() {

		return valueClassColumn;
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, Boolean> getConcealedColumn() {

		return isConcealedColumn;
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, Boolean> getEditableColumn() {

		return isEditableColumn;
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, Boolean> getTransientColumn() {

		return isTransientColumn;
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, EmfPredicateWrapper> getPredicateVisibleColumn() {

		return predicateVisibleColumn;
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, EmfPredicateWrapper> getPredicateEditableColumn() {

		return predicateEditableColumn;
	}

	public IDataTableColumn<IEmfAttribute<?, ?>, EmfPredicateWrapper> getPredicateMandatoryColumn() {

		return predicateMandatoryColumn;
	}
}
