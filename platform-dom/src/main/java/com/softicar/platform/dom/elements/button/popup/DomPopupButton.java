package com.softicar.platform.dom.elements.button.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.compositor.DomParentNodeFinder;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A {@link DomButton} to open a {@link DomPopup}.
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
		setClickCallback(() -> openOrRepositionPopup(popupFactory));
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

	private void openOrRepositionPopup(Supplier<DomPopup> popupFactory) {

		if (popup == null || !popup.isAppended()) {
			openPopup(popupFactory);
		} else {
			repositionPopup();
		}
	}

	private void openPopup(Supplier<DomPopup> popupFactory) {

		callbackBeforeOpen.apply();
		popup = popupFactory.get();
		popup.open();
		callbackAfterOpen.apply();
	}

	private void repositionPopup() {

		findFrame(popup).ifPresent(frame -> {
			move(frame);
			raise(frame);
		});
	}

	private void move(DomPopupFrame frame) {

		var event = getDomDocument().getCurrentEvent();
		getDomEngine()
			.movePopup(//
				frame,
				event.getClientX(),
				event.getClientY(),
				DomPopupXAlign.LEFT,
				DomPopupYAlign.TOP);
	}

	private void raise(DomPopupFrame frame) {

		getDomEngine().setMaximumZIndex(frame);
	}

	private Optional<DomPopupFrame> findFrame(DomPopup popup) {

		return new DomParentNodeFinder<>(DomPopupFrame.class).findClosestParent(popup);
	}
}
