package com.softicar.platform.emf.token.parser;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumn;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumnsCollector;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.converter.EmfTokenConverterResult;
import com.softicar.platform.emf.token.parser.converter.EmfTokenConverters;
import java.util.ArrayList;
import java.util.Collection;
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
	private final IEmfTable<R, P, ?> table;
	private List<? extends IDbField<R, ?>> fields;
	private Integer currentRowIndex;
	private List<String> currentRow;
	private Integer currentColumnIndex;
	private String currentToken;
	private EmfImportColumnsCollector<R, P, ?> collector;

	/**
	 * Constructs a new {@link EmfTokenMatrixParser}.
	 *
	 * @param table
	 *            the target {@link IEmfTable} (never <i>null</i>)
	 */
	public EmfTokenMatrixParser(IEmfTable<R, P, ?> table) {

		this.table = Objects.requireNonNull(table);
		this.fields = table.getAllFields();
		this.converters = new EmfTokenConverters();
		this.currentRowIndex = null;
		this.currentRow = null;
		this.currentColumnIndex = null;
		this.currentToken = null;
	}

	/**
	 * Overrides the default list of expected {@link IDbField} objects.
	 * <p>
	 * By default, the list of expected {@link IDbField} objects equals
	 * {@link IEmfTable#getAllFields()}.
	 *
	 * @param fields
	 *            the list of {@link IDbField} objects (never <i>null</i>)
	 * @return this
	 */
	public EmfTokenMatrixParser<R, P, S> setFields(Collection<IDbField<R, ?>> fields) {

		this.fields = new ArrayList<>(fields);
		return this;
	}

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
		List<R> result = new ArrayList<>();

		for (this.currentRowIndex = 0; this.currentRowIndex < tokenMatrix.size(); this.currentRowIndex++) {
			List<String> tokenRow = tokenMatrix.get(currentRowIndex);

			this.currentRow = Objects.requireNonNull(tokenRow);

			assertColumnCount(fields, tokenRow);

			R object = table.getRowFactory().get();
			for (this.currentColumnIndex = 0; currentColumnIndex < fields.size(); currentColumnIndex++) {
				this.currentToken = tokenRow.get(currentColumnIndex);
				IDbField<R, ?> field = fields.get(currentColumnIndex);
				field.setValue(object, convertTokenToValue(field, currentToken));
			}
			result.add(object);
		}
		return result;
	}

	public List<R> parseColumns(List<List<String>> tokenMatrix) {

		Objects.requireNonNull(tokenMatrix);

		List<EmfImportColumn<R, ?>> csvFileColumns = collector.getCsvFileColumnsToImport();
		List<EmfImportColumn<R, P>> tableColumns = collector.getTableColumns();

		List<IDbField<R, ?>> tableFields = tableColumns.stream().map(EmfImportColumn::getField).collect(Collectors.toList());
		fields = new ArrayList<>(tableFields);

		// TODO activate(?):
//		assertColumnCount(fields, tableColumns);

		List<R> result = new ArrayList<>();

		for (this.currentRowIndex = 0; this.currentRowIndex < tokenMatrix.size(); this.currentRowIndex++) {
			List<String> tokenRow = tokenMatrix.get(currentRowIndex);
			this.currentRow = Objects.requireNonNull(tokenRow);

			assertColumnCount(csvFileColumns, currentRow);

			for (this.currentColumnIndex = 0; currentColumnIndex < currentRow.size(); currentColumnIndex++) {
				this.currentToken = tokenRow.get(currentColumnIndex);
				EmfImportColumn<R, ?> csvFileColumn = csvFileColumns.get(currentColumnIndex);
				csvFileColumn.setValue(currentToken);
			}

			R object = table.getRowFactory().get();
			for (this.currentColumnIndex = 0; currentColumnIndex < fields.size(); currentColumnIndex++) {

				IDbField<R, ?> field = fields.get(currentColumnIndex);
				EmfImportColumn<R, P> tableColumn = tableColumns.get(currentColumnIndex);
				if (tableColumn.isForeignKeyColumn()) {
					field.setValue(object, CastUtils.cast(tableColumn.getValue()));
				} else {
					this.currentToken = CastUtils.cast(tableColumn.getValue());
					field.setValue(object, convertTokenToValue(field, currentToken));
				}
			}
			result.add(object);
		}

		return result;
	}

	private <V> V convertTokenToValue(IDbField<R, ?> field, String token) {

		EmfTokenConverterResult<?> result = converters.convert(field, token);
		if (result.isOkay()) {
			return CastUtils.cast(result.getValue());
		} else {
			throw new EmfTokenMatrixParserExceptionBuilder(currentRowIndex, currentRow)//
				.setCurrentToken(currentToken, currentColumnIndex)
				.setCause(result.getFailureCause())
				.setReason(result.getFailureReason())
				.build();
		}
	}

	private void assertColumnCount(List<?> fields, List<?> tokens) {

		if (fields.size() != tokens.size()) {
			throw new EmfTokenMatrixParserExceptionBuilder(currentRowIndex, currentRow)//
				.setReason(EmfI18n.EXPECTED_ARG1_COLUMNS_BUT_ENCOUNTERED_ARG2.toDisplay(fields.size(), tokens.size()))
				.build();
		}
	}
}
