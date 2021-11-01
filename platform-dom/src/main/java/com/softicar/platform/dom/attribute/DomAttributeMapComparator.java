package com.softicar.platform.dom.attribute;

public class DomAttributeMapComparator {

	private static DomAttributeMapComparator comparator = new DomAttributeMapComparator();

	public static DomAttributeMapComparator get() {

		return comparator;
	}

	public boolean isEqual(IDomAttributeMap map1, IDomAttributeMap map2) {

		if (map1.hashCode() != map2.hashCode() || map1.size() != map2.size()) {
			return false;
		}

		for (IDomAttribute attribute: map1.values()) {
			if (!attribute.equals(map2.getAttribute(attribute.getName()))) {
				return false;
			}
		}

		return true;
	}

	private DomAttributeMapComparator() {

		// private
	}
}
