package com.softicar.platform.common.container.map.weak.identity;

import java.util.Objects;

class TestKey {

	private final int id;

	public TestKey(int id) {

		this.id = id;
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof TestKey) {
			return id == ((TestKey) other).id;
		} else {
			return false;
		}
	}
}
