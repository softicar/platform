package com.softicar.platform.core.module.file.stored.repository;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AGStoredFileRepository extends AGStoredFileRepositoryGenerated implements IEmfObject<AGStoredFileRepository> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getUrl());
	}

	public boolean isPrimary() {

		return getPrimary()//
			.map(it -> it.equals(this))
			.orElse(false);
	}

	public static List<AGStoredFileRepository> getAllActiveWithPrimaryFirst() {

		List<AGStoredFileRepository> repositories = new ArrayList<>();
		Optional<AGStoredFileRepository> primaryRepository = getPrimary().filter(AGStoredFileRepository::isActive);
		primaryRepository.ifPresent(repositories::add);
		AGStoredFileRepository.TABLE//
			.createSelect()
			.where(AGStoredFileRepository.ACTIVE)
			.whereIf(primaryRepository.isPresent(), () -> AGStoredFileRepository.ID.isNotEqual(primaryRepository.get()))
			.orderBy(AGStoredFileRepository.ID)
			.forEach(repositories::add);
		return repositories;
	}

	public static Optional<AGStoredFileRepository> getPrimary() {

		return Optional//
			.ofNullable(AGCoreModuleInstance.getInstance())
			.map(AGCoreModuleInstance::getPrimaryFileRepository);
	}
}
