package com.softicar.platform.dom.text;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.node.AbstractDomNode;
import java.util.Optional;

/**
 * The abstract base class of all text nodes.
 *
 * @author Oliver Richers
 */
public abstract class DomTextNode extends AbstractDomNode implements IDomTextNode {

	/**
	 * Creates a new {@link DomTextNode} from the given {@link IDisplayString}.
	 * <p>
	 * If the given argument is <i>null</i>, an empty text node will be created.
	 *
	 * @param displayString
	 *            the text to display (may be null)
	 * @return a new {@link DomTextNode} which contains the text to display
	 *         (never null)
	 */
	public static DomTextNode create(IDisplayString displayString) {

		return create(Optional.ofNullable(displayString).map(IDisplayString::toString).orElse(null));
	}

	/**
	 * Creates a new {@link DomTextNode} from the given {@link String}.
	 * <p>
	 * If the given argument is <i>null</i>, an empty text node will be created.
	 *
	 * @param text
	 *            the text to display (may be null)
	 * @return a new {@link DomTextNode} which contains the text to display
	 *         (never null)
	 */
	public static DomTextNode create(String text) {

		return TextNodeFactory.create(text);
	}

	protected DomTextNode(String text) {

		super(CurrentDomDocument.get());

		getDomEngine().createTextNode(getNodeId(), text);
	}

	@Override
	public abstract String getText();
}
