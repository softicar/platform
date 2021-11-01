package com.softicar.platform.emf.collection.list;







import com.softicar.platform.common.date.Day;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.EmfForeignEntityListAttribute;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.simple.list.EmfTestObjectList;
import java.util.Arrays;
import org.junit.Test;

public class AbstractEmfEntityListTableTest extends AbstractEmfTest {

	private final EmfTestObject entityA;
	private final EmfTestObject entityB;
	private final EmfTestObject entityC;

	public AbstractEmfEntityListTableTest() {

		this.entityA = insertEntity(Day.today(), "a");
		this.entityB = insertEntity(Day.today().getRelative(1), "b");
		this.entityC = insertEntity(Day.today().getRelative(2), "c");
	}

	@Test
	public void testGetAttributeWithListField() {

		IEmfAttribute<EmfTestObjectWithEntityListField, EmfTestObjectList> attribute =//
				EmfTestObjectWithEntityListField.TABLE.getAttribute(EmfTestObjectWithEntityListField.LIST);

		assertTrue(attribute instanceof EmfForeignEntityListAttribute);
	}

	@Test
	public void testInsert() {

		EmfTestObjectList list = getOrInsertEntityList(entityA, entityB);

		assertNotNull(list);
		assertNotNull(list.getElements());
		assertEquals(2, list.getElements().size());
		assertSame(entityA, list.getElements().get(0));
		assertSame(entityB, list.getElements().get(1));
		assertFalse(list.getElements().contains(entityC));
		assertEquals(1, EmfTestObjectList.TABLE.loadAll().size());
	}

	@Test
	public void testMultipleInserts() {

		EmfTestObjectList firstList = getOrInsertEntityList(entityA, entityB);
		EmfTestObjectList secondList = getOrInsertEntityList(entityA, entityB);

		assertSame(firstList, secondList);
		assertEquals(1, EmfTestObjectList.TABLE.loadAll().size());
	}

	@Test
	public void testInsertWithReverseOrder() {

		EmfTestObjectList list = getOrInsertEntityList(entityB, entityA);

		assertSame(entityB, list.getElements().get(0));
		assertSame(entityA, list.getElements().get(1));
	}

	@Test
	public void testInsertWithSwappedEntities() {

		EmfTestObjectList firstList = getOrInsertEntityList(entityA, entityB);
		EmfTestObjectList secondList = getOrInsertEntityList(entityB, entityA);

		assertNotSame(firstList, secondList);
		assertEquals(2, EmfTestObjectList.TABLE.loadAll().size());
	}

	@Test
	public void testInsertRepeatedEntities() {

		EmfTestObjectList list = getOrInsertEntityList(entityA, entityB, entityA, entityA, entityC);

		assertEquals(5, list.getElements().size());
		assertSame(entityA, list.getElements().get(0));
		assertSame(entityB, list.getElements().get(1));
		assertSame(entityA, list.getElements().get(2));
		assertSame(entityA, list.getElements().get(3));
		assertSame(entityC, list.getElements().get(4));
		assertEquals(1, EmfTestObjectList.TABLE.loadAll().size());
	}

	@Test
	public void testInsertWithoutEntities() {

		EmfTestObjectList list = getOrInsertEntityList();

		assertNotNull(list);
		assertNotNull(list.getElements());
		assertEquals(0, list.getElements().size());
		assertFalse(list.getElements().contains(entityA));
		assertFalse(list.getElements().contains(entityB));
		assertFalse(list.getElements().contains(entityC));
		assertEquals(1, EmfTestObjectList.TABLE.loadAll().size());
	}

	private EmfTestObject insertEntity(Day day, String name) {

		EmfTestObject entity = new EmfTestObject();
		entity.setDay(day);
		entity.setName(name);
		entity.save();
		return entity;
	}

	private EmfTestObjectList getOrInsertEntityList(EmfTestObject...entities) {

		return EmfTestObjectList.TABLE.getOrInsert(Arrays.asList(entities));
	}
}
