package com.softicar.platform.emf.form.tab.factory;

import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Supplier;

public class EmfFormTabFactory<R extends IEmfTableRow<R, ?>> implements IEmfFormTabFactory<R> {

	private final Supplier<DomTab> supplier;
	private final IEmfPredicate<R> visiblePredicate;

	public EmfFormTabFactory(Supplier<DomTab> supplier) {

		this(supplier, EmfPredicates.always());
	}

	public EmfFormTabFactory(Supplier<DomTab> supplier, IEmfPredicate<R> visiblePredicate) {

		this.supplier = supplier;
		this.visiblePredicate = visiblePredicate;
	}

	@Override
	public boolean isVisible(R tableRow) {

		return !tableRow.impermanent() && visiblePredicate.test(tableRow);
	}

	@Override
	public DomTab createTab(IEmfForm<R> form) {

		return supplier.get();
	}
}
