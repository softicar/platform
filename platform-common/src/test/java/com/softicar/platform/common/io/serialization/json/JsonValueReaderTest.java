package com.softicar.platform.common.io.serialization.json;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class JsonValueReaderTest extends AbstractTest {

	@Test
	public void testReadWithEmptyJsonString() {

		assertException(IllegalArgumentException.class, () -> new JsonValueReader(""));
	}

	@Test
	public void testReadWithEmptyPath() {

		var reader = createReader();
		assertException(IllegalArgumentException.class, () -> reader.read(""));
	}

	@Test
	public void testReadWithValue() {

		var reader = createReader();
		Optional<String> value = reader.read("$.outerElement.innerArray[1]");
		assertEquals("second", value.get());
	}

	@Test
	public void testReadWithValueArray() {

		var reader = createReader();
		Optional<String> value = reader.read("$.outerElement.innerArray");
		assertFalse(value.isPresent());
	}

	@Test
	public void testReadWithNonexistentPath() {

		var reader = createReader();
		Optional<String> value = reader.read("$.xxxx");
		assertFalse(value.isPresent());
	}

	@Test
	public void testReadListWithValue() {

		var reader = createReader();
		List<String> valueList = reader.readList("$.outerElement.innerArray[1].title");
		assertTrue(valueList.isEmpty());
	}

	@Test
	public void testReadListWithValueArray() {

		var reader = createReader();
		List<String> valueList = reader.readList("$.outerElement.innerArray");
		assertFalse(valueList.isEmpty());
		assertEquals("first", valueList.get(0));
		assertEquals("second", valueList.get(1));
		assertEquals("third", valueList.get(2));
	}

	@Test
	public void testReadListWithNonexistentPath() {

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
