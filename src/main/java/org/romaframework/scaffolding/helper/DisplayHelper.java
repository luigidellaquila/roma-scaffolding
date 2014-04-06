package org.romaframework.scaffolding.helper;


import org.romaframework.aspect.view.feature.ViewActionFeatures;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.HtmlViewAspect;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.core.Roma;
import org.romaframework.core.binding.Bindable;
import org.romaframework.core.schema.SchemaClass;
import org.romaframework.frontend.domain.wrapper.EnumWrapper;
import org.romaframework.frontend.domain.wrapper.SelectWrapper;
import org.romaframework.scaffolding.view.domain.simplesearch.SimpleSearch;

public class DisplayHelper {

	public static void enableSelectWrapper(Object obj, String field, boolean enable) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		ViewComponent thisForm = a.getFormByObject(obj);
		HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) thisForm.getFieldComponent(field);
		Roma.setFeature(form.getContent(), "list", ViewFieldFeatures.ENABLED, enable);
	}

	public static void enableEnumWrapper(Object obj, String field, boolean enable) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		ViewComponent thisForm = a.getFormByObject(obj);
		HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) thisForm.getFieldComponent(field);
		Roma.setFeature(form.getContent(), "list", ViewFieldFeatures.ENABLED, enable);
	}

	public static void enableHistoryView(Object obj, String field, boolean enable) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		ViewComponent thisForm = a.getFormByObject(obj);
		HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) thisForm.getFieldComponent(field);
		Roma.setFeature(form.getContent(), "wrapField", ViewFieldFeatures.ENABLED, enable);
	}

	public static void refreshSelectWrapper(Object obj, String field) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		ViewComponent thisForm = a.getFormByObject(obj);
		HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) thisForm.getFieldComponent(field);
		if (form != null) {
			Roma.fieldChanged(form.getContent(), "list");
		}
	}

	public static void refreshEnumWrapper(Object obj, String field) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		ViewComponent thisForm = a.getFormByObject(obj);
		HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) thisForm.getFieldComponent(field);
		Roma.fieldChanged(form.getContent(), "list");
	}

	public static void refreshSimpleSearch(Object obj, String field) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		ViewComponent thisForm = a.getFormByObject(obj);
		HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) thisForm.getFieldComponent(field);
		if (form != null) {
			Roma.fieldChanged(form.getContent(), "currentValue");
		}
	}

	public static void enableSimpleSearch(Object obj, String field, boolean enable) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		ViewComponent thisForm = a.getFormByObject(obj);
		HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) thisForm.getFieldComponent(field);
		try {
			Roma.setFeature(form.getContent(), "currentValue", ViewFieldFeatures.ENABLED, enable);
			Roma.setFeature(form.getContent(), "edit", ViewActionFeatures.ENABLED, enable);
			Roma.setFeature(form.getContent(), "reset", ViewActionFeatures.ENABLED, enable);
		} catch (Exception e) {
		}
	}

	public static void enableFields(Object obj, boolean enable) {
		HtmlViewAspect a = (HtmlViewAspect) Roma.view();
		HtmlViewContentForm thisForm = (HtmlViewContentForm) a.getFormByObject(obj);
		if (thisForm != null && thisForm.getChildren() != null) {
			for (HtmlViewGenericComponent form : thisForm.getChildren()) {
				if (form == null || form.getSchemaField() == null) {
					continue;
				}
				SchemaClass dw = form.getSchemaField().getFeature(ViewFieldFeatures.DISPLAY_WITH);
				if (dw != null && !dw.equals(Bindable.class)) {
					if (SimpleSearch.class.isAssignableFrom((Class<?>) dw.getLanguageType())) {
						enableSimpleSearch(obj, form.getSchemaField().getName(), enable);
					} else if (SelectWrapper.class.isAssignableFrom((Class<?>) dw.getLanguageType())) {
						enableSelectWrapper(obj, form.getSchemaField().getName(), enable);
					} else if (EnumWrapper.class.isAssignableFrom((Class<?>) dw.getLanguageType())) {
						enableEnumWrapper(obj, form.getSchemaField().getName(), enable);
					} 
				}
			}
		}
	}
}
