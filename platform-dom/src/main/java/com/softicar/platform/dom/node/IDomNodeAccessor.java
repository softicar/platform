package com.softicar.platform.dom.node;

import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * This class provides access to some internal properties of {@link IDomNode}.
 * <p>
 * These methods are not intended for normal use, but only used by the
 * framework. That is why they are not part of the {@link IDomNode} interface.
 * <p>
 * Here, the word <i>internal</i> does not refer to implementation details but
 * to internal properties that are irrelevant for normal use cases.
 * 
 * @author Oliver Richers
 */
public interface IDomNodeAccessor {

	/**
	 * Sets the parent property of the node to the specified value.
	 * 
	 * @param parent
	 *            the new parent
	 */
	void setParent(IDomParentElement parent);

	/**
	 * Sets the specified node attribute internally.
	 * <p>
	 * This method will not call the {@link IDomEngine}, but only update the
	 * attribute map of the node.
	 */
	void setAttributeInMap(IDomAttribute attribute);

	/**
	 * Convenience method for {@link #setAttributeInMap(IDomAttribute)}.
	 */
	void setAttributeInMap(String name, boolean value);

	/**
	 * Convenience method for {@link #setAttributeInMap(IDomAttribute)}.
	 */
	void setAttributeInMap(String name, String value);

	/**
	 * Convenience method for {@link #setAttributeInMap(IDomAttribute)}.
	 */
	void setAttributeInMap(String name, String value, boolean quote);
}
