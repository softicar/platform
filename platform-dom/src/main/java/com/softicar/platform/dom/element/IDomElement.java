package com.softicar.platform.dom.element;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;
import com.softicar.platform.dom.style.ICssStyleValue;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.dom.styles.CssPosition;
import com.softicar.platform.dom.text.DomTextNode;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface of all DOM elements.
 * <p>
 * There are generally only two types of {@link IDomNode} objects:
 * {@link IDomElement} and {@link DomTextNode}. All {@link IDomElement} have a
 * {@link DomElementTag} defining their type.
 *
 * @author Oliver Richers
 */
public interface IDomElement extends IDomNode {

	/**
	 * Returns the HTML tag representing this type of element.
	 *
	 * @return the HTML element tag of this element
	 */
	DomElementTag getTag();

	// -------------------- events -------------------- //

	void listenToEvent(DomEventType type);

	void unlistenToEvent(DomEventType type);

	// -------------------- CSS class methods -------------------- //

	default void setCssClass(ICssClass...cssClasses) {

		setCssClasses(List.of(cssClasses));
	}

	void setCssClasses(Collection<ICssClass> cssClasses);

	default void addCssClass(ICssClass...cssClasses) {

		addCssClasses(List.of(cssClasses));
	}

	void addCssClasses(Collection<ICssClass> cssClasses);

	default void removeCssClass(ICssClass...cssClasses) {

		removeCssClasses(List.of(cssClasses));
	}

	void removeCssClasses(Collection<ICssClass> cssClasses);

	void unsetCssClass();

	// -------------------- CSS style methods -------------------- //

	IDomElement setStyle(ICssStyle style, String value);

	default IDomElement setStyle(ICssStyle style, ICssStyleValue value) {

		return setStyle(style, value.toString());
	}

	IDomElement setStyle(ICssStyleAttribute styleAttribute);

	IDomElement unsetStyle(ICssStyle style);

	// -------------------- convenience CSS style methods -------------------- //

	/**
	 * Sets the CSS <i>display</i> style to <i>none</i> on this
	 * {@link IDomElement}.
	 *
	 * @return this
	 */
	default IDomElement setDisplayNone() {

		return setStyle(CssDisplay.NONE);
	}

	/**
	 * Defines the color to be used to draw the background of this element.
	 * <p>
	 * If the specified color is <i>null</i> the background of this element will
	 * be transparent.
	 *
	 * @param color
	 *            the background color to use, or null to make the background
	 *            transparent
	 * @return this element
	 */
	default IDomElement setBackgroundColor(IColor color) {

		return setStyle(CssStyle.BACKGROUND_COLOR, color != null? color.toHtml() : null);
	}

	// ------------------------------ color ------------------------------ //

	default IDomElement setColor(IColor color) {

		return setStyle(CssStyle.COLOR, color != null? color.toHtml() : null);
	}

	// ------------------------------ tab-index ------------------------------ //

	/**
	 * Defines the value for the <i>tabindex</i> attribute.
	 *
	 * @param tabIndex
	 *            the tab-index (may be null to unset attribute)
	 * @return this element
	 */
	default IDomElement setTabIndex(Integer tabIndex) {

		setAttribute("tabindex", tabIndex);
		return this;
	}

	// -------------------- title -------------------- //

	/**
	 * Sets the title attribute (tool-tip), to display further information about
	 * this element.
	 *
	 * @param text
	 *            the text to display (may be null)
	 */
	IDomElement setTitle(String text);

	/**
	 * Sets the title attribute (tool-tip), to display further information about
	 * this element.
	 *
	 * @param displayString
	 *            the {@link IDisplayString} to translate and display
	 */
	default IDomElement setTitle(IDisplayString displayString) {

		return setTitle(Optional.ofNullable(displayString).map(IDisplayString::toString).orElse(null));
	}

	// -------------------- dragging -------------------- //

	/**
	 * Makes this node draggable.
	 *
	 * @param position
	 *            should be either {@link CssPosition#RELATIVE} or
	 *            {@link CssPosition#ABSOLUTE}
	 */
	void makeDraggable(CssPosition position);

	/**
	 * Makes this node draggable.
	 * <p>
	 * The difference to the {@link #makeDraggable(CssPosition)} method is that
	 * you can specify another note that starts the dragging of this node. This
	 * is important for pop-up menus, where the title bar and not the menu
	 * itself starts the dragging of the whole menu.
	 *
	 * @param position
	 *            should be either {@link CssPosition#RELATIVE} or
	 *            {@link CssPosition#ABSOLUTE}
	 * @param initNode
	 *            the node that the user can click to start the dragging process
	 */
	void makeDraggable(CssPosition position, IDomNode initNode);
}
