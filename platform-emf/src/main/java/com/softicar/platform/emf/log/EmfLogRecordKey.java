package com.softicar.platform.emf.log;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.equals.Equals;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Comparator;
import java.util.Objects;

public class EmfLogRecordKey implements Comparable<EmfLogRecordKey> {

	private static final Comparator<EmfLogRecordKey> COMPARATOR = Comparator.comparing(EmfLogRecordKey::getAt).thenComparing(EmfLogRecordKey::getBy);
	private final DayTime at;
	private final IBasicUser by;

	public EmfLogRecordKey(IEmfTransactionObject<?> transactionLog) {

		this(transactionLog.getAt(), transactionLog.getBy());
	}

	public EmfLogRecordKey(DayTime at, IBasicUser by) {

		this.at = at;
		this.by = by;
	}

	public DayTime getAt() {

		return at;
	}

	public IBasicUser getBy() {

		return by;
	}

	@Override
	public int compareTo(EmfLogRecordKey other) {

		return COMPARATOR.compare(this, other);
	}

	@Override
	public boolean equals(Object object) {

		return Equals//
			.comparing(EmfLogRecordKey::getAt)
			.comparing(EmfLogRecordKey::getBy)
			.compareToObject(this, object);
	}

	@Override
	public int hashCode() {

		return Objects.hash(at, by);
	}
}
