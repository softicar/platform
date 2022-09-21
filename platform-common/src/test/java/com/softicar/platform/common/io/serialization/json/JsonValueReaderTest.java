package com.softicar.platform.common.io.serialization.json;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.testing.Asserts;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class JsonValueReaderTest extends AbstractTest {

	@Test
	public void testReadValueWithEmptyJsonString() {

		var reader = new JsonValueReader("");
		Optional<String> value = reader.readValue("$.foo");
		assertFalse(value.isPresent());
	}

	@Test
	public void testReadValueWithEmptyPath() {

		var reader = createReader();
		assertException(IllegalArgumentException.class, () -> reader.readValue(""));
	}

	@Test
	public void testReadValueWithValue() {

		var reader = createReader();
		Optional<String> value = reader.readValue("$.outerElement.innerArray[1]");
		assertEquals("second", value.get());
	}

	@Test
	public void testReadValueWithValueArray() {

		var reader = createReader();
		Asserts.assertException(ClassCastException.class, () -> reader.readValue("$.outerElement.innerArray"));
	}

	@Test
	public void testReadValueWithNonexistentPath() {

		var reader = createReader();
		Optional<String> value = reader.readValue("$.xxxx");
		assertFalse(value.isPresent());
	}

	@Test
	public void testReadValueListWithValue() {

		var reader = createReader();
		List<String> valueList = reader.readList("$.outerElement.innerArray[1].title");
		assertTrue(valueList.isEmpty());
	}

	@Test
	public void testReadValueListWithValueArray() {

		var reader = createReader();
		List<String> valueList = reader.readList("$.outerElement.innerArray");
		assertFalse(valueList.isEmpty());
		assertEquals("first", valueList.get(0));
		assertEquals("second", valueList.get(1));
		assertEquals("third", valueList.get(2));
	}

	@Test
	public void testReadValueListWithNonexistentPath() {

		var reader = createReader();
		List<String> valueList = reader.readList("$.xxxx");
		assertTrue(valueList.isEmpty());
	}

	private JsonValueReader createReader() {

		return new JsonValueReader(getJsonString());
	}

	private String getJsonString() {

		return """
				{
				    "outerElement": {
				        "innerArray": [
				           "first",
				           "second",
				           "third"
				        ]
				    }
				}
				""";
	}
}
