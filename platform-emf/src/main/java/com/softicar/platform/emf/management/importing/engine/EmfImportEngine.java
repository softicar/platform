package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import com.softicar.platform.emf.management.importing.variable.find.EmfImportVariablesFinder;
import com.softicar.platform.emf.management.importing.variable.replace.EmfImportVariablesReplacer;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.EmfTokenMatrixParser;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

// TODO integrate with EmfTokenMatrixParser and add unit tests
public class EmfImportEngine<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final Optional<S> scope;
	private List<List<String>> textualRows;
	private List<List<String>> textualRowsWithReplacements;
	private SetMap<String, EmfImportVariableCoordinates> variableCoordinates;
	private List<R> parsedRows;
	private final EmfImportColumnsStructure<R, P, S> columnsStructure;

	public EmfImportEngine(IEmfTable<R, P, S> table, S scope) {

		this.table = Objects.requireNonNull(table);
		this.scope = Optional.of(scope);
		this.columnsStructure = new EmfImportColumnsStructure<>(table, new EmfImportFieldsToImportCollector<>(table).collect());
	}

	public void addCsvRows(String csv) {

		textualRows = new EmfImportCsvReader(csv).parse(getCsvFileColumnsToImport().size());
		variableCoordinates = new EmfImportVariablesFinder(textualRows).find();
		textualRowsWithReplacements = textualRows;
	}

	public boolean containsVariables() {

		return !variableCoordinates.isEmpty();
	}

	public void replaceVariables(Map<String, String> variableValueMap) {

		textualRowsWithReplacements = new EmfImportVariablesReplacer(textualRows).execute(variableCoordinates, variableValueMap);
	}

	public void parseRows() {

		parsedRows = new EmfTokenMatrixParser<>(table).setColumnsCollector(columnsStructure).parse(textualRowsWithReplacements);
		scope.ifPresent(this::setScopeValues);
	}

	private void setScopeValues(S scope) {

		table.getScopeField().ifPresent(scopeField -> parsedRows.forEach(row -> scopeField.setValue(row, scope)));
	}

	public void insertRows() {

		if (parsedRows.isEmpty()) {
			throw new SofticarUserException(EmfI18n.NOTHING_TO_IMPORT);
		} else {
			table.saveAll(parsedRows);
		}
	}

	public Set<String> getVariables() {

		return variableCoordinates.keySet();
	}

	public List<List<String>> getTextualRows() {

		return textualRows;
	}

	public List<List<String>> getTextualRowsWithReplacements() {

		return textualRowsWithReplacements;
	}

	public List<R> getParsedRows() {

		return parsedRows;
	}

	public List<EmfImportColumn<R, ?>> getCsvFileColumnsToImport() {

		return columnsStructure.getCsvFileColumns();
	}

	public List<? extends IDbField<R, ?>> getAllTableFields() {

		return table.getAllFields();
	}
}
