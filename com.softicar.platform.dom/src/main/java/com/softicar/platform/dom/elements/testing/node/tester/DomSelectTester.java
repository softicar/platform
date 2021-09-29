package com.softicar.platform.dom.elements.testing.node.tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import com.softicar.platform.dom.elements.DomValueOption;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.input.DomSelect;
import java.util.Objects;

public class DomSelectTester<T> extends AbstractDomNodeTester<DomSelect<?>> {

	private final DomSelect<?> select;

	public DomSelectTester(IDomTestEngine engine, DomSelect<?> select) {

		super(engine, select);
		this.select = select;
	}

	public DomSelectTester<T> selectValue(T value) {

		// set selected option
		DomValueOption<?> option = findNodes(DomValueOption.class)//
			.filter(node -> Objects.equals(node.getValue(), value))
			.first()
			.assertType(DomValueOption.class);
		node.setSelectedOptions_noJS("n" + option.getNodeId());

		// execute CHANGE event
		if (node instanceof IDomEventHandler) {
			sendEvent(DomEventType.CHANGE);
		}

		return this;
	}

	public DomSelectTester<T> assertSelected(String expected) {

		assertSelected(null, expected);
		return this;
	}

	public DomSelectTester<T> assertSelected(String message, String expected) {

		var selectedOption = select.getSelectedOption();
		if (expected != null) {
			assertNotNull(message, selectedOption);
			DomNodeTester optionTester = new DomNodeTester(getEngine(), selectedOption);
			String selectedOptionText = optionTester.getAllTextsInTree().findFirst().orElseThrow();
			assertEquals(message, expected, selectedOptionText);
		} else {
			assertNull(message, selectedOption);
		}
		return this;
	}
}
