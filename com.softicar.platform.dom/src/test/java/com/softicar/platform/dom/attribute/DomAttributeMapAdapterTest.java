package com.softicar.platform.dom.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.container.set.SetFactory;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link DomAttributeMapAdapter}.
 *
 * @author Oliver Richers
 */
public class DomAttributeMapAdapterTest {

	private DomAttributeMapComparator comparator;
	private IDomAttribute attribute1a;
	private IDomAttribute attribute1b;
	private IDomAttribute attribute2;
	private IDomAttributeRegistry attributeRegistry;
	private IDomAttributeMap map;
	private IDomAttributeMap adapterWithNewAttributeAdded;
	private IDomAttributeMap adapterWithSameAttributeAdded;
	private IDomAttributeMap adapterWithAttributeReplaced;
	private IDomAttributeMap adapterWithAttributeRemoved;
	private IDomAttributeMap adapterWithMissingAttributeRemoved;

	@Before
	public void setup() {

		comparator = DomAttributeMapComparator.get();

		attribute1a = new DomAttribute("colspan", "4", true);
		attribute1b = new DomAttribute("colspan", "8", false);
		attribute2 = new DomAttribute("align", "right", true);

		attributeRegistry = new DomAttributeRegistry(new DomDocument());

		map = attributeRegistry.getEmptyMap();
		map = attributeRegistry.put(map, attribute1a);

		adapterWithNewAttributeAdded = DomAttributeMapAdapter.add(map, attribute2);
		adapterWithSameAttributeAdded = DomAttributeMapAdapter.add(map, attribute1a);
		adapterWithAttributeReplaced = DomAttributeMapAdapter.add(map, attribute1b);
		adapterWithAttributeRemoved = DomAttributeMapAdapter.remove(map, attribute1a.getName());
		adapterWithMissingAttributeRemoved = DomAttributeMapAdapter.remove(map, attribute2.getName());
	}

	@Test
	public void returnsCorrectSize() {

		assertEquals(2, adapterWithNewAttributeAdded.size());
		assertEquals(1, adapterWithSameAttributeAdded.size());
		assertEquals(1, adapterWithAttributeReplaced.size());
		assertEquals(0, adapterWithAttributeRemoved.size());
		assertEquals(1, adapterWithMissingAttributeRemoved.size());
	}

	@Test
	public void returnsCorrectHash() {

		assertEquals(map.hashCode(), adapterWithSameAttributeAdded.hashCode());
		assertEquals(map.hashCode(), adapterWithMissingAttributeRemoved.hashCode());
	}

	@Test
	public void returnsCorrectValues() {

		assertEquals(SetFactory.createTreeSetFrom(attribute1a, attribute2), SetFactory.createTreeSet(adapterWithNewAttributeAdded.values()));
		assertEquals(SetFactory.createTreeSetFrom(attribute1a), SetFactory.createTreeSet(adapterWithSameAttributeAdded.values()));
		assertEquals(SetFactory.createTreeSetFrom(attribute1b), SetFactory.createTreeSet(adapterWithAttributeReplaced.values()));
		assertEquals(SetFactory.createTreeSet(), SetFactory.createTreeSet(adapterWithAttributeRemoved.values()));
		assertEquals(SetFactory.createTreeSetFrom(attribute1a), SetFactory.createTreeSet(adapterWithMissingAttributeRemoved.values()));
	}

	@Test
	public void comparatorReturnsTrueIfEqualAndFalseElse() {

		assertFalse(comparator.isEqual(map, adapterWithNewAttributeAdded));
		assertTrue(comparator.isEqual(map, adapterWithSameAttributeAdded));
		assertFalse(comparator.isEqual(map, adapterWithAttributeReplaced));
		assertFalse(comparator.isEqual(map, adapterWithAttributeRemoved));
		assertTrue(comparator.isEqual(map, adapterWithMissingAttributeRemoved));
	}
}
