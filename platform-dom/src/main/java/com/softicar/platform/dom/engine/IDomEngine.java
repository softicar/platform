package com.softicar.platform.dom.engine;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.DomHead;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomLink;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.IDomPopupFrame;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomKeys;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.timeout.IDomTimeoutNode;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

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

	void raise(IDomNode node);

	// -------------------------------- node events -------------------------------- //

	void listenToEvent(IDomNode node, DomEventType type);

	void unlistenToEvent(IDomNode node, DomEventType type);

	/**
	 * Defines for which keys the events {@link DomEventType#KEYDOWN} and/or
	 * {@link DomEventType#KEYUP} are triggered.
	 * <p>
	 * Valid values are those defined by the HTML <i>KeyboardEvent.key</i>
	 * field. The key names are <b>case-sensitive</b>, so <b>ArrowDown</b> is a
	 * valid name while <b>arrowDown</b> is not, and the latter will not trigger
	 * any event. Also, if you want to listen for example to the <b>x</b> key
	 * while <i>shift</i> is down or up, you have to listen to lower-case
	 * <b>x</b> as well as upper-case <b>X</b>.
	 * <p>
	 * Common key names are listed in {@link DomKeys}.
	 *
	 * @param node
	 *            the listening {@link IDomNode} (never <i>null</i>)
	 * @param keys
	 *            the names of keys to listen to (never <i>null</i>)
	 */
	void setListenToKeys(IDomNode node, Collection<String> keys);

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

	/**
	 * Tells the client browser to not perform the default action if an event of
	 * the given {@link DomEventType} occurs on the given {@link IDomNode}.
	 *
	 * @param node
	 *            the {@link IDomNode} on which the event might occur (never
	 *            <i>null</i>)
	 * @param type
	 *            the {@link DomEventType} of the event that might occur (never
	 *            <i>null</i>)
	 * @param enabled
	 *            <i>true</i> if the default action shall be omitted;
	 *            <i>false</i> otherwise
	 */
	void setPreventDefaultBehavior(IDomNode node, DomEventType type, boolean enabled);

	/**
	 * Tells the client browser to not perform the default action if a
	 * mouse-down event occurs on the given {@link IDomNode}.
	 *
	 * @param node
	 *            the {@link IDomNode} on which the event might occur (never
	 *            <i>null</i>)
	 * @param enabled
	 *            <i>true</i> if the default action shall be omitted;
	 *            <i>false</i> otherwise
	 */
	void setPreventDefaultOnMouseDown(IDomNode node, boolean enabled);

	/**
	 * Tells the client browser to not perform the default action if a mouse
	 * wheel event occurs on the given {@link IDomNode} while the given
	 * modifiers are pressed.
	 * <p>
	 * For instance, if this method is called with modifiers
	 * {@link DomModifier#CONTROL} and {@link DomModifier#ALT}, the default
	 * action is only omitted for mouse wheel events that occur while that
	 * specific combination of modifiers is pressed.
	 * <p>
	 * If an empty set of modifiers is given, the default action is only omitted
	 * while <b>no</b> modifiers are pressed.
	 *
	 * @param node
	 *            the {@link IDomNode} on which the event might occur (never
	 *            <i>null</i>)
	 * @param modifiers
	 *            the modifiers that are pressed while the event might occur
	 *            (never <i>null</i>)
	 * @param enabled
	 *            <i>true</i> if the default action shall be omitted;
	 *            <i>false</i> otherwise
	 */
	void setPreventDefaultOnWheel(IDomNode node, Set<DomModifier> modifiers, boolean enabled);

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

	/**
	 * Makes an {@link IDomNode} movable via drag-and-drop.
	 * <p>
	 * The given dragged {@link IDomNode} needs to have CSS style
	 * {@code "position: absolute"}.
	 * <p>
	 * The given drag handle {@link IDomNode} may or may not be the same as the
	 * given dragged {@link IDomNode}.
	 * <p>
	 * If a limiting {@link IDomNode} is given, the top and left edges of its
	 * bounding box will serve as boundaries for dragging. If no limiting
	 * {@link IDomNode} is given, the top and left edges of the viewport will
	 * serve as boundaries.
	 *
	 * @param draggedNode
	 *            the {@link IDomNode} that shall be moved by dragging (never
	 *            <i>null</i>)
	 * @param dragHandleNode
	 *            the {@link IDomNode} that the user needs to drag (never
	 *            <i>null</i>)
	 * @param limitingNode
	 *            an {@link IDomNode} that defines top and left boundaries below
	 *            which the dragged {@link IDomNode} cannot be moved (may be
	 *            <i>null</i>)
	 */
	void makeDraggable(IDomNode draggedNode, IDomNode dragHandleNode, IDomNode limitingNode);

	// -------------------------------- alert, confirm, prompt, export -------------------------------- //

	IDomExport createExport();

	// -------------------------------- input nodes -------------------------------- //

	void blur(IDomNode inputNode);

	void focus(IDomNode inputNode);

	void selectText(IDomNode inputNode);

	void insertTextAtCaret(IDomTextualInput input, String text);

	void moveCaretToPosition(IDomTextualInput input, int position);

	// -------------------------------- pop-ups -------------------------------- //

	/**
	 * Moves the {@link DomPopup} with the given {@link IDomPopupFrame},
	 * according to the given parameters.
	 * <p>
	 * The corresponding {@link IDomPopupFrame} must be appended when this
	 * method is called. Otherwise, the {@link DomPopup} will not be moved.
	 *
	 * @param popupFrame
	 *            the {@link IDomPopupFrame} to move (never <i>null</i>)
	 * @param x
	 *            the horizontal position of the {@link IDomPopupFrame}
	 * @param y
	 *            the vertical position of the {@link IDomPopupFrame}
	 * @param xAlign
	 *            the horizontal alignment of the {@link IDomPopupFrame} (never
	 *            <i>null</i>)
	 * @param yAlign
	 *            the vertical alignment of the {@link IDomPopupFrame} (never
	 *            <i>null</i>)
	 */
	void movePopup(IDomPopupFrame popupFrame, int x, int y, DomPopupXAlign xAlign, DomPopupYAlign yAlign);

	void initializePopup(IDomPopupFrame popupFrame, boolean autoRaise);

	// -------------------------------- scripting -------------------------------- //

	void loadScriptLibrary(IDomScriptLibrary scriptLibrary);

	void executeScriptCode(String scriptCode);

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

	void uploadFiles(DomForm form);

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
