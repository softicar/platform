package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.EmfFieldAttributeList;
import com.softicar.platform.emf.attribute.field.foreign.entity.EmfForeignEntityAttribute;
import com.softicar.platform.emf.attribute.field.indirect.entity.foreign.EmfForeignIndirectEntityAttribute;
import com.softicar.platform.emf.attribute.field.indirect.entity.foreign.EmfForeignIndirectEntityAttributeCastException;
import com.softicar.platform.emf.attribute.field.string.EmfStringAttribute;
import com.softicar.platform.emf.attribute.transients.EmfTransientAttribute;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link IEmfAttributeList}.
 *
 * @author Oliver Richers
 */
public class EmfAttributeList<R extends IEmfTableRow<R, P>, P> implements IEmfAttributeList<R> {

	private final IEmfTable<R, P, ?> table;
	private final List<IEmfAttribute<R, ?>> attributes;
	private final Map<IDbField<R, ?>, EmfFieldAttribute<R, ?>> nativeFieldToAttributeMap;
	private final Map<ITransientField<R, ?>, EmfTransientAttribute<R, ?>> transientFieldToAttributeMap;

	public EmfAttributeList(IEmfTable<R, P, ?> table) {

		this.table = table;
		this.attributes = new ArrayList<>();
		this.nativeFieldToAttributeMap = new HashMap<>();
		this.transientFieldToAttributeMap = new HashMap<>();

		for (IDbField<R, ?> field: table.getAllFields()) {
			addFieldAttribute(field);
		}

		table.getTableSpecialization().initializeAttributeList(this);
	}

	@Override
	public final <V> IEmfAttribute<R, V> getAttribute(ISqlField<R, V> field) {

		return editAttribute(field);
	}

	@Override
	public final <V> IEmfAttribute<R, V> getAttribute(ITransientField<R, V> field) {

		return editAttribute(field);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final <V> EmfFieldAttribute<R, V> editAttribute(ISqlField<R, V> field) {

		EmfFieldAttribute<R, ?> attribute = nativeFieldToAttributeMap.get(field);
		if (attribute != null) {
			return (EmfFieldAttribute<R, V>) attribute;
		} else {
			throw new SofticarException("No matching attribute found for field: %s", field.getName());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public final <V> EmfTransientAttribute<R, V> editAttribute(ITransientField<R, V> field) {

		EmfTransientAttribute<R, ?> attribute = transientFieldToAttributeMap.get(field);
		if (attribute != null) {
			return (EmfTransientAttribute<R, V>) attribute;
		} else {
			throw new SofticarException("No matching attribute found for transient field.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmfFieldAttributeList<R> editAttributes(ISqlField<R, ?>...fields) {

		return new EmfFieldAttributeList<>(Arrays.asList(fields).stream().map(this::editAttribute).collect(Collectors.toList()));
	}

	@Override
	public final <F extends IEmfEntity<F, ?>> EmfForeignEntityAttribute<R, F> editEntityAttribute(ISqlForeignRowField<R, F, ?> field) {

		return (EmfForeignEntityAttribute<R, F>) editAttribute(field);
	}

	@Override
	public <F extends IEmfEntity<F, ?>> EmfForeignIndirectEntityAttribute<R, F> editIndirectEntityAttribute(ISqlForeignRowField<R, F, ?> field) {

		EmfFieldAttribute<R, F> attribute = editAttribute(field);
		if (attribute instanceof EmfForeignIndirectEntityAttribute) {
			return (EmfForeignIndirectEntityAttribute<R, F>) attribute;
		} else {
			throw new EmfForeignIndirectEntityAttributeCastException(attribute);
		}
	}

	@Override
	public final EmfStringAttribute<R> editStringAttribute(IDbField<R, String> field) {

		return (EmfStringAttribute<R>) editAttribute(field);
	}

	@Override
	public final <A extends IEmfAttribute<R, ?>> A addAttribute(A attribute) {

		attributes.add(attribute);
		return attribute;
	}

	@Override
	public final <F extends IEmfTableRow<F, ?>, V> EmfTransientAttribute<R, V> addTransientAttribute(ITransientField<R, V> transientField) {

		EmfTransientAttribute<R, V> attribute = new EmfTransientAttribute<>(table, transientField);
		attributes.add(attribute);
		transientFieldToAttributeMap.put(transientField, attribute);
		return attribute;
	}

	@Override
	public final <V> void setAttribute(IDbField<R, V> field, EmfFieldAttribute<R, V> attribute) {

		IEmfAttribute<R, ?> currentAttribute = nativeFieldToAttributeMap.remove(field);
		attributes.set(attributes.indexOf(currentAttribute), attribute);
		nativeFieldToAttributeMap.put(field, attribute);
	}

	public List<IEmfAttribute<R, ?>> getAttributes() {

		return attributes;
	}

	public EmfAttributeReorderer<R> createReorderer() {

		return new EmfAttributeReorderer<>(this);
	}

	private <V> void addFieldAttribute(IDbField<R, V> field) {

		EmfFieldAttribute<R, ?> attribute = new EmfAttributeFactory<>(field).create();
		attributes.add(attribute);
		nativeFieldToAttributeMap.put(field, attribute);
	}
}
