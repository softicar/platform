package com.softicar.platform.common.container.map.weak;

import java.util.ArrayList;
import java.util.List;

class TestList {

	private final List<TestValue> values = new ArrayList<>();

	public TestList() {

		for (int i = 0; i <= 1000000; ++i) {
			values.add(new TestValue(i));
		}
	}

	public TestValue get(int i) {

		return values.get(i);
	}
}
