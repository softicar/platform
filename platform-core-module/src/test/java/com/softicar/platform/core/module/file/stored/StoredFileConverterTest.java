package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.core.module.email.converter.EmailToPdfConverterTestFiles;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import org.junit.Test;

public class StoredFileConverterTest extends AbstractCoreTest {

	private final AGStoredFile misclassifiedEmlFile;
	private final AGStoredFile unsupportedFile;

	public StoredFileConverterTest() {

		this.misclassifiedEmlFile = insertStoredFile(//
			"foo.eml",
			MimeType.MESSAGE_RFC822,
			"content of a file that is erroneously stored as EML".getBytes());

		this.unsupportedFile = insertStoredFile(//
			"foo.bar",
			MimeType.APPLICATION_OCTET_STREAM,
			"content of an unsupported file type".getBytes());
	}

	@Test
	public void testToPdfBytesWithEmlFile() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithEmlFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithEmlFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithMsgFile() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithMsgFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithMsgFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithUnidentifiedEmailFile() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertFalse(pdfBytes.isPresent());
	}

	@Test(expected = RuntimeException.class)
	public void testToPdfBytesWithMisclassifiedFile() {

		new StoredFileConverter(misclassifiedEmlFile).toPdfBytes();
	}

	@Test
	public void testToPdfBytesWithUnsupportedFile() {

		Optional<byte[]> pdfBytes = new StoredFileConverter(unsupportedFile).toPdfBytes();
		assertFalse(pdfBytes.isPresent());
	}

	@Test
	public void testIsConvertibleToPdfWithEmlFile() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithEmlFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithEmlFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMsgFile() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMsgFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.MSG_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMsgFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithUnidentifiedEmailFile() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertFalse(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMisclassifiedFile() {

		assertTrue(new StoredFileConverter(misclassifiedEmlFile).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithUnsupportedFile() {

		assertFalse(new StoredFileConverter(unsupportedFile).isConvertibleToPdf());
	}

	private AGStoredFile insertStoredFile(String filename, MimeType mimeType, IResourceSupplier resourceSupplier) {

		try (var inputStream = resourceSupplier.getResource().getResourceAsStream()) {
			var file = insertStoredFile(filename).setContentType(mimeType.getIdentifier()).save();
			file.uploadFileContent(inputStream);
			return file;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private AGStoredFile insertStoredFile(String filename, IMimeType mimeType, byte[] bytes) {

		var file = insertStoredFile(filename).setContentType(mimeType.getIdentifier()).save();
		file.uploadFileContent(new ByteArrayInputStream(bytes));
		return file;
	}
}
