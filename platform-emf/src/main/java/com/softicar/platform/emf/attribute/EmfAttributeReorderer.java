package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.container.list.ListElementsMover;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EmfAttributeReorderer<R extends IEmfTableRow<R, ?>> {

	private final IEmfAttributeList<R> attributeList;
	private final List<IEmfAttribute<R, ?>> attributes;

	public EmfAttributeReorderer(EmfAttributeList<R, ?> attributeList) {

		this.attributeList = attributeList;
		this.attributes = attributeList.getAttributes();
	}

	public final Mover moveAttribute(IDbField<R, ?> field) {

		return moveAttribute(attributeList.getAttribute(field));
	}

	public final Mover moveAttribute(ITransientField<R, ?> transientField) {

		return moveAttribute(attributeList.getAttribute(transientField));
	}

	@SafeVarargs
	public final Mover moveAttributes(ITransientField<R, ?>...transientFields) {

		return moveAttributes(Arrays.asList(transientFields).stream().map(attributeList::getAttribute).collect(Collectors.toList()));
	}

	public final Mover moveAttribute(IEmfAttribute<R, ?> attribute) {

		return new Mover(Collections.singletonList(attribute));
	}

	public final Mover moveAttributes(Collection<IEmfAttribute<R, ?>> attributes) {

		return new Mover(attributes);
	}

	@SafeVarargs
	public final Mover moveAttributes(IDbField<R, ?>...fields) {

		return moveAttributes(Arrays.asList(fields).stream().map(attributeList::getAttribute).collect(Collectors.toList()));
	}

	@SafeVarargs
	public final Mover moveAttributes(IEmfAttribute<R, ?>...attributes) {

		return moveAttributes(Arrays.asList(attributes));
	}

	public class Mover {

		private final Collection<IEmfAttribute<R, ?>> moverAttributes;

		public Mover(Collection<IEmfAttribute<R, ?>> attributes) {

			this.moverAttributes = attributes;
		}

		public EmfAttributeReorderer<R> inFrontOf(IEmfAttribute<R, ?> attribute) {

			new ListElementsMover<>(attributes, moverAttributes).moveInFrontOf(attribute);
			return EmfAttributeReorderer.this;
		}

		public EmfAttributeReorderer<R> inFrontOf(IDbField<R, ?> field) {

			return inFrontOf(attributeList.getAttribute(field));
		}

		public EmfAttributeReorderer<R> behind(IEmfAttribute<R, ?> attribute) {

			new ListElementsMover<>(attributes, moverAttributes).moveBehind(attribute);
			return EmfAttributeReorderer.this;
		}

		public EmfAttributeReorderer<R> behind(IDbField<R, ?> field) {

			return behind(attributeList.getAttribute(field));
		}

		public EmfAttributeReorderer<R> toFront() {

			new ListElementsMover<>(attributes, moverAttributes).moveToFront();
			return EmfAttributeReorderer.this;
		}

		public EmfAttributeReorderer<R> toBack() {

			new ListElementsMover<>(attributes, moverAttributes).moveToBack();
			return EmfAttributeReorderer.this;
		}
	}
}
