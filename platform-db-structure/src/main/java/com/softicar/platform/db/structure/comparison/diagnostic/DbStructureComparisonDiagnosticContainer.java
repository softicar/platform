package com.softicar.platform.db.structure.comparison.diagnostic;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DbStructureComparisonDiagnosticContainer {

	private final Collection<IDbStructureComparisonDiagnostic> errors;
	private final Collection<IDbStructureComparisonDiagnostic> warnings;
	private final Collection<IDbStructureComparisonDiagnostic> infos;

	public DbStructureComparisonDiagnosticContainer() {

		this.errors = new ArrayList<>();
		this.warnings = new ArrayList<>();
		this.infos = new ArrayList<>();
	}

	// -------------------------------- collect diagnostics -------------------------------- //

	public void add(IDbStructureComparisonDiagnostic diagnostic) {

		addAll(Collections.singleton(diagnostic));
	}

	public void addAll(Collection<IDbStructureComparisonDiagnostic> diagnostics) {

		for (IDbStructureComparisonDiagnostic diagnostic: diagnostics) {
			getCollection(diagnostic.getLevel()).add(diagnostic);
		}
	}

	public void addAllFromOthers(Collection<DbStructureComparisonDiagnosticContainer> others) {

		others.forEach(this::addAllFromOther);
	}

	public void addAllFromOther(DbStructureComparisonDiagnosticContainer other) {

		addAll(other.getAll());
	}

	// -------------------------------- get diagnostics -------------------------------- //

	public Collection<IDbStructureComparisonDiagnostic> getErrors() {

		return Collections.unmodifiableCollection(getCollection(DbStructureComparisonDiagnosticLevel.ERROR));
	}

	public Collection<IDbStructureComparisonDiagnostic> getWarnings() {

		return Collections.unmodifiableCollection(getCollection(DbStructureComparisonDiagnosticLevel.WARNING));
	}

	public Collection<IDbStructureComparisonDiagnostic> getInfos() {

		return Collections.unmodifiableCollection(getCollection(DbStructureComparisonDiagnosticLevel.INFO));
	}

	public Collection<IDbStructureComparisonDiagnostic> getAll() {

		List<IDbStructureComparisonDiagnostic> diagnostics = new ArrayList<>();
		diagnostics.addAll(getErrors());
		diagnostics.addAll(getWarnings());
		diagnostics.addAll(getInfos());
		return Collections.unmodifiableCollection(diagnostics);
	}

	// -------------------------------- get diagnostic counts -------------------------------- //

	public int getErrorCount() {

		return getDiagnosticCount(DbStructureComparisonDiagnosticLevel.ERROR);
	}

	public int getWarningCount() {

		return getDiagnosticCount(DbStructureComparisonDiagnosticLevel.WARNING);
	}

	public int getInfoCount() {

		return getDiagnosticCount(DbStructureComparisonDiagnosticLevel.INFO);
	}

	public int getDiagnosticCount(DbStructureComparisonDiagnosticLevel level) {

		return getCollection(level).size();
	}

	// -------------------------------- check diagnostic presence -------------------------------- //

	public boolean hasErrors() {

		return hasDiagnostics(DbStructureComparisonDiagnosticLevel.ERROR);
	}

	public boolean hasWarnings() {

		return hasDiagnostics(DbStructureComparisonDiagnosticLevel.WARNING);
	}

	public boolean hasInfos() {

		return hasDiagnostics(DbStructureComparisonDiagnosticLevel.INFO);
	}

	public boolean hasDiagnostics(DbStructureComparisonDiagnosticLevel level) {

		return getDiagnosticCount(level) > 0;
	}

	public boolean isEmptyErrors() {

		return !hasErrors();
	}

	public boolean isEmptyWarnings() {

		return !hasWarnings();
	}

	public boolean isEmptyInfos() {

		return !hasInfos();
	}

	public boolean isEmpty() {

		return isEmptyErrors() && isEmptyWarnings() && isEmptyInfos();
	}

	// -------------------------------- miscellaneous -------------------------------- //

	public void clear() {

		this.errors.clear();
		this.warnings.clear();
		this.infos.clear();
	}

	@Override
	public String toString() {

		StringBuilder output = new StringBuilder();
		output.append(toStringForLevel(DbStructureComparisonDiagnosticLevel.ERROR));
		output.append(toStringForLevel(DbStructureComparisonDiagnosticLevel.WARNING));
		output.append(toStringForLevel(DbStructureComparisonDiagnosticLevel.INFO));
		return output.toString();
	}

	private String toStringForLevel(DbStructureComparisonDiagnosticLevel level) {

		StringBuilder output = new StringBuilder();
		output.append(String.format("%s: %s\n", level, getDiagnosticCount(level)));
		getCollection(level).forEach(it -> output.append("[" + level + "] " + it + "\n"));
		return output.toString();
	}

	private Collection<IDbStructureComparisonDiagnostic> getCollection(DbStructureComparisonDiagnosticLevel level) {

		switch (level) {
		case ERROR:
			return errors;
		case WARNING:
			return warnings;
		case INFO:
			return infos;
		default:
			throw new SofticarUnknownEnumConstantException(level);
		}
	}
}
