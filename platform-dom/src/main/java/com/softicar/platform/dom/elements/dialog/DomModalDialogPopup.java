package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.text.IDomTextNode;
import java.util.Arrays;
import java.util.Objects;

/**
 * A basic, custom modal dialog.
 * <p>
 * Enforces user interaction as follows:
 * <ul>
 * <li>Adds a <i>backdrop</i> element to the document, as an overlay above all
 * other displayed elements. The dialog is the only element that is displayed
 * above the backdrop. It is displayed as long as the modal dialog is displayed.
 * The backdrop intercepts mouse clicks.</li>
 * <li>Traps the <i>TAB</i> key focus. That is, attempts to focus elements
 * outside of the modal dialog via <i>TAB</i> or <i>Shift-TAB</i> are
 * intercepted, such that the focus cycles inside the modal dialog.</li>
 * </ul>
 *
 * @author Alexander Schmidt
 */
public class DomModalDialogPopup extends DomPopup {

	private final IDomParentElement contentContainer;

	/**
	 * Constructs a new {@link DomModalAlertPopup}
	 */
	public DomModalDialogPopup() {

		addCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_POPUP);
		addMarker(DomModalDialogMarker.POPUP);

		this.contentContainer = appendChild(new Content());

		this.configuration.setPositionStrategyByViewportCenter(CssPercent._50, CssPercent._25);
		this.configuration.setDisplayModeDialog();
		this.configuration.addFrameMarker(DomModalDialogMarker.FRAME);
	}

	@Override
	public void open() {

		super.open();
		IDomFocusable.focusFirst(DomButton.class, this);
	}

	/**
	 * Appends the given {@link IDomNode} to the content container.
	 *
	 * @param node
	 *            the {@link IDomNode} to append (never <i>null</i>)
	 * @return the given {@link IDomNode} (never <i>null</i>)
	 */
	public <T extends IDomNode> T appendContent(T node) {

		Objects.requireNonNull(node);
		return contentContainer.appendChild(node);
	}

	/**
	 * Appends the given {@link IDisplayString} to the content container.
	 *
	 * @param text
	 *            the {@link IDisplayString} to append (never <i>null</i>)
	 * @return the appended {@link IDomTextNode} (never <i>null</i>)
	 */
	public IDomTextNode appendContent(IDisplayString text) {

		Objects.requireNonNull(text);
		return contentContainer.appendText(text);
	}

	/**
	 * Convenience for
	 * {@link #appendActionButton(IResource, IDisplayString, INullaryVoidFunction, IStaticObject...)}
	 * without variable arguments.
	 */
	public DomModalDialogPopup appendActionButton(IResource icon, IDisplayString label, INullaryVoidFunction callback) {

		return appendActionButton(icon, label, callback, new IStaticObject[0]);
	}

	/**
	 * Appends a button to the action-node container.
	 *
	 * @param icon
	 *            the icon of the button (never <i>null</i>)
	 * @param label
	 *            the label of the button (never <i>null</i>)
	 * @param callback
	 *            the callback of the button (never <i>null</i>)
	 * @param markers
	 *            the markers of the button (never <i>null</i>)
	 * @return this {@link DomModalDialogPopup}
	 */
	public DomModalDialogPopup appendActionButton(IResource icon, IDisplayString label, INullaryVoidFunction callback, IStaticObject...markers) {

		IDomNode button = new DomButton()//
			.setIcon(icon)
			.setLabel(label)
			.setClickCallback(() -> {
				callback.apply();
				close();
			})
			.addMarkers(Arrays.asList(markers));
		appendActionNode(button);
		return this;
	}

	/**
	 * Defines the callback to be executed directly before the {@link DomPopup}
	 * is opened.
	 *
	 * @param callbackBeforeOpen
	 *            the callback (never <i>null</i>)
	 * @return this {@link DomModalDialogPopup}
	 */
	public DomModalDialogPopup setCallbackBeforeOpen(INullaryVoidFunction callbackBeforeOpen) {

		configuration.setCallbackBeforeOpen(callbackBeforeOpen);
		return this;
	}

	private class Content extends DomDiv {

		public Content() {

			addCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_POPUP_CONTENT);
			addMarker(DomModalDialogMarker.CONTENT);
		}
	}
}
