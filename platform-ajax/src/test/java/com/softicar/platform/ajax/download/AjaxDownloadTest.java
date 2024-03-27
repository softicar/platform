package com.softicar.platform.ajax.download;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.MimeType;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Test;

public class AjaxDownloadTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final byte[] DATA = { 21, 0, -20, 12 };
	private final DownloadButton button;

	public AjaxDownloadTest() {

		this.button = openTestNode(DownloadButton::new);
	}

	@Test
	public void test() {

		// trigger download button
		click(button);
		waitForServer();

		// assert resource has correct URL
		String source = getFileDownloadSource().orElseThrow(AssertionError::new);
		assertTrue(source.matches("http.*?resourceId=1"));

		// assert resource has correct content
		try {
			URLConnection connection = URL.of(URI.create(source), null).openConnection();
			connection.setRequestProperty("Cookie", getSessionCookie());
			try (InputStream inputStream = connection.getInputStream()) {
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				StreamUtils.copy(inputStream, buffer);
				assertArrayEquals(DATA, buffer.toByteArray());
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testWithoutSessionCookie() throws IOException {

		// trigger download button
		click(button);
		waitForServer();

		// assert resource has correct URL
		String source = getFileDownloadSource().orElseThrow(AssertionError::new);
		assertTrue(source.matches("http.*?resourceId=1"));

		// assert resource is not available
		try (InputStream inputStream = URL.of(URI.create(source), null).openStream()) {
			fail("unexpected");
		}
	}

	private String getSessionCookie() {

		return testEngine.getOutput().getCookie("JSESSIONID");
	}

	private class DownloadButton extends TestButton {

		public DownloadButton() {

			setClickCallback(this::download);
		}

		private void download() {

			try (OutputStream outputStream = getDomEngine()//
				.createExport()
				.setFilename("foo.dat")
				.setMimeType(MimeType.APPLICATION_OCTET_STREAM)
				.openOutputStream()) {
				outputStream.write(DATA);
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		}
	}
}
