package com.softicar.platform.dom.elements.dialog.testing;

public interface IDomModalPromptNodes<T> extends IDomModalDialogNodes<T> {

	T getInputElement();

	T getOkayButton();

	T getCancelButton();
}
