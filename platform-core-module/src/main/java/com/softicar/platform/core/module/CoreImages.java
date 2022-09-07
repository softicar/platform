package com.softicar.platform.core.module;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface CoreImages extends DomImages {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(CoreImages.class);

	IResourceSupplier CORE_MODULE = FACTORY.create("core-module.svg");
	IResourceSupplier EMAIL_ATTACHMENT = FACTORY.create("email-attachment.svg");
	IResourceSupplier EMAIL_BUFFER = FACTORY.create("email-buffer.svg");
	IResourceSupplier EMAIL_SERVER = FACTORY.create("email-server.svg");
	IResourceSupplier EVENT_LEVEL_PANIC = FACTORY.create("event-level-panic.svg");
	IResourceSupplier EVENT_LEVEL_PANIC_RECEIVERS = FACTORY.create("event-level-panic-receivers.svg");
	IResourceSupplier EXCEPTION = FACTORY.create("exception.svg");
	IResourceSupplier EXECUTE = FACTORY.create("execute.svg");
	IResourceSupplier FILE_TYPE_IMAGE = FACTORY.create("file-type-image.svg");
	IResourceSupplier FILE_TYPE_PDF = FACTORY.create("file-type-pdf.svg");
	IResourceSupplier FILE_TYPE_TEXT = FACTORY.create("file-type-text.svg");
	IResourceSupplier FILE_TYPE_UNKNOWN = FACTORY.create("file-type-unknown.svg");
	IResourceSupplier FINISH_MAINTENANCE = FACTORY.create("finish-maintenance.svg");
	IResourceSupplier FLAG_GERMANY = FACTORY.create("flag-germany.svg");
	IResourceSupplier FLAG_UNITED_KINGDOM = FACTORY.create("flag-united-kingdom.svg");
	IResourceSupplier LOGIN = FACTORY.create("login.svg");
	IResourceSupplier LOGIN_FAILURE = FACTORY.create("login-failure.svg");
	IResourceSupplier LOGOUT = FACTORY.create("logout.svg");
	IResourceSupplier MEDIA_PLAY = FACTORY.create("media-play.svg");
	IResourceSupplier MEDIA_STOP = FACTORY.create("media-stop.svg");
	IResourceSupplier MODULE_INSTANCE = FACTORY.create("module-instance.svg");
	IResourceSupplier MODULE_INSTANCE_CREATE = FACTORY.create("module-instance-create.svg");
	IResourceSupplier MODULE_INSTANCE_DETAILS = FACTORY.create("module-instance-details.svg");
	IResourceSupplier MODULES = FACTORY.create("modules.svg");
	IResourceSupplier NETWORK = FACTORY.create("network.svg");
	IResourceSupplier NOTIFICATION_SEND = FACTORY.create("notification-send.svg");
	IResourceSupplier PAGE_ICON = FACTORY.create("page-icon.svg");
	IResourceSupplier PAGE_NAVIGATION_OPEN_IN_NEW_TAB_ICON = FACTORY.create("page-navigation-open-in-new-tab-icon.svg");
	IResourceSupplier PAGE_NAVIGATION_TOGGLE_ICON = FACTORY.create("page-navigation-toggle-icon.svg");
	IResourceSupplier PASSWORD = FACTORY.create("password.svg");
	IResourceSupplier PASSWORD_POLICY = FACTORY.create("password-policy.svg");
	IResourceSupplier PASSWORD_RESET = FACTORY.create("password-reset.svg");
	IResourceSupplier QUEUE_REMOVE = FACTORY.create("queue-remove.svg");
	IResourceSupplier SESSION = FACTORY.create("session.svg");
	IResourceSupplier STORED_FILE = FACTORY.create("stored-file.svg");
	IResourceSupplier STORED_FILE_DOWNLOAD = FACTORY.create("stored-file-download.svg");
	IResourceSupplier STORED_FILE_SERVER = FACTORY.create("stored-file-server.svg");
	IResourceSupplier STORED_FILE_UPLOAD = FACTORY.create("stored-file-upload.svg");
	IResourceSupplier SYSTEM = FACTORY.create("system.svg");
	IResourceSupplier TERMINATE = FACTORY.create("terminate.svg");
	IResourceSupplier USER_PROFILE = FACTORY.create("user-profile.svg");
	IResourceSupplier USER_PSEUDONYMIZATION = FACTORY.create("user-pseudonymization.svg");
	IResourceSupplier USERS = FACTORY.create("users.svg");
	IResourceSupplier UUID = FACTORY.create("uuid.svg");
}
