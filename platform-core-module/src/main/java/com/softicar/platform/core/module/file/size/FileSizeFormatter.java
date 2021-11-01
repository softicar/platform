package com.softicar.platform.core.module.file.size;

import com.softicar.platform.common.string.formatting.DoubleFormatter;

public class FileSizeFormatter {

	private static final int DEFAULT_PRECISION = 2;
	private static final FileSizeBase DEFAULT_BASE_FOR_BYTE_VALUES = FileSizeBase.BINARY;

	private int precision = DEFAULT_PRECISION;
	private FileSizeBase baseForByteValues = DEFAULT_BASE_FOR_BYTE_VALUES;

	/**
	 * @param precision
	 *            the precision to which the output is to be rounded
	 */
	public FileSizeFormatter setPrecision(int precision) {

		this.precision = precision;
		return this;
	}

	/**
	 * @param fileSizeBase
	 *            the base as which byte values shall be interpreted
	 */
	public FileSizeFormatter setBaseForByteValues(FileSizeBase fileSizeBase) {

		this.baseForByteValues = fileSizeBase;
		return this;
	}

	public String formatToHumanReadableBase(IFileSize fileSize) {

		if (fileSize != null) {
			FileSizeScale targetScale = calculateHumanReadableScale(fileSize);
			double result = new FileSizeCalculator().getAsScale(fileSize, targetScale);
			return DoubleFormatter.formatDouble(result, precision) + " " + targetScale.getLabel();
		} else {
			return "";
		}
	}

	private FileSizeScale calculateHumanReadableScale(IFileSize fileSize) {

		if (fileSize != null) {
			long sizeInBytes = new FileSizeCalculator().getAsBytes(fileSize);
			FileSizeBase base = getBase(fileSize);
			int baseValue = base.getBaseValue();
			if (sizeInBytes != 0) {
				int scaleIndex = Double.valueOf(Math.floor(Math.log(sizeInBytes) / Math.log(baseValue))).intValue();
				return FileSizeScale.getAllByBase(base).get(scaleIndex);
			}
		}
		return FileSizeScale.B;
	}

	private FileSizeBase getBase(IFileSize fileSize) {

		FileSizeBase base = fileSize.getScale().getBase();
		if (base != null) {
			return base;
		} else {
			return baseForByteValues;
		}
	}
}
