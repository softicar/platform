package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfPromptActionInput<R extends IEmfTableRow<R, ?>> {

	void addSaveHandler(INullaryVoidFunction submitHandler);

	void appendRow(IDisplayString title, IDomNode node);

	DomTextArea appendTextArea(IDisplayString title);

	void appendNode(IDomNode node);

	IEmfFormBody<R> getFormBody();

	void cancelSave();
}
