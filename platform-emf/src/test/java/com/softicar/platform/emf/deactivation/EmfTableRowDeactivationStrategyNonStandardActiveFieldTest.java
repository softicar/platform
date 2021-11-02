package com.softicar.platform.emf.deactivation;

import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.test.active.EmfNonStandardActiveTestObject;
import org.junit.Test;

public class EmfTableRowDeactivationStrategyNonStandardActiveFieldTest extends AbstractDbTest {

	private final EmfTableRowDeactivationStrategy<EmfNonStandardActiveTestObject> undetectedFieldStrategy;
	private final EmfTableRowDeactivationStrategy<EmfNonStandardActiveTestObject> explicitFieldStrategy;

	public EmfTableRowDeactivationStrategyNonStandardActiveFieldTest() {

		this.undetectedFieldStrategy = new EmfTableRowDeactivationStrategy<>(EmfNonStandardActiveTestObject.TABLE);
		this.explicitFieldStrategy = new EmfTableRowDeactivationStrategy<>(EmfNonStandardActiveTestObject.TABLE);
		this.explicitFieldStrategy.setActiveField(EmfNonStandardActiveTestObject.NON_STANDARD_ACTIVE);
	}

	@Test
	public void testIsDeactivationSupportedWithUndetectedField() {

		assertFalse(undetectedFieldStrategy.isDeactivationSupported());
	}

	@Test
	public void testIsActiveWithUndetectedField() {

		EmfNonStandardActiveTestObject a = new EmfNonStandardActiveTestObject().setNonStandardActive(true).save();
		EmfNonStandardActiveTestObject b = new EmfNonStandardActiveTestObject().setNonStandardActive(false).save();

		assertTrue(undetectedFieldStrategy.isActive(a));
		assertTrue(undetectedFieldStrategy.isActive(b));
	}

	@Test
	public void testAddWhereActiveWithUndetectedField() {

		new EmfNonStandardActiveTestObject().setNonStandardActive(true).save();
		new EmfNonStandardActiveTestObject().setNonStandardActive(false).save();

		ISqlSelect<EmfNonStandardActiveTestObject> select = EmfNonStandardActiveTestObject.TABLE.createSelect();

		undetectedFieldStrategy.addWhereActive(select);

		assertEquals(2, select.list().size());
	}

	@Test
	public void testIsActiveAttributeWithUndetectedField() {

		assertFalse(undetectedFieldStrategy.isActiveAttribute(EmfNonStandardActiveTestObject.TABLE.getAttribute(EmfNonStandardActiveTestObject.ID)));
		assertFalse(undetectedFieldStrategy.isActiveAttribute(EmfNonStandardActiveTestObject.TABLE.getAttribute(EmfNonStandardActiveTestObject.DAY)));
		assertFalse(
			undetectedFieldStrategy.isActiveAttribute(EmfNonStandardActiveTestObject.TABLE.getAttribute(EmfNonStandardActiveTestObject.NON_STANDARD_ACTIVE)));
	}

	@Test
	public void testIsDeactivationSupportedWithExplicitField() {

		assertTrue(explicitFieldStrategy.isDeactivationSupported());
	}

	@Test
	public void testIsActiveWithExplicitField() {

		EmfNonStandardActiveTestObject activeEntity = new EmfNonStandardActiveTestObject().setNonStandardActive(true).save();
		EmfNonStandardActiveTestObject inactiveEntity = new EmfNonStandardActiveTestObject().setNonStandardActive(false).save();

		assertTrue(explicitFieldStrategy.isActive(activeEntity));
		assertFalse(explicitFieldStrategy.isActive(inactiveEntity));
	}

	@Test
	public void testAddWhereActiveWithExplicitField() {

		EmfNonStandardActiveTestObject activeEntity = new EmfNonStandardActiveTestObject().setNonStandardActive(true).save();
		new EmfNonStandardActiveTestObject().setNonStandardActive(false).save();

		ISqlSelect<EmfNonStandardActiveTestObject> select = EmfNonStandardActiveTestObject.TABLE.createSelect();

		explicitFieldStrategy.addWhereActive(select);

		EmfNonStandardActiveTestObject selectedEntity = Asserts.assertOne(select.list());
		assertSame(activeEntity, selectedEntity);
	}

	@Test
	public void testIsActiveAttributeWithExplicitField() {

		assertFalse(explicitFieldStrategy.isActiveAttribute(EmfNonStandardActiveTestObject.TABLE.getAttribute(EmfNonStandardActiveTestObject.ID)));
		assertFalse(explicitFieldStrategy.isActiveAttribute(EmfNonStandardActiveTestObject.TABLE.getAttribute(EmfNonStandardActiveTestObject.DAY)));
		assertTrue(
			explicitFieldStrategy.isActiveAttribute(EmfNonStandardActiveTestObject.TABLE.getAttribute(EmfNonStandardActiveTestObject.NON_STANDARD_ACTIVE)));
	}
}
