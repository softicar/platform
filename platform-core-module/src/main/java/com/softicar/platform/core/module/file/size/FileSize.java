package com.softicar.platform.core.module.file.size;

import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Objects;

public class FileSize implements IFileSize {

	private final long size;
	private final FileSizeScale scale;

	public FileSize(long size) {

		this(size, FileSizeScale.B);
	}

	public FileSize(long size, FileSizeScale fileSizeScale) {

		if (size < 0) {
			throw new IllegalArgumentException();
		}

		this.size = size;
		this.scale = fileSizeScale != null? fileSizeScale : FileSizeScale.B;
	}

	@Override
	public long getSize() {

		return size;
	}

	@Override
	public FileSizeScale getScale() {

		return scale;
	}

	@Override
	public boolean equals(Object obj) {

		return compareTo(CastUtils.cast(obj)) == 0;
	}

	@Override
	public int hashCode() {

		return Objects.hash(size, scale);
	}

	@Override
	public String toString() {

		return size + " " + scale;
	}
}
