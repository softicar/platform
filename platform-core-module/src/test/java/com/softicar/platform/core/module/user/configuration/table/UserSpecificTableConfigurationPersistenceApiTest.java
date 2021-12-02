package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.io.serialization.json.JsonBlock;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupAsserter;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupTestInteractor;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationTableAsserter;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.persistence.CurrentEmfPersistenceApi;
import java.util.List;
import org.junit.Test;

/**
 * Graybox UI test for {@link UserSpecificTableConfigurationPersistenceApi}.
 * <p>
 * Ensures that:
 * <ul>
 * <li>{@link AGUserSpecificTableConfiguration} records are written as expected
 * after interacting with the column configuration UI</li>
 * <li>Previously-saved {@link AGUserSpecificTableConfiguration} records are
 * loaded and applied as expected when the respective table is displayed</li>
 * </ul>
 *
 * @author Alexander Schmidt
 */
public class UserSpecificTableConfigurationPersistenceApiTest extends AbstractCoreTest {

	private static final String TEST_OBJECT_JSON_DEFAULT = new JsonBlock()//
		.add("columnTitlesHash", TestObjectTable.COLUMN_TITLES_HASH)
		.addAll("hiddenColumnIndexes")
		.addAll("columnPositions", 0, 1, 2, 3)
		.addAll("columnOrderBys")
		.add("pageSize", 20)
		.toString();

	private static final String TEST_OBJECT_JSON_ALTERED = new JsonBlock()//
		.add("columnTitlesHash", TestObjectTable.COLUMN_TITLES_HASH)
		.addAll("hiddenColumnIndexes", 3)
		.addAll("columnPositions", 0, 2, 1, 3)
		.addAll(//
			"columnOrderBys",
			new JsonBlock().add("columnIndex", 2).add("direction", "ASCENDING"),
			new JsonBlock().add("columnIndex", 0).add("direction", "DESCENDING"))
		.add("pageSize", 10)
		.toString();

	private final EmfDataTableConfigurationPopupTestInteractor interactor;
	private final EmfDataTableConfigurationPopupAsserter popupAsserter;
	private final EmfDataTableConfigurationTableAsserter tableAsserter;

	public UserSpecificTableConfigurationPersistenceApiTest() {

		CurrentEmfPersistenceApi.set(new UserSpecificTableConfigurationPersistenceApi());
		insertTestRecords();
		this.interactor = new EmfDataTableConfigurationPopupTestInteractor(this);
		this.popupAsserter = new EmfDataTableConfigurationPopupAsserter(this);
		this.tableAsserter = new EmfDataTableConfigurationTableAsserter(this, TestObject.TABLE);
	}

	@Test
	public void testInsertionWithManagementTable() {

		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor//
			.openConfiguration()
			.clickApply();

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertTableIdentifierHash(TestObjectTable.TABLE_IDENTIFIER_HASH)
			.assertUser(CurrentUser.get())
			.assertColumnTitlesHash(TestObjectTable.COLUMN_TITLES_HASH)
			.assertSerialization(TEST_OBJECT_JSON_DEFAULT)
			.assertNoMoreRecords();
	}

	@Test
	public void testUpdateWithManagementTable() {

		TestObjectTable.insertConfiguration(TEST_OBJECT_JSON_DEFAULT);
		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor//
			.openConfiguration()
			.enterPageSize("10")
			.toggle("Date")
			.moveDown("Name")
			.selectOrderDirection("ID", OrderDirection.DESCENDING)
			.selectOrderPriority("ID", 2)
			.selectOrderDirection("Amount", OrderDirection.ASCENDING)
			.selectOrderPriority("Amount", 1)
			.clickApply();

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertTableIdentifierHash(TestObjectTable.TABLE_IDENTIFIER_HASH)
			.assertUser(CurrentUser.get())
			.assertColumnTitlesHash(TestObjectTable.COLUMN_TITLES_HASH)
			.assertSerialization(TEST_OBJECT_JSON_ALTERED)
			.assertNoMoreRecords();
	}

