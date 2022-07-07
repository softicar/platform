package com.softicar.platform.core.module.file.stored.server;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.List;
import org.junit.Test;

public class AGStoredFileServerTest extends AbstractDbTest {

	private final AGStoredFileServer primaryServer;

	public AGStoredFileServerTest() {

		insertStoredFileServer("first");
		this.primaryServer = insertStoredFileServer("primary");
		AGCoreModuleInstance.getInstance().setPrimaryFileServer(primaryServer);
	}

	@Test
	public void testGetAllActiveWithPrimaryFirst() {

		insertStoredFileServer("junk1");
		insertStoredFileServer("junk2");
		insertStoredFileServer("inactive").setActive(false).save();
		List<AGStoredFileServer> servers = AGStoredFileServer.getAllActiveWithPrimaryFirst();
		assertEquals(primaryServer, servers.get(0));
		assertEquals(4, servers.size());
	}

	private AGStoredFileServer insertStoredFileServer(String domain) {

		return new AGStoredFileServer()//
			.setDomain(domain)
			.setPassword("password")
			.setUrl("someUrl")
			.setUsername("username")
			.save();
	}
}
