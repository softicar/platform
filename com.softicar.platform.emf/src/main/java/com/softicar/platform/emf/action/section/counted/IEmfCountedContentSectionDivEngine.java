package com.softicar.platform.emf.action.section.counted;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfCountedContentSectionDivEngine<R extends IEmfTableRow<R, ?>> {

	IEmfFormBody<R> getFormBody();

	IDomElement createContentElement();

	int getContentCounter();

	void setCountChangedCallback(INullaryVoidFunction countChangedCallback);
}
