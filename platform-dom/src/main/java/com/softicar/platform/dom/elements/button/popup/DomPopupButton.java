package com.softicar.platform.dom.elements.button.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A {@link DomButton} to open a {@link DomPopup}.
 * <p>
 * TODO write a Selenium test to ensure x and y repositioning
 *
 * @author Alexander Schmidt
 */
public class DomPopupButton extends DomButton {

	private DomPopup popup;
	private INullaryVoidFunction callbackBeforeOpen = INullaryVoidFunction.NO_OPERATION;
	private INullaryVoidFunction callbackAfterOpen = INullaryVoidFunction.NO_OPERATION;

	/**
	 * Constructs a new {@link DomPopupButton}.
	 * <p>
	 * Use {@link #setPopupFactory(Supplier)} to specify the {@link DomPopup} to
	 * open.
	 */
	public DomPopupButton() {

		this.popup = null;
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

	private void openPopup(Supplier<DomPopup> popupFactory) {

		boolean alreadyOpen = isOpen();
		if (!alreadyOpen) {
			popup = popupFactory.get();
			callbackBeforeOpen.apply();
		}
		popup.open();
		if (!alreadyOpen) {
			callbackAfterOpen.apply();
		}
	}

	private boolean isOpen() {

		return popup != null && popup.isAppended();
	}
}
