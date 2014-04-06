package org.romaframework.scaffolding.view.domain.header;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.romaframework.aspect.core.annotation.AnnotationConstants;
import org.romaframework.aspect.i18n.I18NType;
import org.romaframework.aspect.view.ViewCallback;
import org.romaframework.aspect.view.annotation.ViewAction;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.core.Roma;
import org.romaframework.core.schema.SchemaClass;
import org.romaframework.core.schema.SchemaClassResolver;
import org.romaframework.core.schema.SchemaHelper;

public class MenuElementContainer<T extends AbstractMenuElement> implements ViewCallback {

	private SchemaClass		clazz;
	private Comparator<T>	comparator;
	private List<T>				elements;
	@ViewField(visible = AnnotationConstants.FALSE)
	private T							current;

	protected MenuElementContainer() {
		this.elements = new ArrayList<T>();
		this.clazz = SchemaHelper.getSuperclassGenericType(this.getClass());
		populate();
	}

	protected MenuElementContainer(Comparator<T> comparator) {
		this.elements = new ArrayList<T>();
		this.clazz = SchemaHelper.getSuperclassGenericType(this.getClass());
		this.comparator = comparator;
		populate();
	}

	public MenuElementContainer(Class<? extends T> clazz) {
		this(Roma.schema().getSchemaClass(clazz));
	}

	public MenuElementContainer(Class<? extends T> clazz, Comparator<T> comparator) {
		this(Roma.schema().getSchemaClass(clazz), comparator);
	}

	public MenuElementContainer(SchemaClass schemaClass) {
		this(schemaClass, null);
	}

	public MenuElementContainer(SchemaClass schemaClass, Comparator<T> comparator) {
		this.elements = new ArrayList<T>();
		this.clazz = schemaClass;
		this.comparator = comparator;
		populate();
	}

	@ViewField(label = "")
	public List<T> getElements() {
		return elements;
	}

	@ViewAction(visible = AnnotationConstants.FALSE)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populate() {
		List<Class<T>> moduleClasses;
		moduleClasses = (List) Roma.component(SchemaClassResolver.class).getLanguageClassByInheritance((Class<?>) clazz.getLanguageType());
		for (Class<T> class1 : moduleClasses) {
			if (!Modifier.isAbstract(class1.getModifiers()) && !Modifier.isInterface(class1.getModifiers())) {
				try {
					elements.add(class1.newInstance());
				} catch (Exception e) {
					throw new RuntimeException("Error on creating pluggable class " + clazz, e);
				}
			}
		}
		if (comparator != null)
			Collections.sort((List) elements, comparator);
		else if (clazz.isAssignableAs(Comparable.class))
			Collections.sort((List) elements);
		for (AbstractMenuElement instanceTab : elements) {
			String s = Roma.i18n().get(instanceTab, I18NType.LABEL);
			updateElement(s, instanceTab);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateElement(String s, AbstractMenuElement element) {
		element.setLabel(s);
		element.setContainer((MenuElementContainer<AbstractMenuElement>) this);
	}

	public void onShow() {
		Roma.setFeature(this, "elements", ViewFieldFeatures.RENDER, "renderheadermenuelements");
	}

	public void onDispose() {
	}

	@SuppressWarnings("unchecked")
	public void setCurrent(AbstractMenuElement abstractMenuElement) {
		if (current != null) {
			current.clear();
		}
		current = (T) abstractMenuElement;
		Roma.fieldChanged(this, "elements");
	}

}
