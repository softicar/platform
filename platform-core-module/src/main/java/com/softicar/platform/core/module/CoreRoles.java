package com.softicar.platform.core.module;

import com.softicar.platform.core.module.access.role.EmfSystemModuleRole;

public interface CoreRoles {

	final EmfSystemModuleRole SUPER_USER = new EmfSystemModuleRole("abeb1897-5079-4bf8-9f1b-6c7cd7e71890", CoreI18n.SUPER_USER);

	final EmfSystemModuleRole ACCESS_MANAGER = new EmfSystemModuleRole("c0b0e2ea-7475-4e39-addf-f977f5eb8986", CoreI18n.ACCESS_MANAGER);

	final EmfSystemModuleRole SYSTEM_ADMINISTRATOR = new EmfSystemModuleRole("e1bb654c-4a85-4c2f-b0a2-1116b2fca657", CoreI18n.SYSTEM_ADMINISTRATOR);
}
