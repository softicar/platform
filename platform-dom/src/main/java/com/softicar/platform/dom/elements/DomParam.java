package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html param element.
 * <p>
 * Html param elements are used in {@link DomObject} elements.
 *
 * @author Oliver Richers
 */
public class DomParam extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.PARAM;
	}

	/**
	 * Sets the name of this parameter.
	 *
	 * @param name
	 *            the name (may be null)
	 * @return this object
	 */
	public DomParam setName(String name) {

		setAttribute("name", name);
		return this;
	}

	/**
	 * Specifies the MIME type of values found at the URI specified by value.
	 * <p>
	 * Only used if the value type is set to <i>ref</i>.
	 *
	 * @param type
	 *            the MIME type (may be null)
	 * @return this object
	 */
	public DomParam setType(String type) {

		setAttribute("type", type);
		return this;
	}

	/**
	 * Sets the value of this parameter.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return this object
	 */
	public DomParam setValue(String value) {

		setAttribute("value", value);
		return this;
	}

	/**
	 * Specifies the type of the value attribute.
	 * <p>
	 * Possible values are:
	 * <ul>
	 * <li><i>data</i>: Default value. The value is passed to the object's
	 * implementation as a string.</li>
	 * <li><i>ref</i>: The value is a URI to a resource where run-time values
	 * are stored.</li>
	 * <li><i>object</i>: An ID of another {@link DomObject} in the same
	 * document.</li>
	 * </ul>
	 *
	 * @param valueType
	 *            the type of the value (may be null)
	 * @return this object
	 */
	public DomParam setValueType(String valueType) {

		setAttribute("valuetype", valueType);
		return this;
	}
}
