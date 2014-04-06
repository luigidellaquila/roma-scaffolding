package org.romaframework.scaffolding.view.domain.simplesearch;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.romaframework.aspect.core.annotation.AnnotationConstants;
import org.romaframework.aspect.core.annotation.CoreClass;
import org.romaframework.aspect.core.feature.CoreClassFeatures;
import org.romaframework.aspect.persistence.PersistenceAspect;
import org.romaframework.aspect.persistence.QueryByFilter;
import org.romaframework.aspect.persistence.QueryByFilterItem;
import org.romaframework.aspect.persistence.QueryByFilterItemGroup;
import org.romaframework.aspect.persistence.QueryOperator;
import org.romaframework.aspect.view.ViewCallback;
import org.romaframework.aspect.view.annotation.ViewAction;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.aspect.view.feature.ViewActionFeatures;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.core.Roma;
import org.romaframework.core.binding.Bindable;
import org.romaframework.core.schema.SchemaClass;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaHelper;
import org.romaframework.core.util.parser.ObjectVariableResolver;

@CoreClass(orderFields = "searchFilter searchResult")
public class SimpleSearch implements ViewCallback, Bindable {

	@ViewField(visible = AnnotationConstants.FALSE)
	protected boolean										enabled	= true;

	@ViewField(visible = AnnotationConstants.FALSE)
	protected Object										parent;
	@ViewField(visible = AnnotationConstants.FALSE)
	protected String										fieldName;
	@ViewField(visible = AnnotationConstants.FALSE)
	protected Class<?>									fieldClass;
	@ViewField(visible = AnnotationConstants.FALSE)
	protected String[]									searchFields;

	private String[]										orders;

	private int													limit		= 100;

	@ViewField(visible = AnnotationConstants.FALSE)
	protected boolean										editing	= false;

	private QueryByFilterItem[]					additionalFilters;

	@ViewField(label = "")
	private String											searchFilter;
	@ViewField(selectionField = "selectedElement", label = "", enabled = AnnotationConstants.FALSE)
	private List<SimpleSearchListable>	searchResult;
	@ViewField(visible = AnnotationConstants.FALSE)
	private SimpleSearchListable				selectedElement;

	public SimpleSearch() {
	}

	public SimpleSearch(Object parent, String fieldName, String[] searchFields, Class<?> fieldClass, QueryByFilterItem[] additionalFilters, String[] orders) {
		this.parent = parent;
		this.fieldName = fieldName;
		this.searchFields = searchFields;
		this.fieldClass = fieldClass;
		this.additionalFilters = additionalFilters;
		this.orders = orders;
	}

	public SimpleSearch(Object parent, String fieldName, String[] searchFields, Class<?> fieldClass, QueryByFilterItem[] additionalFilters) {
		this(parent, fieldName, searchFields, fieldClass, additionalFilters, null);
	}

	public SimpleSearch(Object parent, String fieldName, String[] searchFields, QueryByFilterItem[] additionalFilters, String[] orders) {
		this(parent, fieldName, searchFields, null, additionalFilters, orders);
	}

	public SimpleSearch(Object parent, String fieldName, String[] searchFields, QueryByFilterItem[] additionalFilters) {
		this(parent, fieldName, searchFields, null, additionalFilters, null);
	}

	public SimpleSearch(Object parent, String fieldName, String[] searchFields, Class<?> fieldClass) {
		this(parent, fieldName, searchFields, fieldClass, null, null);
	}

	public SimpleSearch(Object parent, String fieldName, String[] searchFields) {
		this(parent, fieldName, searchFields, null, null, null);
	}

	@ViewAction(label = "")
	public void confirm() {
		if (getSelectedElement() == null) {
			return;
		}
		SchemaHelper.setFieldValue(parent, fieldName, getSelectedElement().getContent());
		editing = false;
		refresh();
	}

	@ViewAction(label = "")
	public void cancel() {
		editing = false;
		refresh();
	}

	@ViewAction(label = "")
	public void edit() {
		this.selectedElement = null;
		this.searchResult = null;
		editing = true;
		refresh();
	}

	@ViewAction(label = "")
	public void reset() {
		SchemaHelper.setFieldValue(parent, fieldName, null);
		refresh();
	}

	@ViewAction(submit = AnnotationConstants.TRUE, label = "")
	public void search() {
		QueryByFilter query = createQuery();
		List<Object> result = Roma.context().persistence().query(query);
		this.searchResult = new ArrayList<SimpleSearchListable>();
		for (Object o : result) {
			this.searchResult.add(new SimpleSearchListable(o));
		}
		if (this.searchResult.size() > 0) {
			this.selectedElement = this.searchResult.get(0);
		} else {
			this.selectedElement = null;
		}
		Roma.fieldChanged(this, "searchResult");
	}

	public void onShow() {
		refresh();
	}

