package com.softicar.platform.common.code.java.reflection;

import com.softicar.platform.common.core.utils.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class DeclaredFieldFinderTest extends Assert {

	private final DeclaredFieldFinder finder;

	public DeclaredFieldFinderTest() {

		this.finder = new DeclaredFieldFinder(TestClass.class);
	}

	@Test
	public void testFindDeclaredFields() {

		Collection<Field> fields = finder.findDeclaredFields();
		assertEquals(4, fields.size());
	}

	@Test
	public void testFindDeclaredFieldsWithIsPublicFilter() {

		finder.setFilter(ReflectionUtils::isPublic);
		Collection<Field> fields = finder.findDeclaredFields();
		assertEquals(2, fields.size());
	}

	@Test
	public void testFindDeclaredFieldsWithIsStaticFilter() {

		finder.setFilter(ReflectionUtils::isStatic);
		Collection<Field> fields = finder.findDeclaredFields();
		assertEquals(1, fields.size());
	}

	@Test
	public void testFindDeclaredFieldsWithIsFinalFilter() {

		finder.setFilter(ReflectionUtils::isFinal);
		Collection<Field> fields = finder.findDeclaredFields();
		assertEquals(3, fields.size());
	}

	private static class TestClass extends TestBaseClass {

		@SuppressWarnings("unused") private static final int PRIVATE_STATIC_FINAL_FIELD = 0;
		@SuppressWarnings("unused") private final int privateFinalField = 0;
		@SuppressWarnings("unused") public final int publicFinalField = 0;
		@SuppressWarnings("unused") public int publicField = 0;

		/**
		 * This method is only here to avoid that Eclipse's save-actions add
		 * "final" to {@link #publicField}.
		 */
		@SuppressWarnings("unused")
		private void setPublicField() {

			this.publicField = 0;
		}
	}

	private static class TestBaseClass {

		/**
		 * This field shall never be found when searching {@link TestClass} for
		 * declared fields.
		 */
		@SuppressWarnings("unused") public static final int OTHER_FIELD = 0;
	}
}
