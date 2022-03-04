package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbCredentials;
import com.softicar.platform.core.module.file.smb.testing.SmbTestServerConfiguration;
import com.softicar.platform.core.module.file.smb.testing.SmbTestServerController;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import jcifs.CIFSContext;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Provides an ephemeral, Docker-based SMB server to extending JUnit test
 * classes.
 *
 * @author Alexander Schmidt
 */
public abstract class AbtractJcifsNgSmbTest extends AbstractTest {

	private static final String TEST_PASSWORD = "testpassword";
	private static final String TEST_SHARE = "testshare";
	private static final String TEST_USER = "testuser";
	private static final String TEST_WORKGROUP = "WORKGROUP";
	private static final SmbTestServerController SERVER_CONTROLLER = createController();

	protected SmbCredentials credentials;
	protected DayTime startTime;
	protected String shareUrl;
	protected String shareUrlSlash;
	protected String someEntryUrl;
	protected String someEntryUrlSlash;
	protected String someFileUrl;
	protected String someFileUrlSlash;
	protected String someDirUrl;
	protected String someDirUrlSlash;

	// TODO remove - debug only
	protected final String uuid;
	protected long start;

	public AbtractJcifsNgSmbTest() {

		LogLevel.ERROR.set();

		// TODO remove - debug only
		this.uuid = UUID.randomUUID().toString();
		this.start = System.currentTimeMillis();
	}

	@BeforeClass
	public static void beforeClass() {

		SERVER_CONTROLLER.startup();
		SERVER_CONTROLLER.registerRuntimeShutdownHook();
	}

	@AfterClass
	public static void afterClass() {

		SERVER_CONTROLLER.shutdown();
	}

	@Before
	public void beforeTest() {

		this.credentials = new SmbCredentials(TEST_WORKGROUP, TEST_USER, TEST_PASSWORD);
		this.startTime = DayTime.now();
		this.shareUrl = "smb://" + SERVER_CONTROLLER.getServerIpAddress() + "/" + TEST_SHARE;
		this.shareUrlSlash = shareUrl.concat("/");
		this.someEntryUrl = shareUrl.concat("/someEntry");
		this.someEntryUrlSlash = someEntryUrl.concat("/");
		this.someFileUrl = shareUrl.concat("/someFile");
		this.someFileUrlSlash = someFileUrl.concat("/");
		this.someDirUrl = shareUrl.concat("/someDir");
		this.someDirUrlSlash = someDirUrl.concat("/");

		flushShareRoot();
	}

	protected ISmbEntry entry(String url) {

		return new JcifsNgSmbEntry(url, createContext());
	}

	protected ISmbFile file(String url) {

		return new JcifsNgSmbFile(url, createContext());
	}

	protected ISmbDirectory directory(String url) {

		return new JcifsNgSmbDirectory(url, createContext(), uuid, start);
	}

	protected CIFSContext createContext() {

		Log.ferror("%s: %sms: createContext: CAL", uuid, System.currentTimeMillis() - start);
		CIFSContext context = JcifsNgSmbCifsContextFactory.create(credentials);
		Log.ferror("%s: %sms: createContext: RET", uuid, System.currentTimeMillis() - start);
		return context;
	}

	/**
	 * Creates the following directories and files on the share:
	 *
	 * <pre>
	 * someDir          // directory
	 * |- emptySubDir   // directory
	 * |- subDir        // directory
	 * |  '- deepFile   // empty file
	 * '- otherFile     // empty file
	 * '- someFile      // empty file
	 * </pre>
	 */
	protected void populateShareWithSimpleStructure() {

		directory(someDirUrl).makeDirectories();
		directory(someDirUrl).getFile("otherFile").touch();
		directory(someDirUrl).getFile("someFile").touch();
		directory(someDirUrl).getDirectory("emptySubDir").makeDirectories();
		directory(someDirUrl).getDirectory("subDir").makeDirectories();
		directory(someDirUrlSlash.concat("subDir")).getFile("deepFile").touch();
	}

	protected ISmbFile writeFile(String url, String content) {

		ISmbFile file = file(url);
		try (var outputStream = file.createOutputStream()) {
			outputStream.write(content.getBytes(Charsets.UTF8));
			return file;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	protected String readFile(String url) {

		return readFile(file(url));
	}

	protected String readFile(ISmbFile file) {

		try (var inputStream = file.createInputStream()) {
			return StreamUtils.toString(inputStream, Charsets.UTF8);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	protected static void assertYear1970(DayTime actual) {

		assertEquals(DayTime.get1970().getDay().getYear(), actual.getDay().getYear());
	}

	protected static void assertSmbServerUrl(String url) {

		assertTrue(url.matches("smb://[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}/"));
	}

	private void flushShareRoot() {

		Log.ferror("%s: %sms: flushShareRoot: CAL", uuid, System.currentTimeMillis() - start);

		try {
			ISmbDirectory directory = directory(shareUrl);
			Log.ferror("%s: %sms: flushShareRoot: got upper dir", uuid, System.currentTimeMillis() - start);

			List<ISmbEntry> entries = directory.listEntries();
			Log.ferror("%s: %sms: flushShareRoot: got upper entries", uuid, System.currentTimeMillis() - start);

			entries.forEach(ISmbEntry::delete);
			Log.ferror("%s: %sms: flushShareRoot: deleted them", uuid, System.currentTimeMillis() - start);
		} catch (Exception exception) {
			throw new SofticarDeveloperException(exception, "Failed to flush the share.");
		}

		try {
			ISmbDirectory directory = directory(shareUrl);
			Log.ferror("%s: %sms: flushShareRoot: got lower dir", uuid, System.currentTimeMillis() - start);

			List<ISmbEntry> entries = directory.listEntries();
			Log.ferror("%s: %sms: flushShareRoot: got lower entries", uuid, System.currentTimeMillis() - start);

			assertTrue(//
				"Expected an empty share before the execution of a test method. Encountered %s leftover entries from a previous execution: [%s]"
					.formatted(entries.size(), Imploder.implode(entries, ISmbEntry::getName, ", ")),
				entries.isEmpty());
		} catch (Exception exception) {
			throw new SofticarDeveloperException(exception, "Failed to list the entries in the share root.");
		}

		Log.ferror("%s: %sms: flushShareRoot: RET", uuid, System.currentTimeMillis() - start);
	}

	private static SmbTestServerController createController() {

		var configuration = new SmbTestServerConfiguration(TEST_PASSWORD, TEST_SHARE, TEST_USER);
		return new SmbTestServerController(configuration);
	}
}
