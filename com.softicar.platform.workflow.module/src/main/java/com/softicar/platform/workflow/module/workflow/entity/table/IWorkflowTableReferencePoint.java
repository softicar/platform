package com.softicar.platform.workflow.module.workflow.entity.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Optional;

/**
 * A special {@link IEmfSourceCodeReferencePoint} referring to an
 * {@link IEmfEntityTable} used in workflows.
 *
 * @author Oliver Richers
 */
public interface IWorkflowTableReferencePoint<E extends IEmfEntity<E, ?>> extends IEmfSourceCodeReferencePoint {

	@Override
	default IDisplayString toDisplay() {

		return getTable().getTitle();
	}

	/**
	 * Returns the {@link IEmfEntity} represented by the given
	 * {@link AGWorkflowItem}.
	 *
	 * @param workflowItem
	 *            the {@link AGWorkflowItem} (never <i>null</i>)
	 * @return the respective entity as {@link Optional}
	 * @throws MultipleEntitiesForWorkflowItemException
	 *             if multiple entities were found
	 */
	default Optional<E> getEntity(AGWorkflowItem workflowItem) {

		return getTable()//
			.createSelect()
			.where(getItemField().isEqual(workflowItem))
			.getOneAsOptional(() -> new MultipleEntitiesForWorkflowItemException(workflowItem));
	}

	/**
	 * Returns the {@link IEmfEntity} represented by the given
	 * {@link AGWorkflowItem}.
	 *
	 * @param workflowItem
	 *            the {@link AGWorkflowItem} (never <i>null</i>)
	 * @return the respective entity (never <i>null</i>)
	 * @throws MissingEntityForWorkflowItemException
	 *             if no matching entity was found
	 * @throws MultipleEntitiesForWorkflowItemException
	 *             if multiple entities were found
	 */
	default E getEntityOrThrow(AGWorkflowItem workflowItem) {

		return getEntity(workflowItem).orElseThrow(() -> new MissingEntityForWorkflowItemException(workflowItem));
	}

	/**
	 * Returns the {@link IEmfTable} represented by this
	 * {@link IWorkflowTableReferencePoint}.
	 *
	 * @return the {@link IEmfTable} (never <i>null</i>)
	 */
	IEmfTable<E, ?, ?> getTable();

	/**
	 * Returns the {@link IDbForeignField} of the {@link IEmfTable} referencing
	 * the {@link AGWorkflowItem}.
	 *
	 * @return the {@link IDbForeignField} (never <i>null</i>)
	 */
	IDbForeignField<E, AGWorkflowItem> getItemField();
}
