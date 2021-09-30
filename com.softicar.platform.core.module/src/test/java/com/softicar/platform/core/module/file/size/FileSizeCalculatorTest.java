package com.softicar.platform.core.module.file.size;

import org.junit.Assert;
import org.junit.Test;

public class FileSizeCalculatorTest extends Assert {

	private static final double DELTA_MILLI = 0.0001;
	private static final double DELTA_MICRO = 0.0000001;
	private static final double DELTA_NANO = 0.0000000001;
	private static final double DELTA_PICO = 0.0000000000001;

	private final FileSizeCalculator calculator = new FileSizeCalculator();

	@Test
	public void testGetAsBytesFromByteScale() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.B);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(123, result);
	}

	@Test
	public void testGetAsBytesFromKiloByteScale() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(123l * 1000, result);
	}

	@Test
	public void testGetAsBytesFromKibiByteScale() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(123l * 1024, result);
	}

	@Test
	public void testGetAsBytesFromMegaByteScale() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.MB);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(123l * 1000 * 1000, result);
	}

	@Test
	public void testGetAsBytesFromMebiByteScale() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.MIB);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(123l * 1024 * 1024, result);
	}

	@Test
	public void testGetAsBytesFromGigaByteScale() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.GB);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(123l * 1000 * 1000 * 1000, result);
	}

	@Test
	public void testGetAsBytesFromGibiByteScale() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.GIB);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(123l * 1024 * 1024 * 1024, result);
	}

	@Test
	public void testGetAsBytesWithZero() {

		IFileSize fileSize = new FileSize(0);
		long result = calculator.getAsBytes(fileSize);
		assertEquals(0, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAsBytesThrowsExceptionWithNullArgument() {

		calculator.getAsBytes(null);
	}

	@Test
	public void testGetAsScaleFromBytesToBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.B);
		double result = calculator.getAsScale(fileSize, FileSizeScale.B);
		assertEquals(123d, result, DELTA_MILLI);
	}

	@Test
	public void testGetAsScaleFromBytesToKiloBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.B);
		double result = calculator.getAsScale(fileSize, FileSizeScale.KB);
		assertEquals(123d / 1000, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromBytesToKibiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.B);
		double result = calculator.getAsScale(fileSize, FileSizeScale.KIB);
		assertEquals(123d / 1024, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromBytesToMegaBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.B);
		double result = calculator.getAsScale(fileSize, FileSizeScale.MB);
		assertEquals(123d / 1000 / 1000, result, DELTA_NANO);
	}

	@Test
	public void testGetAsScaleFromBytesToMebiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.B);
		double result = calculator.getAsScale(fileSize, FileSizeScale.MIB);
		assertEquals(123d / 1024 / 1024, result, DELTA_NANO);
	}

	@Test
	public void testGetAsScaleFromKiloBytesToBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.B);
		assertEquals(123d * 1000, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromKiloBytesToKiloBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.KB);
		assertEquals(123d, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromKiloBytesToKibiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.KIB);
		assertEquals(123d * 1000 / 1024, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromKiloBytesToMegaBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.MB);
		assertEquals(123d / 1000, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromKiloBytesToMebiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.MIB);
		assertEquals(123d * 1000 / 1024 / 1024, result, DELTA_NANO);
	}

	@Test
	public void testGetAsScaleFromKiloBytesToGigaBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.GB);
		assertEquals(123d / 1000 / 1000, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromKiloBytesToGibiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.GIB);
		assertEquals(123d * 1000 / 1024 / 1024 / 1024, result, DELTA_PICO);
	}

	@Test
	public void testGetAsScaleFromKibiBytesToBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.B);
		assertEquals(123d * 1024, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromKibiBytesToKiloBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.KB);
		assertEquals(123d * 1024 / 1000, result, DELTA_MICRO);
	}

	@Test
	public void testGetAsScaleFromKibiBytesToKibiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.KIB);
		assertEquals(123d, result, DELTA_MILLI);
	}

	@Test
	public void testGetAsScaleFromKibiBytesToMegaBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.MB);
		assertEquals(123d * 1024 / 1000 / 1000, result, DELTA_NANO);
	}

	@Test
	public void testGetAsScaleFromKibiBytesToMebiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.MIB);
		assertEquals(123d / 1024, result, DELTA_NANO);
	}

	@Test
	public void testGetAsScaleFromKibiBytesToGigaBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.GB);
		assertEquals(123d * 1024 / 1000 / 1000 / 1000, result, DELTA_PICO);
	}

	@Test
	public void testGetAsScaleFromKibiBytesToGibiBytes() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		double result = calculator.getAsScale(fileSize, FileSizeScale.GIB);
		assertEquals(123d / 1024 / 1024, result, DELTA_PICO);
	}
}
