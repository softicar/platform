package com.softicar.platform.core.module.file.stored.server;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.validation.EmfValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class AGStoredFileServerTest extends AbstractDbTest {

	private AGStoredFileServer primaryServer;

	@Test(expected = EmfValidationException.class)
	public void testPrimaryCannotBeDeactivated() {

		insertServers();
		primaryServer.setActive(false).save();
	}

	@Test
	public void testGetAllActiveWithPrimaryFirst() {

		insertServers();
		List<AGStoredFileServer> servers = AGStoredFileServer.getAllActiveWithPrimaryFirst();
		assertEquals(4, servers.size());
		assertEquals(primaryServer, servers.get(0));
		assertDomains(servers, "primary", "first", "junk1", "junk2");
	}

	@Test
	public void testGetAllActiveWithoutPrimary() {

		insertServers();
		AGCoreModuleInstance.getInstance().setPrimaryFileServer(null).save();
		List<AGStoredFileServer> servers = AGStoredFileServer.getAllActiveWithPrimaryFirst();
		assertEquals(4, servers.size());
		assertEquals(primaryServer, servers.get(1));
		assertDomains(servers, "first", "primary", "junk1", "junk2");
	}

	@Test
	public void testGetAllActiveWithoutAnyServer() {

		List<AGStoredFileServer> servers = AGStoredFileServer.getAllActiveWithPrimaryFirst();
		assertEquals(0, servers.size());
	}

	private void insertServers() {

		insertStoredFileServer("first");
		this.primaryServer = insertStoredFileServer("primary");
		AGCoreModuleInstance.getInstance().setPrimaryFileServer(primaryServer).save();
		insertStoredFileServer("junk1");
		insertStoredFileServer("junk2");
		insertStoredFileServer("inactive").setActive(false).save();
	}

	private AGStoredFileServer insertStoredFileServer(String domain) {

		return new AGStoredFileServer()//
			.setDomain(domain)
			.setPassword("password")
			.setUrl("someUrl")
			.setUsername("username")
			.save();
	}

	private void assertDomains(List<AGStoredFileServer> servers, String...expectedDomains) {

		var domains = servers//
			.stream()
			.map(AGStoredFileServer::getDomain)
			.collect(Collectors.toList());
		assertEquals(Arrays.asList(expectedDomains), domains);
	}
}
