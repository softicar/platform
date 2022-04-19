package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfAttributeListTest extends AbstractTest {

	private final EmfAttributeList<EmfTestSubObject, EmfTestObject> attributeList;

	public EmfAttributeListTest() {

		this.attributeList = new EmfAttributeList<>(EmfTestSubObject.TABLE);
	}

	@Test
	public void testReadOnlyOfBaseField() {

		IEmfAttribute<EmfTestSubObject, EmfTestObject> simpleEntityAttribute = attributeList.getAttribute(EmfTestSubObject.SIMPLE_ENTITY);

		assertFalse(simpleEntityAttribute.isEditable());
	}
}
