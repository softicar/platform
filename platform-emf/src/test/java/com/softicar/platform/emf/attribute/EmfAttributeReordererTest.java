package com.softicar.platform.emf.attribute;

import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class EmfAttributeReordererTest extends Assert {

	private final EmfAttributeList<EmfTestSubObject, EmfTestObject> attributeList;

	public EmfAttributeReordererTest() {

		attributeList = new EmfAttributeList<>(EmfTestSubObject.TABLE);
	}

	@Test
	public void testMoveAttributesInFrontOfIEmfAttribute() {

		new EmfAttributeReorderer<>(attributeList)
			.moveAttributes(//
				EmfTestSubObject.NAME,
				EmfTestSubObject.VALUE,
				EmfTestSubObject.NOT_NULLABLE_VALUE,
				EmfTestSubObject.TRANSACTION,
				EmfTestSubObject.SIMPLE_ENTITY)
			.inFrontOf(EmfTestSubObject.DAY); // == IEmfAttribute

		assertAttributeOrder(//
			EmfTestSubObject.NAME,
			EmfTestSubObject.VALUE,
			EmfTestSubObject.NOT_NULLABLE_VALUE,
			EmfTestSubObject.TRANSACTION,
			EmfTestSubObject.SIMPLE_ENTITY,
			EmfTestSubObject.DAY);
	}

	@Test
	public void testMoveAttributesInFrontOfIDbField() {

		new EmfAttributeReorderer<>(attributeList)
			.moveAttributes(//
				EmfTestSubObject.NAME,
				EmfTestSubObject.VALUE,
				EmfTestSubObject.NOT_NULLABLE_VALUE,
				EmfTestSubObject.TRANSACTION)
			.inFrontOf(EmfTestSubObject.SIMPLE_ENTITY); // == IDbField

		assertAttributeOrder(//
			EmfTestSubObject.NAME,
			EmfTestSubObject.VALUE,
			EmfTestSubObject.NOT_NULLABLE_VALUE,
			EmfTestSubObject.TRANSACTION,
			EmfTestSubObject.SIMPLE_ENTITY);
	}

	@Test
	public void testMoveAttributesBehindIDbField() {

		new EmfAttributeReorderer<>(attributeList)
			.moveAttributes(//
				EmfTestSubObject.NAME,
				EmfTestSubObject.VALUE,
				EmfTestSubObject.NOT_NULLABLE_VALUE,
				EmfTestSubObject.TRANSACTION)
			.behind(EmfTestSubObject.SIMPLE_ENTITY);  // == IDbField

		assertAttributeOrder(//
			EmfTestSubObject.SIMPLE_ENTITY,
			EmfTestSubObject.NAME,
			EmfTestSubObject.VALUE,
			EmfTestSubObject.NOT_NULLABLE_VALUE,
			EmfTestSubObject.TRANSACTION);
	}

	@Test
	public void testMoveAttributesBehindIEmfAttribute() {

		new EmfAttributeReorderer<>(attributeList)
			.moveAttributes(//
				EmfTestSubObject.TRANSACTION,
				EmfTestSubObject.SIMPLE_ENTITY,
				EmfTestSubObject.NAME,
				EmfTestSubObject.VALUE,
				EmfTestSubObject.NOT_NULLABLE_VALUE)
			.behind(EmfTestSubObject.DAY); // == IEmfAttribute

		assertAttributeOrder(//
			EmfTestSubObject.DAY,
			EmfTestSubObject.TRANSACTION,
			EmfTestSubObject.SIMPLE_ENTITY,
			EmfTestSubObject.NAME,
			EmfTestSubObject.VALUE,
			EmfTestSubObject.NOT_NULLABLE_VALUE);
	}

	@Test
	public void testMoveAttributesToBack() {

		new EmfAttributeReorderer<>(attributeList)
			.moveAttributes(//
				EmfTestSubObject.NAME)
			.toBack();

		assertAttributeOrder(//
			EmfTestSubObject.SIMPLE_ENTITY,
			EmfTestSubObject.TRANSACTION,
			EmfTestSubObject.VALUE,
			EmfTestSubObject.NOT_NULLABLE_VALUE,
			EmfTestSubObject.NAME);
	}

	@Test
	public void testMoveAttributesToFront() {

		new EmfAttributeReorderer<>(attributeList)
			.moveAttributes(//
				EmfTestSubObject.NAME)
			.toFront();

		assertAttributeOrder(//
			EmfTestSubObject.NAME,
			EmfTestSubObject.SIMPLE_ENTITY,
			EmfTestSubObject.TRANSACTION,
			EmfTestSubObject.VALUE,
			EmfTestSubObject.NOT_NULLABLE_VALUE);
	}

	private void assertAttributeOrder(Object...expectedList) {

		List<Object> actualList = attributeList //
			.getAttributes()
			.stream()
			.map(a -> {
				if (a.getClass().isAssignableFrom(EmfInheritedAttribute.class)) {
					return a;
				} else {
					return a.getField().get();
				}
			})
			.collect(Collectors.toList());

		assertEquals(Arrays.asList(expectedList), actualList);
	}
}
