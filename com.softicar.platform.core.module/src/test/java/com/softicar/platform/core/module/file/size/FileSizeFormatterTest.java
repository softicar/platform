package com.softicar.platform.core.module.file.size;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: add precision tests?
 */
public class FileSizeFormatterTest extends Assert {

	@Test
	public void testFormatToHumanReadableBaseFromByteToByte() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.B);
		String output = new FileSizeFormatter().formatToHumanReadableBase(fileSize);
		assertEquals("123 B", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromByteToKiloByte() {

		IFileSize fileSize = new FileSize(123456, FileSizeScale.B);
		String output = new FileSizeFormatter().setBaseForByteValues(FileSizeBase.DECIMAL).formatToHumanReadableBase(fileSize);
		assertEquals("123.46 kB", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromByteToKibiByte() {

		IFileSize fileSize = new FileSize(123456, FileSizeScale.B);
		String output = new FileSizeFormatter().setBaseForByteValues(FileSizeBase.BINARY).formatToHumanReadableBase(fileSize);
		assertEquals("120.56 KiB", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromByteToMegaByte() {

		IFileSize fileSize = new FileSize(123456789, FileSizeScale.B);
		String output = new FileSizeFormatter().setBaseForByteValues(FileSizeBase.DECIMAL).formatToHumanReadableBase(fileSize);
		assertEquals("123.46 MB", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromByteToMebiByte() {

		IFileSize fileSize = new FileSize(123456789, FileSizeScale.B);
		String output = new FileSizeFormatter().setBaseForByteValues(FileSizeBase.BINARY).formatToHumanReadableBase(fileSize);
		assertEquals("117.74 MiB", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromKiloByteToKiloByte() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KB);
		String output = new FileSizeFormatter().formatToHumanReadableBase(fileSize);
		assertEquals("123 kB", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromKiloByteToMegaByte() {

		IFileSize fileSize = new FileSize(123456, FileSizeScale.KB);
		String output = new FileSizeFormatter().formatToHumanReadableBase(fileSize);
		assertEquals("123.46 MB", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromKibiByteToKibiByte() {

		IFileSize fileSize = new FileSize(123, FileSizeScale.KIB);
		String output = new FileSizeFormatter().formatToHumanReadableBase(fileSize);
		assertEquals("123 KiB", output);
	}

	@Test
	public void testFormatToHumanReadableBaseFromKibiByteToMebiByte() {

		IFileSize fileSize = new FileSize(123456, FileSizeScale.KIB);
		String output = new FileSizeFormatter().formatToHumanReadableBase(fileSize);
		assertEquals("120.56 MiB", output);
	}
}
