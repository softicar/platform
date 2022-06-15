package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import java.util.NoSuchElementException;
import org.junit.Test;

public class DomPopupContextStasherTest extends AbstractTest {

	private final DomPopupContextStasher stasher;
	private final DomDocument document;

	public DomPopupContextStasherTest() {

		this.stasher = new DomPopupContextStasher();
		this.document = new DomDocument();
		CurrentDomDocument.set(document);
	}

	@Test
	public void testStash() {

		// setup
		var firstContext = appendChild(createContext(2));
		var secondContext = appendChild(createContext(0));
		var thirdContext = appendChild(createContext(5));

		// assert initial state
		assertEquals(2, firstContext.getChildCount());
		assertEquals(0, secondContext.getChildCount());
		assertEquals(5, thirdContext.getChildCount());

		// execute
		stasher.stash(firstContext);
		stasher.stash(secondContext);
		stasher.stash(thirdContext);

		// assert result
		assertEquals(0, firstContext.getChildCount());
		assertEquals(0, secondContext.getChildCount());
		assertEquals(0, thirdContext.getChildCount());
	}

	@Test(expected = NullPointerException.class)
	public void testStashWithNull() {

		stasher.stash(null);
	}

	@Test
	public void testUnstash() {

		// setup
		var firstContext = appendChild(createContext(2));
		var secondContext = appendChild(createContext(0));
		var thirdContext = appendChild(createContext(5));
		stasher.stash(firstContext);
		stasher.stash(secondContext);
		stasher.stash(thirdContext);

		// execute
		stasher.unstash(thirdContext);
		stasher.unstash(secondContext);
		stasher.unstash(firstContext);

		// assert result
		assertEquals(2, firstContext.getChildCount());
		assertEquals(0, secondContext.getChildCount());
		assertEquals(5, thirdContext.getChildCount());
	}

	@Test(expected = NullPointerException.class)
	public void testUnstashWithNull() {

		stasher.unstash(null);
	}

	@Test
	public void testClear() {

		// setup
		var firstContext = appendChild(createContext(2));
		var secondContext = appendChild(createContext(0));
		stasher.stash(firstContext);
		stasher.stash(secondContext);

		// execute
		stasher.clear();

		// assert result
		assertException(NoSuchElementException.class, () -> stasher.unstash(secondContext));
	}

	@Test
	public void testClearWithEmptyStash() {

		stasher.clear();
		// expect no Exception
	}

	private IDomPopupContext createContext(int children) {

		var context = new TestContext();
		for (int i = 1; i <= children; i++) {
			context.appendChild(createDiv(i + ""));
		}
		return context;
	}

	private <T extends IDomNode> T appendChild(T node) {

		return document.getBody().appendChild(node);
	}

	private DomDiv createDiv(String text) {

		var div = new DomDiv();
		div.appendText(text);
		return div;
	}

	private static class TestContext extends DomDiv implements IDomPopupContext {

		// nothing
	}
}
