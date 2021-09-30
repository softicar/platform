package com.softicar.platform.dom.attribute;

import com.softicar.platform.dom.document.IDomDocument;

/**
 * Interface of node attribute registry.
 * 
 * @author Oliver Richers
 */
public interface IDomAttributeRegistry {

	IDomDocument getDomDocument();

	IDomAttributeMap getEmptyMap();

	IDomAttributeMap put(IDomAttributeMap attributeMap, IDomAttribute attribute);

	IDomAttributeMap remove(IDomAttributeMap attributeMap, String attributeName);
}
