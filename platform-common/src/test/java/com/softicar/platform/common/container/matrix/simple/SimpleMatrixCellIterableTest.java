package com.softicar.platform.common.container.matrix.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.container.matrix.IMatrix;
import com.softicar.platform.common.container.matrix.IMatrixCell;
import com.softicar.platform.common.container.matrix.MatrixFactory;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

public class SimpleMatrixCellIterableTest {

	private IMatrix<String, Integer, Double> matrix;

	@Before
	public void setup() {

		matrix = MatrixFactory.createDoubleMatrix();
	}

	@Test
	public void worksWithEmptyMatrices() {

		Iterator<IMatrixCell<String, Integer, Double>> iterator = matrix.getMatrixCells().iterator();

		assertFalse(iterator.hasNext());
	}

	@Test
	public void skipsUnsetValues() {

		matrix.setValue("test1", 1, 42.0);
		matrix.setValue("test1", 2, 43.0);
		matrix.setValue("test2", 1, 44.0);
		Iterator<IMatrixCell<String, Integer, Double>> iterator = matrix.getMatrixCells().iterator();

		assertTrue(iterator.hasNext());
		assertEquals(new SimpleMatrixCell<>("test1", 1, 42.0), iterator.next());
		assertEquals(new SimpleMatrixCell<>("test1", 2, 43.0), iterator.next());
		assertEquals(new SimpleMatrixCell<>("test2", 1, 44.0), iterator.next());
		assertFalse(iterator.hasNext());
	}
}
