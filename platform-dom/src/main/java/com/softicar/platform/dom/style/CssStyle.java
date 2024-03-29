package com.softicar.platform.dom.style;

public enum CssStyle implements ICssStyle {

	ALIGN_CONTENT("alignContent"),
	ALIGN_ITEMS("alignItems"),
	ALIGN_SELF("alignSelf"),
	BACKGROUND("background"),
	BACKGROUND_COLOR("backgroundColor"),
	BACKGROUND_IMAGE("backgroundImage"),
	BORDER("border"),
	BORDER_BOTTOM("borderBottom"),
	BORDER_BOTTOM_COLOR("borderBottomColor"),
	BORDER_BOTTOM_STYLE("borderBottomStyle"),
	BORDER_BOTTOM_WIDTH("borderBottomWidth"),
	BORDER_COLLAPSE("borderCollapse"),
	BORDER_COLOR("borderColor"),
	BORDER_LEFT("borderLeft"),
	BORDER_LEFT_COLOR("borderLeftColor"),
	BORDER_LEFT_STYLE("borderLeftStyle"),
	BORDER_LEFT_WIDTH("borderLeftWidth"),
	BORDER_RADIUS("borderRadius"),
	BORDER_RIGHT("borderRight"),
	BORDER_RIGHT_COLOR("borderRightColor"),
	BORDER_RIGHT_STYLE("borderRightStyle"),
	BORDER_RIGHT_WIDTH("borderRightWidth"),
	BORDER_SPACING("borderSpacing"),
	BORDER_STYLE("borderStyle"),
	BORDER_TOP("borderTop"),
	BORDER_TOP_COLOR("borderTopColor"),
	BORDER_TOP_LEFT_RADIUS("borderTopLeftRadius"),
	BORDER_TOP_RIGHT_RADIUS("borderTopRightRadius"),
	BORDER_TOP_STYLE("borderTopStyle"),
	BORDER_TOP_WIDTH("borderTopWidth"),
	BORDER_WIDTH("borderWidth"),
	BOTTOM("bottom"),
	BOX_SHADOW("boxShadow"),
	BOX_SIZING("boxSizing"),
	CLEAR("clear"),
	CLIP("clip"),
	COLOR("color"),
	COLUMN_GAP("columnGap"),
	CURSOR("cursor"),
	DISPLAY("display"),
	FILTER("filter"),
	FLEX("flex"),
	FLEX_BASIS("flexBasis"),
	FLEX_DIRECTION("flexDirection"),
	FLEX_FLOW("flexFlow"),
	FLEX_GROW("flexGrow"),
	FLEX_SHRINK("flexShrink"),
	FLEX_WRAP("flexWrap"),
	FLOAT("cssFloat"),
	FONT_FAMILY("fontFamily"),
	FONT_SIZE("fontSize"),
	FONT_STYLE("fontStyle"),
	FONT_VARIANT("fontVariant"),
	FONT_WEIGHT("fontWeight"),
	GRID("grid"),
	GRID_AREA("gridArea"),
	GRID_AUTO_COLUMNS("gridAutoColumns"),
	GRID_AUTO_FLOW("gridAutoFlow"),
	GRID_AUTO_ROWS("gridAutoRows"),
	GRID_COLUMN("gridColumn"),
	GRID_COLUMN_END("gridColumnEnd"),
	GRID_COLUMN_START("gridColumnStart"),
	GRID_GAP("gridGap"),
	GRID_ROW("gridRow"),
	GRID_ROW_END("gridRowEnd"),
	GRID_ROW_START("gridRowStart"),
	GRID_TEMPLATE("gridTemplate"),
	GRID_TEMPLATE_AREAS("gridTemplateAreas"),
	GRID_TEMPLATE_COLUMNS("gridTemplateColumns"),
	GRID_TEMPLATE_ROWS("gridTemplateRows"),
	HEIGHT("height"),
	INSET("inset"),
	JUSTIFY_CONTENT("justifyContent"),
	LEFT("left"),
	LINE_HEIGHT("lineHeight"),
	MARGIN("margin"),
	MARGIN_BOTTOM("marginBottom"),
	MARGIN_LEFT("marginLeft"),
	MARGIN_RIGHT("marginRight"),
	MARGIN_TOP("marginTop"),
	MAX_HEIGHT("maxHeight"),
	MAX_WIDTH("maxWidth"),
	MIN_HEIGHT("minHeight"),
	MIN_WIDTH("minWidth"),
	OPACITY("opacity"),
	ORDER("order"),
	OUTLINE_COLOR("outlineColor"),
	OUTLINE_STYLE("outlineStyle"),
	OUTLINE_WIDTH("outlineWidth"),
	OVERFLOW("overflow"),
	OVERFLOW_WRAP("overflowWrap"),
	OVERFLOW_X("overflowX"),
	OVERFLOW_Y("overflowY"),
	PADDING("padding"),
	PADDING_BOTTOM("paddingBottom"),
	PADDING_LEFT("paddingLeft"),
	PADDING_RIGHT("paddingRight"),
	PADDING_TOP("paddingTop"),
	POINTER_EVENTS("pointerEvents"),
	POSITION("position"),
	RESIZE("resize"),
	RIGHT("right"),
	ROW_GAP("rowGap"),
	TABLE_LAYOUT("tableLayout"),
	TEXT_ALIGN("textAlign"),
	TEXT_DECORATION("textDecoration"),
	TEXT_SHADOW("textShadow"),
	TOP("top"),
	TRANSFORM("transform"),
	TRANSFORM_ORIGIN("transformOrigin"),
	VERTICAL_ALIGN("verticalAlign"),
	VISIBILITY("visibility"),
	WIDTH("width"),
	WHITE_SPACE("whiteSpace"),
	WORD_BREAK("wordBreak"),
	WORD_SPACING("wordSpacing"),
	WORD_WRAP("wordWrap"),
	Z_INDEX("zIndex");

	@Override
	public String getJavascriptName() {

		return jsName;
	}

	private CssStyle(String jsName) {

		this.jsName = jsName;
	}

	private String jsName;
}
