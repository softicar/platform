package com.softicar.platform.core.module.file.size;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.List;

public class FileSizeCalculator {

	public long getAsBytes(IFileSize fileSize) {

		if (fileSize != null) {
			long size = fileSize.getSize();
			FileSizeScale scale = fileSize.getScale();

			FileSizeBase base = scale.getBase();
			if (base != null) {
				int scaleIndex = getScaleIndex(scale);
				Double resultDouble = size * Math.pow(base.getBaseValue(), scaleIndex);
				long result = resultDouble.longValue();
				if (result < Long.MAX_VALUE) {
					return result;
				} else {
					throw new SofticarDeveloperException("The file size cannot be represented as a Long value.");
				}
			} else {
				return size;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public double getAsScale(IFileSize fileSize, FileSizeScale targetScale) {

		long bytes = getAsBytes(fileSize);
		FileSizeBase targetBase = targetScale.getBase();
		if (targetBase != null) {
			int targetScaleIndex = getScaleIndex(targetScale);
			return bytes / Math.pow(targetBase.getBaseValue(), targetScaleIndex);
		} else {
			return bytes;
		}
	}

	private int getScaleIndex(FileSizeScale scale) {

		FileSizeBase base = scale.getBase();
		if (base != null) {
			List<FileSizeScale> scalesByBase = FileSizeScale.getAllByBase(base);
			return scalesByBase.indexOf(scale);
		} else {
			return 0;
		}
	}
}
