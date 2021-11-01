package com.softicar.platform.emf.action.section.counted;

import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.header.IEmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * A section with counted content.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class EmfCountedContentSectionDiv<R extends IEmfTableRow<R, ?>> extends EmfFormSectionDiv<R> {

	public EmfCountedContentSectionDiv(IEmfCountedContentSectionDivEngine<R> engine, IEmfFormSectionHeader sectionHeader) {

		super(engine.getFormBody(), new EmfCountedContentSectionHeader<>(sectionHeader, engine));

		addElement(engine::createContentElement);
		setOpen(engine.getContentCounter() > 0);

		engine.setCountChangedCallback(this::refreshHeader);
	}
}
