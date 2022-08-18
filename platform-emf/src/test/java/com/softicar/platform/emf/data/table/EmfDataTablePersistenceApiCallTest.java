package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHasher;
import com.softicar.platform.emf.persistence.CurrentEmfPersistenceApi;
import com.softicar.platform.emf.persistence.EmfPersistentTableConfiguration;
import com.softicar.platform.emf.persistence.IEmfPersistenceApi;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

/**
 * Ensures that calls to {@link CurrentEmfPersistenceApi} take place when
 * expected.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTablePersistenceApiCallTest extends AbstractEmfDataTableTest {

	private final TestPersistenceApi persistenceApi;

	public EmfDataTablePersistenceApiCallTest() {

		this.persistenceApi = new TestPersistenceApi();
		CurrentEmfPersistenceApi.set(persistenceApi);
	}

	@Test
	public void testApiCallsAfterClickOnOrderButton() {

		setup("foo");

		findNodes(EmfTestMarker.DATA_TABLE_ORDER_BY_BUTTON).first().click();

		assertSaveCalls(1);
		assertLoadCalls(1);
	}

	@Test
	public void testApiCallsAfterRepeatedClickOnOrderButton() {

		setup("foo");

		findNodes(EmfTestMarker.DATA_TABLE_ORDER_BY_BUTTON).first().click();
		findNodes(EmfTestMarker.DATA_TABLE_ORDER_BY_BUTTON).first().click();

		assertSaveCalls(2);
		assertLoadCalls(1);
	}

	@Test
	public void testApiCallsAfterClickOnOrderButtonWithoutIdentifier() {

		setup("");

		findNodes(EmfTestMarker.DATA_TABLE_ORDER_BY_BUTTON).first().click();

		assertSaveCalls(0);
		assertLoadCalls(0);
	}

	@Test
	public void testApiCallsAfterClickOnApplyButtonInColumnConfigurationPopup() {

		setup("foo");

		findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_TABLE_CONFIGURATION_BUTTON).click();
		findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_APPLY_BUTTON).click();

		assertSaveCalls(1);
		assertLoadCalls(1);
	}

	@Test
	public void testApiCallsAfterClickOnApplyButtonInColumnConfigurationPopupWithoutIdentifier() {

		setup("");

		findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_TABLE_CONFIGURATION_BUTTON).click();
		findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_APPLY_BUTTON).click();

		assertSaveCalls(0);
		assertLoadCalls(0);
	}

	@Test
	public void testApiCallsAfterInitialization() {

		setup("foo");

		assertSaveCalls(0);
		assertLoadCalls(1);
	}

	@Test
	public void testApiCallsAfterInitializationWithoutIdentifier() {

		setup("");

		assertSaveCalls(0);
		assertLoadCalls(0);
	}

	private void assertSaveCalls(int expectedSaveCount) {

		assertEquals(expectedSaveCount, persistenceApi.getSaveCount());
	}

	private void assertLoadCalls(int expectedLoadCount) {

		assertEquals(expectedLoadCount, persistenceApi.getLoadCount());
	}

	private void setup(String tableIdentifier) {

		setNodeSupplier(createDataTableDivBuilder(() -> List.of(new TestTableRow()))::build);
		getDataTable().setIdentifier(new DataTableIdentifier(tableIdentifier));

		// enforce initialization
		findBody();
	}

	private static class TestPersistenceApi implements IEmfPersistenceApi {

		private int saveCount;
		private int loadCount;

		public TestPersistenceApi() {

			this.saveCount = 0;
			this.loadCount = 0;
		}

		@Override
		public void savePersistentTableConfiguration(EmfDataTablePath dataTablePath, EmfPersistentTableConfiguration configuration) {

			++saveCount;
		}

		@Override
		public Optional<EmfPersistentTableConfiguration> loadPersistentTableConfiguration(EmfDataTablePath dataTablePath,
				EmfDataTableColumnTitlesHasher columnTitlesHasher) {

			++loadCount;
			return Optional.empty();
		}

		@Override
		public String toString() {

			return "TestPersistenceApi [saveCount=" + saveCount + ", loadCount=" + loadCount + "]";
		}

		public int getSaveCount() {

			return saveCount;
		}

		public int getLoadCount() {

			return loadCount;
		}
	}
}
