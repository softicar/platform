package com.softicar.platform.dom.elements.button.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A {@link DomButton} to open a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public class DomPopupButton extends DomButton {

	private DomPopup popup;
	private boolean retainOpen;
	private INullaryVoidFunction callbackBeforeOpen;
	private INullaryVoidFunction callbackAfterOpen;

	/**
	 * Constructs a new {@link DomPopupButton}.
	 * <p>
	 * Use {@link #setPopupFactory(Supplier)} to specify the {@link DomPopup} to
	 * open.
	 */
	public DomPopupButton() {

		this.popup = null;
		this.retainOpen = true;
		this.callbackBeforeOpen = INullaryVoidFunction.NO_OPERATION;
		this.callbackAfterOpen = INullaryVoidFunction.NO_OPERATION;
	}

	/**
	 * Specifies the {@link DomPopup} to open when this {@link DomPopupButton}
	 * is clicked.
	 *
	 * @param popupFactory
	 *            supplies the {@link DomPopup} to open (never <i>null</i>)
	 * @return this {@link DomPopupButton}
	 */
	public DomPopupButton setPopupFactory(Supplier<DomPopup> popupFactory) {

		Objects.requireNonNull(popupFactory);
		setClickCallback(() -> openPopup(popupFactory));
		return this;
	}

	/**
	 * Specifies a callback to be executed immediately before the
	 * {@link DomPopup} is opened.
	 *
	 * @param callback
	 *            the callback to execute (never <i>null</i>)
	 * @return this {@link DomPopupButton}
	 */
	public DomPopupButton setCallbackBeforeOpen(INullaryVoidFunction callback) {

		this.callbackBeforeOpen = Objects.requireNonNull(callback);
		return this;
	}

	/**
	 * Specifies a callback to be executed immediately after the
	 * {@link DomPopup} is opened.
	 *
	 * @param callback
	 *            the callback to execute (never <i>null</i>)
	 * @return this {@link DomPopupButton}
	 */
	public DomPopupButton setCallbackAfterOpen(INullaryVoidFunction callback) {

		this.callbackAfterOpen = Objects.requireNonNull(callback);
		return this;
	}

	/**
	 * Specifies whether an already-open {@link DomPopup} shall be retained if
	 * this {@link DomPopupButton} is clicked again.
	 *
	 * @param retainOpen
	 *            <i>true</i> if the {@link DomPopup} shall be retained on
	 *            click; <i>false</i> if an additional {@link DomPopup} shall be
	 *            opened on click
	 * @return this {@link DomPopupButton}
	 */
	public DomPopupButton setRetainOpen(boolean retainOpen) {

		this.retainOpen = retainOpen;
		return this;
	}

	private void openPopup(Supplier<DomPopup> popupFactory) {

		if (isOpen() && retainOpen) {
			popup.open();
		} else {
			popup = popupFactory.get();
			callbackBeforeOpen.apply();
			popup.open();
			callbackAfterOpen.apply();
		}
	}

	private boolean isOpen() {

		return popup != null && popup.isAppended();
	}
}
