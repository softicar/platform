package com.softicar.platform.db.runtime.object;

import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class DbObjectCompareToTest extends AbstractDbObjectTest {

	/**
	 * Definition: Null is less than non-null.
	 */
	@Test
	public void compareObjectToNull() {

		DbTestObject object = new DbTestObject(1);
		assertEquals(1, object.compareTo(null));
	}

	/**
	 * Objects are equal to themselves.
	 */
	@Test
	public void compareObjectToItself() {

		DbTestObject object = new DbTestObject(1);
		assertEquals(0, object.compareTo(object));
	}

	/**
	 * Definition: Null-ID is less than non-null-ID.
	 */
	@Test
	public void compareObjectToObjectWithoutId() {

		DbTestObject object1 = new DbTestObject(1);
		DbTestObject object2 = new DbTestObject((Integer) null);
		assertEquals(+1, object1.compareTo(object2));
		assertEquals(-1, object2.compareTo(object1));
	}

	/**
	 * Data cannot (may not) be different if the IDs are equal.
	 * <p>
	 * Actually, in this case, where different objects with the same ID exist,
	 * an exception should better be thrown. But because of backwards
	 * compatibility, we cannot do that.
	 */
	@Test
	public void compareObjectsOfSameTableAndWithSameId() {

		// create objects
		DbTestObject object1 = new DbTestObject(1);
		DbTestObject object2 = new DbTestObject(1);

		// make sure data fields compare to non-zero
		object1.setString("1");
		object2.setString("2");

		// assert comparisons return zero
		assertEquals(0, object1.compareTo(object2));
		assertEquals(0, object2.compareTo(object1));
	}

	@Test
	public void compareObjectsOfSameTableAndWithDifferentIds() {

		// create objects
		DbTestObject object1 = new DbTestObject(1);
		DbTestObject object2 = new DbTestObject(2);

		// assert comparisons return zero
		assertEquals(-1, object1.compareTo(object2));
		assertEquals(+1, object2.compareTo(object1));
	}

	/**
	 * If objects have no ID, fall back to comparing the data.
	 * <p>
	 * Actually, using non-persistent objects as keys in a {@link Set} or
	 * {@link Map} is not a good idea, because such keys must be immutable, with
	 * respect to the <i>compareTo</i> method. It would be better to throw an
	 * exception here, but because of backwards compatibility, we cannot do
	 * that.
	 */
	@Test
	public void compareObjectsOfSameTableAndWithoutIdAndDifferentData() {

		// create objects
		DbTestObject object1 = new DbTestObject();
		DbTestObject object2 = new DbTestObject();

		// initialize data fields
		object1.setString("1");
		object2.setString("2");

		// assert comparison by data fields
		assertEquals(-1, object1.compareTo(object2));
		assertEquals(+1, object2.compareTo(object1));
	}

	/**
	 * If objects have no ID, fall back to comparing the data.
	 */
	@Test
	public void compareObjectsOfSameTableAndWithoutIdAndEqualData() {

		// create objects
		DbTestObject object1 = new DbTestObject();
		DbTestObject object2 = new DbTestObject();

		// initialize data fields
		object1.setString("1");
		object2.setString("1");

		// assert comparisons return zero
		assertEquals(0, object1.compareTo(object2));
		assertEquals(0, object2.compareTo(object1));
	}

	@Test
	public void compareByteArrays() {

		// create objects
		DbTestObject object1 = new DbTestObject();
		DbTestObject object2 = new DbTestObject();

		// initialize byte arrays with equal data and compare
		object1.setBytes(new byte[] { 1, 2, 3 });
		object2.setBytes(new byte[] { 1, 2, 3 });
		assertEquals(0, object1.compareTo(object2));
		assertEquals(0, object2.compareTo(object1));

		// initialize byte arrays with differing data and compare
		object2.setBytes(new byte[] { 1, 2, 4 });
		assertEquals(-1, object1.compareTo(object2));
		assertEquals(+1, object2.compareTo(object1));
	}
}
