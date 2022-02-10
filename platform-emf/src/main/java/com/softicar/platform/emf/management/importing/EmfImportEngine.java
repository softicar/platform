package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.EmfTokenMatrixParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmfImportEngine<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final List<List<String>> textualRows;
	private final List<R> parsedRows;
	private Optional<S> scope;

	public EmfImportEngine(IEmfTable<R, P, S> table) {

		this.table = table;
		this.textualRows = new ArrayList<>();
		this.parsedRows = new ArrayList<>();
		this.scope = Optional.empty();
	}

	public void addCsvRows(String csv) {

		textualRows.addAll(new EmfImportCsvReader(csv).parse(getFieldsToImport().size()));
	}

	public void parseRows() {

		parsedRows.clear();
		parsedRows.addAll(new EmfTokenMatrixParser<>(table).parse(textualRows));
	}

	public void insertRows() {

		if (parsedRows.isEmpty()) {
			throw new SofticarUserException(EmfI18n.NOTHING_TO_IMPORT);
		} else {
			new EmfImportRowsInserter<>(table).insertAll(parsedRows);
		}
	}

	public List<List<String>> getTextualRows() {

		return textualRows;
	}

	public List<R> getParsedRows() {

		return parsedRows;
	}

	public EmfImportEngine<R, P, S> setScope(S scope) {

		this.scope = Optional.of(scope);
		return this;
	}

	public IDisplayString getFieldTitle(IDbField<R, ?> field) {

		return table.getAttribute(field).getTitle();
	}

	public Collection<IDbField<R, ?>> getFieldsToImport() {

		return table//
			.getAllFields()
			.stream()
			.filter(this::filterScopeField)
			.collect(Collectors.toList());
	}

	private boolean filterScopeField(IDbField<R, ?> field) {

		return scope.isEmpty() || !isScopeField(field);
	}

	private boolean isScopeField(IDbField<R, ?> field) {

		return table//
			.getScopeField()
			.map(scopeField -> field == scopeField)
			.orElse(false);
	}
}
