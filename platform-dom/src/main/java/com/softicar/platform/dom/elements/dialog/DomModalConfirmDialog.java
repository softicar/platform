package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import java.util.Objects;
import java.util.Optional;

/**
 * A custom modal dialog that replaces a native "confirm" dialog.
 *
 * @author Alexander Schmidt
 */
public class DomModalConfirmDialog extends DomModalDialog {

	private final INullaryVoidFunction confirmHandler;
	private final INullaryVoidFunction cancelHandler;
	private boolean confirmed;

	/**
	 * Constructs a new {@link DomModalConfirmDialog} that displays the given
	 * message, and "OK" / "Cancel" buttons.
	 *
	 * @param confirmHandler
	 *            the handler to be processed in case the user clicks "OK"
	 *            (never <i>null</i>)
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	public DomModalConfirmDialog(INullaryVoidFunction confirmHandler, IDisplayString message) {

		this(confirmHandler, null, message);
	}

	/**
	 * Constructs a new {@link DomModalConfirmDialog} that displays the given
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
	public DomModalConfirmDialog(INullaryVoidFunction confirmHandler, INullaryVoidFunction cancelHandler, IDisplayString message) {

		this.confirmHandler = Objects.requireNonNull(confirmHandler);
		this.cancelHandler = cancelHandler;
		this.confirmed = false;
		this.configuration.setCallbackBeforeClose(this::executeCancelCallback);

		appendContent(message);
		appendActionNode(new OkayButton());
		appendCancelButton().addMarker(DomTestMarker.MODAL_CONFIRM_CANCEL_BUTTON);
	}

	private void executeCancelCallback() {

		if (!confirmed) {
			Optional.ofNullable(cancelHandler).ifPresent(INullaryVoidFunction::apply);
		}
	}

	private class OkayButton extends DomButton {

		public OkayButton() {

			setLabel(DomI18n.OK);
			setIcon(DomImages.DIALOG_OKAY.getResource());
			setClickCallback(this::handleClick);
			addMarker(DomTestMarker.MODAL_CONFIRM_OKAY_BUTTON);
		}

		private void handleClick() {

			confirmed = true;
			close();
			confirmHandler.apply();
		}
	}
}
