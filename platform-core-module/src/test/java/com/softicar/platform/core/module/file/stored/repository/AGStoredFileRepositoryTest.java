package com.softicar.platform.core.module.file.stored.repository;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.validation.EmfValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class AGStoredFileRepositoryTest extends AbstractDbTest {

	private AGStoredFileRepository primaryRepository;

	@Test(expected = EmfValidationException.class)
	public void testPrimaryCannotBeDeactivated() {

		insertRepositories();
		primaryRepository.setActive(false).save();
	}

	@Test
	public void testGetAllActiveWithPrimaryFirst() {

		insertRepositories();
		List<AGStoredFileRepository> repositories = AGStoredFileRepository.getAllActiveWithPrimaryFirst();
		assertEquals(4, repositories.size());
		assertEquals(primaryRepository, repositories.get(0));
		assertDomains(repositories, "primary", "first", "junk1", "junk2");
	}

	@Test
	public void testGetAllActiveWithoutPrimary() {

		insertRepositories();
		AGCoreModuleInstance.getInstance().setPrimaryFileRepository(null).save();
		List<AGStoredFileRepository> repositories = AGStoredFileRepository.getAllActiveWithPrimaryFirst();
		assertEquals(4, repositories.size());
		assertEquals(primaryRepository, repositories.get(1));
		assertDomains(repositories, "first", "primary", "junk1", "junk2");
	}

	@Test
	public void testGetAllActiveWithoutAnyRepository() {

		List<AGStoredFileRepository> repositories = AGStoredFileRepository.getAllActiveWithPrimaryFirst();
		assertEquals(0, repositories.size());
	}

	private void insertRepositories() {

		insertStoredFileRepository("first");
		this.primaryRepository = insertStoredFileRepository("primary");
		AGCoreModuleInstance.getInstance().setPrimaryFileRepository(primaryRepository).save();
		insertStoredFileRepository("junk1");
		insertStoredFileRepository("junk2");
		insertStoredFileRepository("inactive").setActive(false).save();
	}

	private AGStoredFileRepository insertStoredFileRepository(String domain) {

		return new AGStoredFileRepository()//
			.setDomain(domain)
			.setPassword("password")
			.setUrl("someUrl")
			.setUsername("username")
			.save();
	}

	private void assertDomains(List<AGStoredFileRepository> repositories, String...expectedDomains) {

		var domains = repositories//
			.stream()
			.map(AGStoredFileRepository::getDomain)
			.collect(Collectors.toList());
		assertEquals(Arrays.asList(expectedDomains), domains);
	}
}
