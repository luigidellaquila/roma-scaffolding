/*
 *
 * Copyright 2012 Luca Molino (luca.molino--AT--assetdata.it)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.romaframework.scaffolding.view.domain.util;


import org.romaframework.aspect.core.annotation.AnnotationConstants;
import org.romaframework.aspect.view.ViewCallback;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.annotation.ViewAction;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.core.Roma;
import org.romaframework.core.binding.Bindable;
import org.romaframework.core.schema.Feature;
import org.romaframework.core.schema.SchemaClass;
import org.romaframework.core.schema.SchemaFeaturesChangeListener;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaHelper;
import org.romaframework.scaffolding.listener.RefreshManager;

/**
 * @author luca.molino
 * 
 */
public class YesNoSelectWrapper implements Bindable, ViewCallback {

	private Object			parent;
	private String			fieldName;
	private SchemaField	field;

	@ViewField(render = ViewConstants.RENDER_RADIO, label = "", selectionField = "selectedElement")
	protected String[]	selection	= new String[] { Roma.i18n().resolve("$true.label"), Roma.i18n().resolve("$false.label") };

	public YesNoSelectWrapper() {
	}

	public YesNoSelectWrapper(Object parent, String fieldName) {
		setSource(parent, fieldName);
	}

	public String[] getSelection() {
		return selection;
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public String getSelectedElement() {
		if (parent != null) {
			return convertBooleanToI18nString((Boolean) SchemaHelper.getFieldValue(parent, fieldName));
		} else {
			return Roma.i18n().resolve("$false.label");
		}
	}

	private String convertBooleanToI18nString(Boolean fieldValue) {
		if (Boolean.TRUE.equals(fieldValue)) {
			return "Yes";
		}
		return "No";
	}

	public void setSelectedElement(String selectedElement) {
		if (parent != null) {
			SchemaHelper.setFieldValue(parent, fieldName, convertI18nStringToBoolean(selectedElement));
		}
	}

	private Object convertI18nStringToBoolean(String selectedElement) {
		if ("yes".equals(("" + selectedElement.toString()).toLowerCase())) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return getSelectedElement() != null ? getSelectedElement().toString() : "";
	}

	public void setSource(Object parent, String fieldName) {
		SchemaClass schemaClass = Roma.schema().getSchemaClass(parent.getClass());
		this.parent = parent;
		this.fieldName = fieldName;
		this.field = schemaClass.getField(fieldName);
		final Class<?> fieldType = ((Class<?>) this.field.getLanguageType());
		final YesNoSelectWrapper wrapper = this;
		Roma.component(RefreshManager.class).register(parent, fieldName, new SchemaFeaturesChangeListener() {

			@Override
			public <T> void signalChangeField(Object iUserObject, String iFieldName, Feature<T> iFeature, T iOldValue, T iFeatureValue) {
				Roma.setFeature(wrapper, "selection", iFeature, iFeatureValue);
			}

			@Override
			public <T> void signalChangeClass(Object iUserObject, Feature<T> iFeature, T iOldValue, T iFeatureValue) {
			}

			@Override
			public <T> void signalChangeAction(Object iUserObject, String iActionName, Feature<T> iFeature, T iOldValue, T iFeatureValue) {
			}
		});
		if ((fieldType.isPrimitive() && ((Class<?>) this.field.getLanguageType()).equals(boolean.class)) || ((Class<?>) this.field.getLanguageType()).isAssignableFrom(Boolean.class)) {
			Roma.fieldChanged(this, "selection");
		} else {
			throw new IllegalArgumentException("trying to use a BooleanWrapper on an object that is not an Boolean");
		}
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public Object getSourceObject() {
		return parent;
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public SchemaField getSourceField() {
		return this.field;
	}

	@ViewAction(visible = AnnotationConstants.FALSE)
	public void refresh() {
		Roma.fieldChanged(this, "selection");
	}

	@Override
	public void onShow() {
		boolean value = Roma.getFeature(parent, field.getName(), ViewFieldFeatures.ENABLED);
		Roma.setFeature(this, "selection", ViewFieldFeatures.ENABLED, value);
	}

	@Override
	public void onDispose() {

	}
}
