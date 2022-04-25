package com.softicar.platform.dom.elements.testing.node.iterable;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFieldset;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.text.IDomTextNode;
import java.util.stream.Collectors;
import org.junit.Test;
import org.mockito.Mockito;

public class DomNodeIterableTest extends AbstractTest {

	private final DomDiv root;

	public DomNodeIterableTest() {

		CurrentDomDocument.set(new DomDocument());
		this.root = createSampleDiv();
	}

	@Test
	public void test() {

		// extract all text
		String text = new DomNodeTester(createDummyEngine(), root)//
			.findNodes()
			.stream()
			.filter(IDomTextNode.class::isInstance)
			.map(IDomTextNode.class::cast)
			.map(IDomTextNode::getText)
			.collect(Collectors.joining("|"));
		assertEquals("Some Button|Some Fieldset", text);
	}

	private IDomTestExecutionEngine createDummyEngine() {

		return Mockito.mock(IDomTestExecutionEngine.class);
	}

	private DomDiv createSampleDiv() {

		DomDiv root = new DomDiv();
		DomDiv div = root.appendChild(new DomDiv());
		div
			.appendChild(
				new DomButton()//
					.setLabel(IDisplayString.create("Some Button"))
					.setTitle(IDisplayString.create("Foo"))
					.setClickCallback(INullaryVoidFunction.NO_OPERATION));
		DomFieldset fieldset = root.appendChild(new DomFieldset());
		fieldset.appendText("Some Fieldset");
		return root;
	}
}
