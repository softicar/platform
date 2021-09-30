package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.string.normalizer.DiacriticNormalizer;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Comparator;

public class EmfAttributeByTitleComparator<R extends IEmfTableRow<R, ?>> implements Comparator<IEmfAttribute<R, ?>> {

	private final DiacriticNormalizer normalizer;

	public EmfAttributeByTitleComparator() {

		this.normalizer = new DiacriticNormalizer();
	}

	@Override
	public int compare(IEmfAttribute<R, ?> first, IEmfAttribute<R, ?> second) {

		return Comparator//
			.comparing(this::getNormalizedTitle)
			.compare(first, second);
	}

	private String getNormalizedTitle(IEmfAttribute<R, ?> attribute) {

		return normalizer.normalize(attribute.getTitle().toString());
	}
}
