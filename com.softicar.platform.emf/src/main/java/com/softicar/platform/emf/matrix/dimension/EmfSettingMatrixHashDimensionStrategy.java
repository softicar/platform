package com.softicar.platform.emf.matrix.dimension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmfSettingMatrixHashDimensionStrategy implements IEmfSettingMatrixDimensionStrategy {

	@Override
	public <D> Set<D> createSet() {

		return new HashSet<>();
	}

	@Override
	public <D, X> Map<D, X> createMap() {

		return new HashMap<>();
	}
}
