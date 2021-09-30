package com.softicar.platform.ajax.upload;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.upload.AjaxUploadTestForm.UploadData;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.dom.elements.DomAnchor;
import com.softicar.platform.dom.elements.DomFileInput;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import org.junit.Test;

public class AjaxUploadTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final byte[] DATA = { 21, 0, -20, 12 };
	private final AjaxUploadTestForm testForm;
	private final DomFileInput fileInput;
	private final DomAnchor submitAnchor;

	public AjaxUploadTest() {

		this.testForm = openTestNode(AjaxUploadTestForm::new);
		this.fileInput = testForm.getFileInput();
		this.submitAnchor = testForm.getSubmitAnchor();
	}

	@Test
	public void test() throws IOException {

		File file = File.createTempFile("test-", ".dat");
		try {
			// prepare file content
			writeDataToFile(file, DATA);

			// upload file
			send(fileInput, file.getAbsolutePath());
			click(submitAnchor);
			waitForServer();

			// assert upload finished
			Collection<UploadData> uploads = testForm.getUploads();
			assertEquals(1, uploads.size());

			// assert correct upload data
			UploadData upload = uploads.iterator().next();
			assertEquals(file.getName(), upload.getFilename());
			assertArrayEquals(DATA, upload.getContent());
		} finally {
			file.delete();
		}
	}

	private void writeDataToFile(File file, byte[] data) throws IOException {

		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			StreamUtils.copy(new ByteArrayInputStream(data), outputStream);
		}
	}
}
