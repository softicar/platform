package com.softicar.platform.emf.form.indicator;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;
import java.util.stream.Stream;

public class EmfFormIndicatorRow<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final R tableRow;
	private final IndicatorContainer left;
	private final IndicatorContainer center;
	private final IndicatorContainer right;

	public EmfFormIndicatorRow(R tableRow) {

		this.tableRow = tableRow;
		this.left = new IndicatorContainer();
		this.center = new IndicatorContainer();
		this.right = new IndicatorContainer();

		setCssClass(EmfCssClasses.EMF_FORM_INDICATOR_ROW);
		appendChild(left);
		appendChild(center);
		appendChild(right);
		refresh();
	}

	public void refresh() {

		refreshIndicatorContainer(left, EmfFormIndicatorAlignment.LEFT);
		refreshIndicatorContainer(center, EmfFormIndicatorAlignment.CENTER);
		refreshIndicatorContainer(right, EmfFormIndicatorAlignment.RIGHT);
	}

	private void refreshIndicatorContainer(IndicatorContainer container, EmfFormIndicatorAlignment alignment) {

		container.removeChildren();
		getIndicators(tableRow, alignment)//
			.filter(Objects::nonNull)
			.forEach(indicator -> container.appendChild(new EmfFormIndicatorDiv(indicator, alignment)));
	}

	private Stream<IEmfFormIndicator> getIndicators(R tableRow, EmfFormIndicatorAlignment alignment) {

		return tableRow//
			.table()
			.getIndicatorFactories(alignment)
			.stream()
			.map(factory -> factory.createIndicator(tableRow));
	}

	private class IndicatorContainer extends DomDiv {

		public IndicatorContainer() {

			setCssClass(EmfCssClasses.EMF_FORM_INDICATOR_CONTAINER);
		}
	}
}
