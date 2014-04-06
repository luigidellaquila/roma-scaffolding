package org.romaframework.scaffolding.view.domain.header;

import org.romaframework.aspect.core.annotation.AnnotationConstants;
import org.romaframework.aspect.view.ViewCallback;
import org.romaframework.aspect.view.annotation.ViewAction;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.aspect.view.feature.ViewActionFeatures;
import org.romaframework.aspect.view.feature.ViewClassFeatures;
import org.romaframework.core.Roma;

public abstract class AbstractMenuElement implements ViewCallback{
	//voce selezionata e non selezionata
	private static final String	ACTION_SELECTED	= "menuActionSelected";
	private static final String	ACTION_DESELECTED	= "menuActionDeselected";
	//la label e' cio' che andra' scritto sopra il menu
	private String label;
	
	private MenuElementContainer<? extends AbstractMenuElement> container;
	
	@ViewField(visible = AnnotationConstants.FALSE)
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@ViewField(visible = AnnotationConstants.FALSE)
	public MenuElementContainer<? extends AbstractMenuElement> getContainer() {
		return container;
	}
	
	public void setContainer(MenuElementContainer<AbstractMenuElement> container) {
		this.container = container;
	}
	
	@ViewAction(render = "rendermenuelementlink")
	public void action(){
		Roma.flow().clearHistory();
		if(container!=null){
			container.setCurrent(this);
		}
		Roma.setFeature(this, ViewClassFeatures.STYLE, ACTION_SELECTED);
		execute();
	}

	@ViewAction(visible = AnnotationConstants.FALSE)
	public abstract void execute();

	public void onShow() {
		Roma.setFeature(this, "action", ViewActionFeatures.LABEL, label);
	}
	
	@ViewAction(visible = AnnotationConstants.FALSE)
	public void clear() {
		Roma.setFeature(this, ViewClassFeatures.STYLE, ACTION_DESELECTED);
	}

	public void onDispose() {
	}

}