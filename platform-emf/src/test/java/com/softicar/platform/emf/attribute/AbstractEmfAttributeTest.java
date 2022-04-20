package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.attribute.field.integer.EmfIntegerAttribute;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.test.EmfTestSubObject;
import org.junit.Test;

/**
 * This is the unit test for {@link AbstractEmfAttributeTest}.
 * <p>
 * This class is not an abstract base class.
 * <p>
 * The class {@link EmfIntegerAttribute} is used to indirectly test
 * {@link AbstractEmfAttribute} since the latter cannot be instantiated.
 *
 * @author Oliver Richers
 */
public class AbstractEmfAttributeTest extends AbstractTest {

	private final EmfTestSubObject subObject;
	private final EmfIntegerAttribute<EmfTestSubObject> attribute;

	public AbstractEmfAttributeTest() {

		this.subObject = new EmfTestSubObject();
		this.attribute = new EmfIntegerAttribute<>(EmfTestSubObject.VALUE);
	}

	// ------------------------------ isVisible() ------------------------------ //

	@Test
	public void testIsVisibleWithDefaultPredicate() {

		assertTrue(attribute.isVisible(subObject));
	}

	@Test
	public void testIsVisibleWithPredicateNever() {

		attribute.setPredicateVisible(EmfPredicates.never());

		assertFalse(attribute.isVisible(subObject));
	}

	// ------------------------------ isEditable() ------------------------------ //

	@Test
	public void testIsEditableWithDefaultPredicate() {

		assertTrue(attribute.isEditable(subObject));
	}

	@Test
	public void testIsEditableWithPedicateNever() {

		attribute.setPredicateEditable(EmfPredicates.never());

		assertFalse(attribute.isEditable(subObject));
	}

	// ------------------------------ isMandatory() ------------------------------ //

	@Test
	public void testIsMandatoryWithDefaultPredicate() {

		assertFalse(attribute.isMandatory(subObject));
	}

	@Test
	public void testIsMandatoryWithPedicateAlways() {

		attribute.setPredicateMandatory(EmfPredicates.always());
		assertTrue(attribute.isMandatory(subObject));
	}

	@Test
	public void testIsMandatoryWithNotNullable() {

		EmfIntegerAttribute<EmfTestSubObject> notNullableAttribute = new EmfIntegerAttribute<>(EmfTestSubObject.NOT_NULLABLE_VALUE);
		assertTrue(notNullableAttribute.isMandatory(subObject));
	}
}
