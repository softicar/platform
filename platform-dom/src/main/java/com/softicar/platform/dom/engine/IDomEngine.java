package com.softicar.platform.dom.engine;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomHead;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomLink;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.IDomPopupFrame;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.timeout.IDomTimeoutNode;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputIndicatorMode;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Collection;
import java.util.Optional;

/**
 * This engine maps the manipulation of the {@link IDomDocument} to the actual
 * output.
 *
 * @author Oliver Richers
 */
public interface IDomEngine {

	// -------------------------------- node creation -------------------------------- //

	void createElement(int nodeId, DomElementTag elementTag);

	void createTextNode(int nodeId, String text);

	// -------------------------------- node tree -------------------------------- //

	void appendChild(IDomNode parent, IDomNode child);

	void insertBefore(IDomNode parent, IDomNode child, IDomNode otherChild);

	void removeChild(IDomNode parent, IDomNode child);

	void replaceChild(IDomNode parent, IDomNode newChild, IDomNode oldChild);

	// -------------------------------- node attributes -------------------------------- //

	void setNodeAttribute(IDomNode node, IDomAttribute attribute);

	void unsetNodeAttribute(IDomNode node, String name);

	void setNodeStyle(IDomNode node, String name, String value);

	void unsetNodeStyle(IDomNode node, String name);

	Optional<String> getNodeStyle(IDomNode node, String name);

	void setMaximumZIndex(IDomNode node);

	// -------------------------------- node events -------------------------------- //

	void listenToEvent(IDomNode node, DomEventType type);

	void unlistenToEvent(IDomNode node, DomEventType type);

	/**
	 * Defines that the given {@link IDomNode} does not fire while a key is
	 * pressed down, but only when the key is released (on key-up).
	 *
	 * @param node
	 *            the {@link IDomNode} to configure (never <i>null</i>)
	 * @param type
	 *            the {@link DomEventType}, specifying the respective key (never
	 *            <i>null</i>)
	 * @param enabled
	 *            <i>true</i> to only fire on key-up; <i>false</i> otherwise
	 *            (never <i>null</i>)
	 */
	void setFireOnKeyUp(IDomNode node, DomEventType type, boolean enabled);

	void setPreventDefaultBehavior(IDomNode node, DomEventType type, boolean enabled);

	/**
	 * Adds an event handler that stops propagation for the given
	 * {@link IDomNode}, and the event with the given name (e.g.
	 * {@code "onmousedown"}).
	 *
	 * @param node
	 *            the {@link IDomNode} to configure (never <i>null</i>)
	 * @param eventName
	 *            the event name (never <i>null</i>)
	 */
	void stopPropagation(IDomNode node, String eventName);

	/**
	 * Defines the CSS classes to assign to a given {@link IDomNode} while a key
	 * is pressed down on it.
	 *
	 * @param node
	 *            the {@link IDomNode} listening for the key-down events (never
	 *            <i>null</i>)
	 * @param eventType
	 *            the {@link DomEventType}, specifying the respective key (never
	 *            <i>null</i>)
	 * @param cssClasses
	 *            the CSS classes to assign (never <i>null</i>)
	 */
	default void setCssClassOnKeyDown(IDomNode node, DomEventType eventType, Collection<ICssClass> cssClasses) {

		setCssClassOnKeyDown(node, eventType, node, cssClasses);
	}

	/**
	 * Defines the CSS classes to assign to a given {@link IDomNode} while a key
	 * is pressed down on another {@link IDomNode}.
	 *
	 * @param eventTarget
	 *            the {@link IDomNode} listening for the key-down events (never
	 *            <i>null</i>)
	 * @param eventType
	 *            the {@link DomEventType}, specifying the respective key (never
	 *            <i>null</i>)
	 * @param cssTargetNode
	 *            the target {@link IDomNode} for the CSS class assignment
	 *            (never <i>null</i>)
	 * @param cssClasses
	 *            the CSS classes to assign (never <i>null</i>)
	 */
	void setCssClassOnKeyDown(IDomNode eventTarget, DomEventType eventType, IDomNode cssTargetNode, Collection<ICssClass> cssClasses);

	void setClickTargetForEventDelegation(IDomNode sourceNode, DomEventType eventType, IDomNode targetNode);

	void unsetClickTargetForEventDelegation(IDomNode sourceNode, DomEventType eventType);

	void scheduleTimeout(IDomTimeoutNode timeoutNode, Double seconds);

	void makeDraggable(IDomNode draggedNode, IDomNode initNode);

	void enableAutoComplete(IDomAutoCompleteInput<?> input);

	void setAutoCompleteInputInvalid(IDomAutoCompleteInput<?> input);

	void setAutoCompleteInputMandatory(IDomAutoCompleteInput<?> input, boolean mandatory);

