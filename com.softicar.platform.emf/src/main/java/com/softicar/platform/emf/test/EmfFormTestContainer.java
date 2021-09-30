package com.softicar.platform.emf.test;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.function.Supplier;

public class EmfFormTestContainer<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfFormSectionDiv<R> {

	private final EmfFormTestBody<R> formBody;

	public EmfFormTestContainer(EmfFormTestBody<R> formBody) {

		this.formBody = formBody;
	}

	@Override
	public IEmfFormBody<R> getFormBody() {

		return formBody;
	}

	@Override
	public void clearElements() {

		removeChildren();
	}

	@Override
	public void addElement(Supplier<IDomElement> elementSupplier) {

		appendChild(elementSupplier.get());
	}

	@Override
	public void setElements(Collection<Supplier<IDomElement>> elementSuppliers) {

		removeChildren();
		elementSuppliers.forEach(this::addElement);
	}

	@Override
	public void setInvisibleMode() {

		// nothing to do
	}
}
