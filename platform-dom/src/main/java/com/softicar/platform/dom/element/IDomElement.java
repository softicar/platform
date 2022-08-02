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
import com.softicar.platform.dom.text.DomTextNode;
import java.util.Collection;
import java.util.List;

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

	default IDomElement setListenToKeys(Collection<String> keys) {

		getDomEngine().setListenToKeys(this, keys);
		return this;
	}

	// -------------------- CSS class -------------------- //

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

	// -------------------- CSS style -------------------- //

	IDomElement setStyle(ICssStyle style, String value);

	default IDomElement setStyle(ICssStyle style, ICssStyleValue value) {

		return setStyle(style, value.toString());
	}

	default IDomElement setStyle(ICssStyleAttribute styleAttribute) {

		return setStyle(styleAttribute.getStyle(), styleAttribute.getValue());
	}

	IDomElement unsetStyle(ICssStyle style);

	/**
	 * Toggles the {@link CssStyle#DISPLAY} between {@link CssDisplay#NONE} and
	 * undefined.
	 *
	 * @param displayNone
	 *            <i>true</i> will set the {@link CssStyle#DISPLAY} to
	 *            {@link CssDisplay#NONE}; <i>false</i> will call
	 *            {@link #unsetStyle} with {@link CssStyle#DISPLAY}
	 * @return this
	 */
	IDomElement setDisplayNone(boolean displayNone);

	// ------------------------------ color ------------------------------ //

	/**
	 * Defines the HTML background color of this {@link IDomElement}.
	 *
	 * @param color
	 *            the background color to use (may be <i>null</i>)
	 * @return this
	 */
	default IDomElement setBackgroundColor(IColor color) {

		return setStyle(CssStyle.BACKGROUND_COLOR, color != null? color.toHtml() : null);
	}

	/**
	 * Defines the HTML foreground color of this {@link IDomElement}.
	 *
	 * @param color
	 *            the foreground color to use (may be <i>null</i>)
	 * @return this
	 */
	default IDomElement setColor(IColor color) {

		return setStyle(CssStyle.COLOR, color != null? color.toHtml() : null);
	}

	// ------------------------------ tab-index ------------------------------ //

	/**
	 * Defines the value for the HTML <i>tabindex</i> attribute.
	 *
	 * @param tabIndex
	 *            the tab-index (may be null to clear the attribute)
	 * @return this
	 */
	IDomElement setTabIndex(Integer tabIndex);

	// -------------------- title -------------------- //

	/**
	 * Sets the HTML <i>title</i> attribute, to display on hover.
	 *
	 * @param title
	 *            the text to display (may be <i>null</i>)
	 * @return this
	 */
	IDomElement setTitle(String title);

	/**
	 * Sets the HTML <i>title</i> attribute, to display on hover.
	 *
	 * @param title
	 *            the {@link IDisplayString} to display (may be <i>null</i>)
	 * @return this
	 */
	default IDomElement setTitle(IDisplayString title) {

		return setTitle(title != null? title.toString() : null);
	}
}