	@Test
	public void testLoadingWithManagementTable() {

		TestObjectTable.insertConfiguration(TEST_OBJECT_JSON_ALTERED);
		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor.openConfiguration();

		popupAsserter//
			.assertRow(true, "ID", OrderDirection.DESCENDING, 2)
			.assertRow(true, "Amount", OrderDirection.ASCENDING, 1)
			.assertRow(true, "Name")
			.assertRow(false, "Date")
			.assertNoMoreRows();

		tableAsserter//
			.assertDisplayedColumns("ID", "Amount", "Name");

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertNoMoreRecords();
	}

	@Test
	public void testLoadingWithManagementTableAndWithoutConfigurationRecord() {

		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor.openConfiguration();

		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Date")
			.assertNoMoreRows();

		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Date");

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.assertNoMoreRecords();
	}

	@Test
	public void testLoadingWithManagementTableAndWithConfigurationRecordOfOtherUser() {

		TestObjectTable.insertConfiguration(TEST_OBJECT_JSON_ALTERED);
		setNodeSupplier(TestObjectTable::createManagementDiv);
		CurrentUser.set(insertUser("otherUser"));

		interactor.openConfiguration();

		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Date")
			.assertNoMoreRows();

		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Date");

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertNoMoreRecords();
	}

	@Test
	public void testLoadingWithManagementTableAndTableIdentifierHashMismatch() {

		TestObjectTable//
			.insertConfiguration(TEST_OBJECT_JSON_ALTERED)
			.setTableIdentifierHash("mismatching-identifier-hash")
			.save();
		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor.openConfiguration();

		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Date")
			.assertNoMoreRows();

		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Date");

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertNoMoreRecords();
	}

	@Test
	public void testLoadingWithManagementTableAndTableColumnTitlesHashMismatch() {

		TestObjectTable//
			.insertConfiguration(TEST_OBJECT_JSON_ALTERED)
			.setColumnTitlesHash("mismatching-column-hash")
			.save();
		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor.openConfiguration();

		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Date")
			.assertNoMoreRows();

		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Date");

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.assertNoMoreRecords();
	}

	@Test
	public void testResetAndApplyWithManagementTable() {

		TestObjectTable.insertConfiguration(TEST_OBJECT_JSON_ALTERED);
		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor//
			.openConfiguration()
			.clickReset()
			.clickApply()
			.openConfiguration();

		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Date")
			.assertNoMoreRows();

		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Date");

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertTableIdentifierHash(TestObjectTable.TABLE_IDENTIFIER_HASH)
			.assertUser(CurrentUser.get())
			.assertColumnTitlesHash(TestObjectTable.COLUMN_TITLES_HASH)
			.assertSerialization(TEST_OBJECT_JSON_DEFAULT)
			.assertNoMoreRecords();
	}

	@Test
	public void testResetAndCancelWithManagementTable() {

		TestObjectTable.insertConfiguration(TEST_OBJECT_JSON_ALTERED);
		setNodeSupplier(TestObjectTable::createManagementDiv);

		interactor//
			.openConfiguration()
			.clickReset()
			.clickCancel()
			.openConfiguration();

		popupAsserter//
			.assertRow(true, "ID", OrderDirection.DESCENDING, 2)
			.assertRow(true, "Amount", OrderDirection.ASCENDING, 1)
			.assertRow(true, "Name")
			.assertRow(false, "Date")
			.assertNoMoreRows();

		tableAsserter//
			.assertDisplayedColumns("ID", "Amount", "Name");

		new UserSpecificTableConfigurationRecordAsserter(loadAllConfigurations())//
			.nextRecord()
			.assertTableIdentifierHash(TestObjectTable.TABLE_IDENTIFIER_HASH)
			.assertUser(CurrentUser.get())
			.assertColumnTitlesHash(TestObjectTable.COLUMN_TITLES_HASH)
			.assertSerialization(TEST_OBJECT_JSON_ALTERED)
			.assertNoMoreRecords();
	}

	private void insertTestRecords() {

		new TestObject().setName("foo").setAmount(10).setDate(Day.fromYMD(2021, 1, 1)).save();
		new TestObject().setName("bar").setAmount(20).setDate(Day.fromYMD(2021, 2, 1)).save();
		new TestObject().setName("baz").setAmount(30).setDate(Day.fromYMD(2021, 3, 1)).save();
	}

	private List<AGUserSpecificTableConfiguration> loadAllConfigurations() {

		return AGUserSpecificTableConfiguration.createSelect().orderBy(AGUserSpecificTableConfiguration.ID).list();
	}

