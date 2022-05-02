package com.softicar.platform.dom;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface DomCssPseudoClasses {

	ICssClass ACTIVE = new CssClass("active");
	ICssClass CLICKABLE = new CssClass("clickable");
	ICssClass DISABLED = new CssClass("disabled");
	ICssClass DRAGGABLE = new CssClass("draggable");
	ICssClass ERROR = new CssClass("error");
	ICssClass INFO = new CssClass("info");
	ICssClass INVISIBLE = new CssClass("invisible");
	ICssClass PRECOLORED = new CssClass("precolored");
	ICssClass ROTATED = new CssClass("rotated");
	ICssClass SELECTED = new CssClass("selected");
	ICssClass SUCCESS = new CssClass("success");
	ICssClass VISIBLE = new CssClass("visible");
	ICssClass WARNING = new CssClass("warning");
}
