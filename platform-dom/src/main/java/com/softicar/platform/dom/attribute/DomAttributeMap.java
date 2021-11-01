package com.softicar.platform.dom.attribute;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.dom.document.IDomDocument;
import java.util.HashMap;

/**
 * A simple map of {@link DomAttribute} elements.
 *
 * @author Oliver Richers
 */
public class DomAttributeMap implements IDomAttributeMap {

	private int hash;
	private final DomAttributeRegistry attributeRegistry;
	private final HashMap<String, IDomAttribute> map = new HashMap<>();

	// -------------------------------- INTERFACE -------------------------------- //

	@Override
	public IDomDocument getDomDocument() {

		return attributeRegistry.getDomDocument();
	}

	@Override
	public IDomAttributeRegistry getAttributeRegistry() {

		return attributeRegistry;
	}

	@Override
	public IDomAttribute getAttribute(String name) {

		return map.get(name);
	}

	@Override
	public Iterable<IDomAttribute> values() {

		return map.values();
	}

	@Override
	public int size() {

		return map.size();
	}

	// -------------------------------- OBJECT INTERFACE -------------------------------- //

	@Override
	public String toString() {

		return "[" + Imploder.implode(values(), ", ") + "]";
	}

	@Override
	public int hashCode() {

		return hash;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof IDomAttributeMap) {
			return DomAttributeMapComparator.get().isEqual(this, (IDomAttributeMap) other);
		}

		return false;
	}

	// -------------------------------- INTERNAL -------------------------------- //

	DomAttributeMap(DomAttributeRegistry attributeRegistry) {

		this.attributeRegistry = attributeRegistry;
	}

	void add(IDomAttribute newAttribute) {

		IDomAttribute oldAttribute = map.put(newAttribute.getName(), newAttribute);
		if (oldAttribute != null) {
			hash -= oldAttribute.hashCode();
		}
		hash += newAttribute.hashCode();
	}

	void remove(String name) {

		IDomAttribute oldAttribute = map.remove(name);
		if (oldAttribute != null) {
			hash -= oldAttribute.hashCode();
		}
	}
}
