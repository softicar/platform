package com.softicar.platform.emf.table.listener;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfTableListenerTest extends AbstractEmfTest {

	@Test
	public void testAfterCommit() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = CurrentDomDocument.get().getBody().appendChild(new TestDiv());

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
	public void testAfterCommitWithReloadOfUnchangedObject() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = CurrentDomDocument.get().getBody().appendChild(new TestDiv());

		user.reload();
		CurrentDomDocument.get().getRefreshBus().submitEvent();

		IDomRefreshBusEvent event = testDiv.getEvent();
		assertNull(event);
	}

	@Test
	public void testAfterCommitWithReloadOfChangedObject() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = CurrentDomDocument.get().getBody().appendChild(new TestDiv());

		updateUserInDatabase(user);
		user.reload();
		CurrentDomDocument.get().getRefreshBus().submitEvent();

		IDomRefreshBusEvent event = testDiv.getEvent();
		assertNotNull(event);
		assertTrue(event.isChanged(user));
	}

	@Test
	public void testAfterCommitWithReloadForUpdate() {

		CurrentDomDocument.set(new DomDocument());
		TestDiv testDiv = CurrentDomDocument.get().getBody().appendChild(new TestDiv());

		try (DbTransaction transaction = new DbTransaction()) {
			updateUserInDatabase(user);
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
			updateUserInDatabase(user);
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

	private void updateUserInDatabase(EmfTestUser user) {

		EmfTestUser.TABLE//
			.createUpdate()
			.set(EmfTestUser.FIRST_NAME, "boo")
			.where(EmfTestUser.ID.isEqual(user.getId()))
			.execute();
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
