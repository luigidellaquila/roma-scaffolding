/*
 *
 * Copyright 2013 Luca Molino (luca.molino--AT--assetdata.it)
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

import java.util.ArrayList;
import java.util.List;

import org.romaframework.aspect.core.annotation.AnnotationConstants;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.annotation.ViewAction;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.core.Roma;
import org.romaframework.core.binding.Bindable;
import org.romaframework.core.schema.SchemaClass;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaHelper;

/**
 * @author luca.molino
 * 
 */
public class EnumWrapperSchemaFree implements Bindable {

	protected Class<?>		enumClass;
	protected Object			parent;
	protected String			fieldName;
	protected SchemaField	field;

	@ViewField(render = ViewConstants.RENDER_SELECT, selectionField = "selectedElement", label = "")
	private List<Object>	list;

	public EnumWrapperSchemaFree(Class<?> iEnumClass, Object parent, String fieldName) {
		enumClass = iEnumClass;
		setSource(parent, fieldName);
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	@ViewField(visible = AnnotationConstants.FALSE)
	public Object getSelectedElement() {
		if (parent != null) {
			return (Object) SchemaHelper.getFieldValue(parent, fieldName);
		} else {
			return null;
		}
	}

	public void setSelectedElement(Object selectedElement) {
		if (parent != null) {
			SchemaHelper.setFieldValue(parent, fieldName, selectedElement);
		}
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
		if (!enumClass.isEnum()) {
			throw new IllegalArgumentException("trying to use an EnumWrapper on an object that is not an enum");
		}
		list = new ArrayList<Object>();
		for (Object elem : enumClass.getEnumConstants()) {
			list.add(elem);
		}
		Roma.fieldChanged(this, "list");
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
		Roma.fieldChanged(this, "list");
	}

}
