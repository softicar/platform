package com.softicar.platform.common.container.matrix;

import com.softicar.platform.common.container.matrix.simple.SimpleMatrix;
import com.softicar.platform.common.container.matrix.traits.BigDecimalMatrixTraits;
import com.softicar.platform.common.container.matrix.traits.CharacterMatrixTraits;
import com.softicar.platform.common.container.matrix.traits.DoubleMatrixTraits;
import com.softicar.platform.common.container.matrix.traits.IntegerMatrixTraits;
import com.softicar.platform.common.container.matrix.traits.LongMatrixTraits;
import com.softicar.platform.common.container.matrix.traits.ObjectMatrixTraits;
import java.math.BigDecimal;
import java.util.Comparator;

public class MatrixFactory {

	public static <R, C, V> IMatrix<R, C, V> createMatrix(IMatrixTraits<V> traits) {

		return new SimpleMatrix<>(traits);
	}

	public static <R, C, V> IMatrix<R, C, V> createMatrix(IMatrixTraits<V> traits, Comparator<R> rowComparator, Comparator<C> columnComparator) {

		return new SimpleMatrix<>(traits, rowComparator, columnComparator);
	}

	public static <R, C, V> IMatrix<R, C, V> createObjectMatrix() {

		return createMatrix(new ObjectMatrixTraits<V>());
	}

	public static <R, C, V> IMatrix<R, C, V> createObjectMatrix(Comparator<R> rowComparator, Comparator<C> columnComparator) {

		return createMatrix(new ObjectMatrixTraits<V>(), rowComparator, columnComparator);
	}

	public static <R, C> IMatrix<R, C, Integer> createIntegerMatrix() {

		return createMatrix(new IntegerMatrixTraits());
	}

	public static <R, C> IMatrix<R, C, Integer> createIntegerMatrix(Comparator<R> rowComparator, Comparator<C> columnComparator) {

		return createMatrix(new IntegerMatrixTraits(), rowComparator, columnComparator);
	}

	public static <R, C> IMatrix<R, C, Character> createCharacterMatrix() {

		return createMatrix(new CharacterMatrixTraits());
	}

	public static <R, C> IMatrix<R, C, Long> createLongMatrix() {

		return createMatrix(new LongMatrixTraits());
	}

	public static <R, C> IMatrix<R, C, Double> createDoubleMatrix() {

		return createMatrix(new DoubleMatrixTraits());
	}

	public static <R, C> IMatrix<R, C, BigDecimal> createBigDecimalMatrix() {

		return createMatrix(new BigDecimalMatrixTraits());
	}
}
