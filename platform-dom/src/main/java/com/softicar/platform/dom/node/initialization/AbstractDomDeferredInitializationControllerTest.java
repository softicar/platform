package com.softicar.platform.dom.node.initialization;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.mockito.Mockito;

public abstract class AbstractDomDeferredInitializationControllerTest extends AbstractTest {

	protected final List<IDomNode> initializedNodes;

	protected AbstractDomDeferredInitializationControllerTest() {

		initializedNodes = new ArrayList<>();
	}

	protected void addInitializedNode(IDomNode node) {

		this.initializedNodes.add(node);
	}

	protected static class TestDiv extends DomDiv {

		public static final IStaticObject CONNECT_UPPER_NODE_BUTTON = Mockito.mock(IStaticObject.class);
		public static final IStaticObject CONNECT_MIDDLE_AND_LOWER_NODES_BUTTON = Mockito.mock(IStaticObject.class);
		public DomDiv upperNode;
		public DomDiv middleNode;
		public DomDiv lowerNode;

		public TestDiv(Consumer<IDomNode> nodeInitializationHandler) {

			upperNode = new DomDiv();
			upperNode.addDeferredInitializer(() -> nodeInitializationHandler.accept(upperNode));

			middleNode = new DomDiv();
			middleNode.addDeferredInitializer(() -> nodeInitializationHandler.accept(middleNode));

			lowerNode = new DomDiv();
			lowerNode.addDeferredInitializer(() -> nodeInitializationHandler.accept(lowerNode));

			appendChild(//
				new DomButton().addMarker(CONNECT_UPPER_NODE_BUTTON).setLabel("connect upper node").setClickCallback(() -> {
					// append only the upper node
					appendChild(upperNode);
				}));
			appendChild(//
				new DomButton().addMarker(CONNECT_MIDDLE_AND_LOWER_NODES_BUTTON).setLabel("connect lower and middle nodes").setClickCallback(() -> {
					// append the lower node first; then, append the middle node
					middleNode.appendChild(lowerNode);
					upperNode.appendChild(middleNode);
				}));
		}
	}
}
