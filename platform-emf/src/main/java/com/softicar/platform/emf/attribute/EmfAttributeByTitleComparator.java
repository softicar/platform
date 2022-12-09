package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.string.normalizer.CurrentDiacriticNormalizationCache;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Comparator;

public class EmfAttributeByTitleComparator<R extends IEmfTableRow<R, ?>> implements Comparator<IEmfAttribute<R, ?>> {

	@Override
	public int compare(IEmfAttribute<R, ?> first, IEmfAttribute<R, ?> second) {

		return Comparator//
			.comparing(this::getNormalizedTitle)
			.compare(first, second);
	}

	private String getNormalizedTitle(IEmfAttribute<R, ?> attribute) {

		return CurrentDiacriticNormalizationCache.get().normalize(attribute.getTitle().toString());
	}
}
