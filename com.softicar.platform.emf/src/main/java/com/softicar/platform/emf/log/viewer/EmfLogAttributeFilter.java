package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.transients.EmfTransientAttribute;
import com.softicar.platform.emf.authorizer.IEmfAttributeAuthorizer;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Collection;
import java.util.stream.Collectors;

public class EmfLogAttributeFilter<R extends IEmfTableRow<R, ?>> {

	private final IEmfTable<R, ?, ?> table;
	private final IEmfAttributeAuthorizer<R> authorizer;
	private final EmfLogStructuralMapper<R> mapper;

	public EmfLogAttributeFilter(R tableRow) {

		this.table = tableRow.table();
		this.authorizer = tableRow.table().getAttributeAuthorizer(tableRow, CurrentBasicUser.get());
		this.mapper = new EmfLogStructuralMapper<>(table);
	}

	public Collection<IEmfAttribute<R, ?>> getDisplayableAttributes() {

		return table//
			.getAttributes()
			.stream()
			.filter(this::isUsedInLogger)
			.filter(this::isNotTransaction)
			.filter(this::isNotBase)
			.filter(this::isNotTransient)
			.filter(this::isCurrentUserAuthorized)
			.collect(Collectors.toList());
	}

	private boolean isUsedInLogger(IEmfAttribute<R, ?> attribute) {

		return mapper.getAllLoggerAttributes().contains(attribute);
	}

	private boolean isNotTransaction(IEmfAttribute<R, ?> attribute) {

		return !IEmfTransactionObject.class.isAssignableFrom(attribute.getValueClass());
	}

	private boolean isNotBase(IEmfAttribute<R, ?> attribute) {

		return mapper
			.getBaseAttribute(attribute.getTable())//
			.map(it -> it != attribute)
			.orElse(true);
	}

	private boolean isNotTransient(IEmfAttribute<R, ?> attribute) {

		return !(attribute instanceof EmfTransientAttribute);
	}

	private boolean isCurrentUserAuthorized(IEmfAttribute<R, ?> attribute) {

		return authorizer.isVisible(attribute);
	}
}
