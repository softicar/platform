package com.softicar.platform.dom.engine;

import com.softicar.platform.dom.node.IDomNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class DomTestEngine extends DomNullEngine {

	private final Map<IDomNode, Map<String, String>> styleValues;
	private int maxZIndex;

	public DomTestEngine() {

		this.styleValues = new WeakHashMap<>();
		this.maxZIndex = 1;
	}

	@Override
	public void setNodeStyle(IDomNode node, String name, String value) {

		styleValues.computeIfAbsent(node, dummy -> new HashMap<>()).put(name, value);
	}

	@Override
	public void unsetNodeStyle(IDomNode node, String name) {

		Optional//
			.ofNullable(styleValues.get(node))
			.ifPresent(map -> map.remove(name));
	}

	@Override
	public Optional<String> getNodeStyle(IDomNode node, String name) {

		return Optional//
			.ofNullable(styleValues.get(node))
			.map(map -> map.get(name));
	}

	@Override
	public void setMaximumZIndex(IDomNode node) {

		setNodeStyle(node, "zIndex", "" + maxZIndex++);
	}
}
