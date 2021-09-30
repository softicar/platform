package com.softicar.platform.emf.deactivation;




import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfTableRowDeactivationStrategyTest extends AbstractEmfTest {

	private final EmfTableRowDeactivationStrategy<EmfTestObject> strategy;

	public EmfTableRowDeactivationStrategyTest() {

		this.strategy = new EmfTableRowDeactivationStrategy<>(EmfTestObject.TABLE);
	}

	@Test
	public void testIsDeactivationSupported() {

		assertTrue(strategy.isDeactivationSupported());
	}

	@Test
	public void testIsActive() {

		EmfTestObject activeEntity = new EmfTestObject().setActive(true).save();
		EmfTestObject inactiveEntity = new EmfTestObject().setActive(false).save();

		assertTrue(strategy.isActive(activeEntity));
		assertFalse(strategy.isActive(inactiveEntity));
	}

	@Test
	public void testAddWhereActive() {

		EmfTestObject activeEntity = new EmfTestObject().setActive(true).save();
		new EmfTestObject().setActive(false).save();

		ISqlSelect<EmfTestObject> select = EmfTestObject.TABLE.createSelect();

		strategy.addWhereActive(select);

		EmfTestObject selectedEntity = Asserts.assertOne(select.list());
		assertSame(activeEntity, selectedEntity);
	}

	@Test
	public void testIsActiveAttribute() {

		assertFalse(strategy.isActiveAttribute(EmfTestObject.TABLE.getAttribute(EmfTestObject.ID)));
		assertFalse(strategy.isActiveAttribute(EmfTestObject.TABLE.getAttribute(EmfTestObject.DAY)));
		assertTrue(strategy.isActiveAttribute(EmfTestObject.TABLE.getAttribute(EmfTestObject.ACTIVE)));
	}
}
