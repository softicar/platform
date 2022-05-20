package com.softicar.platform.demo.invoice.module.contact;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.DemoInvoiceModule;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

/**
 * The purpose of this class is to show an {@link IEmfDataTableDiv} which is
 * based on an {@link IDbQuery}, showing an foreign key column with null values.
 *
 * @author Oliver Richers
 */
@EmfSourceCodeReferencePointUuid("adb2b956-1397-4b3d-a9bc-c3683221d66b")
public class DemoInvoiceContactReportPage implements IEmfPage<AGDemoInvoiceModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoInvoiceModuleInstance>> getModuleClass() {

		return DemoInvoiceModule.class;
	}

	@Override
	public IDomNode createContentNode(AGDemoInvoiceModuleInstance moduleInstance) {

		var query = IDemoInvoiceContactReportQuery.FACTORY//
			.createQuery()
			.setModuleInstance(moduleInstance);

		return new EmfDataTableDivBuilder<>(query)//
			.addTableMarker(AGDemoInvoice.TABLE)
			.addColumnMarker(IDemoInvoiceContactReportQuery.CONTACT_COLUMN, IDemoInvoiceContactReportQuery.CONTACT_COLUMN)
			.build();
	}

	@Override
	public IDisplayString getTitle(AGDemoInvoiceModuleInstance moduleInstance) {

		return DemoI18n.DEMO_INVOICE_CONTACT_REPORT;
	}

	@Override
	public IEmfRole<AGDemoInvoiceModuleInstance> getAuthorizedRole() {

		return EmfRoles.anybody();
	}
}
