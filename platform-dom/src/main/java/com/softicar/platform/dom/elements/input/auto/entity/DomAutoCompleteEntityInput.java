package com.softicar.platform.dom.elements.input.auto.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * {@link IEntity} based standard implementation of
 * {@link DomAutoCompleteInput}. Allows for both literal and ID based selection
 * of {@link IEntity}s which are represented by their respective
 * {@link IEntity#toDisplay()}.
 * <p>
 * The default validation mode is
 * {@link DomAutoCompleteInputValidationMode#DEDUCTIVE}.
 * <p>
 * <b>Recommended validation modes:</b><br>
 * - {@link DomAutoCompleteInputValidationMode#DEDUCTIVE} if the element should
 * try to derive selectable {@link IEntity}s when only a substring of their
 * {@link IDisplayString} is entered. For example, if "Foo Bar [123]" is offered
 * for selection (i.e. available from auto-complete popup), and "Ba" is entered,
 * the element tries to identify all items which have an {@link IDisplayString}
 * that contains "Ba". Then, if "Foo Bar [123]" is the only item with an
 * {@link IDisplayString} that contains "Ba", the element concludes that "Ba"
 * means "Foo Bar [123]". {@link #getSelection()} would then provide access to
 * said selected item.
 * <p>
 * <b>Discouraged validation modes:</b><br>
 * - {@link DomAutoCompleteInputValidationMode#PERMISSIVE} considers any input
 * valid. However, the return value of {@link #getRawValueString()} then needs
 * to be interpreted, because {@link #getSelection()} can only contain items
 * which have an {@link IDisplayString} that is offered for selection.<br>
 * - {@link DomAutoCompleteInputValidationMode#RESTRICTIVE} disables deduction
 * and enforces entering the full {@link IEntity#toDisplay()} of a selectable
 * item.
 *
 * @author Alexander Schmidt
 */
public class DomAutoCompleteEntityInput<T extends IEntity> extends DomAutoCompleteInput<T> {

	public DomAutoCompleteEntityInput(IDomAutoCompleteInputEngine<T> inputEngine) {

		super(inputEngine, true, DomAutoCompleteInputValidationMode.DEDUCTIVE);
	}

	public DomAutoCompleteEntityInput(Supplier<Collection<T>> loader) {

		this(new DomAutoCompleteEntityInMemoryInputEngine<>(loader));
	}
}
