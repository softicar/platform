package com.softicar.platform.emf.form.delegator;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.EmfFormMode;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * An {@link IEmfForm} that can be supplemented by an adjacent {@link IDomNode},
 * e.g. an image that is displayed next to a regular {@link EmfForm}.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractEmfAdjacentNodeForm<R extends IEmfTableRow<R, ?>> extends AbstractEmfFormDelegator<R> {

	private final IDomParentElement container;

	public AbstractEmfAdjacentNodeForm(IEmfFormFrame<R> frame, R tableRow) {

		super(frame, tableRow);
		this.container = appendChild(createContainer());
		this.form.setModeChangeCallback(this::handleModeChange);
	}

	/**
	 * Creates an {@link IDomNode} to be displayed next to the {@link EmfForm}.
	 * <p>
	 * If <i>null</i> is returned, only the {@link EmfForm} will be displayed.
	 * <p>
	 * This method gets invoked whenever the {@link EmfFormMode} of the
	 * {@link EmfForm} changes.
	 *
	 * @param mode
	 *            the current {@link EmfFormMode} (never <i>null</i>)
	 * @param tableRow
	 *            the current table row (never <i>null</i>)
	 * @return the adjacent {@link IDomNode} (may be <i>null</i>)
	 */
	protected abstract IDomNode createAdjacentNode(EmfFormMode mode, R tableRow);

	/**
	 * The side, relative to the {@link EmfForm}, on which the adjacent
	 * {@link IDomNode} shall be displayed.
	 *
	 * @return the side (never <i>null</i>)
	 */
	protected abstract AdjacentNodeSide getAdjacentNodeSide();

	private IDomParentElement createContainer() {

		if (getAdjacentNodeSide().isHorizontal()) {
			return new DomBar();
		} else {
			return new DomDiv();
		}
	}

	protected void handleModeChange(EmfFormMode mode) {

		IDomNode node = createAdjacentNode(mode, getTableRow());
		container.removeChildren();
		if (getAdjacentNodeSide().isBefore()) {
			appendToContainer(node, form);
		} else {
			appendToContainer(form, node);
		}
	}

	private void appendToContainer(IDomNode...nodes) {

		for (IDomNode node: nodes) {
			if (node != null) {
				container.appendChildren(node);
			}
		}
	}

	/**
	 * Represents the side on which the adjacent {@link IDomNode} shall be
	 * displayed, relative to the {@link EmfForm}.
	 *
	 * @author Alexander Schmidt
	 */
	public static enum AdjacentNodeSide {

		BOTTOM,
		LEFT,
		RIGHT,
		TOP;

		public boolean isHorizontal() {

			return this == LEFT || this == RIGHT;
		}

		public boolean isBefore() {

			return this == LEFT || this == TOP;
		}
	}
}
