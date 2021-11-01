package com.softicar.platform.emf.table.listener;




import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import org.junit.Test;

public class EmfTableListenerTest extends AbstractEmfTest {

	@Test
	public void testAfterCommit() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = new TestDiv();

		EmfTestSubObject entity = new EmfTestSubObject()//
			.setName("x")
			.setNotNullableValue(420)
			.save();
		CurrentDomDocument.get().getRefreshBus().submitEvent();

		IDomRefreshBusEvent event = testDiv.getEvent();
		assertNotNull(event);
		assertTrue(event.isChanged(entity));
	}

	@Test
	public void testAfterCommitWithoutDomDocument() {

		CurrentDomDocument.set(null);

		new EmfTestSubObject()//
			.setName("x")
			.setNotNullableValue(420)
			.save();
	}

	@Test
	public void testAfterCommitWithReload() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = new TestDiv();

		user.reload();
		CurrentDomDocument.get().getRefreshBus().submitEvent();

		IDomRefreshBusEvent event = testDiv.getEvent();
		assertNotNull(event);
		assertTrue(event.isChanged(user));
	}

	@Test
	public void testAfterCommitWithReloadForUpdate() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = new TestDiv();

		try (DbTransaction transaction = new DbTransaction()) {
			user.reloadForUpdate();
			transaction.commit();
		}
		CurrentDomDocument.get().getRefreshBus().submitEvent();

		IDomRefreshBusEvent event = testDiv.getEvent();
		assertNotNull(event);
		assertTrue(event.isChanged(user));
	}

	@Test
	public void testAfterCommitWithReloadForUpdateAndRollback() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = new TestDiv();

		try (DbTransaction transaction = new DbTransaction()) {
			user.reloadForUpdate();
			transaction.rollback();
		}
		CurrentDomDocument.get().getRefreshBus().submitEvent();

		IDomRefreshBusEvent event = testDiv.getEvent();
		assertNull(event);
	}

	@Test
	public void testAfterCommitWithoutReloading() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = new TestDiv();

		CurrentDomDocument.get().getRefreshBus().submitEvent();

		IDomRefreshBusEvent event = testDiv.getEvent();
		assertNull(event);
	}

	private static class TestDiv extends DomDiv implements IDomRefreshBusListener {

		private IDomRefreshBusEvent event;

		@Override
		public void refresh(IDomRefreshBusEvent event) {

			this.event = event;
		}

		public IDomRefreshBusEvent getEvent() {

			return event;
		}
	}
}
