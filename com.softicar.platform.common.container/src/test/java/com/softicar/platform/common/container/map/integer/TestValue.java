package com.softicar.platform.common.container.map.integer;

class TestValue {

	public TestValue(int id) {

		_id = id;
	}

	@Override
	public String toString() {

		return "Value(" + _id + ")";
	}

	public int getID() {

		return _id;
	}

	int _id;
}
