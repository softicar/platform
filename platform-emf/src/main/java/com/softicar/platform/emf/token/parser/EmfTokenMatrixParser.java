package com.softicar.platform.emf.token.parser;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumn;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumnLoadException;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumnsCollector;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.converter.EmfTokenConverterResult;
import com.softicar.platform.emf.token.parser.converter.EmfTokenConverters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creates an {@link IEmfTableRow} from a matrix of {@link String} based tokens.
 *
 * @author Alexander Schmidt
 */
public class EmfTokenMatrixParser<R extends IEmfTableRow<R, P>, P, S> {

	private final EmfTokenConverters converters;
	private EmfImportColumnsCollector<R, P, ?> collector;
	private Integer currentRowIndex;
	private List<String> currentRow;

	/**
	 * Constructs a new {@link EmfTokenMatrixParser}.
	 *
	 * @param table
	 *            the target {@link IEmfTable} (never <i>null</i>)
	 */
	public EmfTokenMatrixParser(IEmfTable<R, P, S> table) {

		this.converters = new EmfTokenConverters();
		this.collector = new EmfImportColumnsCollector<>(table);
		this.currentRowIndex = null;
		this.currentRow = null;
	}

//	/**
//	 * Overrides the default list of expected {@link IDbField} objects.
//	 * <p>
//	 * By default, the list of expected {@link IDbField} objects equals
//	 * {@link IEmfTable#getAllFields()}.
//	 *
//	 * @param fields
//	 *            the list of {@link IDbField} objects (never <i>null</i>)
//	 * @return this
//	 */
//	public EmfTokenMatrixParser<R, P, S> setFields(Collection<IDbField<R, ?>> fields) {
//
//		this.fields = new ArrayList<>(fields);
//		return this;
//	}

	public EmfTokenMatrixParser<R, P, S> setColumnsCollector(EmfImportColumnsCollector<R, P, ?> collector) {

		this.collector = collector;
		return this;
	}

	/**
	 * Parses the given matrix of {@link String} tokens to generate
	 * {@link IEmfTableRow} instances of the target {@link IEmfTable}.
	 * <p>
	 * The returned {@link IEmfTableRow} instances will be impermanent.
	 *
	 * @param tokenMatrix
	 *            a matrix of {@link String} tokens; each inner {@link List}
	 *            instance represents a row of {@link String} tokens (never
	 *            <i>null</i>)
	 * @return a the generated {@link IEmfTableRow} instances (never
	 *         <i>null</i>)
	 */
	public List<R> parse(List<List<String>> tokenMatrix) {

		Objects.requireNonNull(tokenMatrix);

		List<EmfImportColumn<R, ?>> csvFileColumns = collector.getCsvFileColumnsToImport();
		List<EmfImportColumn<R, P>> tableColumns = collector.getTableColumns();

		List<R> rows = new ArrayList<>();

		for (this.currentRowIndex = 0; this.currentRowIndex < tokenMatrix.size(); this.currentRowIndex++) {
			this.currentRow = Objects.requireNonNull(tokenMatrix.get(currentRowIndex));
			setValuesToCsvFileColumns(csvFileColumns);
			rows.add(createRow(tableColumns));
		}

		return rows;
	}

	private void setValuesToCsvFileColumns(List<EmfImportColumn<R, ?>> csvFileColumns) {

		assertColumnCount(csvFileColumns, currentRow);

		for (int columnIndex = 0; columnIndex < csvFileColumns.size(); columnIndex++) {
			EmfImportColumn<R, ?> csvFileColumn = csvFileColumns.get(columnIndex);
			Object value = convertTokenToValue(csvFileColumn.getField(), currentRow.get(columnIndex), columnIndex);
			csvFileColumn.setValue(value);
		}
	}

	private void assertColumnCount(List<?> fields, List<?> tokens) {

		if (fields.size() != tokens.size()) {
			throw new EmfTokenMatrixParserExceptionBuilder(currentRowIndex, currentRow)//
				.setReason(EmfI18n.EXPECTED_ARG1_COLUMNS_BUT_ENCOUNTERED_ARG2.toDisplay(fields.size(), tokens.size()))
				.build();
		}
	}

	private <V> V convertTokenToValue(IDbField<R, ?> field, String token, int columnIndex) {

		EmfTokenConverterResult<?> result = converters.convert(field, token);
		if (result.isOkay()) {
			return CastUtils.cast(result.getValue());
		} else {
			throw new EmfTokenMatrixParserExceptionBuilder(currentRowIndex, currentRow)//
				.setCurrentToken(token, columnIndex)
				.setCause(result.getFailureCause())
				.setReason(result.getFailureReason())
				.build();
		}
	}

	private R createRow(List<EmfImportColumn<R, P>> tableColumns) {

		R row = collector.getTable().getRowFactory().get();
		for (EmfImportColumn<R, P> tableColumn: tableColumns) {
			tableColumn.getField().setValue(row, loadTableColumnValueOrThrow(tableColumn));
		}
		return row;
	}

	private <V> V loadTableColumnValueOrThrow(EmfImportColumn<R, P> tableColumn) {

		try {
			return CastUtils.cast(tableColumn.getValue());
		} catch (EmfImportColumnLoadException exception) {
			throw new EmfTokenMatrixParserExceptionBuilder(currentRowIndex, currentRow)//
				.setReason(buildReason(exception.getColumn()))
				.build();
		}
	}

	private IDisplayString buildReason(EmfImportColumn<?, ?> column) {

		IDisplayString columnTitle = column.getTitle();
		List<Object> columnParentsValues = column.getParentColumns().stream().map(EmfImportColumn::getValue).collect(Collectors.toList());
		return EmfI18n.ARG1_CANNOT_BE_LOADED_BY_ARG2.toDisplay(columnTitle, Imploder.implode(columnParentsValues, ", "));
	}
}
