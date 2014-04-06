package org.romaframework.scaffolding;


import org.romaframework.core.Roma;
import org.romaframework.module.users.listener.DefaultLoginListener;
import org.romaframework.scaffolding.view.domain.MainPage;

public class LoginListener extends DefaultLoginListener {

	@Override
	public void onSuccess() {
		openScenarioPanel();
	}

	private void openScenarioPanel() {
		Roma.flow().forward(new MainPage());
	}

}
