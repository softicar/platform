package com.softicar.platform.emf.form.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Objects;
import java.util.function.Consumer;

public class EmfFormPopup<R extends IEmfTableRow<R, ?>> extends DomPopup implements IEmfFormFrame<R> {

	private final IEmfForm<R> form;

	public EmfFormPopup(R tableRow) {

		Objects.requireNonNull(tableRow);
		var table = tableRow.table();

		this.form = table.getFormFactory().createForm(this, tableRow);
		configuration.setCallbackBeforeOpen(form::peekAndRefresh);

		table//
			.getFormPopupConfiguration()
			.getConfigurationModifiers()
			.forEach(this::configure);

		appendChild(form);
	}

	@Override
	public void setTitle(IDisplayString title, IDisplayString subTitle) {

		setCaption(title);
		setSubCaption(subTitle);
	}

	@Override
	public void focusFrame() {

		CurrentDomPopupCompositor.get().focus(this);
	}

	@Override
	public void closeFrame() {

		CurrentDomPopupCompositor.get().close(this);
	}

	public R getTableRow() {

		return form.getTableRow();
	}

	/**
	 * Delegates to {@link EmfForm#setCallbackAfterCreation(Consumer)}.
	 */
	public EmfFormPopup<R> setCallbackAfterCreation(Consumer<R> callbackAfterCreation) {

		form.setCallbackAfterCreation(callbackAfterCreation);
		return this;
	}

	/**
	 * Delegates to {@link EmfForm#addAdditionalValidator(IEmfValidator)}.
	 */
	public EmfFormPopup<R> addAdditionalValidator(IEmfValidator<R> validator) {

		form.addAdditionalValidator(validator);
		return this;
	}

	/**
	 * Delegates to {@link EmfForm#setDirectEditing(boolean)}.
	 */
	public EmfFormPopup<R> setDirectEditing(boolean enabled) {

		form.setDirectEditing(enabled);
		return this;
	}
}
