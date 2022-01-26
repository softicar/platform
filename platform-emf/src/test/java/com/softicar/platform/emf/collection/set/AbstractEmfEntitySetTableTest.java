package com.softicar.platform.emf.collection.set;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.set.EmfForeignEntitySetAttribute;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.simple.set.EmfTestObjectSet;
import java.util.Arrays;
import java.util.TreeSet;
import org.junit.Test;

public class AbstractEmfEntitySetTableTest extends AbstractEmfTest {

	private final EmfTestObject entityA;
	private final EmfTestObject entityB;
	private final EmfTestObject entityC;

	public AbstractEmfEntitySetTableTest() {

		this.entityA = insertEntity(Day.today(), "a");
		this.entityB = insertEntity(Day.today().getRelative(1), "b");
		this.entityC = insertEntity(Day.today().getRelative(2), "c");
	}

	@Test
	public void testGetAttributeWithSetField() {

		IEmfAttribute<EmfTestObjectWithEntitySetField, EmfTestObjectSet> attribute =//
				EmfTestObjectWithEntitySetField.TABLE.getAttribute(EmfTestObjectWithEntitySetField.SET);

		assertTrue(attribute instanceof EmfForeignEntitySetAttribute);
	}

	@Test
	public void testGetOrInsert() {

		EmfTestObjectSet set = getOrInsertEntitySet(entityA, entityB);

		assertNotNull(set);
		assertNotNull(set.getElements());
		assertEquals(2, set.getElements().size());
		assertTrue(set.getElements().contains(entityA));
		assertTrue(set.getElements().contains(entityB));
		assertFalse(set.getElements().contains(entityC));
		assertEquals(1, EmfTestObjectSet.TABLE.loadAll().size());
	}

	@Test
	public void testGetOrInsertWithEqualConsecutiveCalls() {

		EmfTestObjectSet firstSet = getOrInsertEntitySet(entityA, entityB);
		EmfTestObjectSet secondSet = getOrInsertEntitySet(entityA, entityB);

		assertSame(firstSet, secondSet);
		assertEquals(1, EmfTestObjectSet.TABLE.loadAll().size());
	}

	@Test
	public void testGetOrInsertWithConsecutiveCallsWithDifferentEntityOrder() {

		EmfTestObjectSet firstSet = getOrInsertEntitySet(entityA, entityB);
		EmfTestObjectSet secondSet = getOrInsertEntitySet(entityB, entityA);

		assertSame(firstSet, secondSet);
		assertEquals(1, EmfTestObjectSet.TABLE.loadAll().size());
	}

	@Test
	public void testGetOrInsertWithDuplicatedEntitiesInSet() {

		EmfTestObjectSet set = getOrInsertEntitySet(entityA, entityB, entityA);

		assertEquals(2, set.getElements().size());
		assertTrue(set.getElements().contains(entityA));
		assertTrue(set.getElements().contains(entityB));
		assertEquals(1, EmfTestObjectSet.TABLE.loadAll().size());
	}

	@Test
	public void testGetOrInsertWithEmptySet() {

		EmfTestObjectSet set = getOrInsertEntitySet();

		assertNotNull(set);
		assertNotNull(set.getElements());
		assertEquals(0, set.getElements().size());
		assertFalse(set.getElements().contains(entityA));
		assertFalse(set.getElements().contains(entityB));
		assertFalse(set.getElements().contains(entityC));
		assertEquals(1, EmfTestObjectSet.TABLE.loadAll().size());
	}

	private EmfTestObject insertEntity(Day day, String name) {

		EmfTestObject entity = new EmfTestObject();
		entity.setDay(day);
		entity.setName(name);
		entity.save();
		return entity;
	}

	private EmfTestObjectSet getOrInsertEntitySet(EmfTestObject...entities) {

		return EmfTestObjectSet.TABLE.getOrInsert(new TreeSet<>(Arrays.asList(entities)));
	}
}
