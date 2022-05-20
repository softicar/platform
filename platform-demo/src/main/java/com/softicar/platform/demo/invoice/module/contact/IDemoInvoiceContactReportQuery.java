package com.softicar.platform.demo.invoice.module.contact;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryColumn;
import com.softicar.platform.db.runtime.query.DbQueryTableStubColumn;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.IDbQueryFactory;
import com.softicar.platform.db.runtime.query.IDbQueryRow;
import com.softicar.platform.db.runtime.query.IDbQueryTableColumn;
import com.softicar.platform.db.runtime.query.builder.AbstractDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IDemoInvoiceContactReportQuery extends IDbQuery<IDemoInvoiceContactReportQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryColumn<IRow, String> INVOICE_NUMBER_COLUMN = new DbQueryColumn<>(IRow::getInvoiceNumber, "invoiceNumber", SqlValueTypes.STRING);
	IDbQueryTableColumn<IRow, AGDemoPerson> CONTACT_COLUMN = new DbQueryTableStubColumn<>(IRow::getContact, "contact", AGDemoPerson.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		String getInvoiceNumber();
		AGDemoPerson getContact();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IModuleInstanceSetter createQuery();
	}

	interface IModuleInstanceSetter {

		IDemoInvoiceContactReportQuery setModuleInstance(AGDemoInvoiceModuleInstance moduleInstance);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(2);

			public Factory() {

				this.columns.add(INVOICE_NUMBER_COLUMN);
				this.columns.add(CONTACT_COLUMN);
			}

			@Override
			public IModuleInstanceSetter createQuery() {

				return new Query().new ModuleInstanceSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private AGDemoInvoiceModuleInstance moduleInstance;
		}

		private static class Query extends AbstractDbQuery<IRow> implements IDemoInvoiceContactReportQuery {

			private final Parameters parameters = new Parameters();

			@Override
			public IRow createRow(IDbSqlSelect select, DbResultSet resultSet) {

				return new Row(this, select, resultSet);
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return FACTORY.getColumns();
			}

			@Override
			public QuerySqlBuilder createSqlBuilder() {

				return new QuerySqlBuilder();
			}

			public class ModuleInstanceSetter implements IModuleInstanceSetter {

				@Override
				public final IDemoInvoiceContactReportQuery setModuleInstance(AGDemoInvoiceModuleInstance moduleInstance) {

					Query.this.parameters.moduleInstance = moduleInstance;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					FROM();
					addIdentifier(AGDemoInvoice.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("invoice");
					SELECT(INVOICE_NUMBER_COLUMN);
					addIdentifier("invoice", AGDemoInvoice.INVOICE_NUMBER);
					addToken(SqlKeyword.AS);
					addIdentifier("invoiceNumber");
					SELECT(CONTACT_COLUMN);
					addIdentifier("invoice", AGDemoInvoice.CONTACT);
					addToken(SqlKeyword.AS);
					addIdentifier("contact");
					WHERE();
					addIdentifier("invoice", AGDemoInvoice.MODULE_INSTANCE);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.moduleInstance);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final String invoiceNumber;
			private final AGDemoPerson contact;

			private Row(IDemoInvoiceContactReportQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.invoiceNumber = INVOICE_NUMBER_COLUMN.loadValue(select, resultSet);
				this.contact = CONTACT_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public String getInvoiceNumber() {

				return this.invoiceNumber;
			}

			@Override
			public AGDemoPerson getContact() {

				return this.contact;
			}
		}
	}
}

