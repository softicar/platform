package com.softicar.platform.emf.action;

import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfConfirmedAction<R extends IEmfTableRow<R, ?>> extends AbstractEmfPromptAction<R> {

	@Override
	public final void buildPrompt(R tableRow, IEmfPromptActionInput<R> input) {

		input.appendNode(new DomMessageDiv(DomMessageType.WARNING, getConfirmationMessageNode(tableRow)));
		input.addSaveHandler(() -> save(tableRow));
	}

	protected abstract IDomNode getConfirmationMessageNode(R tableRow);

	protected abstract void save(R tableRow);
}
