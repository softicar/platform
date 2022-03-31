package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfInheritedAttributeTest extends AbstractEmfTest {

	private final IEmfAttribute<EmfTestObject, String> attribute;
	private final EmfTestObject simpleEntity1;
	private final EmfTestObject simpleEntity2;
	private final EmfTestSubObject testEntity1;
	private final EmfTestSubObject testEntity2;
	private final EmfInheritedAttribute<EmfTestSubObject, EmfTestObject, String> inheritedAttribute;

	public EmfInheritedAttributeTest() {

		attribute = Mockito.mock(IEmfAttribute.class);
		simpleEntity1 = new EmfTestObject().save();
		simpleEntity2 = new EmfTestObject().save();
		testEntity1 = EmfTestSubObject.TABLE//
			.createObject(simpleEntity1)
			.setName("TEST")
			.setNotNullableValue(420)
			.save();
		testEntity2 = EmfTestSubObject.TABLE//
			.createObject(simpleEntity2)
			.setName("TEST")
			.setNotNullableValue(420)
			.save();
		inheritedAttribute = //
				new EmfInheritedAttribute<>(EmfTestSubObject.SIMPLE_ENTITY, attribute);
	}

	@Test
	public void testGetValueClass() {

		assertDelegation(() -> attribute.getValueClass(), () -> inheritedAttribute.getValueClass(), String.class);
	}

	@Test
	public void testGetValueComparator() {

		Comparator<Object> mock = Mockito.mock(Comparator.class);
		Optional<Comparator<Object>> element = Optional.of(mock);
		assertDelegation(() -> attribute.getValueComparator(), () -> inheritedAttribute.getValueComparator(), element);
	}

	@Test
	public void testPrefetchValues() {

		inheritedAttribute.prefetchValues(Arrays.asList(testEntity1, testEntity2));

		Mockito.verify(attribute).prefetchValues(Arrays.asList(simpleEntity1, simpleEntity2));
	}

	@Test
	public void testGetValue() {

		assertDelegation(() -> attribute.getValue(simpleEntity1), () -> inheritedAttribute.getValue(testEntity1), "TEST");
	}

	@Test
	public void testSetValue() {

		String expectedValue = "TEST";
		inheritedAttribute.setValue(testEntity1, expectedValue);

		Mockito.verify(attribute).setValue(simpleEntity1, expectedValue);
	}

	@Test
	public void testGetTitle() {

		IDisplayString element = Mockito.mock(IDisplayString.class);
		assertDelegation(() -> attribute.getTitle(), () -> inheritedAttribute.getTitle(), element);
	}

	@Test
	public void testGetTitleAfterSettingTitle() {

		DisplayString displayString = new DisplayString();
		inheritedAttribute.setTitle(displayString);

		assertEquals(//
			displayString,
			inheritedAttribute.getTitle());
	}

	@Test
	public void testIsConcealed() {

		assertDelegation(() -> attribute.isConcealed(), () -> inheritedAttribute.isConcealed(), true);
		assertDelegation(() -> attribute.isConcealed(), () -> inheritedAttribute.isConcealed(), false);
	}

	@Test
	public void testIsConcealedWithOverrideToTrue() {

		Mockito.when(attribute.isConcealed()).thenReturn(false);
		inheritedAttribute.setConcealed(true);
		assertTrue(inheritedAttribute.isConcealed());
	}

	@Test
	public void testIsConcealedWithOverrideToFalse() {

		Mockito.when(attribute.isConcealed()).thenReturn(true);
		inheritedAttribute.setConcealed(false);
		assertTrue(inheritedAttribute.isConcealed());
	}

	@Test
	public void testIsEditable() {

		assertDelegation(() -> attribute.isEditable(), () -> inheritedAttribute.isEditable(), true);
		assertDelegation(() -> attribute.isEditable(), () -> inheritedAttribute.isEditable(), false);
	}

	@Test
	public void testIsEditableWithOverrideToTrue() {

		Mockito.when(attribute.isEditable()).thenReturn(false);
		inheritedAttribute.setEditable(true);
		assertFalse(inheritedAttribute.isEditable());
	}

	@Test
	public void testIsEditableWithOverrideToFalse() {

		Mockito.when(attribute.isEditable()).thenReturn(true);
		inheritedAttribute.setEditable(false);
		assertFalse(inheritedAttribute.isEditable());
	}

	@Test
	public void testIsTransient() {

		assertFalse(inheritedAttribute.isTransient());
	}

	@Test
	public void testIsVisible() {

		assertDelegation(() -> attribute.isVisible(simpleEntity1), () -> inheritedAttribute.isVisible(testEntity1), true);
		assertDelegation(() -> attribute.isVisible(simpleEntity1), () -> inheritedAttribute.isVisible(testEntity1), false);
	}

	@Test
	public void testIsVisibleWithConcealedOverrideToTrue() {

		Mockito.when(attribute.isVisible(simpleEntity1)).thenReturn(true);
		inheritedAttribute.setConcealed(true);
		assertFalse(inheritedAttribute.isVisible(testEntity1));
	}

	@Test
	public void testIsEditableOnEntity() {

		assertDelegation(() -> attribute.isEditable(simpleEntity1), () -> inheritedAttribute.isEditable(testEntity1), true);
		assertDelegation(() -> attribute.isEditable(simpleEntity1), () -> inheritedAttribute.isEditable(testEntity1), false);
	}

	@Test
	public void testIsEditableOnEntityWithEditableOverrideToFalse() {

		Mockito.when(attribute.isEditable(simpleEntity1)).thenReturn(true);
		inheritedAttribute.setEditable(false);
		assertFalse(inheritedAttribute.isEditable(testEntity1));
	}

	@Test
	public void testIsMandatory() {

		assertDelegation(() -> attribute.isMandatory(simpleEntity1), () -> inheritedAttribute.isMandatory(testEntity1), true);
		assertDelegation(() -> attribute.isMandatory(simpleEntity1), () -> inheritedAttribute.isMandatory(testEntity1), false);
	}

	@Test
	public void testCreateDisplay() {

		IDomElement element = Mockito.mock(IDomElement.class);
		assertDelegation(() -> attribute.createDisplay(simpleEntity1), () -> inheritedAttribute.createDisplay(testEntity1), element);
	}

	@Test
	public void testCreateManagementDisplay() {

		IDomElement element = Mockito.mock(IDomElement.class);
		assertDelegation(() -> attribute.createTabularDisplay(simpleEntity1), () -> inheritedAttribute.createTabularDisplay(testEntity1), element);
	}

	@Test
	public void testCreateInput() {

		IDomElement element = Mockito.mock(IEmfInput.class);
		assertDelegation(() -> attribute.createInput(simpleEntity1), () -> inheritedAttribute.createInput(testEntity1), element);
	}

	private <V> void assertDelegation(Supplier<V> mockedSupplier, Supplier<V> supplierUnderTest, V value) {

		Mockito.when(mockedSupplier.get()).thenReturn(value);
		assertEquals(value, supplierUnderTest.get());
	}
}
