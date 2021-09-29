package com.softicar.platform.dom.elements.button;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomAnchor;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.anchor.DomHiddenLinkAnchor;
import com.softicar.platform.dom.elements.interfaces.IDomLabeledElement;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.parent.DomParentElement;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A <i>button</i> UI element.
 * <p>
 * Possesses an icon, a label, and a callback function to be executed when the
 * button is triggered (i.e. the <i>click callback</i>).
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomButton extends DomParentElement
		implements IDomFocusable, IDomLabeledElement<DomButton>, IDomClickEventHandler, IDomEnterKeyEventHandler, IDomSpaceKeyEventHandler {

	private Icon icon;
	private Label label;
	private DomAnchor linkAnchor;
	private INullaryVoidFunction clickCallback;
	private Supplier<Optional<IDisplayString>> confirmationMessageSupplier;
	private boolean enabled;

	/**
	 * Constructs a new {@link DomButton}.
	 * <p>
	 * Use {@link #setIcon(IResource)} and/or {@link #setLabel(IDisplayString)}
	 * to specify an icon or a label, respectively.
	 * <p>
	 * Use {@link #setClickCallback(INullaryVoidFunction)} to specify the
	 * callback function to be executed when the {@link DomButton} is triggered.
	 */
	public DomButton() {

		this.icon = null;
		this.label = null;
		this.linkAnchor = null;
		this.clickCallback = INullaryVoidFunction.NO_OPERATION;
		this.confirmationMessageSupplier = Optional::empty;
		this.enabled = true;

		setAttribute("type", "button");
		setTabIndex(0);

		addCssClass(DomElementsCssClasses.DOM_BUTTON);

		getDomEngine().setFireOnKeyUp(this, DomEventType.ENTER, true);
		getDomEngine().setFireOnKeyUp(this, DomEventType.SPACE, true);
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.ENTER, Collections.singleton(DomCssPseudoClasses.ACTIVE));
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.SPACE, Collections.singleton(DomCssPseudoClasses.ACTIVE));
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.BUTTON;
	}

	// -------------------- Basic Properties -------------------- //

	/**
	 * Defines the icon to be shown on this {@link DomButton}.
	 *
	 * @param iconResource
	 *            the {@link IResource} that represents the icon (never
	 *            <i>null</i>)
	 * @return this {@link DomButton}
	 */
	public DomButton setIcon(IResource iconResource) {

		return setIcon(iconResource, false);
	}

	/**
	 * Defines the icon to be shown on this {@link DomButton}, in a way that
	 * protects its colors from being altered by the theme.
	 * <p>
	 * Internally, sets {@link DomCssPseudoClasses#PRECOLORED} on the icon.
	 *
	 * @param iconResource
	 *            the {@link IResource} that represents the pre-colored icon
	 *            (never <i>null</i>)
	 * @return this {@link DomButton}
	 */
	public DomButton setPrecoloredIcon(IResource iconResource) {

		return setIcon(iconResource, true);
	}

	/**
	 * Removes the icon to be shown on this {@link DomButton}.
	 * <p>
	 * If there is no defined icon, nothing will happen.
	 *
	 * @return this {@link DomButton}
	 */
	public DomButton removeIcon() {

		if (icon != null) {
			removeChild(icon);
			this.icon = null;
		}
		return this;
	}

	/**
	 * Defines the label to be shown on this {@link DomButton}.
	 *
	 * @param labelString
	 *            the label as {@link IDisplayString} (never <i>null</i>)
	 * @return this {@link DomButton}
	 */
	@Override
	public DomButton setLabel(IDisplayString labelString) {

		removeLabel();
		this.label = appendChild(new Label(labelString));
		return this;
	}

	/**
	 * Removes the label to be shown on this {@link DomButton}.
	 * <p>
	 * If there is no defined label, nothing will happen.
	 *
	 * @return this {@link DomButton}
	 */
	public DomButton removeLabel() {

		if (label != null) {
			removeChild(label);
			this.label = null;
		}
		return this;
	}

	/**
	 * Defines the title (tool tip) to be shown on this {@link DomButton}.
	 *
	 * @param titleString
	 *            the title as {@link IDisplayString} (never <i>null</i>)
	 * @return this {@link DomButton}
	 */
	@Override
	public DomButton setTitle(IDisplayString titleString) {

		super.setTitle(titleString);
		return this;
	}

	/**
	 * Defines the {@link IStaticObject} marker for this node.
	 *
	 * @param marker
	 *            the marker to set (never <i>null</i>)
	 * @return this {@link DomButton}
	 * @throws UnsupportedOperationException
	 *             if the {@link IDomDocument} does not support marking of nodes
	 */
	@Override
	public DomButton setMarker(IStaticObject marker) {

		super.setMarker(marker);
		return this;
	}

	// -------------------- Callback -------------------- //

	/**
	 * Defines the callback function to be executed when this {@link DomButton}
	 * is triggered.
	 * <p>
	 * If this method is <b>not</b> called at all, nothing will happen when this
	 * {@link DomButton} is triggered.
	 *
	 * @param clickCallback
	 *            the <i>click callback</i> function (never <i>null</i>)
	 * @return this {@link DomButton}
	 */
	public final DomButton setClickCallback(INullaryVoidFunction clickCallback) {

		this.clickCallback = clickCallback;
		return this;
	}

	/**
	 * The handler method that will be executed when this {@link DomButton} is
	 * clicked.
	 * <p>
	 * Should not be called directly.
	 */
	@Override
	public final void handleClick(IDomEvent event) {

		triggerButton();
	}

	/**
	 * The handler method that will be executed when {@code ENTER} is pressed on
	 * this {@link DomButton}.
	 * <p>
	 * Should not be called directly.
	 */
	@Override
	public final void handleEnterKey(IDomEvent event) {

		triggerButton();
	}

	/**
	 * The handler method that will be executed when {@code SPACE} is pressed on
	 * this {@link DomButton}.
	 * <p>
	 * Should not be called directly.
	 */
	@Override
	public final void handleSpaceKey(IDomEvent event) {

		triggerButton();
	}

	// -------------------- Enabled / Disabled -------------------- //

	/**
	 * Enables or disables this {@link DomButton}.
	 * <p>
	 * {@link DomButton} is enabled by default.
	 *
	 * @param enabled
	 *            <i>true</i> if it shall be enabled; <i>false</i> if it shall
	 *            be disabled.
	 * @return this {@link DomButton}
	 */
	public DomButton setEnabled(boolean enabled) {

		if (enabled != this.enabled) {
			this.enabled = enabled;

			if (enabled) {
				setTabIndex(0);
				listenToEvent(DomEventType.CLICK);
				listenToEvent(DomEventType.ENTER);
				listenToEvent(DomEventType.SPACE);
				removeCssClass(DomCssPseudoClasses.DISABLED);
			} else {
				setTabIndex(-1);
				unlistenToEvent(DomEventType.CLICK);
				unlistenToEvent(DomEventType.ENTER);
				unlistenToEvent(DomEventType.SPACE);
				addCssClass(DomCssPseudoClasses.DISABLED);
			}
		}
		return this;
	}

	/**
	 * Determines whether this {@link DomButton} is enabled.
	 *
	 * @return <i>true</i> if this {@link DomButton} is enabled; <i>false</i>
	 *         otherwise
	 */
	public final boolean isEnabled() {

		return enabled;
	}

	// -------------------- Confirmation -------------------- //

	/**
	 * Defines a {@link Supplier} of a confirmation message.
	 * <p>
	 * When a confirmation message {@link Supplier} is defined, the user will be
	 * prompted for confirmation after triggering the {@link DomButton}. The
	 * <i>click callback</i> (see
	 * {@link #setClickCallback(INullaryVoidFunction)}) will then only be
	 * executed in case the user confirms their intention to trigger the
	 * {@link DomButton}.
	 * <p>
	 * When the given {@link Supplier#get()} yields <i>null</i>, no confirmation
	 * message will be displayed to the user. In that case, the <i>click
	 * callback</i> will be executed without confirmation.
	 *
	 * @param confirmationMessageSupplier
	 *            the {@link Supplier} of a confirmation message (never
	 *            <i>null</i>; the supplied {@link IDisplayString} may be
	 *            <i>null</i>)
	 * @return this {@link DomButton}
	 */
	public final DomButton setConfirmationMessageSupplier(Supplier<IDisplayString> confirmationMessageSupplier) {

		Objects.requireNonNull(confirmationMessageSupplier);
		this.confirmationMessageSupplier = () -> Optional.ofNullable(confirmationMessageSupplier.get());
		return this;
	}

	/**
	 * Defines a confirmation message.
	 * <p>
	 * When a confirmation message {@link Supplier} is defined, the user will be
	 * prompted for confirmation after triggering the {@link DomButton}. The
	 * <i>click callback</i> (see
	 * {@link #setClickCallback(INullaryVoidFunction)}) will then only be
	 * executed in case the user confirms their intention to trigger the
	 * {@link DomButton}.
	 * <p>
	 * When <i>null</i> is given, no confirmation message will be displayed to
	 * the user. In that case, the <i>click callback</i> will be executed
	 * without confirmation.
	 *
	 * @param message
	 *            the confirmation message (may be <i>null</i>)
	 * @return this {@link DomButton}
	 */
	public final DomButton setConfirmationMessage(IDisplayString message) {

		return setConfirmationMessageSupplier(() -> message);
	}

	// ------------------------------ external link ------------------------------ //

	/**
	 * Sets the hyper reference to an external URL, to be opened in a new tab
	 * when this {@link DomButton} is triggered.
	 *
	 * @param href
	 *            the hyper reference, or URL (never <i>null</i>)
	 * @return this {@link DomButton}
	 */
	public DomButton setExternalLink(String href) {

		appendLinkAnchor(href);
		return this;
	}

	// ------------------------------ private ------------------------------ //

	private void triggerButton() {

		if (enabled) {
			Optional<IDisplayString> confirmationMessage = confirmationMessageSupplier.get();
			if (confirmationMessage.isPresent()) {
				executeConfirm(clickCallback::apply, confirmationMessage.get());
			} else {
				clickCallback.apply();
			}
		}
	}

	private void appendLinkAnchor(String href) {

		if (linkAnchor == null) {
			this.linkAnchor = new DomHiddenLinkAnchor()//
				.setOpenInNewTab(true)
				.enableEventDelegation(this);
			appendChild(linkAnchor);
		}

		linkAnchor.setHRef(href);
	}

	private DomButton setIcon(IResource iconResource, boolean precolored) {

		removeIcon();
		this.icon = prependChild(new Icon(iconResource, precolored));
		return this;
	}

	private class Icon extends DomImage {

		public Icon(IResource iconResource, boolean precolored) {

			super(iconResource);
			addCssClass(DomElementsCssClasses.DOM_BUTTON_ICON);
			if (precolored) {
				addCssClass(DomCssPseudoClasses.PRECOLORED);
			}
		}
	}

	private class Label extends DomDiv {

		public Label(IDisplayString labelString) {

			addCssClass(DomElementsCssClasses.DOM_BUTTON_LABEL);
			appendText(labelString);
		}
	}
}
