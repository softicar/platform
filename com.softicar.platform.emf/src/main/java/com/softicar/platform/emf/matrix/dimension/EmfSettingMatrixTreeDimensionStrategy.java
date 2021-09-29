package com.softicar.platform.emf.matrix.dimension;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EmfSettingMatrixTreeDimensionStrategy implements IEmfSettingMatrixDimensionStrategy {

	@Override
	public <D> Set<D> createSet() {

		return new TreeSet<>();
	}

	@Override
	public <D, X> Map<D, X> createMap() {

		return new TreeMap<>();
	}
}