	void setAutoCompleteValidationMode(IDomAutoCompleteInput<?> input, DomAutoCompleteInputValidationMode validationMode);

	void setAutoCompleteIndicatorMode(IDomAutoCompleteInput<?> input, DomAutoCompleteInputIndicatorMode indicatorMode);

	void setAutoCompleteEnabled(IDomAutoCompleteInput<?> input, boolean enabled);

	// -------------------------------- alert, confirm, prompt, export -------------------------------- //

	IDomExport createExport();

	// -------------------------------- input nodes -------------------------------- //

	void blur(IDomNode inputNode);

	void focus(IDomNode inputNode);

	void select(IDomNode inputNode);

	void insertTextAtCaret(IDomTextualInput input, String text);

	void moveCaretToPosition(IDomTextualInput input, int position);

	// -------------------------------- pop-ups -------------------------------- //

	/**
	 * Moves the {@link DomPopup} with the given {@link IDomPopupFrame},
	 * according to the given parameters.
	 * <p>
	 * The {@link IDomPopupFrame} must be appended to the {@link DomBody} or one
	 * of its children before this method is called. Otherwise, the position
	 * might be unexpected.
	 *
	 * @param popupFrame
	 *            the {@link IDomPopupFrame} to move (never <i>null</i>)
	 * @param x
	 *            the horizontal position of the {@link IDomPopupFrame}
	 * @param y
	 *            the vertical position of the {@link IDomPopupFrame}
	 * @param xAlign
	 *            the horizontal alignment of the {@link IDomPopupFrame}
	 * @param yAlign
	 *            the vertical alignment of the {@link IDomPopupFrame}
	 */
	void movePopup(IDomPopupFrame popupFrame, int x, int y, DomPopupXAlign xAlign, DomPopupYAlign yAlign);

	void initializePopup(IDomPopupFrame popupFrame, boolean autoRaise);

	// -------------------------------- scripting -------------------------------- //

	void loadScriptLibrary(IDomScriptLibrary scriptLibrary);

	void executeScriptCode(String scriptCode);

	// -------------------------------- forms -------------------------------- //

	void triggerUploadOnChange(IDomNode form, IDomNode triggerNode);

	void triggerUploadOnClick(IDomNode form, IDomNode triggerNode);

	// -------------------------------- focus trap -------------------------------- //

	/**
	 * Makes the given {@link IDomNode} act as a <i>TAB</i>-focus trap.
	 * <p>
	 * That is, restricts the elements which can be focused by pressing
	 * <i>TAB</i> or <i>Shift-TAB</i> to the children of the given
	 * {@link IDomNode}.
	 * <p>
	 * The <i>TAB</i>-focus trap remains active as long as the given
	 * {@link IDomNode} is appended to the {@link IDomDocument}, and displayed.
	 *
	 * @param node
	 *            the {@link IDomNode} to act as a <i>TAB</i>-focus trap (never
	 *            <i>null</i>)
	 */
	void trapTabFocus(IDomNode node);

	// -------------------------------- special -------------------------------- //

	void setWorkingIndicatorEnabled(boolean enabled);

	void approveNodeValues();

	<O extends DomOption> void setSelectedOptions(DomSelect<O> select, Collection<O> selectedOptions);

	/**
	 * Generates an {@link IResourceUrl} to access the specified
	 * {@link IResource}.
	 *
	 * @param resource
	 *            the {@link IResource} to generate an {@link IResourceUrl} for
	 *            (never <i>null</i>)
	 * @return an {@link IResourceUrl} to access the specified {@link IResource}
	 *         (never <i>null</i>)
	 */
	IResourceUrl getResourceUrl(IResource resource);

	void setDocumentTitle(String pageTitle);

	void pushBrowserHistoryState(String pageName, String pageUrl);

	void reloadPage();

	void setReloadPageOnClick(IDomNode node);

	/**
	 * Adds the given {@link IResource} as a {@link DomLink} element to the
	 * {@link DomHead} of the {@link IDomDocument}.
	 * <p>
	 * If the given {@link IResource} was already added, this method does
	 * nothing.
	 *
	 * @param resource
	 *            the {@link IResource} to add (never <i>null</i>)
	 * @param relationship
	 *            the {@link Relationship} attribute (never <i>null</i>)
	 * @param mimeType
	 *            the {@link MimeType} attribute (never <i>null</i>)
	 */
	void registerResourceLink(IResource resource, Relationship relationship, MimeType mimeType);

	/**
	 * Calls {@link #registerResourceLink} with {@link Relationship#STYLESHEET}
	 * and {@link MimeType#TEXT_CSS}.
	 *
	 * @param resource
	 *            the {@link IResource} to add (never <i>null</i>)
	 */
	default void registerCssResourceLink(IResource resource) {

		registerResourceLink(resource, Relationship.STYLESHEET, MimeType.TEXT_CSS);
	}
}
