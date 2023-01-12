package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.common.core.uuid.IUuidAnnotated;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import java.util.Collection;
import java.util.UUID;

// TODO rename AGUuid into AGSourceCodeReferencePoint
public class AGUuid extends AGUuidGenerated implements IUuid, IEmfObject<AGUuid> {

	private static final Singleton<UuidCache> CACHE = new Singleton<>(UuidCache::new);

	@Override
	public IDisplayString toDisplayWithoutId() {

		return SourceCodeReferencePoints//
			.getReferencePoint(getUuid())
			.map(ISourceCodeReferencePoint::toDisplay)
			.orElse(getStaticPermissionTitle(getUuid()));
	}

	@Override
	public UUID getUuid() {

		return UUID.fromString(getUuidString());
	}

	public static AGUuid getOrCreate(UUID uuid) {

		return CACHE.get().getOrInsertUuid(uuid);
	}

	/**
	 * Converts this {@link AGUuid} into the {@link ISourceCodeReferencePoint}
	 * of the given {@link Class}.
	 *
	 * @param <T>
	 *            the type of {@link ISourceCodeReferencePoint}
	 * @param referencePointClass
	 *            the {@link Class} of the {@link ISourceCodeReferencePoint}
	 *            (never <i>null</i>)
	 * @return the converted {@link ISourceCodeReferencePoint} (never
	 *         <i>null</i>)
	 * @throws RuntimeException
	 *             if the conversion failed
	 */
	public <T extends ISourceCodeReferencePoint> T toReferencePoint(Class<T> referencePointClass) {

		return SourceCodeReferencePoints.getReferencePointOrThrow(getUuid(), referencePointClass);
	}

	public static AGUuid getOrCreate(ISourceCodeReferencePoint referencePoint) {

		return getOrCreate(referencePoint.getAnnotatedUuid());
	}

	public static AGUuid getOrCreate(Class<? extends ISourceCodeReferencePoint> referencePointClass) {

		return getOrCreate(SourceCodeReferencePoints.getUuidOrThrow(referencePointClass));
	}

	public static <T extends IDisplayable & IUuidAnnotated> UuidIndirectEntityCollection<T> createIndirectEntityCollection(Collection<T> elements) {

		return new UuidIndirectEntityCollection<>(elements);
	}

	// FIXME Should be replaced when permissions can be created at runtime
	private IDisplayString getStaticPermissionTitle(UUID uuid) {

		return CurrentEmfPermissionRegistry//
			.get()
			.getStaticPermission(uuid)
			.map(IEmfStaticPermission::getTitle)
			.orElse(IDisplayString.create(getUuidString()));
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}
