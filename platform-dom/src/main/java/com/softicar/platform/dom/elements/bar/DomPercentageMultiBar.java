package com.softicar.platform.dom.elements.bar;

import com.softicar.platform.common.math.Clamping;
import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.style.CssStyle;

/**
 * A bar element showing one or more stacked percentage values.
 * <p>
 * The values are not given in percent but in the range [0,1].
 *
 * @author Oliver Richers
 */
public class DomPercentageMultiBar extends DomDiv {

	public DomPercentageMultiBar() {

		setCssClass(DomElementsCssClasses.DOM_PERCENTAGE_BAR);
		addCssClass(DomElementsCssClasses.DOM_PERCENTAGE_MULTI_BAR);
	}

	public DomPercentageMultiBar addPercentage(double percentage, IColor backgroundColor, IColor textColor) {

		appendChild(new PercentageDiv(percentage, backgroundColor, textColor));
		return this;
	}

	private class PercentageDiv extends DomDiv {

		private static final double LABEL_THRESHOLD = 0.2;

		public PercentageDiv(double percentage, IColor backgroundColor, IColor textColor) {

			setCssClass(DomElementsCssClasses.DOM_PERCENTAGE);

			setBackgroundColor(backgroundColor);
			setColor(textColor);
			setStyle(CssStyle.WIDTH, new CssPercent(Clamping.clamp(0, 100, percentage * 100)));

			String label = DoubleFormatter.formatDouble(100 * percentage, 1) + "%";
			setTitle(label);
			if (percentage > LABEL_THRESHOLD) {
				appendText(label);
			}
		}
	}
}
