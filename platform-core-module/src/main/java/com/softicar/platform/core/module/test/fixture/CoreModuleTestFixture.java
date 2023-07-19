package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.common.core.supplier.LazySupplier;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.ui.image.Images;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.user.AGUser;
import java.awt.image.BufferedImage;

/**
 * Basic test fixture for the {@link CoreModule}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public final class CoreModuleTestFixture implements ITestFixture, CoreModuleTestFixtureMethods {

	private static final byte[] DUMMY_PNG = Images.writeImageToByteArray(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), "PNG");
	private AGCoreModuleInstance moduleInstance;
	private AGUser viewUser;
	private AGUser normalUser;
	private AGUser adminUser;
	private final LazySupplier<AGStoredFile> dummyPngFile;

	public CoreModuleTestFixture() {

		this.moduleInstance = null;
		this.viewUser = null;
		this.normalUser = null;
		this.adminUser = null;
		this.dummyPngFile = new LazySupplier<>(() -> insertStoredFile("dummy.png", MimeType.IMAGE_PNG).uploadFileContent(DUMMY_PNG));
	}

	public AGCoreModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	@Override
	public void apply() {

		this.moduleInstance = AGCoreModuleInstance//
			.getInstance()
			.setTestSystem(true)
			.setDefaultLocalization(insertLocalizationPresetGermany())
			.setEmailServer(insertDummyServer())
			.save();

		this.viewUser = insertUser("View", "User");
		this.normalUser = insertUser("Normal", "User");
		this.adminUser = insertUser("Admin", "User");

		insertPassword(viewUser, "test");
		insertPassword(normalUser, "test");
		insertPassword(adminUser, "test");

		insertStandardPermissionAssignments(AGCoreModuleInstance.getInstance());
	}

	@Override
	public AGUser getViewUser() {

		return viewUser;
	}

	@Override
	public AGUser getNormalUser() {

		return normalUser;
	}

	@Override
	public AGUser getAdminUser() {

		return adminUser;
	}

	public CoreModuleTestFixture insertUsers(int count) {

		for (int i = 0; i < count; i++) {
			insertUser("user#" + i);
		}
		return this;
	}

	public AGStoredFile getDummyPngFile() {

		return dummyPngFile.get();
	}
}
