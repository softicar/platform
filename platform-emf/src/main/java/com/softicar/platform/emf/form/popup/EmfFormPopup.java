package com.softicar.platform.emf.form.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Objects;
import java.util.function.Consumer;

public class EmfFormPopup<R extends IEmfTableRow<R, ?>> extends DomPopup implements IEmfFormFrame<R> {

	private final EmfForm<R> form;
	private final IEmfFormPopupConfiguration<R> popupConfiguration;

	public EmfFormPopup(R tableRow) {

		Objects.requireNonNull(tableRow);

		this.form = new EmfForm<>(this, tableRow);
		this.popupConfiguration = tableRow.table().getEmfTableConfiguration().getFormPopupConfiguration();

		setCallbackBeforeShow(form::peekAndRefresh);

		IDomParentElement container = appendChild(new DomBar());
		container.addCssClass(EmfCssClasses.EMF_FORM_POPUP_CONTAINER);
		appendAdditionalContent(container);

		container.appendChild(form);
	}

	@Override
	public void setTitle(IDisplayString title, IDisplayString subTitle) {

		setCaption(title);
		setSubCaption(subTitle);
	}

	@Override
	public void focusFrame() {

		focusFrameOrFirstInputElement();
	}

	@Override
	public void closeFrame() {

		hide();
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

	public EmfFormPopup<R> addAdditionalValidator(IEmfValidator<R> validator) {

		form.addAdditionalValidator(validator);
		return this;
	}

	/**
	 * Enables or disables direct editing for this pop-up.
	 * <p>
	 * If direct editing is enabled, the pop-up will be spawned in edit mode, so
	 * that clicking the generic "edit" action is not necessary. Additionally,
	 * when the user clicks the "save" or "cancel" buttons, the pop-up is
	 * closed, instead of the enclosed {@link EmfForm} switching to view mode.
	 * <p>
	 * Direct editing is disabled by default.
	 *
	 * @param enabled
	 *            <i>true</i> if direct editing should be enabled for this
	 *            pop-up; <i>false</i> otherwise
	 * @return this {@link EmfFormPopup}
	 */
	public EmfFormPopup<R> setDirectEditing(boolean enabled) {

		form.setDirectEditing(enabled);
		return this;
	}

	private void appendAdditionalContent(IDomParentElement container) {

		popupConfiguration//
			.createAdditionalContent(form.getTableRow())
			.forEach(container::appendChild);
	}
}
