package com.softicar.platform.integration.database.structure.version;

public class PlatformDatabaseStructureVersionMigrationsTest extends AbstractDatabaseStructureVersionMigrationsTest {

	private static final int RECENT_PLATFORM_MIGRATIONS_TO_TEST = 2;

	public PlatformDatabaseStructureVersionMigrationsTest() {

		super(PlatformDatabaseStructureVersionResource.class, RECENT_PLATFORM_MIGRATIONS_TO_TEST);
	}
}
