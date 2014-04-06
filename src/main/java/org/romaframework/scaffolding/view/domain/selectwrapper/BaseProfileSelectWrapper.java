package org.romaframework.scaffolding.view.domain.selectwrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.romaframework.core.Roma;
import org.romaframework.frontend.domain.wrapper.SelectWrapper;
import org.romaframework.module.users.domain.BaseProfile;
import org.romaframework.module.users.repository.BaseProfileRepository;

public class BaseProfileSelectWrapper<T> extends SelectWrapper<T> {

	public BaseProfileSelectWrapper() {
		super();
	}

	public BaseProfileSelectWrapper(Class<T> iClass, Object iObject, String iSelectionField, boolean iLazy) {
		super(iClass, iObject, iSelectionField, iLazy);
	}

	public BaseProfileSelectWrapper(Class<T> iClass, Object iObject, String iSelectionField) {
		super(iClass, iObject, iSelectionField);
	}

	public BaseProfileSelectWrapper(Class<T> iClass) {
		super(iClass);
	}

	public BaseProfileSelectWrapper(Set<T> iSetValues, Object iObject, String iSelectionField, boolean iAutoSelection) {
		super(iSetValues, iObject, iSelectionField, iAutoSelection);
	}

	public BaseProfileSelectWrapper(Set<T> iSetValues, Object iObject, String iSelectionField) {
		super(iSetValues, iObject, iSelectionField);
	}

	public BaseProfileSelectWrapper(String iClassName, Object iObject, String iSelectionField, boolean iLazy) {
		super(iClassName, iObject, iSelectionField, iLazy);
	}

	public BaseProfileSelectWrapper(T[] iArrayValues, Object iObject, String iSelectionField, boolean iAutoSelection) {
		super(iArrayValues, iObject, iSelectionField, iAutoSelection);
	}

	public BaseProfileSelectWrapper(T[] iArrayValues, Object iObject, String iSelectionField) {
		super(iArrayValues, iObject, iSelectionField);
	}

	protected void load() {
		List<BaseProfile> allProfiles = Roma.component(BaseProfileRepository.class).getAll();
		list = (List) filterSubtree(allProfiles);
	}

	private List<BaseProfile> filterSubtree(List<BaseProfile> iBaseProfiles) {
		List<BaseProfile> result = new ArrayList<BaseProfile>();
		Object myProfile = Roma.session().getAccount().getProfile();
		if (iBaseProfiles != null) {
			for (BaseProfile b : iBaseProfiles) {
				BaseProfile item = b;
				while (item != null) {
					if (item.equals(myProfile)) {
						result.add(b);
						break;
					}
					item = item.getParent();
				}
			}
		}
		return result;
	}

}