	@SuppressWarnings("all")
	public static class TestObject extends AbstractDbObject<TestObject> implements IEmfObject<TestObject> {

		// -------------------------------- STATIC CONSTANTS -------------------------------- //

		// @formatter:off
		private static final DbObjectTableBuilder<TestObject, TestObject> BUILDER = new DbObjectTableBuilder<>("Core", "TestObject", TestObject::new, TestObject.class);
		static {
			BUILDER.setTitle(IDisplayString.create("TestObject"));
			BUILDER.setPluralTitle(IDisplayString.create("TestObjects"));
		}

		public static final IDbIdField<TestObject> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(IDisplayString.create("ID"));
		public static final IDbStringField<TestObject> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(IDisplayString.create("Name")).setNullable().setDefault(null).setMaximumLength(255);
		public static final IDbIntegerField<TestObject> AMOUNT = BUILDER.addIntegerField("amount", o->o.m_amount, (o,v)->o.m_amount=v).setTitle(IDisplayString.create("Amount")).setDefault(0);
		public static final IDbIntegerField<TestObject> CONCEALED = BUILDER.addIntegerField("concealed", o->o.m_concealed, (o,v)->o.m_concealed=v).setTitle(IDisplayString.create("Concealed")).setDefault(0);
		public static final IDbDayField<TestObject> DATE = BUILDER.addDayField("date", o->o.m_date, (o,v)->o.m_date=v).setTitle(IDisplayString.create("Date")).setNullable().setDefault(null);
		public static final TestObjectTable TABLE = new TestObjectTable(BUILDER);
		// @formatter:on

		// -------------------------------- STATIC FUNCTIONS -------------------------------- //

		public static ISqlSelect<TestObject> createSelect() {

			return TABLE.createSelect();
		}

		public static TestObject get(Integer id) {

			return TABLE.get(id);
		}

		// -------------------------------- EMF -------------------------------- //

		@Override
		public IDisplayString toDisplayWithoutId() {

			return IDisplayString.create(getName());
		}

		// -------------------------------- GETTERS AND SETTERS -------------------------------- //

		public final String getName() {

			return getValue(NAME);
		}

		public final TestObject setName(String value) {

			return setValue(NAME, value);
		}

		public final Integer getAmount() {

			return getValue(AMOUNT);
		}

		public final TestObject setAmount(Integer value) {

			return setValue(AMOUNT, value);
		}

		public final Integer getConcealed() {

			return getValue(CONCEALED);
		}

		public final TestObject setConcealed(Integer value) {

			return setValue(CONCEALED, value);
		}

		public final Day getDate() {

			return getValue(DATE);
		}

		public final TestObject setDate(Day value) {

			return setValue(DATE, value);
		}

		// -------------------------------- UTILS -------------------------------- //

		@Override
		public final EmfObjectTable<TestObject, SystemModuleInstance> table() {

			return TABLE;
		}

		// -------------------------------- FIELD MEMBERS -------------------------------- //

		private Integer m_id;
		private String m_name;
		private Integer m_amount;
		private Integer m_concealed;
		private Day m_date;
	}

	private static class TestObjectTable extends EmfObjectTable<TestObject, SystemModuleInstance> {

		public static final String TABLE_IDENTIFIER_HASH = "c3b5c16bb6166dbbc01324914d8f7c64b6cf51c7";
		public static final String COLUMN_TITLES_HASH = "789f49d9beba2f498328db8d3dd2ff40a2084dd8";

		public TestObjectTable(IDbObjectTableBuilder<TestObject> builder) {

			super(builder);
		}

		@Override
		public void customizeAttributeProperties(IEmfAttributeList<TestObject> attributes) {

			attributes.editAttribute(TestObject.CONCEALED).setConcealed(true);
		}

		public static IDomNode createManagementDiv() {

			return new EmfManagementDiv<>(TestObject.TABLE, CoreModule.getModuleInstance());
		}

		public static AGUserSpecificTableConfiguration insertConfiguration(String json) {

			return new AGUserSpecificTableConfiguration()//
				.setTableIdentifierHash(TABLE_IDENTIFIER_HASH)
				.setUser(CurrentUser.get())
				.setColumnTitlesHash(COLUMN_TITLES_HASH)
				.setSerialization(json)
				.save();
		}
	}
}
