package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.popup.button.DomPopupCancelButton;
import com.softicar.platform.dom.elements.popup.button.DomPopupCloseButton;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupConfiguration;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupDisplayMode;
import com.softicar.platform.dom.elements.popup.configuration.IDomPopupConfiguration;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A pop-up element that is displayed on top of the regular {@link IDomDocument}
 * content.
 * <p>
 * Provides various display modes. The default display mode is
 * {@link DomPopupDisplayMode#DRAGGABLE}.
 * <p>
 * Use {@link #setCaption(IDisplayString)} and
 * {@link #setSubCaption(IDisplayString)} to set caption and sub-caption,
 * respectively.
 * <p>
 * Use {@link #configure(Consumer)} to further customize display settings and
 * behavior.
 * <p>
 * Use convenience methods like {@link #appendCancelButton()} or
 * {@link #appendCloseButton()} to append standard elements, as required.
 * <p>
 * Use {@link #open()} to display this {@link DomPopup}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomPopup extends DomDiv {

	protected DomPopupConfiguration configuration;
	private DomActionBar actionBarFooter;

	/**
	 * Constructs a new {@link DomPopup}.
	 * <p>
	 * Initially, this {@link DomPopup} is not displayed. Use {@link #open()} to
	 * display it.
	 */
	public DomPopup() {

		this.configuration = new DomPopupConfiguration();
		this.actionBarFooter = null;
		setCssClass(DomElementsCssClasses.DOM_POPUP);
	}

	// -------------------------------- configuration -------------------------------- //

	/**
	 * Returns the read-only {@link IDomPopupConfiguration} of this
	 * {@link DomPopup}.
	 *
	 * @return the {@link IDomPopupConfiguration} (never <i>null</i>)
	 * @see #open()
	 */
	public IDomPopupConfiguration getConfiguration() {

		return configuration;
	}

	/**
	 * Modifies the {@link DomPopupConfiguration} of this {@link DomPopup}.
	 * <p>
	 * Must be called before this {@link DomPopup} is shown.
	 *
	 * @param configurator
	 *            a {@link Consumer} to customize the
	 *            {@link DomPopupConfiguration} of this {@link DomPopup} (never
	 *            <i>null</i>)
	 * @return this {@link DomPopup}
	 */
	public DomPopup configure(Consumer<DomPopupConfiguration> configurator) {

		Objects.requireNonNull(configurator);
		configurator.accept(configuration);
		return this;
	}

	// -------------------------------- caption and sub-caption -------------------------------- //

	/**
	 * Defines the caption for this {@link DomPopup}.
	 * <p>
	 * Updates the frame if this {@link DomPopup} is already displayed.
	 *
	 * @param caption
	 *            the caption text (may be <i>null</i>)
	 */
	public void setCaption(IDisplayString caption) {

		configuration.setCaption(caption);
		CurrentDomPopupCompositor.get().refreshFrame(this);
	}

	/**
	 * Sets the sub caption for this {@link DomPopup}.
	 * <p>
	 * Updates the frame if this {@link DomPopup} is already displayed.
	 *
	 * @param subCaption
	 *            the sub caption text (may be <i>null</i>)
	 */
	public void setSubCaption(IDisplayString subCaption) {

		configuration.setSubCaption(subCaption);
		CurrentDomPopupCompositor.get().refreshFrame(this);
	}

	// -------------------------------- show and close -------------------------------- //

	/**
	 * @deprecated use {@link #open()} instead
	 */
	@Deprecated
	public void show() {

		open();
	}

	/**
	 * Opens this {@link DomPopup}.
	 */
	public void open() {

		CurrentDomPopupCompositor.get().open(this);
	}

	/**
	 * @deprecated use {@link #close()} instead
	 */
	@Deprecated
	public void hide() {

		close();
	}

	/**
	 * Closes this {@link DomPopup}.
	 * <p>
	 * The user will <b>not</b> be prompted for confirmation, even if
	 * {@link IDomPopupConfiguration#isConfirmBeforeClose()} is <i>true</i>.
	 */
	public void close() {

		CurrentDomPopupCompositor.get().close(this);
	}

	// ------------------------- content -------------------------- //

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
}
