package com.softicar.platform.emf.form.section;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public interface IEmfFormSectionDiv<R extends IEmfTableRow<R, ?>> extends IEmfFormSectionContainer {

	IEmfFormBody<R> getFormBody();

	void clearElements();

	void addElement(Supplier<IDomElement> elementSupplier);

	void setElements(Collection<Supplier<IDomElement>> elementSuppliers);

	default void setElement(Supplier<IDomElement> elementSupplier) {

		setElements(Collections.singleton(elementSupplier));
	}

	void setInvisibleMode();
}
