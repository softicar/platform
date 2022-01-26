package com.softicar.platform.emf.token.parser;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.converter.EmfTokenConverterResult;
import com.softicar.platform.emf.token.parser.converter.EmfTokenConverters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Creates an {@link IEmfTableRow} from a matrix of {@link String} based tokens.
 *
 * @author Alexander Schmidt
 */
public class EmfTokenMatrixParser<R extends IEmfTableRow<R, P>, P> {

	private final EmfTokenConverters converters;
	private final IEmfTable<R, P, ?> table;
	private Integer currentRowIndex;
	private List<String> currentRow;
	private Integer currentColumnIndex;
	private String currentToken;

	/**
	 * Constructs a new {@link EmfTokenMatrixParser}.
	 *
	 * @param table
	 *            the target {@link IEmfTable} (never <i>null</i>)
	 */
	public EmfTokenMatrixParser(IEmfTable<R, P, ?> table) {

		this.table = Objects.requireNonNull(table);
		this.converters = new EmfTokenConverters();
		this.currentRowIndex = null;
		this.currentRow = null;
		this.currentColumnIndex = null;
		this.currentToken = null;
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

			var fields = table.getAllFields();
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
