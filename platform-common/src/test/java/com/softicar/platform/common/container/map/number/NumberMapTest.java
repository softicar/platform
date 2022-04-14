package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import com.softicar.platform.common.testing.AbstractTest;
import java.math.BigDecimal;
import org.junit.Test;

public class NumberMapTest extends AbstractTest {

	@Test
	public void testPutBigDecimal() {

		NumberMap<String, BigDecimal> numberMap = prepareBigDecimalMap();
		assertEquals(new BigDecimal("123"), numberMap.get("foo"));
	}

	@Test
	public void testPutBigDecimalWithNull() {

		NumberMap<String, BigDecimal> numberMap = prepareBigDecimalMap();
		numberMap.put("foo", null);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutBigDecimalWithZero() {

		NumberMap<String, BigDecimal> numberMap = prepareBigDecimalMap();
		numberMap.put("foo", new BigDecimal("0"));
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutBigDecimalWithZeroDotZero() {

		NumberMap<String, BigDecimal> numberMap = prepareBigDecimalMap();
		numberMap.put("foo", new BigDecimal("0.0"));
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutDouble() {

		NumberMap<String, Double> numberMap = prepareDoubleMap();
		assertEquals(Double.valueOf(123d), numberMap.get("foo"));
	}

	@Test
	public void testPutDoubleWithNull() {

		NumberMap<String, Double> numberMap = prepareDoubleMap();
		numberMap.put("foo", null);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutDoubleWithZero() {

		NumberMap<String, Double> numberMap = prepareDoubleMap();
		numberMap.put("foo", 0d);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutDoubleWithZeroDotZero() {

		NumberMap<String, Double> numberMap = prepareDoubleMap();
		numberMap.put("foo", Double.valueOf("0.000"));
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutFloat() {

		NumberMap<String, Float> numberMap = prepareFloatMap();
		assertEquals(Float.valueOf(123f), numberMap.get("foo"));
	}

	@Test
	public void testPutFloatWithNull() {

		NumberMap<String, Float> numberMap = prepareFloatMap();
		numberMap.put("foo", null);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutFloatWithZero() {

		NumberMap<String, Float> numberMap = prepareFloatMap();
		numberMap.put("foo", 0f);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutFloatWithZeroDotZero() {

		NumberMap<String, Float> numberMap = prepareFloatMap();
		numberMap.put("foo", Float.valueOf("0.000"));
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutInteger() {

		NumberMap<String, Integer> numberMap = prepareIntegerMap();
		assertEquals(Integer.valueOf(123), numberMap.get("foo"));
	}

	@Test
	public void testPutIntegerWithNull() {

		NumberMap<String, Integer> numberMap = prepareIntegerMap();
		numberMap.put("foo", null);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutIntegerWithZero() {

		NumberMap<String, Integer> numberMap = prepareIntegerMap();
		numberMap.put("foo", 0);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutIntegerWithZeroDotZero() {

		NumberMap<String, Integer> numberMap = prepareIntegerMap();
		numberMap.put("foo", Integer.valueOf("0"));
		assertFalse(numberMap.containsKey("foo"));
	}

	// ----

	@Test
	public void testPutLong() {

		NumberMap<String, Long> numberMap = prepareLongMap();
		assertEquals(Long.valueOf(123), numberMap.get("foo"));
	}

	@Test
	public void testPutLongWithNull() {

		NumberMap<String, Long> numberMap = prepareLongMap();
		numberMap.put("foo", null);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutLongWithZero() {

		NumberMap<String, Long> numberMap = prepareLongMap();
		numberMap.put("foo", 0l);
		assertFalse(numberMap.containsKey("foo"));
	}

	@Test
	public void testPutLongWithZeroDotZero() {

		NumberMap<String, Long> numberMap = prepareLongMap();
		numberMap.put("foo", Long.valueOf("0"));
		assertFalse(numberMap.containsKey("foo"));
	}

	// ----

	private NumberMap<String, BigDecimal> prepareBigDecimalMap() {

		NumberMap<String, BigDecimal> numberMap = new NumberMap<>(Arithmetics.BIG_DECIMAL);
		numberMap.put("foo", new BigDecimal("123"));
		return numberMap;
	}

	private NumberMap<String, Double> prepareDoubleMap() {

		NumberMap<String, Double> numberMap = new NumberMap<>(Arithmetics.DOUBLE);
		numberMap.put("foo", 123d);
		return numberMap;
	}

	private NumberMap<String, Float> prepareFloatMap() {

		NumberMap<String, Float> numberMap = new NumberMap<>(Arithmetics.FLOAT);
		numberMap.put("foo", 123f);
		return numberMap;
	}

	private NumberMap<String, Integer> prepareIntegerMap() {

		NumberMap<String, Integer> numberMap = new NumberMap<>(Arithmetics.INTEGER);
		numberMap.put("foo", 123);
		return numberMap;
	}

	private NumberMap<String, Long> prepareLongMap() {

		NumberMap<String, Long> numberMap = new NumberMap<>(Arithmetics.LONG);
		numberMap.put("foo", 123l);
		return numberMap;
	}
}
