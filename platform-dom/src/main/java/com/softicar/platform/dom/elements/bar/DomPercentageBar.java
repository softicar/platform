package com.softicar.platform.dom.elements.bar;

import com.softicar.platform.common.math.Clamping;
import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.style.CssStyle;

/**
 * A bar element showing a percentage value.
 * <p>
 * The values are not given in percent but in the range [0,1].
 *
 * @author Oliver Richers
 */
public class DomPercentageBar extends DomDiv {

	private final PercentageDiv percentageDiv;
	private final PercentageLabelDiv percentageLabelDiv;
	private double percentage;

	public DomPercentageBar() {

		this(0.0, true);
	}

	/**
	 * Creates a new {@link DomPercentageBar} with the given percentage.
	 *
	 * @param percentage
	 *            percentage in the range [0,1]
	 */
	public DomPercentageBar(double percentage) {

		this(percentage, true);
	}

	/**
	 * Creates a new {@link DomPercentageBar} with the given percentage.
	 *
	 * @param percentage
	 *            percentage in the range [0,1]
	 * @param showPercentage
	 *            determines if the percentage should be shown textually
	 */
	public DomPercentageBar(double percentage, boolean showPercentage) {

		this.percentage = Clamping.clamp(0, 1, percentage);
		this.percentageDiv = new PercentageDiv();
		this.percentageLabelDiv = new PercentageLabelDiv();

		setCssClass(DomElementsCssClasses.DOM_PERCENTAGE_BAR);
		appendChild(percentageDiv);
		if (showPercentage) {
			appendChild(percentageLabelDiv);
		}
	}

	public void updatePercentage(double percentage) {

		this.percentage = percentage;
		this.percentageDiv.updatePercentage();
		this.percentageLabelDiv.updatePercentage();
	}

	private class PercentageDiv extends DomDiv {

		public PercentageDiv() {

			setCssClass(DomElementsCssClasses.DOM_PERCENTAGE);
			updatePercentage();
		}

		public void updatePercentage() {

			setStyle(CssStyle.WIDTH, new CssPercent(Clamping.clamp(0, 100, percentage * 100)));
		}
	}

	private class PercentageLabelDiv extends DomDiv {

		public PercentageLabelDiv() {

			setCssClass(DomElementsCssClasses.DOM_PERCENTAGE_LABEL);
			updatePercentage();
		}

		public void updatePercentage() {

			removeChildren();
			appendText(DoubleFormatter.formatDouble(100 * percentage, 1) + "%");
		}
	}
}
