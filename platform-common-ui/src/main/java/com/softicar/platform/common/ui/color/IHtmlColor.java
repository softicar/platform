package com.softicar.platform.common.ui.color;

/**
 * A simple HTML color, which can be converted into an HTML color value.
 * <P>
 * TODO move the {@link #toHtml()} to {@link IColor} and dissolve this
 * interface.
 *
 * @author Oliver Richers
 */
public interface IHtmlColor {

	/**
	 * Converts this color into a string in the usual HTML color format.
	 * <p>
	 * The returned string starts with a hash character and followed by the
	 * color value in hexadecimal format. The length of the returned string is
	 * always 7 characters.
	 *
	 * @return the value of this color in the usual HTML color format
	 */
	String toHtml();
}
