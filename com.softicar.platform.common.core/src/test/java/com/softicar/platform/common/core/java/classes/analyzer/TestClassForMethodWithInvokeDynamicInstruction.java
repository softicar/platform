package com.softicar.platform.common.core.java.classes.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class TestClassForMethodWithInvokeDynamicInstruction {

	@SuppressWarnings("unused")
	public void foo() {

		// implicit use of Supplier
		Optional.ofNullable(null).orElseGet(() -> 42);

		// dynamic use of ArrayList
		Function<Integer, List<?>> list = ArrayList::new;
	}
}
