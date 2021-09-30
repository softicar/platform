package com.softicar.platform.common.math.range;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;

/**
 * Represents a range, based on the primitive type <i>long</i>.
 * 
 * @author Oliver Richers
 */
public class LongRange {

	private final long begin;
	private final long end;

	public LongRange(long begin, long end) {

		this.begin = begin;
		this.end = end;
	}

	public boolean isEmpty() {

		return begin == end;
	}

	public boolean isValid() {

		return begin <= end;
	}

	public LongRange getIntersection(LongRange other) {

		long begin = Math.max(this.begin, other.begin);
		long end = Math.min(this.end, other.end);
		return new LongRange(begin, end);
	}

	public long size() {

		if (!isValid()) {
			throw new SofticarDeveloperException("Tried to compute size of an invalid range.");
		}

		return end - begin;
	}
}
