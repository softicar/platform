package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import java.util.Objects;
import java.util.Optional;

/**
 * A custom modal dialog that replaces a native "confirm" dialog.
 *
 * @author Alexander Schmidt
 */
public class DomModalConfirmPopup extends DomModalDialogPopup {

	private final OkayButton okayButton;
	private final INullaryVoidFunction confirmHandler;
	private final INullaryVoidFunction cancelHandler;
	private boolean confirmed;

	/**
	 * Constructs a new {@link DomModalConfirmPopup} that displays the given
	 * message, and "OK" / "Cancel" buttons.
	 *
	 * @param confirmHandler
	 *            the handler to be processed in case the user clicks "OK"
	 *            (never <i>null</i>)
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	public DomModalConfirmPopup(INullaryVoidFunction confirmHandler, IDisplayString message) {

		this(confirmHandler, null, message);
	}

	/**
	 * Constructs a new {@link DomModalConfirmPopup} that displays the given
	 * message, and "OK" / "Cancel" buttons.
	 *
	 * @param confirmHandler
	 *            the handler to be processed in case the user clicks "OK"
	 *            (never <i>null</i>)
	 * @param cancelHandler
	 *            the handler to be processed in case the user clicks "Cancel"
	 *            (may be <i>null</i>)
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	public DomModalConfirmPopup(INullaryVoidFunction confirmHandler, INullaryVoidFunction cancelHandler, IDisplayString message) {

		addCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_POPUP_WRAPPED);

		this.confirmHandler = Objects.requireNonNull(confirmHandler);
		this.cancelHandler = cancelHandler;
		Objects.requireNonNull(message);

		this.confirmed = false;
		this.configuration.setCallbackBeforeClose(this::executeCancelCallback);

		getContent().appendText(message);
		appendActionNode(okayButton = new OkayButton());
		appendCancelButton().addMarker(DomModalConfirmMarker.CANCEL_BUTTON);
	}

	@Override
	public void open() {

		super.open();
		getDomEngine().focus(okayButton);
	}

	private void executeCancelCallback() {

		if (!confirmed) {
			Optional.ofNullable(cancelHandler).ifPresent(INullaryVoidFunction::apply);
		}
	}

	private class OkayButton extends DomButton {

		public OkayButton() {

			setLabel(DomI18n.OK);
			setIcon(DomElementsImages.DIALOG_OKAY.getResource());
			setClickCallback(this::handleClick);
			addMarker(DomModalConfirmMarker.OKAY_BUTTON);
		}

		private void handleClick() {

			confirmed = true;
			close();
			confirmHandler.apply();
		}
	}
}
