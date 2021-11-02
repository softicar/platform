package com.softicar.platform.common.core.i18n.key.computer;

class IndexRange {

	private final int start;
	private final int end;

	public IndexRange(int start, int end) {

		this.start = start;
		this.end = end;
	}

	public int getStart() {

		return start;
	}

	public int getEnd() {

		return end;
	}

	public int getLength() {

		return end - start;
	}

	public IndexRange getShifted(int offset) {

		return new IndexRange(start + offset, end + offset);
	}

	@Override
	public String toString() {

		return "IndexRange [start=" + start + ", end=" + end + "]";
	}
}
