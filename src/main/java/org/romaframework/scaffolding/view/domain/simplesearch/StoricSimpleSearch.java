package org.romaframework.scaffolding.view.domain.simplesearch;

import java.util.Date;

import org.romaframework.aspect.persistence.QueryByFilter;
import org.romaframework.aspect.persistence.QueryByFilterItem;
import org.romaframework.aspect.persistence.QueryByFilterItemGroup;
import org.romaframework.aspect.persistence.QueryOperator;

public class StoricSimpleSearch extends SimpleSearch {

	public StoricSimpleSearch() {
		super();
	}

	public StoricSimpleSearch(Object parent, String fieldName, String[] searchFields, Class<?> fieldClass, QueryByFilterItem[] additionalFilters, String[] orders) {
		super(parent, fieldName, searchFields, fieldClass, additionalFilters, orders);
	}

	public StoricSimpleSearch(Object parent, String fieldName, String[] searchFields, Class<?> fieldClass, QueryByFilterItem[] additionalFilters) {
		super(parent, fieldName, searchFields, fieldClass, additionalFilters);
	}

	public StoricSimpleSearch(Object parent, String fieldName, String[] searchFields, Class<?> fieldClass) {
		super(parent, fieldName, searchFields, fieldClass);
	}

	public StoricSimpleSearch(Object parent, String fieldName, String[] searchFields, QueryByFilterItem[] additionalFilters, String[] orders) {
		super(parent, fieldName, searchFields, additionalFilters, orders);
	}

	public StoricSimpleSearch(Object parent, String fieldName, String[] searchFields, QueryByFilterItem[] additionalFilters) {
		super(parent, fieldName, searchFields, additionalFilters);
	}

	public StoricSimpleSearch(Object parent, String fieldName, String[] searchFields) {
		super(parent, fieldName, searchFields);
	}

	@Override
	protected QueryByFilter createQuery() {
		QueryByFilter result = super.createQuery();
		Date today = new Date();
		result.addItem("validSince", QueryOperator.MINOR, today);
		QueryByFilterItemGroup untilGroup = new QueryByFilterItemGroup(QueryByFilter.PREDICATE_OR);
		untilGroup.addItem("validUntil", QueryOperator.EQUALS, null);
		untilGroup.addItem("validUntil", QueryOperator.MAJOR_EQUALS, today);
		result.addItem(untilGroup);
		return result;
	}
}
