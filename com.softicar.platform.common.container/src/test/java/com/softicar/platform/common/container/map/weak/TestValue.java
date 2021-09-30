package com.softicar.platform.common.container.map.weak;

class TestValue {

	public TestValue(int id) {

		_id = id;
	}

	public int getID() {

		return _id;
	}

	@Override
	public String toString() {

		return "TestValue(" + _id + ")";
	}

	private final int _id;
}
