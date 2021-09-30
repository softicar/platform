package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link NumericMatrixTraits}.
 *
 * @author Oliver Richers
 */
public class NumericMatrixTraitsTest extends Assert {

	@Test
	public void testBigDecimal() {

		NumericMatrixTraits<BigDecimal> traits = new NumericMatrixTraits<>(Arithmetics.BIG_DECIMAL);

		assertEquals(BigDecimal.ZERO, traits.getDefaultValue());
		assertEquals(new BigDecimal("3.3"), traits.plus(new BigDecimal("1.1"), new BigDecimal("2.2")));
	}

	@Test
	public void testDouble() {

		NumericMatrixTraits<Double> traits = new NumericMatrixTraits<>(Arithmetics.DOUBLE);

		assertEquals(0.0, traits.getDefaultValue(), 0.01);
		assertEquals(3.3, traits.plus(1.1, 2.2), 0.01);
	}

	@Test
	public void testFloat() {

		NumericMatrixTraits<Float> traits = new NumericMatrixTraits<>(Arithmetics.FLOAT);

		assertEquals(0.0f, traits.getDefaultValue(), 0.01f);
		assertEquals(3.3f, traits.plus(1.1f, 2.2f), 0.01f);
	}

	@Test
	public void testInteger() {

		NumericMatrixTraits<Integer> traits = new NumericMatrixTraits<>(Arithmetics.INTEGER);

		assertEquals(0, traits.getDefaultValue(), 0);
		assertEquals(3, traits.plus(1, 2), 0);
	}

	@Test
	public void testLong() {

		NumericMatrixTraits<Long> traits = new NumericMatrixTraits<>(Arithmetics.LONG);

		assertEquals(0L, traits.getDefaultValue().longValue());
		assertEquals(3L, traits.plus(1L, 2L).longValue());
	}
}
