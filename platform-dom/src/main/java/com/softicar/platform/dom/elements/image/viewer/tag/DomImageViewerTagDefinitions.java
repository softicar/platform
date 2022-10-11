package com.softicar.platform.dom.elements.image.viewer.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DomImageViewerTagDefinitions {

	private final Map<Integer, List<DomImageViewerTagDefinition>> map;

	public DomImageViewerTagDefinitions() {

		this.map = new TreeMap<>();
	}

	public void add(int pageIndex, DomImageViewerTagDefinition definition) {

		map//
			.computeIfAbsent(pageIndex, dummy -> new ArrayList<>())
			.add(definition);
	}

	public Collection<DomImageViewerTagDefinition> get(int pageIndex) {

		return map.getOrDefault(pageIndex, new ArrayList<>());
	}
}
