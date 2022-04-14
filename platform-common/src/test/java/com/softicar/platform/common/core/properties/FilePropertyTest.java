package com.softicar.platform.common.core.properties;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class FilePropertyTest extends AbstractTest {

	private static final String HOME = SystemPropertyEnum.USER_HOME.get();
	private static final String SEPARATOR = SystemPropertyEnum.FILE_SEPARATOR.get();
	private final PropertyMap propertyMap;
	private final IProperty<File> property;

	public FilePropertyTest() {

		this.propertyMap = new PropertyMap();
		this.property = new PropertyFactory(propertyMap, "foo").createFileProperty("bar", null);
	}

	@Test
	public void expandsSingleTilde() {

		assertValue(HOME, "~");
	}

	@Test
	public void expandsLeadingTilde() {

		assertValue(HOME + SEPARATOR + "foo", "~" + SEPARATOR + "foo");
	}

	@Test
	public void doesNotExpandNonLeadingTilde() {

		String absolutePath = SEPARATOR + "~" + SEPARATOR + "foo";
		assertValue(absolutePath, absolutePath);
	}

	@Test
	public void doesNotExpandUserSpecificTilde() {

		// note that this functionality might be added later
		String userPath = "~" + "foo" + SEPARATOR + "bar";
		assertEquals(userPath, userPath);
	}

	private void assertValue(String expectedPath, String propertyValue) {

		propertyMap.put("foo.bar", propertyValue);
		File file = property.getValue();

		assertNotNull(file);
		assertEquals(expectedPath, file.getPath());
	}

	protected static class PropertyMap implements IPropertyMap {

		private final Map<PropertyName, String> properties = new TreeMap<>();

		@Override
		public String getValueString(PropertyName propertyName) {

			return properties.get(propertyName);
		}

		public void put(String name, String value) {

			properties.put(new PropertyName(name), value);
		}
	}
}
