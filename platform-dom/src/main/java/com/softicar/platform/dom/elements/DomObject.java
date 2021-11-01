package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html object element.
 *
 * @author Oliver Richers
 */
public class DomObject extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.OBJECT;
	}

	/**
	 * The address of the resource as a valid URL.
	 * <p>
	 * At least one of data and type must be defined.
	 *
	 * @param data
	 *            the resource URL (may be null)
	 * @return this object
	 */
	public DomObject setData(String data) {

		setAttribute("data", data);
		return this;
	}

	public DomObject setForm(String formId) {

		setAttribute("form", formId);
		return this;
	}

	/**
	 * The height of the display resource in CSS pixels.
	 *
	 * @param height
	 *            the element height in pixels
	 * @return this object
	 */
	public DomObject setHeight(int height) {

		setAttribute("height", height);
		return this;
	}

	/**
	 * The name of valid browsing context.
	 *
	 * @param name
	 *            the browsing context (may be null)
	 * @return this object
	 */
	public DomObject setName(String name) {

		setAttribute("name", name);
		return this;
	}

	/**
	 * The content type of the resource specified by the data attribute.
	 * <p>
	 * At least one of data and type must be defined.
	 *
	 * @param type
	 *            the type (may be null)
	 * @return this object
	 */
	public DomObject setType(String type) {

		setAttribute("type", type);
		return this;
	}

	/**
	 * Indicates whether the type attribute and the actual content type of the
	 * resource must match, to be used.
	 *
	 * @param typeMustMatch
	 *            <i>true</i> to enforce matching; <i>false</i> otherwise
	 * @return this object
	 */
	public DomObject setTypeMustMatch(boolean typeMustMatch) {

		setAttribute("typemustmatch", typeMustMatch);
		return this;
	}

	public DomObject setUseMap(String useMap) {

		setAttribute("usemap", useMap);
		return this;
	}

	/**
	 * The width of the display resource in CSS pixels.
	 *
	 * @param width
	 *            the element width in pixels
	 * @return this object
	 */
	public DomObject setWidth(int width) {

		setAttribute("width", width);
		return this;
	}
}
