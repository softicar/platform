package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.testing.AbstractTest;
import java.io.IOException;
import jcifs.CIFSContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Ensures that SMB connections also work with a server that accepts nothing
 * older than SMB 3.
 *
 * @author Semsudin Mekanic
 */
public class JcifsNgSmbProtocolVersionTest extends AbstractTest {

	private static final SambaTestingServer SERVER = new SambaTestingServer("SMB3");

	public JcifsNgSmbProtocolVersionTest() {

		LogLevel.ERROR.set();
	}

	@BeforeClass
	public static void beforeClass() {

		SERVER.startup();
	}

	@AfterClass
	public static void afterClass() {

		SERVER.shutdown();
	}

	@Test
	public void testListEntries() {

		var entries = new JcifsNgSmbDirectory(SERVER.getShareUrl(), createContext()).listEntries();

		assertNotNull(entries);
	}

	@Test
	public void testWriteAndReadFile() throws IOException {

		var file = new JcifsNgSmbFile(SERVER.getShareUrl() + "/someFile", createContext());
		try (var outputStream = file.createOutputStream()) {
			outputStream.write("content".getBytes(Charsets.UTF8));
		}
		try (var inputStream = file.createInputStream()) {
			assertEquals("content", StreamUtils.toString(inputStream, Charsets.UTF8));
		} finally {
			file.delete();
		}
	}

	private CIFSContext createContext() {

		return JcifsNgSmbCifsContextFactory.create(SERVER.getCredentials());
	}
}
