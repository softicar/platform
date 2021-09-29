package com.softicar.platform.emf.form.section.factory;

import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormSectionFactory<R extends IEmfTableRow<R, ?>> implements IEmfFormSectionFactory<R> {

	private final IEmfFormSectionFactory<R> sectionFactory;
	private final IEmfPredicate<R> predicate;
	private boolean open;

	public EmfFormSectionFactory(IEmfFormSectionFactory<R> sectionFactory) {

		this(sectionFactory, EmfPredicates.always());
		this.open = false;
	}

	public EmfFormSectionFactory(IEmfFormSectionFactory<R> sectionFactory, IEmfPredicate<R> predicate) {

		this.sectionFactory = sectionFactory;
		this.predicate = predicate;
	}

	public void setOpen(boolean open) {

		this.open = open;
	}

	public boolean isAvailable(R tableRow) {

		return predicate.test(tableRow);
	}

	@Override
	public EmfFormSectionDiv<R> createSection(IEmfFormBody<R> body) {

		EmfFormSectionDiv<R> sectionDiv = sectionFactory.createSection(body);
		sectionDiv.setOpen(open);
		return sectionDiv;
	}
}
