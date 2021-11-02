package com.softicar.platform.dom.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link DomAttributeRegistry}.
 *
 * @author Oliver Richers
 */
public class DomAttributeRegistryTest {

	private IDomDocument document;
	private IDomAttribute alignRightAttribute;
	private IDomAttributeRegistry attributeRegistry;

	@Before
	public void setup() {

		document = new DomDocument();
		alignRightAttribute = new DomAttribute("align", "right", true);
		alignRightAttribute = new DomAttribute("align", "left", true);
		attributeRegistry = new DomAttributeRegistry(document);
	}

	@Test
	public void returnsCorrectContext() {

		assertSame(document, attributeRegistry.getDomDocument());
	}

	@Test
	public void otherRegistryReturnsOtherContext() {

		IDomDocument otherContext = new DomDocument();
		IDomAttributeRegistry otherRegistry = new DomAttributeRegistry(otherContext);

		assertSame(document, attributeRegistry.getDomDocument());
		assertSame(otherContext, otherRegistry.getDomDocument());
	}

	@Test
	public void emptyAttributeMapIsEmpty() {

		IDomAttributeMap map = attributeRegistry.getEmptyMap();
		assertEquals(0, map.size());
	}

	@Test
	public void alwaysReturnsTheSameEmptyMap() {

		IDomAttributeMap map = attributeRegistry.getEmptyMap();
		assertSame(attributeRegistry.getEmptyMap(), map);
	}

	@Test
	public void putDoesNotModifySourceMap() {

		IDomAttributeMap map = attributeRegistry.put(attributeRegistry.getEmptyMap(), alignRightAttribute);

		assertNotSame(attributeRegistry.getEmptyMap(), map);
		assertEquals(0, attributeRegistry.getEmptyMap().size());
	}

	@Test
	public void putsCorrectAttribute() {

		IDomAttributeMap map = attributeRegistry.put(attributeRegistry.getEmptyMap(), alignRightAttribute);

		assertEquals(1, map.size());
		assertSame(alignRightAttribute, map.getAttribute(alignRightAttribute.getName()));
	}

	@Test
	public void reusesAttributeMapWhenEqualAfterAdd() {

		IDomAttributeMap map1 = attributeRegistry.put(attributeRegistry.getEmptyMap(), alignRightAttribute);
		IDomAttributeMap map2 = attributeRegistry.put(attributeRegistry.getEmptyMap(), alignRightAttribute);

		assertSame(map1, map2);
	}

	@Test
	public void reusesAttributeMapWhenEqualAfterRemove() {

		IDomAttributeMap map1 = attributeRegistry.put(attributeRegistry.getEmptyMap(), alignRightAttribute);
		IDomAttributeMap map2 = attributeRegistry.remove(map1, alignRightAttribute.getName());

		assertSame(attributeRegistry.getEmptyMap(), map2);
	}
}
