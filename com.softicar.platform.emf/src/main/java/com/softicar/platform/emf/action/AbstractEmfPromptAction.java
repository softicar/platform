package com.softicar.platform.emf.action;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfPromptAction<R extends IEmfTableRow<R, ?>> extends AbstractEmfTitledAction<R> implements IEmfCommonAction<R> {

	protected abstract void buildPrompt(R tableRow, IEmfPromptActionInput<R> input);

	@Override
	protected IDomElement createNewElement(IEmfFormBody<R> formBody) {

		EmfPromptActionInput<R> input = new EmfPromptActionInput<>(formBody);
		buildPrompt(formBody.getTableRow(), input);
		return input;
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		showPrompt(formBody);
	}
}
