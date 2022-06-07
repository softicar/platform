package com.softicar.platform.ajax.upload;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.io.StreamUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AjaxUploadTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final byte[] DATA = { 21, 0, -20, 12 };
	private File file;

	public AjaxUploadTest() {

		this.file = null;
	}

	@Before
	public void before() throws IOException {

		file = File.createTempFile("test-", ".dat");
	}

	@After
	public void after() {

		file.delete();
	}

	@Test
	public void testBySubmitAnchor() {

		var form = openTestNode(() -> new AjaxUploadTestForm());
		writeDataToFile(file, DATA);

		send(form.getFileInput(), file.getAbsolutePath());
		click(form.getUploadButton());
		waitForServer();

		assertUpload(form);
	}

	@Test
	public void testByChangeEvent() {

		var form = openTestNode(() -> new AjaxUploadTestForm().setupOnChangeTrigger());
		writeDataToFile(file, DATA);

		send(form.getFileInput(), file.getAbsolutePath());
		waitForServer();

		assertUpload(form);
	}

	private void assertUpload(AjaxUploadTestForm form) {

		// assert upload finished
		var uploads = form.getUploads();
		assertEquals(1, uploads.size());

		// assert correct upload data
		var upload = uploads.iterator().next();
		assertEquals(file.getName(), upload.getFilename());
		assertArrayEquals(DATA, upload.getContent());
	}

	private void writeDataToFile(File file, byte[] data) {

		try (var outputStream = new FileOutputStream(file)) {
			StreamUtils.copy(new ByteArrayInputStream(data), outputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
