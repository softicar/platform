package com.softicar.platform.emf.sub.object.table;

import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.attribute.EmfAttributeList;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfSubObjectTableSpecializationTest extends AbstractDbTest {

	@Test
	public void testInitializeAttributeList() {

		IEmfAttributeList<EmfTestSubObject> attributeList = new EmfAttributeList<>(EmfTestSubObject.TABLE);

		EmfTestSubObject.TABLE.getTableSpecialization().initializeAttributeList(attributeList);

		IEmfAttribute<EmfTestSubObject, EmfTestObject> baseAttribute = attributeList.getAttribute(EmfTestSubObject.TABLE.getBaseField());
		assertTrue(baseAttribute.isConcealed());
		assertFalse(baseAttribute.isEditable());
		assertTrue(baseAttribute.isImmutable());
	}
}
