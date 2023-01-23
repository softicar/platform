package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.core.module.email.converter.EmailToPdfConverterTestFiles;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import java.io.IOException;
import java.util.Optional;
import org.junit.Test;

public class StoredFileConverterTest extends AbstractCoreTest {

	@Test
	public void testFromEmailToPdfBytesWithEmlFile() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = file.convert().fromEmailToPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testFromEmailToPdfBytesWithEmlFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = file.convert().fromEmailToPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testFromEmailToPdfBytesWithEmlFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = file.convert().fromEmailToPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testFromEmailToPdfBytesWithMsgFile() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = file.convert().fromEmailToPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testFromEmailToPdfBytesWithMsgFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = file.convert().fromEmailToPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testFromEmailToPdfBytesWithMsgFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = file.convert().fromEmailToPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testFromEmailToPdfBytesWithUnknownFile() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = file.convert().fromEmailToPdfBytes();
		assertFalse(pdfBytes.isPresent());
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
}
