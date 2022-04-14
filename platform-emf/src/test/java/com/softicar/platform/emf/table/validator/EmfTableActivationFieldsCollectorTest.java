package com.softicar.platform.emf.table.validator;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.Collection;
import org.junit.Test;

public class EmfTableActivationFieldsCollectorTest extends AbstractTest {

	@Test
	public void testWithNativeField() {

		Collection<IDbField<?, Boolean>> fields = new EmfTableActivationFieldsCollector(EmfTestObject.TABLE).collect();

		assertSame(EmfTestObject.ACTIVE, Asserts.assertOne(fields));
	}

	@Test
	public void testWithInheritedField() {

		Collection<IDbField<?, Boolean>> fields = new EmfTableActivationFieldsCollector(EmfTestSubObject.TABLE).collect();

		assertSame(EmfTestObject.ACTIVE, Asserts.assertOne(fields));
	}
}
