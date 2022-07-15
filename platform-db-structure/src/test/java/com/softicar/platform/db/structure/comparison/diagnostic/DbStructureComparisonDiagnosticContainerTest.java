package com.softicar.platform.db.structure.comparison.diagnostic;

import com.softicar.platform.common.core.exceptions.SofticarNotImplementedYetException;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.junit.Test;

public class DbStructureComparisonDiagnosticContainerTest extends AbstractTest {

	private final DbStructureComparisonDiagnosticContainer container;

	public DbStructureComparisonDiagnosticContainerTest() {

		this.container = new DbStructureComparisonDiagnosticContainer();
	}

	@Test
	public void testAdd() {

		container.add(createErrorDiagnostic());
		container.add(createErrorDiagnostic());
		container.add(createErrorDiagnostic());
		container.add(createWarningDiagnostic());
		container.add(createWarningDiagnostic());
		container.add(createInfoDiagnostic());

		new Asserter(container).assertDiagnosticCounts(3, 2, 1);
	}

	@Test
	public void testAddAll() {

		container.addAll(Arrays.asList(createErrorDiagnostic(), createErrorDiagnostic(), createErrorDiagnostic()));
		container.addAll(Arrays.asList(createWarningDiagnostic(), createWarningDiagnostic()));
		container.addAll(Arrays.asList(createInfoDiagnostic()));

		new Asserter(container).assertDiagnosticCounts(3, 2, 1);
	}

	@Test
	public void addAllFrom() {

		DbStructureComparisonDiagnosticContainer otherContainer = new DbStructureComparisonDiagnosticContainer();
		otherContainer.add(createErrorDiagnostic());
		otherContainer.add(createWarningDiagnostic());
		otherContainer.add(createInfoDiagnostic());

		container.add(createErrorDiagnostic());
		container.add(createErrorDiagnostic());
		container.add(createErrorDiagnostic());
		container.add(createWarningDiagnostic());
		container.add(createWarningDiagnostic());
		container.add(createInfoDiagnostic());
		container.addAllFromOther(otherContainer);

		new Asserter(container).assertDiagnosticCounts(4, 3, 2);
	}

	@Test
	public void addAllTo() {

		DbStructureComparisonDiagnosticContainer otherContainer = new DbStructureComparisonDiagnosticContainer();
		otherContainer.add(createErrorDiagnostic());
		otherContainer.add(createWarningDiagnostic());
		otherContainer.add(createInfoDiagnostic());

		container.add(createErrorDiagnostic());
		container.add(createErrorDiagnostic());
		container.add(createErrorDiagnostic());
		container.add(createWarningDiagnostic());
		container.add(createWarningDiagnostic());
		container.add(createInfoDiagnostic());
		otherContainer.addAllFromOther(container);

		new Asserter(otherContainer).assertDiagnosticCounts(4, 3, 2);
	}

	@Test
	public void testGetErrors() {

		IDbStructureComparisonDiagnostic diagnostic = createErrorDiagnostic();

		container.add(diagnostic);

		Collection<IDbStructureComparisonDiagnostic> errors = container.getErrors();
		assertEquals(1, errors.size());
		assertSame(diagnostic, errors.iterator().next());
	}

	@Test
	public void testGetWarnings() {

		IDbStructureComparisonDiagnostic diagnostic = createWarningDiagnostic();

		container.add(diagnostic);

		Collection<IDbStructureComparisonDiagnostic> warnings = container.getWarnings();
		assertEquals(1, warnings.size());
		assertSame(diagnostic, warnings.iterator().next());
	}

	@Test
	public void testGetInfos() {

		IDbStructureComparisonDiagnostic diagnostic = createInfoDiagnostic();

		container.add(diagnostic);

		Collection<IDbStructureComparisonDiagnostic> infos = container.getInfos();
		assertEquals(1, infos.size());
		assertSame(diagnostic, infos.iterator().next());
	}

	@Test
	public void testGetAll() {

		IDbStructureComparisonDiagnostic errorDiagnostic = createErrorDiagnostic();
		IDbStructureComparisonDiagnostic warningDiagnostic = createWarningDiagnostic();
		IDbStructureComparisonDiagnostic infoDiagnostic = createInfoDiagnostic();

		container.add(errorDiagnostic);
		container.add(warningDiagnostic);
		container.add(infoDiagnostic);

		Collection<IDbStructureComparisonDiagnostic> diagnostics = container.getAll();
		assertTrue(diagnostics.contains(errorDiagnostic));
		assertTrue(diagnostics.contains(warningDiagnostic));
		assertTrue(diagnostics.contains(infoDiagnostic));
	}

