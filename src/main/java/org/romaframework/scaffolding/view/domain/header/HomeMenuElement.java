package org.romaframework.scaffolding.view.domain.header;

import org.romaframework.core.Roma;
import org.romaframework.module.users.domain.BaseAccount;

public class HomeMenuElement extends HeaderMenuElement {
	@Override
	public void execute() {
		HeaderMenuElement.removeConfirmRequired();
		BaseAccount currAccount = Roma.session().getAccount();
		Roma.flow().forward(currAccount.getProfile().getHomePage(), "screen://body");

	}

}
