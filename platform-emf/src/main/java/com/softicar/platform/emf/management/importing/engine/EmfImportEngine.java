package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionAttribute;
import com.softicar.platform.emf.deactivation.IEmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import com.softicar.platform.emf.management.importing.variable.find.EmfImportVariablesFinder;
import com.softicar.platform.emf.management.importing.variable.replace.EmfImportVariablesReplacer;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.EmfTokenMatrixParser;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// TODO integrate with EmfTokenMatrixParser and add unit tests
public class EmfImportEngine<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final Set<ISqlField<?, ?>> ignoredFields;
	private Optional<S> scope;
	private List<List<String>> textualRows;
	private List<List<String>> textualRowsWithReplacements;
	private SetMap<String, EmfImportVariableCoordinates> variableCoordinates;
	private List<R> parsedRows;

	public EmfImportEngine(IEmfTable<R, P, S> table) {

		this.table = Objects.requireNonNull(table);
		this.ignoredFields = new HashSet<>();
		this.scope = Optional.empty();

		ignoreActiveField();
		ignoreGeneratedFields();
		ignoreTransactionFields();
		ignoreConcealedFields();
	}

	public IEmfTable<R, P, S> getTable() {

		return table;
	}

	public void addCsvRows(String csv) {

		textualRows = new EmfImportCsvReader(csv).parse(getFieldsToImport().size());
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

		parsedRows = new EmfTokenMatrixParser<>(table).setFields(getFieldsToImport()).parse(textualRowsWithReplacements);
		scope.ifPresent(this::setScopeValues);
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

	public EmfImportEngine<R, P, S> setScope(S scope) {

		this.scope = Optional.of(scope);
		ignoreScopeField();
		return this;
	}

	public IDisplayString getFieldTitle(IDbField<R, ?> field) {

		return table.getAttribute(field).getTitle();
	}

	public Collection<IDbField<R, ?>> getFieldsToImport() {

		return table//
			.getAllFields()
			.stream()
			.filter(field -> !ignoredFields.contains(field))
			.collect(Collectors.toList());
	}

	private void setScopeValues(S scope) {

		table.getScopeField().ifPresent(scopeField -> parsedRows.forEach(row -> scopeField.setValue(row, scope)));
	}

	private void ignoreActiveField() {

		IEmfTableRowDeactivationStrategy<R> deactivationStrategy = table.getEmfTableConfiguration().getDeactivationStrategy();
		if (deactivationStrategy.isDeactivationSupported()) {
			for (IDbField<R, ?> field: table.getAllFields()) {
				if (deactivationStrategy.isActiveAttribute(table.getAttribute(field))) {
					ignoredFields.add(field);
				}
			}
		}
	}

	private void ignoreGeneratedFields() {

		if (table.getPrimaryKey().isGenerated()) {
			ignoredFields.addAll(table.getPrimaryKey().getFields());
		}
	}

	private void ignoreScopeField() {

		table.getScopeField().ifPresent(field -> ignoredFields.add(field));
	}

	private void ignoreTransactionFields() {

		for (IDbField<R, ?> field: table.getAllFields()) {
			if (table.getAttribute(field) instanceof EmfTransactionAttribute) {
				ignoredFields.add(field);
			}
		}
	}

	private void ignoreConcealedFields() {

		for (IDbField<R, ?> field: table.getAllFields()) {
			if (table.getAttribute(field).isConcealed()) {
				ignoredFields.add(field);
			}
		}
	}
}
