package com.softicar.platform.emf.deactivation;




import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfSubObjectDeactivationStrategyTest extends AbstractEmfTest {

	private final EmfTableRowDeactivationStrategy<EmfTestSubObject> strategy;

	public EmfSubObjectDeactivationStrategyTest() {

		this.strategy = new EmfSubObjectDeactivationStrategy<>(EmfTestSubObject.TABLE);
	}

	@Test
	public void testIsDeactivationSupported() {

		assertTrue(strategy.isDeactivationSupported());
	}

	@Test
	public void testIsActive() {

		EmfTestSubObject activeEntity = insertEntity("active", true);
		EmfTestSubObject inactiveEntity = insertEntity("inactive", false);

		assertTrue(strategy.isActive(activeEntity));
		assertFalse(strategy.isActive(inactiveEntity));
	}

	@Test
	public void testAddWhereActive() {

		EmfTestSubObject activeEntity = insertEntity("active", true);
		insertEntity("inactive", false);

		ISqlSelect<EmfTestSubObject> select = EmfTestSubObject.TABLE.createSelect();

		strategy.addWhereActive(select);

		EmfTestSubObject selectedEntity = Asserts.assertOne(select.list());
		assertSame(activeEntity, selectedEntity);
	}

	@Test
	public void testIsActiveAttribute() {

		assertFalse(strategy.isActiveAttribute(EmfTestSubObject.TABLE.getAttribute(EmfTestSubObject.NAME)));
		assertFalse(strategy.isActiveAttribute(EmfTestSubObject.DAY));
		assertTrue(strategy.isActiveAttribute(EmfTestSubObject.ACTIVE));
	}

	private EmfTestSubObject insertEntity(String name, boolean active) {

		EmfTestObject simpleTestEntity = new EmfTestObject().setActive(active);
		return EmfTestSubObject.TABLE//
			.createObject(simpleTestEntity)
			.setName(name)
			.setNotNullableValue(420)
			.save();
	}
}
