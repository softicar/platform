package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import org.junit.Assert;

public class DomCheckboxTester extends AbstractDomNodeTester<DomCheckbox> {

	private final DomCheckbox checkbox;

	public DomCheckboxTester(IDomTestExecutionEngine engine, DomCheckbox checkbox) {

		super(engine, checkbox);
		this.checkbox = checkbox;
	}

	public void assertChecked(boolean checked) {

		Assert.assertEquals(checked, checkbox.isChecked());
	}
}
