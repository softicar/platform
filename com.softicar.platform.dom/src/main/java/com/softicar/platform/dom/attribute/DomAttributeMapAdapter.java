package com.softicar.platform.dom.attribute;

import com.softicar.platform.common.container.iterator.MergeIterator;
import com.softicar.platform.common.container.iterator.SkipIterator;
import com.softicar.platform.dom.document.IDomDocument;
import java.util.Collections;

/**
 * This class simulates an {@link IDomAttributeMap} with a single changed
 * attribute.
 * <p>
 * This class takes an existing {@link IDomAttributeMap} and virtually modifies
 * an attribute, i.e. adds, removes or changes an attribute.
 *
 * @author Oliver Richers
 */
public class DomAttributeMapAdapter implements IDomAttributeMap {

	private int hash;
	private int size;
	private final String name;
	private final IDomAttributeMap map;
	private final IDomAttribute newAttribute;
	private final IDomAttribute oldAttribute;

	// -------------------------------- INTERFACE -------------------------------- //

	@Override
	public IDomDocument getDomDocument() {

		return map.getDomDocument();
	}

	@Override
	public IDomAttributeRegistry getAttributeRegistry() {

		return map.getAttributeRegistry();
	}

	@Override
	public IDomAttribute getAttribute(String name) {

		return name.equals(this.name)? newAttribute : map.getAttribute(name);
	}

	@Override
	public Iterable<IDomAttribute> values() {

		Iterable<IDomAttribute> iterable = map.values();

		if (oldAttribute != null) {
			iterable = new SkipIterator<>(iterable, oldAttribute);
		}

		if (newAttribute != null) {
			iterable = new MergeIterator<>(iterable, Collections.singleton(newAttribute));
		}

		return iterable;
	}

	@Override
	public int size() {

		return size;
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

	static DomAttributeMapAdapter add(IDomAttributeMap map, IDomAttribute newAttribute) {

		return new DomAttributeMapAdapter(map, newAttribute.getName(), newAttribute);
	}

	static DomAttributeMapAdapter remove(IDomAttributeMap map, String attributeName) {

		return new DomAttributeMapAdapter(map, attributeName, null);
	}

	private DomAttributeMapAdapter(IDomAttributeMap map, String name, IDomAttribute newAttribute) {

		this.map = map;
		this.name = name;
		this.newAttribute = newAttribute;
		this.oldAttribute = this.map.getAttribute(name);

		this.hash = this.map.hashCode();
		this.size = this.map.size();

		if (this.oldAttribute != null) {
			this.hash -= this.oldAttribute.hashCode();
			this.size -= 1;
		}

		if (this.newAttribute != null) {
			this.hash += this.newAttribute.hashCode();
			this.size += 1;
		}
	}
}
