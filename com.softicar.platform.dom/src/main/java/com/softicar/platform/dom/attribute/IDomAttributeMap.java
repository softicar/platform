package com.softicar.platform.dom.attribute;

import com.softicar.platform.dom.document.IDomDocument;

/**
 * Interface of all node attribute maps.
 * 
 * @author Oliver Richers
 */
public interface IDomAttributeMap {

	/**
	 * Returns the associated {@link IDomDocument}.
	 */
	IDomDocument getDomDocument();

	/**
	 * Returns the associated attribute registry.
	 */
	IDomAttributeRegistry getAttributeRegistry();

	/**
	 * Returns the attribute with the specified name.
	 * <p>
	 * If no attribute of the given name has been defined, this returns null.
	 * 
	 * @return the corresponding attribute or null
	 */
	IDomAttribute getAttribute(String name);

	/**
	 * Returns all attributes, defined in this attribute map.
	 * 
	 * @return iterable of all defined attributes, never null
	 */
	Iterable<IDomAttribute> values();

	/**
	 * Returns the number of defined attributes in this attribute map.
	 */
	int size();
}
