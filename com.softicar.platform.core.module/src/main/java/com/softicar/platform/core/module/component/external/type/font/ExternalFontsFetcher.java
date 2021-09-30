package com.softicar.platform.core.module.component.external.type.font;

import com.softicar.platform.common.ui.font.Fonts;
import com.softicar.platform.core.module.component.external.ExternalComponent;
import com.softicar.platform.core.module.component.external.IExternalComponent;
import com.softicar.platform.core.module.component.external.key.ExternalComponentKey;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import com.softicar.platform.core.module.license.License;
import java.util.List;

/**
 * Creates {@link IExternalComponent} instances for specific fonts.
 * <p>
 * Needs to be kept in sync with {@link Fonts}.
 * <p>
 * TODO Manual synchronization should not be necessary.
 * <p>
 * TODO We should have a test that ensures that we declare a license for each
 * TTF file in the class path.
 *
 * @author Alexander Schmidt
 */
public class ExternalFontsFetcher {

	public List<IExternalComponent> fetchAll() {

		return List
			.of(//
				create("Liberation Mono", "Liberation Mono TrueType Font", License.OFL_1_1, "2.1.4"),
				create("Liberation Sans", "Liberation Sans TrueType Font", License.OFL_1_1, "2.1.4"),
				create("Liberation Serif", "Liberation Serif TrueType Font", License.OFL_1_1, "2.1.4"),
				create("Rubik", "Rubik TrueType Font", License.OFL_1_1, "2.102")
			//
			);
	}

	private ExternalComponent create(String name, String description, License license, String version) {

		var component = new ExternalComponent(new ExternalComponentKey(ExternalComponentType.FONT, name));
		component.setDescription(() -> description);
		component.setLicense(() -> license.getName());
		component.setVersion(() -> version);
		return component;
	}
}
