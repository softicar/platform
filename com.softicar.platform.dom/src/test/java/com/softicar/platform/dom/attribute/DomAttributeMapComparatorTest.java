package com.softicar.platform.dom.attribute;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link DomAttributeMapComparator}.
 *
 * @author Oliver Richers
 */
public class DomAttributeMapComparatorTest {

	private DomAttributeMapComparator comparator;
	private IDomAttribute attribute1a;
	private IDomAttribute attribute1b;
	private IDomAttribute attribute2;
	private IDomAttributeRegistry attributeRegistry;

	@Before
	public void setup() {

		comparator = DomAttributeMapComparator.get();

		attribute1a = new DomAttribute("colspan", "4", true);
		attribute1b = new DomAttribute("colspan", "8", false);
		attribute2 = new DomAttribute("align", "right", true);

		attributeRegistry = new DomAttributeRegistry(new DomDocument());
	}

	@Test
	public void returnsTrueOnEmptyMaps() {

		assertTrue(comparator.isEqual(attributeRegistry.getEmptyMap(), attributeRegistry.getEmptyMap()));
	}

	@Test
	public void returnsTrueOnSameAttribute() {

		IDomAttributeMap map1 = attributeRegistry.put(attributeRegistry.getEmptyMap(), attribute1a);
		IDomAttributeMap map2 = attributeRegistry.put(attributeRegistry.getEmptyMap(), attribute1a);

		assertTrue(comparator.isEqual(map1, map2));
	}

	@Test
	public void returnsFalseOnDifferentAttribute() {

		IDomAttributeMap map1 = attributeRegistry.put(attributeRegistry.getEmptyMap(), attribute1a);
		IDomAttributeMap map2 = attributeRegistry.put(attributeRegistry.getEmptyMap(), attribute1b);

		assertFalse(comparator.isEqual(map1, map2));
	}

	@Test
	public void returnsFalseOnAdditionalAttribute() {

		IDomAttributeMap map1 = attributeRegistry.put(attributeRegistry.getEmptyMap(), attribute1a);
		IDomAttributeMap map2 = attributeRegistry.put(map1, attribute2);

		assertFalse(comparator.isEqual(map1, map2));
	}
}
