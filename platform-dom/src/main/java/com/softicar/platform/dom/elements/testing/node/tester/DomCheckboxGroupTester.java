package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.dom.elements.checkbox.DomCheckboxGroup;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import org.junit.Assert;

public class DomCheckboxGroupTester<V> extends AbstractDomNodeTester<DomCheckboxGroup<V>> {

	private final DomCheckboxGroup<V> checkboxGroup;

	public DomCheckboxGroupTester(IDomTestExecutionEngine engine, DomCheckboxGroup<V> checkboxGroup) {

		super(engine, checkboxGroup);
		this.checkboxGroup = checkboxGroup;
	}

	public DomCheckboxGroupTester<V> selectValue(V value) {

		checkboxGroup.setValue(value);
		assertValue(value);
		return this;
	}

	public DomCheckboxGroupTester<V> assertValue(V value) {

		Assert.assertEquals(value, checkboxGroup.getValueOrThrow());
		return this;
	}
}
