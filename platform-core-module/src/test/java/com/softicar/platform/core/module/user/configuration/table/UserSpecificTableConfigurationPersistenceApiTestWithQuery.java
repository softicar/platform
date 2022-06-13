package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHashFactory;
import org.junit.Test;

/**
 * Test for {@link UserSpecificTableConfigurationPersistenceApi} on
 * {@link IDbQuery}.
 *
 * @author Oliver Richers
 */
public class UserSpecificTableConfigurationPersistenceApiTestWithQuery extends AbstractUserSpecificTableConfigurationPersistenceApiTest {

	public UserSpecificTableConfigurationPersistenceApiTestWithQuery() {

	}

	@Test
	public void testInsertionWithSqmlTable() {

		var query = IUserSpecificTableConfigurationPersistenceTestQuery.FACTORY.createQuery();
		var queryTableIdentifierHash = query.getIdentifier().getHash();
		var queryColumnTitlesHash = new EmfDataTableColumnTitlesHashFactory()
			.createHashFromColumns(
				IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN,
				IUserSpecificTableConfigurationPersistenceTestQuery.EMAIL_ADDRESS_COLUMN);

		setNodeSupplier(() -> new EmfDataTableDivBuilder<>(query).build());

		interactor//
			.openConfiguration()
			.clickApply();

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertTableIdentifierHash(queryTableIdentifierHash)
			.assertUser(CurrentUser.get())
			.assertColumnTitlesHash(queryColumnTitlesHash)
			.assertSerialization("""
					{"columnTitlesHash":"%s","hiddenColumnIndexes":[],"columnPositions":[0,1],"columnOrderBys":[],"pageSize":20}
					""".formatted(queryColumnTitlesHash).trim())
			.assertNoMoreRecords();
	}

	@Test
	public void testInsertionWithSqmlTableAndConcealedColumn() {

		// TODO

		var query = IUserSpecificTableConfigurationPersistenceTestQuery.FACTORY.createQuery();
		var queryTableIdentifierHash = query.getIdentifier().getHash();
//		var queryColumnTitlesHash = new EmfDataTableColumnTitlesHashFactory()
//				.createHashFromColumns(
//					IUserSpecificTableConfigurationPersistenceTestQuery.LOGIN_NAME_COLUMN,
//					IUserSpecificTableConfigurationPersistenceTestQuery.EMAIL_ADDRESS_COLUMN);

		var dataTableDiv = new EmfDataTableDivBuilder<>(query)//
			.setConcealed(IUserSpecificTableConfigurationPersistenceTestQuery.EMAIL_ADDRESS_COLUMN, true)
			.build();
		setNodeSupplier(() -> dataTableDiv);

		interactor//
			.openConfiguration()
			.clickApply();

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertTableIdentifierHash(queryTableIdentifierHash)
			.assertUser(CurrentUser.get())
			.assertColumnTitlesHash("")
			.assertSerialization("")
			.assertNoMoreRecords();
	}
}
