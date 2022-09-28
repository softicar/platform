package com.softicar.platform.integration.database.structure.version;

public class PlatformDatabaseStructureVersionMigrationsTest extends AbstractDatabaseStructureVersionMigrationsTest {

	private static final int RECENT_MIGRATIONS_TO_TEST = 3;

	public PlatformDatabaseStructureVersionMigrationsTest() {

		super(PlatformDatabaseStructureVersionResource.class, RECENT_MIGRATIONS_TO_TEST);
	}
}
