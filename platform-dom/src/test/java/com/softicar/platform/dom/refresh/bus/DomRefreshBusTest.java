package com.softicar.platform.dom.refresh.bus;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;
import org.junit.Test;

public class DomRefreshBusTest extends AbstractTest {

	private final DomRefreshBus refreshBus;
	private ListeningElement elementA;
	private ListeningElement elementB;
	private ListeningElement elementC;
	private int counterA;
	private int counterB;
	private int counterC;

	public DomRefreshBusTest() {

		this.refreshBus = new DomRefreshBus();

		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void testAddListenerWhileRefreshing() {

		this.elementA = new ListeningElement(() -> {
			refreshBus.addListener(elementB);
			counterA++;
		});

		this.elementB = new ListeningElement(() -> {
			refreshBus.addListener(elementC);
			counterB++;
		});

		this.elementC = new ListeningElement(() -> {
			counterC++;
		});

		refreshBus.addListener(elementA);
		refreshBus.setAllChanged().submitEvent();
		refreshBus.setAllChanged().submitEvent();
		refreshBus.setAllChanged().submitEvent();

		assertEquals(3, counterA);
		assertEquals(2, counterB);
		assertEquals(1, counterC);
	}

	@Test
	public void testRemoveListenerWhileRefreshing() {

		this.elementA = new ListeningElement(() -> {
			refreshBus.removeListener(elementB);
			counterA++;
		});

		this.elementB = new ListeningElement(() -> {
			refreshBus.removeListener(elementC);
			counterB++;
		});

		this.elementC = new ListeningElement(() -> {
			counterC++;
		});

		refreshBus.addListener(elementA);
		refreshBus.addListener(elementB);
		refreshBus.addListener(elementC);
		refreshBus.setAllChanged().submitEvent();
		refreshBus.setAllChanged().submitEvent();
		refreshBus.setAllChanged().submitEvent();

		assertEquals(3, counterA);
		assertEquals(1, counterB);
		assertEquals(1, counterC);
	}

	private class ListeningElement extends DomParentElement implements IDomRefreshBusListener {

		private final INullaryVoidFunction callback;

		public ListeningElement(INullaryVoidFunction callback) {

			this.callback = callback;
		}

		@Override
		public void refresh(IDomRefreshBusEvent event) {

			callback.apply();
		}

		@Override
		public DomElementTag getTag() {

			return DomElementTag.DIV;
		}
	}
}
