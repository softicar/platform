package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import java.util.Objects;

/**
 * A custom modal dialog that replaces a native "confirm" dialog.
 *
 * @author Alexander Schmidt
 */
public class DomModalConfirmPopup extends DomModalDialogPopup {

	private final OkayButton okayButton;

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

		addCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_POPUP_WRAPPED);

		Objects.requireNonNull(confirmHandler);
		Objects.requireNonNull(message);

		getContent().appendText(message);
		appendActionNode(okayButton = new OkayButton(confirmHandler));
		appendCancelButton().setMarker(DomModalConfirmMarker.CANCEL_BUTTON);
	}

	@Override
	public void show() {

		super.open();
		getDomEngine().focus(okayButton);
	}

	private class OkayButton extends DomButton {

		private final INullaryVoidFunction confirmHandler;

		public OkayButton(INullaryVoidFunction confirmHandler) {

			this.confirmHandler = confirmHandler;

			setLabel(DomI18n.OK);
			setIcon(DomElementsImages.DIALOG_OKAY.getResource());
			setClickCallback(this::handleClick);
			setMarker(DomModalConfirmMarker.OKAY_BUTTON);
		}

		private void handleClick() {

			closeWithoutConfirm();
			confirmHandler.apply();
		}
	}
}
