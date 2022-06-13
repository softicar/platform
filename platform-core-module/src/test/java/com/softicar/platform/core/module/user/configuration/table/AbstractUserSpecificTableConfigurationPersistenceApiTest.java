package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupAsserter;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupTestInteractor;
import com.softicar.platform.emf.persistence.CurrentEmfPersistenceApi;
import java.util.List;

/**
 * Base class for tests of {@link UserSpecificTableConfigurationPersistenceApi}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class AbstractUserSpecificTableConfigurationPersistenceApiTest extends AbstractCoreTest {

	protected final EmfDataTableConfigurationPopupTestInteractor interactor;
	protected final EmfDataTableConfigurationPopupAsserter popupAsserter;

	public AbstractUserSpecificTableConfigurationPersistenceApiTest() {

		CurrentEmfPersistenceApi.set(new UserSpecificTableConfigurationPersistenceApi());
		this.interactor = new EmfDataTableConfigurationPopupTestInteractor(this);
		this.popupAsserter = new EmfDataTableConfigurationPopupAsserter(this);
	}

	protected List<AGUserSpecificTableConfiguration> loadAllConfigurations() {

		return AGUserSpecificTableConfiguration.createSelect().orderBy(AGUserSpecificTableConfiguration.ID).list();
	}
}