	@Test
	public void testClear() {

		container.add(createErrorDiagnostic());
		container.add(createWarningDiagnostic());
		container.add(createInfoDiagnostic());

		container.clear();

		new Asserter(container).assertDiagnosticCounts(0, 0, 0);
	}

	@Test
	public void testWithEmptyContainer() {

		new Asserter(container).assertDiagnosticCounts(0, 0, 0);
	}

	private IDbStructureComparisonDiagnostic createErrorDiagnostic() {

		return new TestDiagnostic(DbStructureComparisonDiagnosticLevel.ERROR);
	}

	private IDbStructureComparisonDiagnostic createWarningDiagnostic() {

		return new TestDiagnostic(DbStructureComparisonDiagnosticLevel.WARNING);
	}

	private IDbStructureComparisonDiagnostic createInfoDiagnostic() {

		return new TestDiagnostic(DbStructureComparisonDiagnosticLevel.INFO);
	}

	private class TestDiagnostic implements IDbStructureComparisonDiagnostic {

		private final DbStructureComparisonDiagnosticLevel level;

		public TestDiagnostic(DbStructureComparisonDiagnosticLevel level) {

			this.level = level;
		}

		@Override
		public DbStructureComparisonDiagnosticLevel getLevel() {

			return level;
		}

		@Override
		public DbTableName getTableName() {

			throw new SofticarNotImplementedYetException();
		}

		@Override
		public Optional<IDbTableStructure> getReferenceStructure() {

			throw new SofticarNotImplementedYetException();
		}

		@Override
		public Optional<IDbTableStructure> getSampleStructure() {

			throw new SofticarNotImplementedYetException();
		}

		@Override
		public String getDiagnosticText() {

			throw new SofticarNotImplementedYetException();
		}
	}

	private static class Asserter {

		private final DbStructureComparisonDiagnosticContainer container;

		public Asserter(DbStructureComparisonDiagnosticContainer container) {

			this.container = container;
		}

		public Asserter assertDiagnosticCounts(int errors, int warnings, int infos) {

			boolean allZero = errors == 0 && warnings == 0 && infos == 0;
			assertEquals(allZero, container.getAll().isEmpty());
			assertEquals(allZero, container.isEmpty());
			assertErrorCounts(errors);
			assertWarningCounts(warnings);
			assertInfoCounts(infos);
			return this;
		}

		public Asserter assertErrorCounts(int errors) {

			assertEquals(errors, container.getErrorCount());
			assertEquals(errors, container.getDiagnosticCount(DbStructureComparisonDiagnosticLevel.ERROR));
			assertEquals(errors == 0, container.getErrors().isEmpty());
			assertEquals(errors == 0, container.isEmptyErrors());
			assertEquals(errors > 0, container.hasErrors());
			assertEquals(errors > 0, container.hasDiagnostics(DbStructureComparisonDiagnosticLevel.ERROR));
			return this;
		}

		public Asserter assertWarningCounts(int warnings) {

			assertEquals(warnings, container.getWarningCount());
			assertEquals(warnings, container.getDiagnosticCount(DbStructureComparisonDiagnosticLevel.WARNING));
			assertEquals(warnings == 0, container.getWarnings().isEmpty());
			assertEquals(warnings == 0, container.isEmptyWarnings());
			assertEquals(warnings > 0, container.hasWarnings());
			assertEquals(warnings > 0, container.hasDiagnostics(DbStructureComparisonDiagnosticLevel.WARNING));
			return this;
		}

		public Asserter assertInfoCounts(int infos) {

			assertEquals(infos, container.getInfoCount());
			assertEquals(infos, container.getDiagnosticCount(DbStructureComparisonDiagnosticLevel.INFO));
			assertEquals(infos == 0, container.getInfos().isEmpty());
			assertEquals(infos == 0, container.isEmptyInfos());
			assertEquals(infos > 0, container.hasInfos());
			assertEquals(infos > 0, container.hasDiagnostics(DbStructureComparisonDiagnosticLevel.INFO));
			return this;
		}
	}
}
