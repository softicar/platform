package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.popup.button.DomPopupCancelButton;
import com.softicar.platform.dom.elements.popup.button.DomPopupCloseButton;
import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.elements.popup.position.strategy.DomPopupEventCoordinatesPositionStrategy;
import com.softicar.platform.dom.elements.popup.position.strategy.DomPopupViewportCenterPositionStrategy;
import com.softicar.platform.dom.elements.popup.position.strategy.DomPopupViewportOriginPositionStrategy;
import com.softicar.platform.dom.elements.popup.position.strategy.IDomPopupPositionStrategy;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssPercent;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A movable pop-up element, with a frame and caption.
 * <p>
 * Use {@link #setCaption(IDisplayString)} and
 * {@link #setSubCaption(IDisplayString)} to set caption and sub-caption,
 * respectively.
 * <p>
 * Use convenience methods like {@link #appendCancelButton()} or
 * {@link #appendCloseButton()} to append standard elements.
 * <p>
 * Use {@link #show()} to display the pop-up. Use {@link #hide()} to hide it.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomPopup extends DomDiv {

	protected final DomPopupFrame frame;
	private final DomPopupCloseManager closeActionManager;
	private IDomNode spawningNode;
	private boolean shown;
	private boolean displayHeader;
	private INullaryVoidFunction callbackBeforeShow;
	private DomActionBar actionBarFooter;
	private IDomPopupPositionStrategy positionStrategy;

	/**
	 * Constructs a new {@link DomPopup}.
	 * <p>
	 * Initially, the {@link DomPopup} is not displayed. Use {@link #show()} to
	 * display it.
	 * <p>
	 * Before calling {@link #show()}, the positioning may be specified via
	 * {@code "setPosition*"} methods. By default, the {@link DomPopup} is shown
	 * next to the node which generated the current {@link IDomEvent}.
	 *
	 * @see #show()
	 * @see #setPositionByEvent()
	 * @see #setPositionByViewportCenter(CssPercent, CssPercent)
	 * @see #setPositionByViewportOrigin(CssPercent, CssPercent)
	 */
	public DomPopup() {

		this.closeActionManager = new DomPopupCloseManager(this, this::hidePopup);
		this.frame = new DomPopupFrame(this);
		this.spawningNode = null;
		this.shown = false;
		this.displayHeader = true;
		this.callbackBeforeShow = INullaryVoidFunction.NO_OPERATION;
		this.actionBarFooter = null;

		setPositionByEvent();
		setCssClass(DomElementsCssClasses.DOM_POPUP);
	}

	// -------------------------------- header, caption and sub-caption -------------------------------- //

	/**
	 * Sets the caption for this pop-up.
	 *
	 * @param caption
	 *            the text to use as caption
	 */
	public void setCaption(IDisplayString caption) {

		frame.setCaption(caption);
	}

	/**
	 * Sets the sub caption for this pop-up.
	 * <p>
	 * A sub caption is an additional caption for this pop-up that will be
	 * displayed beneath the regular caption with a smaller font.
	 *
	 * @param subCaption
	 *            the text to use as sub caption
	 */
	public void setSubCaption(IDisplayString subCaption) {

		frame.setSubCaption(subCaption);
	}

	/**
	 * Specifies whether a header shall be displayed for this {@link DomPopup}.
	 * <p>
	 * By default, the header is displayed.
	 * <p>
	 * If no header shall be displayed, this method must be called before the
	 * {@link DomPopup} is displayed for the first time. If this
	 * {@link DomPopup} was once displayed with a header, subsequent attempts to
	 * remove the header with this method will have no effect.
	 *
	 * @param displayHeader
	 *            <i>true</i> if this {@link DomPopup} shall have a header;
	 *            <i>false</i> otherwise
	 */
	public void setDisplayHeader(boolean displayHeader) {

		this.displayHeader = displayHeader;
	}

	// -------------------------------- positioning, show and hide -------------------------------- //

	/**
	 * Positions this {@link DomPopup} according to
	 * {@link DomPopupEventCoordinatesPositionStrategy}.
	 * <p>
	 * Must be called before {@link #show()}.
	 *
	 * @return this {@link DomPopup}
	 */
	public DomPopup setPositionByEvent() {

		this.positionStrategy = new DomPopupEventCoordinatesPositionStrategy();
		return this;
	}

	/**
	 * Positions this {@link DomPopup} at the center of the viewport, using
	 * {@link DomPopupViewportCenterPositionStrategy}.
	 * <p>
	 * Must be called before {@link #show()}.
	 *
	 * @return this {@link DomPopup}
	 */
	public DomPopup setPositionByViewportCenter() {

		setPositionByViewportCenter(CssPercent._50, CssPercent._50);
		return this;
	}

	/**
	 * Positions this {@link DomPopup} according to
	 * {@link DomPopupViewportCenterPositionStrategy}.
	 * <p>
	 * Must be called before {@link #show()}.
	 *
	 * @return this {@link DomPopup}
	 */
	public DomPopup setPositionByViewportCenter(CssPercent xPercent, CssPercent yPercent) {

		this.positionStrategy = new DomPopupViewportCenterPositionStrategy(xPercent, yPercent);
		return this;
	}

	/**
	 * Positions this {@link DomPopup} at the top-left corner of the viewport,
	 * using {@link DomPopupViewportOriginPositionStrategy}.
	 * <p>
	 * Must be called before {@link #show()}.
	 *
	 * @return this {@link DomPopup}
	 */
	public DomPopup setPositionByViewportOrigin() {

		setPositionByViewportOrigin(CssPercent._0, CssPercent._0);
		return this;
	}

	/**
	 * Positions this {@link DomPopup} according to
	 * {@link DomPopupViewportOriginPositionStrategy}.
	 * <p>
	 * Must be called before {@link #show()}.
	 *
	 * @return this {@link DomPopup}
	 */
	public DomPopup setPositionByViewportOrigin(CssPercent xPercent, CssPercent yPercent) {

		this.positionStrategy = new DomPopupViewportOriginPositionStrategy(xPercent, yPercent);
		return this;
	}

	/**
	 * Shows this {@link DomPopup}.
	 */
	public void show() {

		show(positionStrategy.getPosition(getCurrentEvent()));
	}

	/**
	 * Hides this {@link DomPopup}.
	 */
	public void hide() {

		closeActionManager.closePopupNonInteractive();
	}

	/**
	 * Determines whether this {@link DomPopup} is currently shown.
	 *
	 * @return <i>true</i> if this {@link DomPopup} is shown; <i>false</i>
	 *         otherwise
	 */
	public boolean isShown() {

		return shown;
	}

	/**
	 * Specifies a callback to be executed immediately before this
	 * {@link DomPopup} is shown.
	 *
	 * @param callback
	 *            the before-show callback (never <i>null</i>)
	 */
	public void setCallbackBeforeShow(INullaryVoidFunction callback) {

		this.callbackBeforeShow = Objects.requireNonNull(callback);
	}

	// -------------------------------- focus -------------------------------- //

	/**
	 * Tries to focus the first focusable child element of this
	 * {@link DomPopup}. If there is no such element, the frame of this
	 * {@link DomPopup} gets focused.
	 * <p>
	 * Must be called <b>after</b> {@link #show()}.
	 */
	public void focusFrameOrFirstInputElement() {

		getDomEngine().focus(frame);
		focusFirst();
	}

	// -------------------------------- close manager -------------------------------- //

	/**
	 * Returns the {@link DomPopupCloseManager} of this {@link DomPopup}.
	 *
	 * @return the {@link DomPopupCloseManager} (never <i>null</i>)
	 */
	public DomPopupCloseManager getCloseManager() {

		return closeActionManager;
	}

	/**
	 * Determines whether the user shall be prompted with a <i>confirm</i>
	 * dialog before this {@link DomPopup} gets closed.
	 *
	 * @param confirmBeforeClose
	 *            <i>true</i> if the user shall be prompted; <i>false</i>
	 *            otherwise
	 */
	public void setConfirmBeforeClose(boolean confirmBeforeClose) {

		closeActionManager.setConfirmBeforeClose(confirmBeforeClose);
	}

	// ------------------------- actions -------------------------- //

	/**
	 * Appends the given {@link IDomNode} to the {@link DomActionBar} of this
	 * {@link DomPopup}.
	 *
	 * @param node
	 *            the {@link IDomNode} to append (never <i>null</i>)
	 */
	public <T extends IDomNode> T appendActionNode(T node) {

		if (actionBarFooter == null) {
			appendNewChild(DomElementTag.HR);
			this.actionBarFooter = appendChild(new DomActionBar());
		}

		return actionBarFooter.appendChild(node);
	}

	/**
	 * Appends a <i>close</i> button.
	 */
	public IDomElement appendCloseButton() {

		return appendActionNode(new DomPopupCloseButton(this));
	}

	/**
	 * Appends a <i>cancel</i> button.
	 */
	public IDomElement appendCancelButton() {

		return appendActionNode(new DomPopupCancelButton(this));
	}

	// ------------------------- modal -------------------------- //

	protected void showBackdrop(Supplier<DomDiv> backdropSupplier) {

		var backdrop = backdropSupplier.get();
		getDomEngine().setMaximumZIndex(backdrop);
		getDomDocument().getBody().appendChild(backdrop);
		getCloseManager().setCloseCallback(backdrop::disappend);
	}

	protected void trapTabFocus() {

		getDomEngine().trapTabFocus(frame);
	}

	// ------------------------- private -------------------------- //

	private void show(DomPopupPosition position) {

		this.spawningNode = determineSpawningNode();

		// append pop-up to document body
		if (frame.getParent() == null) {
			getDomDocument().getBody().appendChild(frame);
		}

		// prepare to show the pop-up
		frame.initialize(displayHeader);
		callbackBeforeShow.apply();

		// show the pop-up
		getDomEngine().showPopup(frame, position.getX(), position.getY(), position.getXAlign(), position.getYAlign());
		this.shown = true;

		// set focus
		focusFrameOrFirstInputElement();
	}

	private void hidePopup() {

		frame.disappend();
		getDomEngine().hidePopup(frame);
		this.shown = false;

		// try to re-focus the node that spawned the pop-up
		Optional.ofNullable(spawningNode).ifPresent(getDomEngine()::focus);
	}

	private IDomNode determineSpawningNode() {

		return Optional//
			.ofNullable(getCurrentEvent())
			.map(IDomEvent::getCurrentTarget)
			.orElse(null);
	}
}
