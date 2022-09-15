package com.softicar.platform.emf.management.prefilter;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.db.runtime.data.table.DbTableBasedDataTableValueFilter;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.IDomValueInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public abstract class AbstractEmfPrefilterRow<E extends IEmfTableRow<E, ?>> extends DomDiv {

	private final DomDiv contentElement;
	private final Collection<IDomValueInput<?>> inputElements;

	public AbstractEmfPrefilterRow() {

		this.contentElement = new DomDiv();
		this.inputElements = new ArrayList<>();
		contentElement.addCssClass(EmfCssClasses.EMF_PREFILTER_ROW_CONTENT);
	}

	public boolean isEmpty() {

		return inputElements//
			.stream()
			.allMatch(input -> !input.getValue().isPresent());
	}

	public abstract ISqlBooleanExpression<E> getFilterExpression();

	protected <I extends IDomValueInput<?>> I appendInputElement(I inputElement) {

		appendNode(inputElement);
		this.inputElements.add(inputElement);
		return inputElement;
	}

	protected void appendNode(IDomNode node) {

		this.contentElement.appendChild(node);
	}

	protected <V> Optional<ISqlBooleanExpression<E>> createFilterExpressionWithOperator(IDbField<E, V> field, DataTableValueFilterOperator operator,
			Optional<V> value) {

		return value.map(it -> new DbTableBasedDataTableValueFilter<>(field, operator, it)).map(DbTableBasedDataTableValueFilter::getExpression);
	}

	protected <V> Optional<ISqlBooleanExpression<E>> createFilterExpression(IDbField<E, V> field, Optional<V> value) {

		return value.map(field::isEqual);
	}

	protected Optional<ISqlBooleanExpression<E>> createStringFilterExpression(IDbStringField<E> field, Optional<String> value) {

		return value//
			.map(string -> string = "%" + string + "%")
			.map(field::like);
	}

	public void appendRemoveRowButton(DomButton button) {

		this.contentElement.appendChild(button);
	}

	public void appendContent() {

		appendChild(contentElement);
	}

	public void appendContent(DomButton button) {

		appendChild(button);
		appendContent();
	}
}
