package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import static org.junit.Assert.assertArrayEquals;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.common.testing.Asserts;
import java.util.List;

/**
 * Asserts the matrix of a CSS transform attribute with a comparison delta.
 *
 * @author Oliver Richers
 */
class AjaxCssTransformAsserter {

	private static final double DELTA = 0.01;
	private static final String PREFIX = "matrix(";
	private static final String SUFFIX = ")";

	public void assertEquals(String expectedTransform, String actualTransform) {

		assertArrayEquals("css transform", parseToArray(expectedTransform), parseToArray(actualTransform), DELTA);
	}

	private double[] parseToArray(String transformMatrix) {

		Asserts.assertStartsWith(PREFIX, transformMatrix);
		Asserts.assertEndsWith(SUFFIX, transformMatrix);

		transformMatrix = Trim.trimPrefix(transformMatrix, PREFIX);
		transformMatrix = Trim.trimSuffix(transformMatrix, SUFFIX);

		return List//
			.of(transformMatrix.split(","))
			.stream()
			.map(Double::parseDouble)
			.mapToDouble(Double::doubleValue)
			.toArray();
	}
}
