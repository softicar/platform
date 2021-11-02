package com.softicar.platform.emf.concurrency;




import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.EmfTableRowCacheMapTestMethods;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfConcurrencyControllerTest extends AbstractEmfTest {

	@Test
	public void testPeekForFreshnessWithTransactionAgnosticTable() {

		EmfTestObject entity = insertSimpleEntity("foo");
		EmfTableRowCacheMapTestMethods.useOtherCacheMapAndModify(entity, it -> it.setName("bar"));

		assertTrue(entity.isFresh());
		assertName(entity, "foo");
	}

	@Test
	public void testPeekForFreshnessWithTransactionAwareTableAndModificationToEntity() {

		EmfTestSubObject entity = insertEntity("foo", insertSimpleEntity("qwe"));
		EmfTableRowCacheMapTestMethods.useOtherCacheMapAndModify(entity, it -> it.setName("bar"));

		assertFalse(entity.isFresh());
		assertName(entity, "foo");
	}

	@Test
	public void testPeekForFreshnessWithTransactionAwareTableAndModificationToForeignEntity() {

		EmfTestObject simpleEntity = insertSimpleEntity("qwe");
		EmfTestSubObject entity = insertEntity("foo", simpleEntity);
		EmfTableRowCacheMapTestMethods.useOtherCacheMapAndModify(simpleEntity, it -> it.setName("asd"));

		assertTrue(entity.isFresh());
		assertName(entity.getSimpleEntity(), "qwe");
	}

	@Test
	public void testPeekForFreshnessWithTransactionAwareTableAndNoModification() {

		EmfTestSubObject entity = insertEntity("foo", insertSimpleEntity("qwe"));

		assertTrue(entity.isFresh());
		assertName(entity, "foo");
	}

	// TODO this should actually reside in some DbTableRowCacheMapTest
	@Test
	public void testEntityModificationWithoutReloadCircumventsCache() {

		EmfTestSubObject entity = insertEntity("foo", insertSimpleEntity("qwe"));
		EmfTableRowCacheMapTestMethods.useOtherCacheMapAndModify(entity, it -> it.setName("bar"));
		assertName(entity, "foo");
	}

	private EmfTestObject insertSimpleEntity(String name) {

		EmfTestObject entity = new EmfTestObject();
		entity.setName(name);
		entity.save();
		return entity;
	}

	private EmfTestSubObject insertEntity(String name, EmfTestObject simpleEntity) {

		return EmfTestSubObject.TABLE//
			.createObject(simpleEntity)
			.setName(name)
			.setNotNullableValue(420)
			.save();
	}

	private static void assertName(EmfTestObject entity, String name) {

		assertEquals(name, entity.getName());
	}

	private static void assertName(EmfTestSubObject entity, String name) {

		assertEquals(name, entity.getName());
	}
}