	@ViewAction(visible = AnnotationConstants.FALSE)
	public void refresh() {
		Roma.setFeature(this, "searchFilter", ViewFieldFeatures.VISIBLE, enabled && editing);
		Roma.setFeature(this, "searchResult", ViewFieldFeatures.VISIBLE, enabled && editing);
		Roma.setFeature(this, "currentValue", ViewFieldFeatures.VISIBLE, (!enabled) || (!editing));

		Roma.setFeature(this, "reset", ViewActionFeatures.VISIBLE, enabled && !editing);
		Roma.setFeature(this, "edit", ViewActionFeatures.VISIBLE, enabled && !editing);
		Roma.setFeature(this, "cancel", ViewActionFeatures.VISIBLE, enabled && editing);
		Roma.setFeature(this, "confirm", ViewActionFeatures.VISIBLE, enabled && editing);
		Roma.setFeature(this, "search", ViewActionFeatures.VISIBLE, enabled && editing);

		Roma.fieldChanged(this, "currentValue");
		Roma.fieldChanged(this, "searchFilter");
		Roma.fieldChanged(this, "searchResult");

		refreshTooltip();
	}

	private void refreshTooltip() {
		String attr = Roma.getFeature(parent, fieldName, ViewFieldFeatures.DESCRIPTION);
		Object currentValue = SchemaHelper.getFieldValue(parent, fieldName);
		if (currentValue == null)		return;
		String tooltip = null;
		if (attr != null && !("".equals(attr)) && !attr.equals(AnnotationConstants.DEF_VALUE))	tooltip = new ObjectVariableResolver(attr).resolveVariables(currentValue);
		else tooltip = new ObjectVariableResolver(currentValue.toString()).resolveVariables(attr);
		Roma.setFeature(this, "currentValue", ViewFieldFeatures.DESCRIPTION, tooltip);
	}

	protected QueryByFilter createQuery() {
		QueryByFilter query = new QueryByFilter(getFieldClass());
		query.setStrategy(PersistenceAspect.STRATEGY_DETACHING);
		if (searchFilter == null) {
			searchFilter = "";
		}
		String[] splittato = searchFilter.split(" ");
		for (String token : splittato) {
			QueryByFilterItemGroup subfilter = new QueryByFilterItemGroup(QueryByFilter.PREDICATE_OR);
			for (String field : searchFields) {
				if (token.length() > 0) {
					subfilter.addItem(field, QueryByFilter.FIELD_LIKE, token);
				}
			}
			if (!subfilter.getItems().isEmpty()) {
				query.addItem(subfilter);
			}
		}
		if (additionalFilters != null) {
			for (QueryByFilterItem item : additionalFilters) {
				query.addItem(item);
			}
		}

		if (orders != null) {
			for (String order : orders) {
				query.addOrder(order);
			}
		} else {
			SchemaClass sc = getSchemaField().getClassInfo();
			if (sc.isSettedFeature(CoreClassFeatures.ORDER_FIELDS)) {
				String[] orders = sc.getFeature(CoreClassFeatures.ORDER_FIELDS);
				for (String string : orders) {
					query.addOrder(string);
				}
			} else {
				for (String order : searchFields) {
					query.addOrder(order);
				}
			}
		}

		query.setRangeFrom(0, limit);
		return query;
	}

	@ViewField(label = "")
	public String getCurrentValue() {
		Object currentValue = SchemaHelper.getFieldValue(parent, fieldName);
		return currentValue == null ? "" : currentValue.toString();
	}

	public void onCurrentValueFocus() {
		edit();
	}

	public List<SimpleSearchListable> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<SimpleSearchListable> searchResult) {
		this.searchResult = searchResult;
	}

	public SimpleSearchListable getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(SimpleSearchListable selectedElement) {
		this.selectedElement = selectedElement;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public Class<?> getFieldClass() {
		if (fieldClass != null) {
			return fieldClass;
		} else {
			return (Class<?>) getSchemaField().getLanguageType();
		}

	}

	public void setFieldClass(Class<?> fieldClass) {
		this.fieldClass = fieldClass;
	}

	protected SchemaField getSchemaField() {
		return Roma.schema().getSchemaClass(parent.getClass()).getField(fieldName);
	}

	public void onDispose() {
	}

	@Override
	public String toString() {
		Object val = getCurrentValue();
		return val == null ? " - " : val.toString();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		refresh();
	}

	public void setSource(Object iSourceObject, String iSourceFieldName) {
		this.parent = iSourceObject;
		this.fieldName = iSourceFieldName;
		initDefaultSearchFields();
	}

	private void initDefaultSearchFields() {
		SchemaClass entityClass = getSchemaField().getType().getSchemaClass();
		Iterator<SchemaField> it = entityClass.getFieldIterator();
		List<String> fields = new ArrayList<String>();
		while (it.hasNext()) {
			SchemaField sf = it.next();
			if (String.class.equals(sf.getLanguageType()) && Roma.context().persistence().isFieldPersistent(sf))
				// && Roma.context().persistence().isFieldPersistent((Class<?>) entityClass.getLanguageType(), sf))
				fields.add(sf.getName());
		}
		this.searchFields = fields.toArray(new String[0]);
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public Object getSourceObject() {
		return parent;
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public SchemaField getSourceField() {
		return getSchemaField();
	}
}
