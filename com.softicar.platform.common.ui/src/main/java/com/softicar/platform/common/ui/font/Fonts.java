package com.softicar.platform.common.ui.font;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

/**
 * Defines bundled standard fonts.
 *
 * <pre>
 * +------------------+-----------------+----------+-----------------------------------------------------+
 * |    Font Name     |    Replaces     | License  |                      Upstream                       |
 * +------------------+-----------------+----------+-----------------------------------------------------+
 * | Liberation Sans  | Arial           | OFL v1.1 | https://github.com/liberationfonts/liberation-fonts |
 * | Liberation Mono  | Courier New     | OFL v1.1 | https://github.com/liberationfonts/liberation-fonts |
 * | Liberation Serif | Times New Roman | OFL v1.1 | https://github.com/liberationfonts/liberation-fonts |
 * | Rubik            | -               | OFL v1.1 | https://github.com/googlefonts/rubik                |
 * +------------------+-----------------+----------+-----------------------------------------------------+
 * </pre>
 *
 * @author Alexander Schmidt
 */
@ResourceSupplierContainer
public interface Fonts {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(Fonts.class);

	// Liberation Mono (replaces Courier New)
	IResourceSupplier LIBERATION_MONO = FACTORY.create("LiberationMono-Regular.ttf");
	IResourceSupplier LIBERATION_MONO_BOLD = FACTORY.create("LiberationMono-Bold.ttf");
	IResourceSupplier LIBERATION_MONO_BOLD_ITALIC = FACTORY.create("LiberationMono-BoldItalic.ttf");
	IResourceSupplier LIBERATION_MONO_ITALIC = FACTORY.create("LiberationMono-Italic.ttf");

	// Liberation Sans (replaces Arial)
	IResourceSupplier LIBERATION_SANS = FACTORY.create("LiberationSans-Regular.ttf");
	IResourceSupplier LIBERATION_SANS_BOLD = FACTORY.create("LiberationSans-Bold.ttf");
	IResourceSupplier LIBERATION_SANS_BOLD_ITALIC = FACTORY.create("LiberationSans-BoldItalic.ttf");
	IResourceSupplier LIBERATION_SANS_ITALIC = FACTORY.create("LiberationSans-Italic.ttf");

	// Liberation Serif (replaces Times New Roman)
	IResourceSupplier LIBERATION_SERIF = FACTORY.create("LiberationSerif-Regular.ttf");
	IResourceSupplier LIBERATION_SERIF_BOLD = FACTORY.create("LiberationSerif-Bold.ttf");
	IResourceSupplier LIBERATION_SERIF_BOLD_ITALIC = FACTORY.create("LiberationSerif-BoldItalic.ttf");
	IResourceSupplier LIBERATION_SERIF_ITALIC = FACTORY.create("LiberationSerif-Italic.ttf");

	// Rubik
	IResourceSupplier RUBIK_VARIABLE_WEIGHT = FACTORY.create("Rubik-wght.ttf");
}
