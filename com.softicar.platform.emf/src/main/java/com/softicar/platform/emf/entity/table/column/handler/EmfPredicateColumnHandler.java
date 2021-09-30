package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.predicate.EmfPredicateToDisplayVisitor;
import com.softicar.platform.emf.predicate.EmfPredicateWrapper;

public class EmfPredicateColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfPredicateWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfPredicateWrapper> cell, EmfPredicateWrapper value) {

		DomDiv content = new DomDiv();
		content.addCssClass(EmfCssClasses.EMF_PREDICATE_COLUMN_HANDLER);
		content.appendChild(new EmfPredicateToDisplayVisitor<>(value.getPredicate()).toDisplay());
		cell.appendChild(content);
	}
}
