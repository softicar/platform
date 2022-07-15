package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;
import org.junit.Test;

/**
 * Test for {@link UserSpecificTableConfigurationPersistenceApi} on
 * {@link IDbQuery}.
 *
 * @author Oliver Richers
 */
public class UserSpecificTableConfigurationPersistenceApiWithQueryTest extends AbstractUserSpecificTableConfigurationPersistenceApiTest
		implements IEmfTestEngineMethods {

	private IUserSpecificTableConfigurationPersistenceTestQuery query;

	public UserSpecificTableConfigurationPersistenceApiWithQueryTest() {

		this.query = null;
	}

	@Test
	public void testInsertionWithQuery() {

		setupQuery(false);

		var expectedColumnTitlesHash = getColumnTitlesHash(//
			IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN,
			IUserSpecificTableConfigurationPersistenceTestQuery.EMAIL_ADDRESS_COLUMN);

		setNodeSupplier(() -> new EmfDataTableDivBuilder<>(query).build());

		interactor//
			.openConfiguration()
			.clickApply();

		assertOneConfiguration(expectedColumnTitlesHash, """
				{
				"columnTitlesHash":"%s",
				"hiddenColumnIndexes":[],
				"columnPositions":[0,1],
				"columnOrderBys":[],
				"pageSize":20
				}
				""");
	}

	@Test
	public void testInsertionWithQueryAndConcealedColumn() {

		setupQuery(false);

		var expectedColumnTitlesHash = getColumnTitlesHash(//
			IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN);

		setNodeSupplier(() -> {
			return new EmfDataTableDivBuilder<>(query)//
				.setConcealed(IUserSpecificTableConfigurationPersistenceTestQuery.EMAIL_ADDRESS_COLUMN, true)
				.addColumnMarker(
					IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN,
					IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN)
				.build();
		});

		findEmfDataTable().clickOrderByButton(IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN);

		assertOneConfiguration(expectedColumnTitlesHash, """
				{
				"columnTitlesHash":"%s",
				"hiddenColumnIndexes":[],
				"columnPositions":[0],
				"columnOrderBys":[{"columnIndex":0,"direction":"ASCENDING"}],
				"pageSize":20
				}
				""");

		findEmfDataTable().clickOrderByButton(IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN);

		assertOneConfiguration(expectedColumnTitlesHash, """
				{
				"columnTitlesHash":"%s",
				"hiddenColumnIndexes":[],
				"columnPositions":[0],
				"columnOrderBys":[{"columnIndex":0,"direction":"DESCENDING"}],
				"pageSize":20
				}
				""");
	}

	@Test
	public void testInsertionWithQueryAndAdditionalColumn() {

		setupQuery(true);

		var expectedColumnTitlesHash = getColumnTitlesHash(//
			IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN,
			IUserSpecificTableConfigurationPersistenceTestQuery.EMAIL_ADDRESS_COLUMN,
			IUserSpecificTableConfigurationPersistenceTestQuery.LAST_NAME_COLUMN);

		setNodeSupplier(() -> new EmfDataTableDivBuilder<>(query).build());

		interactor//
			.openConfiguration()
			.clickApply();

		assertOneConfiguration(expectedColumnTitlesHash, """
				{
				"columnTitlesHash":"%s",
				"hiddenColumnIndexes":[],
				"columnPositions":[0,1,2],
				"columnOrderBys":[],
				"pageSize":20
				}
				""");
	}

	private void setupQuery(boolean selectLastName) {

		this.query = IUserSpecificTableConfigurationPersistenceTestQuery.FACTORY.createQuery().setSelectLastName(selectLastName);
		this.expectedTableIdentifierHash = query.getIdentifier().getHash();
	}
}
