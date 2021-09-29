package com.softicar.platform.emf.action.section.counted;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.form.section.header.IEmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;
import java.util.function.Supplier;

public class EmfCountedContentSectionHeader<R extends IEmfTableRow<R, ?>> implements IEmfFormSectionHeader {

	private final IEmfFormSectionHeader sectionHeader;
	private final IEmfCountedContentSectionDivEngine<R> engine;

	public EmfCountedContentSectionHeader(IEmfFormSectionHeader sectionHeader, IEmfCountedContentSectionDivEngine<R> engine) {

		this.sectionHeader = sectionHeader;
		this.engine = engine;
	}

	@Override
	public Optional<IResource> getIcon() {

		return sectionHeader.getIcon();
	}

	@Override
	public IDisplayString getDisplayString() {

		return sectionHeader.getDisplayString().concat(" (").concat(engine.getContentCounter() + "").concat(")");
	}

	@Override
	public Optional<Supplier<IDisplayString>> getPlaceholder() {

		return sectionHeader.getPlaceholder();
	}
}
