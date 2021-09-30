package com.softicar.platform.dom.style;

/**
 * Common interface of all CSS size values.
 *
 * @author Oliver Richers
 */
public interface ICssLength extends ICssStyleValue {

	ICssLength AUTO = new CssPseudoLength("auto");
	ICssLength ZERO = new CssPseudoLength("0");

	CssLengthUnit getUnit();

	double getValue();

	ICssLength plus(double value);

	ICssLength minus(double value);

	ICssLength negated();
}
