package org.romaframework.scaffolding.view.domain;


import java.util.HashMap;
import java.util.Map;

import org.romaframework.aspect.core.annotation.CoreClass;
import org.romaframework.aspect.validation.CustomValidation;
import org.romaframework.aspect.validation.ValidationException;
import org.romaframework.aspect.view.screen.config.ScreenManager;
import org.romaframework.core.Roma;
import org.romaframework.module.users.UsersAuthentication;
import org.romaframework.module.users.domain.BaseAccount;
import org.romaframework.module.users.view.domain.AccountManagementUtility;
import org.romaframework.module.users.view.domain.ChangePassword;
import org.romaframework.module.users.view.domain.Login;
import org.romaframework.scaffolding.LoginListener;

@CoreClass(orderFields = "userName userPassword domain languages")
public class CustomLogin extends Login implements CustomValidation {

	public CustomLogin() {
		super();
		this.setListener(new LoginListener());
	}

	public void forgotPassword() {
		Roma.flow().popup(new PasswordRecoveryPanel());
	}

	@Override
	public void setUserName(String userName) {
		if (userName != null) {
			userName = userName.replaceAll("<", "");
			userName = userName.replaceAll(">", "");
			userName = userName.replaceAll("\"", "");
		}
		super.setUserName(userName);
	}

	@Override
	public void setUserPassword(String userPassword) {
		if (userPassword != null) {
			userPassword = userPassword.replaceAll("<", "");
			userPassword = userPassword.replaceAll(">", "");
			userPassword = userPassword.replaceAll("\"", "");
		}
		super.setUserPassword(userPassword);
	}

	@Override
	public void onSuccess() {
		Roma.view().setScreen(Roma.component(ScreenManager.class).getScreen("main-screen2"));
		super.onSuccess();
	}

	@Override
	protected void onError(Throwable t) {

		super.onError(t);
	}

	public void validate() {
		if (this.getUserName() == null || this.getUserName().trim().length() == 0) {
			throw new ValidationException(this, "userName", "");
		}
		if (this.getUserPassword() == null || this.getUserPassword().trim().length() == 0) {
			throw new ValidationException(this, "userPassword", "");
		}
	}

	@Override
	public void login() {
		if (getUserName() == null || getUserName().length() == 0) {
			return;
		}

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(UsersAuthentication.PAR_ALGORITHM, getAlgorithm());

			BaseAccount account = (BaseAccount) authManager.authenticate(getUserName(), getUserPassword(), params);
			if (AccountManagementUtility.isPasswordExpired(account)) {
				showChangePassword();
			} else {
				if (account != null && account.isChangePasswordNextLogin() != null && account.isChangePasswordNextLogin().booleanValue())
					showChangePassword();
				else
					onSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
			onError(e);
		}
	}

	private void showChangePassword() {
		ChangePassword changePw = new ChangePassword((BaseAccount) authManager.getCurrentAccount(), this);
		changePw.setOldPassword(getUserPassword());
		Roma.flow().popup(changePw);
	}

}
