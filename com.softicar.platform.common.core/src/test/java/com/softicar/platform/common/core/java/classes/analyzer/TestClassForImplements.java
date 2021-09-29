package com.softicar.platform.common.core.java.classes.analyzer;

import java.math.BigDecimal;
import java.util.List;

class TestClassForImplements implements Comparable<List<BigDecimal>>, AutoCloseable {

	@Override
	public int compareTo(List<BigDecimal> other) {

		return 0;
	}

	@Override
	public void close() {

		// nothing to do
	}
}
