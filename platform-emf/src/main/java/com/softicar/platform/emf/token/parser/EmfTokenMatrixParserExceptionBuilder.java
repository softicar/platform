package com.softicar.platform.emf.token.parser;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class EmfTokenMatrixParserExceptionBuilder {

	private final Integer rowIndex;
	private final List<String> row;
	private String token;
	private Integer columnIndex;
	private Exception cause;
	private IDisplayString reason;

	public EmfTokenMatrixParserExceptionBuilder(Integer rowIndex, List<String> row) {

		this.rowIndex = Objects.requireNonNull(rowIndex);
		this.row = Objects.requireNonNull(row);
		this.token = null;
		this.columnIndex = null;
		this.cause = null;
		this.reason = null;
	}

	public EmfTokenMatrixParserExceptionBuilder setCurrentToken(String token, Integer columnIndex) {

		this.token = Objects.requireNonNull(token);
		this.columnIndex = Objects.requireNonNull(columnIndex);
		return this;
	}

	public EmfTokenMatrixParserExceptionBuilder setCause(Exception cause) {

		this.cause = cause;
		return this;
	}

	public EmfTokenMatrixParserExceptionBuilder setReason(IDisplayString reason) {

		this.reason = reason;
		return this;
	}

	public EmfTokenMatrixParserException build() {

		var decoratedMessage = createDecoratedMessage();
		if (cause == null) {
			return new EmfTokenMatrixParserException(decoratedMessage);
		} else {
			return new EmfTokenMatrixParserException(cause, decoratedMessage);
		}
	}

	private IDisplayString createDecoratedMessage() {

		IDisplayString output;
		if (token != null && columnIndex != null) {
			output = EmfI18n.ROW_ARG1_COLUMN_ARG2_FAILED_TO_PROCESS_TOKEN_ARG3.toDisplay(rowIndex + 1, columnIndex + 1, createTokenString(token, false));
		} else {
			output = EmfI18n.ROW_ARG1_COULD_NOT_BE_PROCESSED.toDisplay(rowIndex + 1);
		}

		output = output.concatColon().concat("\n").concat(createRowString());

		if (reason != null) {
			output = output.concat("\n").concat(EmfI18n.REASON).concat(": ").concat(reason);
		}

		return output;
	}

	private String createTokenString(String token, boolean emphasize) {

		var output = new StringBuilder();
		output.append("[%s]".formatted(token));
		if (emphasize) {
			output.insert(0, "**");
			output.append("**");
		}
		return output.toString();
	}

	private String createRowString() {

		var tokenStrings = new ArrayList<String>();
		for (int i = 0; i < row.size(); i++) {
			String token = row.get(i);
			boolean emphasize = columnIndex != null && columnIndex.equals(i);
			tokenStrings.add(createTokenString(token, emphasize));
		}

		return tokenStrings.stream().collect(Collectors.joining(", "));
	}
}
