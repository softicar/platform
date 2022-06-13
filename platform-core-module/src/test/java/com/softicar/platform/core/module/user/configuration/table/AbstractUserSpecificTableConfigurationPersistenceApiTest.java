package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHashFactory;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupAsserter;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupTestInteractor;
import com.softicar.platform.emf.persistence.CurrentEmfPersistenceApi;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for tests of {@link UserSpecificTableConfigurationPersistenceApi}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class AbstractUserSpecificTableConfigurationPersistenceApiTest extends AbstractCoreTest {

	protected final EmfDataTableConfigurationPopupTestInteractor interactor;
	protected final EmfDataTableConfigurationPopupAsserter popupAsserter;
	protected String extectedTableIdentifierHash;

	public AbstractUserSpecificTableConfigurationPersistenceApiTest() {

		CurrentEmfPersistenceApi.set(new UserSpecificTableConfigurationPersistenceApi());
		this.interactor = new EmfDataTableConfigurationPopupTestInteractor(this);
		this.popupAsserter = new EmfDataTableConfigurationPopupAsserter(this);
	}

	protected String getColumnTitlesHash(IDataTableColumn<?, ?>...columns) {

		var titles = List//
			.of(columns)
			.stream()
			.map(IDataTableColumn::getTitle)
			.collect(Collectors.toList());
		return new EmfDataTableColumnTitlesHashFactory().createHash(titles);
	}

	protected List<AGUserSpecificTableConfiguration> loadAllConfigurations() {

		return AGUserSpecificTableConfiguration.TABLE//
			.createSelect()
			.orderBy(AGUserSpecificTableConfiguration.ID)
			.list();
	}

	protected void assertOneConfiguration(String expectedColumnTitlesHash, String expectedConfigurationString) {

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertTableIdentifierHash(extectedTableIdentifierHash)
			.assertUser(CurrentUser.get())
			.assertColumnTitlesHash(expectedColumnTitlesHash)
			.assertSerialization(formatConfigurationString(expectedConfigurationString, expectedColumnTitlesHash))
			.assertNoMoreRecords();
	}

	protected String formatConfigurationString(String configurationString, String columnTitlesHash) {

		return configurationString.replace("\n", "").formatted(columnTitlesHash);
	}
}
