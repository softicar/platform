package com.softicar.platform.common.container.schedule.coverage;

import com.softicar.platform.common.container.matrix.IMatrix;
import com.softicar.platform.common.container.matrix.MatrixFactory;
import com.softicar.platform.common.math.arithmetic.Arithmetics;
import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ScheduleCoverageComputerTest extends Assert {

	private static final String ROW = "x";
	private ScheduleCoverageComputer<String, String, Integer> computer;

	@Test
	public void testExactCoverage() {

		compute("a:10", "A:10");
		assertTodoCoverage("a(A:10)");
		assertWorkCoverage("A(a:10)");
	}

	@Test
	public void testUnderWork() {

		compute("a:10", "A:8");
		assertTodoCoverage("a(2 A:8)");
		assertWorkCoverage("A(a:8)");
	}

	@Test
	public void testOverWork() {

		compute("a:10", "A:16");
		assertTodoCoverage("a(A:10)");
		assertWorkCoverage("A(6 a:10)");
	}

	@Test
	public void testDistributedCoverage() {

		compute("a:8 b:16", "A:4 B:10");
		assertTodoCoverage("a(A:4 B:4) b(10 B:6)");
		assertWorkCoverage("A(a:4) B(a:4 b:6)");
	}

	@Test
	public void testNegativeCoveringValues() {

		compute("a:8 b:17", "A:5 B:-6 C:13 D:20");
		assertTodoCoverage("a(A:5 C:3) b(C:4 D:13)");
		assertWorkCoverage("A(a:5) B(-6) C(6 a:3 b:4) D(7 b:13)");
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwsOnNegativeTodoValues() {

		compute("a:8 b:-17", "A:20");
	}

	private void compute(String todoDefinition, String coveringDefinition) {

		this.computer = new ScheduleCoverageComputer<>(parseMatrixDefinition(todoDefinition), parseMatrixDefinition(coveringDefinition), Arithmetics.INTEGER);
		this.computer.compute();
	}

	private void assertTodoCoverage(String expectedCoverage) {

		assertEquals(expectedCoverage, getCoverageAsString(computer.getTodoCoverage()));
	}

	private void assertWorkCoverage(String expectedCoverage) {

		assertEquals(expectedCoverage, getCoverageAsString(computer.getWorkCoverage()));
	}

	private String getCoverageAsString(ScheduleCoverage<String, String, Integer> coverage) {

		List<String> elements = new ArrayList<>();
		for (String column: coverage.getColumns()) {
			List<String> subElements = new ArrayList<>();
			ScheduleCoverageEntry<String, Integer> value = coverage.getValue(ROW, column);
			if (value.getUnassignedQuantity() != 0) {
				subElements.add(value.getUnassignedQuantity() + "");
			}
			for (String key: value.getColumnQuantities().keySet()) {
				subElements.add(key + ":" + value.getColumnQuantities().getOrZero(key));
			}

			if (!subElements.isEmpty()) {
				elements.add(String.format("%s(%s)", column, Imploder.implode(subElements, " ")));
			}
		}
		return Imploder.implode(elements, " ");
	}

	private IMatrix<String, String, Integer> parseMatrixDefinition(String matrixDefinition) {

		IMatrix<String, String, Integer> matrix = MatrixFactory.createIntegerMatrix();
		for (String element: matrixDefinition.split("\\s")) {
			// split element into key and value
			String[] tmp = element.split(":");
			if (tmp.length != 2) {
				throw new IllegalArgumentException(
					String.format("Illegal matrix element '%s'. Each element must have two parts separated by a colon.", element));
			}

			// add to matrix
			String column = tmp[0];
			int value = Integer.parseInt(tmp[1]);
			matrix.setValue(ROW, column, value);
		}
		return matrix;
	}
}
