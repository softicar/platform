package com.softicar.platform.workflow.module.standard.configuration;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.core.module.configuration.AbstractStandardConfiguration;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.workflow.image.AGWorkflowIcon;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class WorkflowIconStandardConfiguration extends AbstractStandardConfiguration {

	private final AGWorkflowModuleInstance workflowModuleInstance;

	public WorkflowIconStandardConfiguration(AGWorkflowModuleInstance workflowModuleInstance) {

		this.workflowModuleInstance = workflowModuleInstance;
	}

	@Override
	public void createAndSaveAll() {

		try (DbTransaction transaction = new DbTransaction()) {
			List<AGStoredFile> files = new ArrayList<>();
			List<AGWorkflowIcon> icons = new ArrayList<>();
			Map<AGStoredFile, IResource> fileToResourceMap = new IdentityHashMap<>();

			for (IResourceSupplier resourceSupplier: IResourceSupplier.getResourceSuppliers(DomImages.class)) {
				IResource resource = resourceSupplier.getResource();
				AGStoredFile file = new AGStoredFile()//
					.setFileName(resource.getFilename().orElseThrow())
					.setContentType(resource.getMimeType().getIdentifier())
					.setCreatedBy(AGUser.getSystemUser());
				files.add(file);
				fileToResourceMap.put(file, resource);
				AGWorkflowIcon icon = new AGWorkflowIcon()//
					.setModuleInstance(workflowModuleInstance)
					.setName(resource.getFilename().map(filename -> filename.replaceFirst("\\.[a-z]+", "")).orElseThrow())
					.setIcon(file);
				icons.add(icon);
			}

			saveAll(files);
			saveAll(icons);

			for (var file: files) {
				file.uploadFileContent(new ByteArrayInputStream(getBytesFromResource(fileToResourceMap.get(file))));
			}
			transaction.commit();
		}
	}

	private byte[] getBytesFromResource(IResource resource) {

		try (InputStream inputStream = resource.getResourceAsStream()) {
			return StreamUtils.toByteArray(inputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
