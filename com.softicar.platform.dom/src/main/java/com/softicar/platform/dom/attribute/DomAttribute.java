package com.softicar.platform.dom.attribute;

import com.softicar.platform.common.core.utils.CompareUtils;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.utils.JavascriptEscaping;

/**
 * Represents the attribute of an {@link IDomNode}.
 * <p>
 * This class is needed because attributes can be quoted or not. For example,
 * the value attribute of a text input field must be quoted while the onclick
 * attribute of a node should not be quoted.
 *
 * @author Oliver Richers
 */
public class DomAttribute implements IDomAttribute {

	/**
	 * Constructs an attribute with the specified name and value.
	 * <p>
	 * The parameter mustBeQuoted specifies if the value must be quoted in the
	 * realm of JavaScript or not.
	 * <p>
	 * Examples: - Value attribute of a text input: DOMAttribute("value",
	 * "hello", true); - OnClick attribute of a node: DOMAttribute("onclick",
	 * "myHandler", false);
	 */
	public DomAttribute(String name, String value, boolean mustBeQuoted) {

		m_name = name.toLowerCase();
		m_value = value;
		m_mustBeQuoted = mustBeQuoted;

		m_hash = m_name.hashCode() + (m_value != null? m_value.hashCode() : 0) + Boolean.valueOf(m_mustBeQuoted).hashCode();
	}

	@Override
	public int compareTo(IDomAttribute other) {

		int cmp1 = getName().compareTo(other.getName());
		if (cmp1 != 0) {
			return cmp1;
		}

		int cmp2 = getValue().compareTo(other.getValue());
		if (cmp2 != 0) {
			return cmp2;
		}

		int cmp3 = (mustBeQuoted()? 1 : 0) - (other.mustBeQuoted()? 1 : 0);
		if (cmp3 != 0) {
			return cmp3;
		}

		return 0;
	}

	@Override
	public String toString() {

		return String.format("%s: %s", getName(), getValue_JS());
	}

	/**
	 * Returns the name of the attribute.
	 */
	@Override
	public String getName() {

		return m_name;
	}

	/**
	 * Returns the value of the attribute
	 * <p>
	 * NOTE: The returned value is always unquoted.
	 */
	@Override
	public String getValue() {

		return m_value;
	}

	/**
	 * Returns the value in the realm of JavaScript.
	 * <p>
	 * If the returned value is quoted depends on the mustBeQuoted parameter to
	 * the constructor of this attribute.
	 */
	@Override
	public String getValue_JS() {

		return m_mustBeQuoted? "'" + JavascriptEscaping.getEscaped(m_value) + "'" : m_value;
	}

	@Override
	public boolean mustBeQuoted() {

		return m_mustBeQuoted;
	}

	@Override
	public int hashCode() {

		return m_hash;
	}

	@Override
	public boolean equals(Object other) {

		if (this == other) {
			return true;
		} else if (other instanceof DomAttribute) {
			DomAttribute otherAttribute = (DomAttribute) other;
			return m_hash == otherAttribute.m_hash && CompareUtils.isEqual(m_name, otherAttribute.m_name)
					&& CompareUtils.isEqual(m_value, otherAttribute.m_value) && m_mustBeQuoted == otherAttribute.m_mustBeQuoted;
		}
		return false;
	}

	private final int m_hash;
	private final String m_name;
	private final String m_value;
	private final boolean m_mustBeQuoted;
}
