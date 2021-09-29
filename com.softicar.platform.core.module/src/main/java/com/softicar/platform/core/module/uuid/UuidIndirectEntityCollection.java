package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.uuid.IUuidAnnotated;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UuidIndirectEntityCollection<T extends IDisplayable & IUuidAnnotated> extends ArrayList<UuidIndirectEntity<T>> {

	public UuidIndirectEntityCollection(Collection<T> elements) {

		super(elements.stream().map(UuidIndirectEntity::new).collect(Collectors.toList()));
	}

	/**
	 * FIXME This method solely exists because we need to determine the
	 * {@link IDisplayString} for an indirect entity. We should have a more
	 * efficient way to do that (e.g. a UUID-to-IDisplayString cache).
	 */
	public Optional<UuidIndirectEntity<T>> getIndirectEntity(UUID uuid) {

		return stream()//
			.filter(new Matcher(uuid)::matches)
			.findAny();
	}

	private class Matcher {

		private final UUID uuid;

		public Matcher(UUID uuid) {

			this.uuid = Objects.requireNonNull(uuid);
		}

		public boolean matches(UuidIndirectEntity<T> indirectEntity) {

			return indirectEntity.getRepresentedEntity().getUuid().equals(uuid);
		}
	}
}
