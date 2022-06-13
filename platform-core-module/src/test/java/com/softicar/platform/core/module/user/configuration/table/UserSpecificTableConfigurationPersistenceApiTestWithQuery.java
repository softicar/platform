package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import org.junit.Test;

/**
 * Test for {@link UserSpecificTableConfigurationPersistenceApi} on
 * {@link IDbQuery}.
 *
 * @author Oliver Richers
 */
public class UserSpecificTableConfigurationPersistenceApiTestWithQuery extends AbstractUserSpecificTableConfigurationPersistenceApiTest {

	private final IUserSpecificTableConfigurationPersistenceTestQuery query;

	public UserSpecificTableConfigurationPersistenceApiTestWithQuery() {

		this.query = IUserSpecificTableConfigurationPersistenceTestQuery.FACTORY.createQuery();
		this.extectedTableIdentifierHash = query.getIdentifier().getHash();
	}

	@Test
	public void testInsertionWithQuery() {

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

		clickOrderByButton(IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN);

		assertOneConfiguration(expectedColumnTitlesHash, """
				{
				"columnTitlesHash":"%s",
				"hiddenColumnIndexes":[],
				"columnPositions":[0],
				"columnOrderBys":[{"columnIndex":0,"direction":"ASCENDING"}],
				"pageSize":20
				}
				""");

		clickOrderByButton(IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN);

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

	private void clickOrderByButton(IStaticObject column) {

		findTable()//
			.findHeaderCell(column)
			.findButton(EmfDataTableDivMarker.ORDER_BY_BUTTON)
			.click();
	}
}
