package org.romaframework.scaffolding.view.domain.util;

import java.util.List;

import org.romaframework.aspect.core.annotation.AnnotationConstants;
import org.romaframework.aspect.core.annotation.CoreClass;
import org.romaframework.aspect.view.ViewCallback;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.core.Roma;
import org.romaframework.core.binding.Bindable;
import org.romaframework.core.repository.PersistenceAspectRepository;
import org.romaframework.core.schema.SchemaClass;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.frontend.domain.crud.CRUDException;
import org.romaframework.frontend.domain.wrapper.ObjectWrapper;

@CoreClass(orderFields = "visibleItems totalItems currentPage totalPages ")
public class PagingTableEdit<T> implements ViewCallback, ObjectWrapper, Bindable {

	protected PersistenceAspectRepository<T>	repository;

	protected Object													object;
	protected String													selectionFieldName;

	@ViewField(visible = AnnotationConstants.FALSE)
	protected SchemaField											sourceField;

	private int																page					= 0;
	protected int															itemsPerPage	= 5;

	public PagingTableEdit() {
	}

	public void onShow() {
		// boolean value = Roma.getFeature(object, sourceField.getName(), ViewFieldFeatures.ENABLED);
		// Roma.setFeature(this, "visibleList", ViewFieldFeatures.ENABLED, value);

	}

	protected void init(Object iObject, String iSelectionField) {
		object = iObject;
		selectionFieldName = iSelectionField;
		SchemaClass cls = Roma.schema().getSchemaClass(iObject.getClass());
		sourceField = cls.getField(iSelectionField);

		if (sourceField == null)
			throw new CRUDException("Cannot find field name " + iObject.getClass().getSimpleName() + "." + iSelectionField + ". Check class definition");

	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public Object getFinalValue() throws Exception {
		return getItems();
	}

	public void onDispose() {
	}

	@Override
	public String toString() {
		return "";
	}

	public void onEnable() {
		// Roma.setFeature(this, "visibleList", ViewFieldFeatures.ENABLED, true);
	}

	public void onDisable() {
		// Roma.setFeature(this, "visibleList", ViewFieldFeatures.ENABLED, false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setSource(Object source, String fieldName) {
		this.object = source;
		this.selectionFieldName = fieldName;
		SchemaClass cls = Roma.schema().getSchemaClass(this.object.getClass());
		sourceField = cls.getField(this.selectionFieldName);

		if (sourceField == null)
			throw new CRUDException("Cannot find field name " + this.object.getClass().getSimpleName() + "." + this.selectionFieldName + ". Check class definition");

		refresh();

	}

	public void prev() {
		int oldPage = page;
		page = page == 0 ? 0 : page - 1;
		if (page == oldPage) {
			return;
		}
		refresh();
	}

	public void next() {
		int oldPage = page;
		page = (page + 1) * itemsPerPage < getTotalItems() ? page + 1 : page;
		if (page == oldPage) {
			return;
		}
		refresh();
	}

	protected void refresh() {
		Roma.fieldChanged(this, "visibleItems", "totalItems", "currentPage", "totalPages");
	}

	@ViewField(enabled = AnnotationConstants.FALSE)
	public int getTotalItems() {
		List<T> items = getItems();
		return items == null ? 0 : items.size();
	}

	@ViewField(enabled = AnnotationConstants.FALSE)
	public int getCurrentPage() {
		return page + 1;
	}

	@ViewField(enabled = AnnotationConstants.FALSE)
	public int getTotalPages() {
		return getTotalItems() % itemsPerPage == 0 ? getTotalItems() / itemsPerPage : (getTotalItems() / itemsPerPage) + 1;
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	protected List<T> getItems() {
		return (List<T>) sourceField.getValue(object);
	}

	@ViewField(render = ViewConstants.RENDER_TABLEEDIT, label = "", enabled = AnnotationConstants.FALSE)
	public List<T> getVisibleItems() {
		List<T> items = getItems();
		if (items == null) {
			return null;
		}

		return items.subList(page * itemsPerPage, Math.min((page + 1) * itemsPerPage, items.size()));
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public Object getSourceObject() {
		return object;
	}

	public SchemaField getSourceField() {
		return sourceField;
	}

}
