package com.softicar.platform.dom.refresh.bus;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.function.Function;
import org.junit.Rule;
import org.junit.Test;

public class DomRefreshBusTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomDiv container;
	private final DomButton button;

	public DomRefreshBusTest() {

		this.container = new DomDiv();
		this.button = container.appendChild(new DomButton());

		setNodeSupplier(() -> container);

		// explicitly initialize the document
		// TODO this should not be necessary
		findBody();
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testRefreshCallsWithSubmitBeforeNodeAppended() {

		var testNode = new TestNode();

		setAllChangedAndSubmit();

		assertEquals(0, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitBeforeNodeAppendedDirectly() {

		var testNode = new TestNode();

		setAllChangedAndSubmit();
		appendViaButtonClick(testNode);

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitBeforeNodeAppendedIndirectly() {

		var wrapper = new DomDiv();
		var testNode = wrapper.appendChild(new TestNode());

		setAllChangedAndSubmit();
		appendViaButtonClick(wrapper);

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitAfterNodeAppendedDirectly() {

		var testNode = new TestNode();

		appendViaButtonClick(testNode);
		setAllChangedAndSubmit();

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitAfterNodeAppendedIndirectly() {

		var wrapper = new DomDiv();
		var testNode = wrapper.appendChild(new TestNode());

		appendViaButtonClick(wrapper);
		setAllChangedAndSubmit();

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitAfterNodeDisappendedDirectly() {

		var testNode = new TestNode();
		appendViaButtonClick(testNode);

		disappendViaButtonClick(testNode);
		setAllChangedAndSubmit();

		assertEquals(0, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitAfterNodeDisappendedIndirectly() {

		var wrapper = new DomDiv();
		var testNode = wrapper.appendChild(new TestNode());
		appendViaButtonClick(wrapper);

		disappendViaButtonClick(wrapper);
		setAllChangedAndSubmit();

		assertEquals(0, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitBeforeNodeReappendedDirectly() {

		var testNode = new TestNode();
		appendViaButtonClick(testNode);
		disappendViaButtonClick(testNode);

		setAllChangedAndSubmit();
		appendViaButtonClick(testNode);

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitBeforeNodeReappendedIndirectly() {

		var wrapper = new DomDiv();
		var testNode = wrapper.appendChild(new TestNode());
		appendViaButtonClick(wrapper);
		disappendViaButtonClick(wrapper);

		setAllChangedAndSubmit();
		appendViaButtonClick(wrapper);

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitAfterNodeReappendedDirectly() {

		var testNode = new TestNode();
		appendViaButtonClick(testNode);
		disappendViaButtonClick(testNode);

		appendViaButtonClick(testNode);
		setAllChangedAndSubmit();

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithSubmitAfterNodeReappendedIndirectly() {

		var wrapper = new DomDiv();
		var testNode = wrapper.appendChild(new TestNode());
		appendViaButtonClick(wrapper);
		disappendViaButtonClick(wrapper);

		appendViaButtonClick(wrapper);
		setAllChangedAndSubmit();

		assertEquals(1, testNode.getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithAppendedChildListener() {

		var testNode = new NodeAppendingTestNode(element -> element);

		appendViaButtonClick(testNode);
		setAllChangedAndSubmit();

		assertEquals(1, testNode.getRefreshCount());
		assertEquals(0, testNode.getNewNode().getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithAppendedSiblingListener() {

		var testNode = new NodeAppendingTestNode(element -> element.getParent());

		appendViaButtonClick(testNode);
		setAllChangedAndSubmit();

		assertEquals(1, testNode.getRefreshCount());
		assertEquals(0, testNode.getNewNode().getRefreshCount());
	}

	@Test
	public void testRefreshCallsWithAppendedBodyChildListener() {

		var testNode = new NodeAppendingTestNode(element -> element.getDomDocument().getBody());

		appendViaButtonClick(testNode);
		setAllChangedAndSubmit();

		assertEquals(1, testNode.getRefreshCount());
		assertEquals(0, testNode.getNewNode().getRefreshCount());
	}

	/**
	 * Simulates a user interaction that appends the given node to an
	 * already-appended parent element that is not the {@link DomBody}.
	 *
	 * @param node
	 *            the {@link IDomNode} to append
	 */
	private void appendViaButtonClick(IDomNode node) {

		// reconfigure the event-generating button
		button.setClickCallback(() -> container.appendChild(node));

		// click it
		asTester(button).click();
	}

	/**
	 * Simulates a user interaction that disappends the given node.
	 *
	 * @param node
	 *            the {@link IDomNode} to disappend
	 */
	private void disappendViaButtonClick(IDomNode node) {

		// reconfigure the event-generating button
		button.setClickCallback(() -> node.disappend());

		// click it
		asTester(button).click();
	}

	private void setAllChangedAndSubmit() {

		CurrentDomDocument//
			.get()
			.getRefreshBus()
			.setAllChanged()
			.submitEvent();
	}

	private class TestNode extends DomDiv implements IDomRefreshBusListener {

		private int refreshCount;

		public TestNode() {

			this.refreshCount = 0;
		}

		@Override
		public void refresh(IDomRefreshBusEvent event) {

			this.refreshCount++;
		}

		public int getRefreshCount() {

			return refreshCount;
		}
	}

	private class NodeAppendingTestNode extends TestNode {

		private final Function<IDomParentElement, IDomParentElement> newNodeParentDeterminer;
		private TestNode newNode;

		public NodeAppendingTestNode(Function<IDomParentElement, IDomParentElement> newNodeParentDeterminer) {

			this.newNodeParentDeterminer = newNodeParentDeterminer;
			this.newNode = null;
		}

		@Override
		public void refresh(IDomRefreshBusEvent event) {

			if (newNode != null) {
				throw new IllegalStateException();
			}

			newNode = new TestNode();
			newNodeParentDeterminer.apply(this).appendChild(newNode);

			super.refresh(event);
		}

		public TestNode getNewNode() {

			return newNode;
		}
	}
}
