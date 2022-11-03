package com.softicar.platform.emf;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface EmfImages extends DomImages {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(EmfImages.class);

	IResourceSupplier ATTRIBUTE_HELP = FACTORY.create("attribute-help.svg");
	IResourceSupplier ENTITY_ACTIONS = FACTORY.create("entity-actions.svg");
	IResourceSupplier ENTITY_CREATE = FACTORY.create("entity-create.svg");
	IResourceSupplier ENTITY_CREATE_NEXT_STEP = FACTORY.create("entity-create-next-step.svg");
	IResourceSupplier ENTITY_DEACTIVATE = FACTORY.create("entity-deactivate.svg");
	IResourceSupplier ENTITY_EDIT = FACTORY.create("entity-edit.svg");
	IResourceSupplier ENTITY_IMPORT = FACTORY.create("entity-import.svg");
	IResourceSupplier ENTITY_LOG = FACTORY.create("entity-log.svg");
	IResourceSupplier ENTITY_OUTDATED = FACTORY.create("entity-outdated.svg");
	IResourceSupplier ENTITY_PREFILTER_DOTS_T = FACTORY.create("entity-prefilter-dots-t.svg");
	IResourceSupplier ENTITY_SAVE = FACTORY.create("entity-save.svg");
	IResourceSupplier ENTITY_SAVE_AND_CLOSE = FACTORY.create("entity-save-and-close.svg");
	IResourceSupplier ENTITY_TRAIT_CONFIGURE = FACTORY.create("entity-trait-configure.svg");
	IResourceSupplier ENTITY_VIEW = FACTORY.create("entity-view.svg");
	IResourceSupplier FIND = FACTORY.create("find.svg");
	IResourceSupplier INPUT_PREVIEW = FACTORY.create("input-preview.svg");
	IResourceSupplier MANAGEMENT_ACTIONS = FACTORY.create("management-actions.svg");
	IResourceSupplier MODULE_DEFAULT = FACTORY.create("module-default.svg");
	IResourceSupplier PAGE_DEFAULT = FACTORY.create("page-default.svg");
	IResourceSupplier REFRESH = FACTORY.create("refresh.svg");
	IResourceSupplier RESET = FACTORY.create("reset.svg");
	IResourceSupplier SHOW_PASSWORD = FACTORY.create("show-password.svg");
	IResourceSupplier TABLE_COLUMN_SORT_ASCENDING = FACTORY.create("table-column-sort-ascending.svg");
	IResourceSupplier TABLE_COLUMN_SORT_DESCENDING = FACTORY.create("table-column-sort-descending.svg");
	IResourceSupplier TABLE_COLUMN_SORT_NONE = FACTORY.create("table-column-sort-none.svg");
	IResourceSupplier TABLE_CONFIGURATION = FACTORY.create("table-configuration.svg");
	IResourceSupplier TABLE_DETAILS = FACTORY.create("table-details.svg");
	IResourceSupplier UPLOAD = FACTORY.create("upload.svg");
	IResourceSupplier USER_PERMISSION_ASSIGNMENT = FACTORY.create("user-permission-assignment.svg");
	IResourceSupplier VIEW_SCOPE = FACTORY.create("view-scope.svg");
	IResourceSupplier WIZARD_NEXT = FACTORY.create("wizard-next.svg");
	IResourceSupplier WIZARD_PREVIOUS = FACTORY.create("wizard-previous.svg");
}
