package com.softicar.platform.core.module.file.size;

public interface IFileSize extends Comparable<IFileSize> {

	long getSize();

	FileSizeScale getScale();

	@Override
	default int compareTo(IFileSize other) {

		FileSizeCalculator calculator = new FileSizeCalculator();
		return Long.valueOf(calculator.getAsBytes(this)).compareTo(calculator.getAsBytes(other));
	}
}
