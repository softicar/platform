package com.softicar.platform.dom.attribute;

import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Central facility to manage the attributes of {@link IDomNode} instances.
 * <p>
 * This class ensures that instances of {@link IDomNode} with equals sets of
 * attributes share a common instance of {@link IDomAttributeMap}. This reduces
 * memory consumption drastically, especially if a lot of nodes with equivalent
 * attributes are created.
 * 
 * @author Oliver Richers
 */
public class DomAttributeRegistry implements IDomAttributeRegistry {

	private final WeakHashMap<IDomAttributeMap, WeakReference<DomAttributeMap>> maps = new WeakHashMap<>();
	private final IDomDocument document;
	private final DomAttributeMap emptyMap;

	public DomAttributeRegistry(IDomDocument document) {

		this.document = document;
		this.emptyMap = new DomAttributeMap(this);

		maps.put(emptyMap, new WeakReference<>(emptyMap));
	}

	@Override
	public IDomDocument getDomDocument() {

		return document;
	}

	@Override
	public IDomAttributeMap getEmptyMap() {

		return emptyMap;
	}

	@Override
	public IDomAttributeMap put(IDomAttributeMap attributeMap, IDomAttribute newAttribute) {

		DomAttributeMapAdapter adapter = DomAttributeMapAdapter.add(attributeMap, newAttribute);
		WeakReference<DomAttributeMap> reference = maps.get(adapter);
		DomAttributeMap newMap = reference != null? reference.get() : null;
		if (newMap == null) {
			newMap = new DomAttributeMap(this);
			for (IDomAttribute attribute: attributeMap.values()) {
				newMap.add(attribute);
			}
			newMap.add(newAttribute);
			maps.put(newMap, new WeakReference<>(newMap));
		}
		return newMap;
	}

	@Override
	public IDomAttributeMap remove(IDomAttributeMap attributeMap, String attributeName) {

		DomAttributeMapAdapter adapter = DomAttributeMapAdapter.remove(attributeMap, attributeName);
		WeakReference<DomAttributeMap> reference = maps.get(adapter);
		DomAttributeMap newMap = reference != null? reference.get() : null;
		if (newMap == null) {
			newMap = new DomAttributeMap(this);
			for (IDomAttribute attribute: attributeMap.values()) {
				newMap.add(attribute);
			}
			newMap.remove(attributeName);
			maps.put(newMap, new WeakReference<>(newMap));
		}
		return newMap;
	}
}
