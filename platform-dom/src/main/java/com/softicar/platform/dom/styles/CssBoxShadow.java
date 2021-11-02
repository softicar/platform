package com.softicar.platform.dom.styles;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

/**
 * Defines CSS text-shadow.
 *
 * @author Oliver Richers
 */
public class CssBoxShadow implements ICssStyleAttribute {

	private final ICssLength x;
	private final ICssLength y;
	private final ICssLength blur;
	private final ICssLength spread;
	private final IColor color;
	private final CssBoxShadowType type;

	// -------------------- normal shadow -------------------- //

	public CssBoxShadow(ICssLength x, ICssLength y, IColor color) {

		this(x, y, ICssLength.ZERO, ICssLength.ZERO, color, CssBoxShadowType.NORMAL);
	}

	public CssBoxShadow(ICssLength x, ICssLength y, ICssLength blur, IColor color) {

		this(x, y, blur, ICssLength.ZERO, color, CssBoxShadowType.NORMAL);
	}

	public CssBoxShadow(ICssLength x, ICssLength y, ICssLength blur, ICssLength spread, IColor color) {

		this(x, y, blur, spread, color, CssBoxShadowType.NORMAL);
	}

	// -------------------- in-set shadow -------------------- //

	public CssBoxShadow(ICssLength x, ICssLength y, IColor color, CssBoxShadowType type) {

		this(x, y, ICssLength.ZERO, ICssLength.ZERO, color, type);
	}

	public CssBoxShadow(ICssLength x, ICssLength y, ICssLength blur, IColor color, CssBoxShadowType type) {

		this(x, y, blur, ICssLength.ZERO, color, type);
	}

	public CssBoxShadow(ICssLength x, ICssLength y, ICssLength blur, ICssLength spread, IColor color, CssBoxShadowType type) {

		this.x = x;
		this.y = y;
		this.blur = blur;
		this.spread = spread;
		this.color = color;
		this.type = type;
	}

	@Override
	public ICssStyle getStyle() {

		return CssStyle.BOX_SHADOW;
	}

	@Override
	public String getValue() {

		String value = String.format("%s %s %s %s %s", x, y, blur, spread, color.toHtml());
		return type == CssBoxShadowType.NORMAL? value : value + " inset";
	}
}
