package com.softicar.platform.common.container.map.integer;

class TestMap extends IntKeySet<TestValue> {

	@Override
	protected int getKey(TestValue value) {

		return value.getID();
	}
}
