package org.romaframework.scaffolding.view.domain;

import org.romaframework.aspect.view.ViewCallback;
import org.romaframework.core.Roma;

public class MainPage implements ViewCallback {

	public void logout() {
		Roma.session().logout();
	}

	@Override
	public void onShow() {

	}

	@Override
	public void onDispose() {

	}
}
