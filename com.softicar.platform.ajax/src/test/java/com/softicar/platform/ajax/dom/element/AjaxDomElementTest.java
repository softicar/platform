package com.softicar.platform.ajax.dom.element;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestConstants;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFieldset;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.styles.CssFontWeight;
import com.softicar.platform.dom.styles.CssTextAlign;
import org.junit.Test;

public class AjaxDomElementTest extends AbstractAjaxSeleniumLowLevelTest {

	@Test
	public void testAppendChild() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.appendChild(new DomFieldset());
			div.appendChild(new DomDiv());
			return div;
		});

		assertChildTags(testDiv, "fieldset", "div");
	}

	@Test
	public void testAppendNewChild() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.appendNewChild(DomElementTag.HR);
			div.appendNewChild(DomElementTag.BR);
			return div;
		});

		assertChildTags(testDiv, "hr", "br");
	}

	@Test
	public void testAppendText() {

		String text = TestConstants.SPECIAL_TEXT_WITHOUT_UNICODE_WHITESPACE;

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.appendText("Hello");
			div.appendText("World");
			div.appendText(text);
			return div;
		});

		assertEquals("HelloWorld" + text, getText(testDiv));
	}

	@Test
	public void testInsertAt() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.insertAt(new DomFieldset(), 0);  // insert into empty list
			div.insertAt(new DomDiv(), 0);       // insert at beginning
			div.insertAt(new DomTextInput(), 1); // insert in between
			div.insertAt(new DomTextArea(), 3);  // insert at the end
			return div;
		});

		assertChildTags(testDiv, "div", "input", "fieldset", "textarea");
	}

	@Test
	public void testPrependChild() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.prependChild(new DomFieldset());
			div.prependChild(new DomDiv());
			return div;
		});

		assertChildTags(testDiv, "div", "fieldset");
	}

	@Test
	public void testRemoveChild() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.appendChild(new DomFieldset());
			div.appendChild(new DomDiv());
			div.appendChild(new DomTextInput());
			div.removeChild(div.getChild(1));
			return div;
		});

		assertChildTags(testDiv, "fieldset", "input");
	}

	@Test
	public void testReplaceChild() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.appendChild(new DomFieldset());
			div.appendChild(new DomDiv());
			div.appendChild(new DomTextInput());
			div.replaceChild(new DomTextArea(), div.getChild(1));
			return div;
		});

		assertChildTags(testDiv, "fieldset", "textarea", "input");
	}

	@Test
	public void testSetAttribute() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.setAttribute("class", "foo");
			div.setAttribute("style", "width: 5px;");
			div.setAttribute("tabindex", null);
			return div;
		});

		assertEquals("foo", getAttributeValue(testDiv, "class"));
		assertEquals("width: 5px;", getAttributeValue(testDiv, "style"));
		assertNull(getAttributeValue(testDiv, "tabindex"));
	}

	@Test
	public void testSetStyle() {

		DomDiv testDiv = openTestNode(() -> {
			DomDiv div = new DomDiv();
			div.setStyle(CssFontWeight.BOLD);
			div.setStyle(CssTextAlign.CENTER);
			return div;
		});

		assertEquals("font-weight: bold; text-align: center;", getAttributeValue(testDiv, "style"));
	}
}
