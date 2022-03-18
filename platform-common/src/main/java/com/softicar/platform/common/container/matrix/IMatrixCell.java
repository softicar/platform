package com.softicar.platform.common.container.matrix;

public interface IMatrixCell<R, C, V> {

	R getRow();

	C getColumn();

	V getValue();
}
